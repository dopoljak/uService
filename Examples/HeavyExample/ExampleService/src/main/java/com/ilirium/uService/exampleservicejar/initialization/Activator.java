package com.ilirium.uService.exampleservicejar.initialization;

import com.ilirium.uService.exampleservicejar.resource.EndUserResource;
import com.ilirium.uService.exampleservicejar.resource.HelloResource;
import com.typesafe.config.Config;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Activator extends com.ilirium.webservice.commons.JaxRsActivator {

    public static Config config;
    private static String swagger_host = "swagger.host";
    private static String swagger_base_path = "swagger.basePath";

    public Activator() {
    }

    @Override
    public String getResourcePackage() {
        return "com.ilirium.uService.exampleservicejar";
    }

    @Override
    public Set<Class<?>> getResources() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(HelloResource.class);
        resources.add(EndUserResource.class);
        return resources;
    }

    @Override
    public String getSwaggerHost() {
        return config.getString(swagger_host);
    }

    @Override
    public String getSwaggerBasePath() {
        return config.getString(swagger_base_path);
    }
}