package com.ilirium.webservice.commons;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author dpoljak
 */
@ApplicationScoped
public class AppConfiguration {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AppConfiguration.class);
    private static final String PROP_FILE_NAME = "app.properties";

    private Properties properties;

    @PostConstruct
    public void readConfig() {
        LOGGER.info(">> readConfig()");

        Optional<String> location = resolvePropertiesLocation();
        LOGGER.info("Resolved properties location = {}", location);

        if (location.isPresent()) {
            properties = loadFromFile(location.get());
        } else {
            properties = loadFromJar();
        }

        LOGGER.info("Loaded properties = {}", properties);
        LOGGER.info("<< readConfig()");
    }

    Properties loadFromFile(String location) {
        Properties p = new Properties();
        LOGGER.info("Loading from DISK location = {}", location);
        try (InputStream fis = new FileInputStream(location)) {
            p.load(fis);
        } catch (Exception e) {
            LOGGER.error("Loading configuration from Configuration file : ", e);
        }
        return p;
    }

    Properties loadFromJar() {
        Properties p = new Properties();
        String jarPropLocation = "config/" + PROP_FILE_NAME;
        LOGGER.info("Loading from JAR File = {}", jarPropLocation);
        try (InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jarPropLocation)) {
            p.load(resourceStream);
        } catch (Exception e) {
            LOGGER.error("Loading configuration from JAR file : ", e);
        }
        return p;
    }

    Optional<String> resolvePropertiesLocation() {
        String resolved;
        String jbossHome = System.getenv("JBOSS_HOME");
        LOGGER.info("jbossHome = {}", jbossHome);
        if (!isNullOrEmpty(jbossHome)) {
            LOGGER.info("Detected 'JBOSS_HOME' variable, loading from JBOSS location.");
            resolved = jbossHome + "/standalone/configuration/" + PROP_FILE_NAME;
        } else {
            resolved = "./" + PROP_FILE_NAME;
        }
        if (Files.exists(Paths.get(resolved))) {
            return Optional.of(resolved);
        }
        return Optional.empty();
    }

    public String getSwaggerHost() {
        String value = properties.getProperty("swagger.host");
        if (isNullOrEmpty(value)) {
            return "localhost:8080";
        }
        return value;
    }

    public String getSwaggerBasePath() {
        String value = properties.getProperty("swagger.basePath");
        if (isNullOrEmpty(value)) {
            return "/api";
        }
        return value;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
