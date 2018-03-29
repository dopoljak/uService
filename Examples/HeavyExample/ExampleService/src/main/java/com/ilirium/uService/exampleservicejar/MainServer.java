package com.ilirium.uService.exampleservicejar;

import com.ilirium.uService.exampleservicejar.initialization.Activator;
import com.ilirium.uservice.undertow.UndertowServer;

public class MainServer {

    public static void main(String... args) throws Exception {
        final String jbossFullDatasource = "jboss/datasources/AppDataSource";
        final boolean flywayMigrate = true;
        final int httpPort = 8080;
        UndertowServer server = UndertowServer.createStarted(MainServer.class.getClassLoader(), Activator.class, httpPort, jbossFullDatasource, flywayMigrate);
    }
}
