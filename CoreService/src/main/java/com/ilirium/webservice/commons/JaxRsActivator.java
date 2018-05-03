package com.ilirium.webservice.commons;

import io.swagger.jaxrs.config.*;
import org.reflections.*;
import org.slf4j.*;

import javax.annotation.*;
import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.util.*;

public abstract class JaxRsActivator extends Application {

    @Inject
    private Logger LOGGER;

    @Inject
    private AppConfiguration appConfiguration;

    //@Inject private BackgroundJobs backgroundJobs;

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        Reflections reflections = new Reflections("");
        Set resourceClassSet = reflections.getTypesAnnotatedWith(Path.class);
        Set providerClassSet = reflections.getTypesAnnotatedWith(Provider.class);
        classes.addAll(resourceClassSet);
        classes.addAll(providerClassSet);
        return classes;
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info(">> JaxRsActivator:postConstruct()");

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("uService");
        beanConfig.setVersion("2.0");
        beanConfig.setSchemes(new String[]{"https"});
        beanConfig.setHost(appConfiguration.getSwaggerHost());
        beanConfig.setBasePath(appConfiguration.getSwaggerBasePath());
        beanConfig.setResourcePackage(getResourcePackage());
        beanConfig.setScan(false);

        LOGGER.info("SwaggerHost     = {}", appConfiguration.getSwaggerHost());
        LOGGER.info("SwaggerBasePath = {}", appConfiguration.getSwaggerBasePath());

        LOGGER.info("<< JaxRsActivator:postConstruct()");
    }

    public abstract String getResourcePackage();

}
