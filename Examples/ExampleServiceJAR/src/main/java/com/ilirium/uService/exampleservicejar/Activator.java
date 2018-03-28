package com.ilirium.uService.exampleservicejar;

import com.ilirium.webservice.resources.SystemResource;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Activator extends com.ilirium.webservice.commons.JaxRsActivator {

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
        return resources;
    }
}