package com.ilirium.uService.exampleservicejar;

import com.ilirium.uService.exampleservicejar.initialization.*;
import com.ilirium.uservice.undertow.*;
import com.typesafe.config.*;

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
//        new MicroServer(sysConf).createDeployment("api", Activator.class, MainServer.class.getClassLoader());
        MicroServerFactory.createStarted(MainServer.class.getClassLoader(), Activator.class, config);
    }
}
