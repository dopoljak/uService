package com.ilirium.uService.exampleservicejar;

import com.ilirium.uService.exampleservicejar.initialization.Activator;
import com.ilirium.uservice.undertow.full.UndertowServer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class MainServer {

    public static void main(String... args) throws Exception {

        /*
        final int httpPort = 8080;
        final Config config = new Config.Builder()
                .url("jdbc:h2:file:./h2_database;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .flywayMigrate(true)
                .fullDatasourceName("jboss/datasources/AppDataSource")
                .pass("sa")
                .user("sa")
                .port(8080)
                .build();
        */
        /*
        final DatabaseConfig dbConfig = new DatabaseConfig.DatabaseConfigBuilder()
                .setFlywayMigrate(true)
                .setFullDatasourceName("jboss/datasources/AppDataSource")
                .setPassword("sa")
                .setUser("sa")
                .setUrl("jdbc:h2:file:./h2_database;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .createDatabaseConfig();
        */

        Config config = ConfigFactory.load();
        UndertowServer server = UndertowServer.createStarted(MainServer.class.getClassLoader(), Activator.class, config);
    }
}
