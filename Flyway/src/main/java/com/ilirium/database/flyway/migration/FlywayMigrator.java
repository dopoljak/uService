package com.ilirium.database.flyway.migration;

import com.ilirium.basic.db.*;
import org.flywaydb.core.*;
import org.slf4j.*;

import javax.annotation.*;
import javax.inject.*;
import javax.sql.*;
import java.sql.*;

public class FlywayMigrator implements DatabaseMigrator {

    @Inject
    private Logger LOGGER;

    //public static final String JBOSS_FULL_DATASOURCE = "jboss/datasources/AppDataSource";
    //public static final String RESOURCE_NAME_DATASOURCE = "java:" + JBOSS_FULL_DATASOURCE;
    // TODO: test on wildfly without default name!!!, refactor if needed
    @Resource
    private DataSource dataSource;

    public void migrate() {
        migrate(dataSource);
    }

    public void migrate(DataSource dataSource) {
        try {
            LOGGER.info("Starting Flyway migration on {}", getSchemaName(dataSource));
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.migrate();
        } catch (Exception e) {
            LOGGER.error("Error while executing Flyway migration!", e);
        }
    }

    private String getSchemaName(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            return connection.getSchema();
        } catch (Exception e) {
            LOGGER.error("Error getting schema name!");
            return null;
        }
    }

}
