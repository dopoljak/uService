package com.ilirium.database.commons;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayUtils {

    public static void flywayMigrate(DataSource dataSource) {
        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            //flyway.setBaselineOnMigrate(true);
            flyway.migrate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
