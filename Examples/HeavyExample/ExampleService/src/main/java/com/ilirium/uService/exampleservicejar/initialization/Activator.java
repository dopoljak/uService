package com.ilirium.uService.exampleservicejar.initialization;

import javax.ws.rs.*;

@ApplicationPath("/api")
public class Activator extends com.ilirium.webservice.commons.JaxRsActivator {

    public Activator() {
    }

    @Override
    public String getResourcePackage() {
        return "com.ilirium.uService.exampleservicejar";
    }
}