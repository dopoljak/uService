package com.ilirium.webservice.commons;

import com.ilirium.basic.*;
import org.slf4j.*;

import javax.annotation.*;
import javax.enterprise.context.*;
import javax.inject.*;
import java.nio.file.*;
import java.util.*;

import static com.google.common.base.Strings.*;

@ApplicationScoped
public class AppConfiguration {

    private static final String PROP_FILE_NAME = "app.properties";

    @Inject
    private Logger LOGGER;
    @Inject
    private ConfigUtils configUtils;

    private Properties properties;

    @PostConstruct
    public void readConfig() {
        LOGGER.info(">> readConfig()");

        Optional<String> location = resolvePropertiesLocation();
        LOGGER.info("Resolved properties location = {}", location);

        if (location.isPresent()) {
            properties = configUtils.loadFromFile(location.get());
        } else {
            location = Optional.of("config/" + PROP_FILE_NAME);
            properties = configUtils.loadFromJar(location.get());
        }

        LOGGER.info("Loaded properties = {}", properties);
        LOGGER.info("<< readConfig()");
    }

    private Optional<String> resolvePropertiesLocation() {
        String resolvedLocation = null;
        String jbossHome = System.getenv("JBOSS_HOME");
        LOGGER.info("jbossHome = {}", jbossHome);
        if (!Cond.isNullOrEmpty(jbossHome)) {
            LOGGER.info("Detected 'JBOSS_HOME' variable, loading from JBOSS location.");
            resolvedLocation = jbossHome + "/standalone/configuration/" + PROP_FILE_NAME;
        } else {
            resolvedLocation = "./" + PROP_FILE_NAME;
        }
        LOGGER.info("Resolved configuration location: {}", resolvedLocation);
        if (Files.exists(Paths.get(resolvedLocation))) {
            return Optional.of(resolvedLocation);
        }
        return Optional.empty();
    }

    public String getSwaggerHost() {
        String value = get("swagger.host");
        if (isNullOrEmpty(value)) {
            return "localhost:8080";
        }
        return value;
    }

    public String getSwaggerBasePath() {
        String value = get("swagger.basePath");
        if (isNullOrEmpty(value)) {
            return "/api";
        }
        return value;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
