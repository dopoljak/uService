package com.ilirium.webservice.commons;

import com.ilirium.database.resources.RepositorySystemResource;
import com.ilirium.webservice.exceptions.JaxRSExceptionProvider;
import com.ilirium.webservice.filters.CORSFilter;
import com.ilirium.webservice.resources.CoreSystemResource;
import io.swagger.jaxrs.config.BeanConfig;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

//@ApplicationPath("/api")
public abstract class JaxRsActivator extends Application {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JaxRsActivator.class);

    @Inject
    private AppConfiguration appConfiguration;

    //@Inject private BackgroundJobs backgroundJobs;

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CoreSystemResource.class);
        classes.add(CoreSystemResource.class);
        classes.add(RepositorySystemResource.class);
        classes.add(CORSFilter.class);
        classes.add(ObjectMapperContextResolver.class);
        classes.add(JaxRSExceptionProvider.class);
        // add swagger classes
        classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        Set<Class<?>> additionalResources = getResources();
        LOGGER.info("Additional Resources = {}", additionalResources);
        classes.addAll(additionalResources);
        return classes;
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info(">> JaxRsActivator:postConstruct()");

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("uService");
        beanConfig.setVersion("2.0");
        //beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setSchemes(new String[]{"https"});
        beanConfig.setHost(appConfiguration.getSwaggerHost());
        beanConfig.setBasePath(appConfiguration.getSwaggerBasePath());
        //beanConfig.setResourcePackage("com.ilirium.webservice.resources;" + getResourcePackage());
        beanConfig.setResourcePackage(getResourcePackage());
        beanConfig.setScan(false);

        LOGGER.info("SwaggerHost     = {}", appConfiguration.getSwaggerHost());
        LOGGER.info("SwaggerBasePath = {}", appConfiguration.getSwaggerBasePath());

        LOGGER.info("<< JaxRsActivator:postConstruct()");
    }

    public abstract String getResourcePackage();

    public abstract Set<Class<?>> getResources();
}
