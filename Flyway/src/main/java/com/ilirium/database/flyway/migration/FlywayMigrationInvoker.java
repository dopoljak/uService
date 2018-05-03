package com.ilirium.database.flyway.migration;

import javax.inject.*;
import javax.servlet.*;
import javax.servlet.annotation.*;

@WebListener
public class FlywayMigrationInvoker implements ServletContextListener {

    @Inject
    private FlywayMigrator flywayMigrator;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        flywayMigrator.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
