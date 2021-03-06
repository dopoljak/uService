package com.ilirium.database.commons;

import org.flywaydb.core.Flyway;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class FlywayMigrationInvoker implements ServletContextListener {

    //public static final String JBOSS_FULL_DATASOURCE = "jboss/datasources/AppDataSource";
    //public static final String RESOURCE_NAME_DATASOURCE = "java:" + JBOSS_FULL_DATASOURCE;

    //@Resource(mappedName = RESOURCE_NAME_DATASOURCE)
    // TODO: test on wildfly without default name!!!, refactor if needed
    @Resource
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FlywayUtils.flywayMigrate(dataSource);
    }



    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
