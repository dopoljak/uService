package com.ilirium.uservice.undertowserver.commons;

import com.typesafe.config.Config;

public class BaseParameters {

    public static final String appPath = "appPath";
    public static final String fullDatasourceName = "fullDatasourceName";
    public static final String databaseUrl = "databaseUrl";
    public static final String databaseUsername = "databaseUsername";
    public static final String databasePassword = "databasePassword";
    public static final String port = "port";
    public static final String flywayMigrate = "flywayMigrate";
    public static final String dumpRequest = "dumpRequest";

    private Config config;

    public BaseParameters(Config config) {
        this.config = config;
    }

    public int getPort() {
        return config.getInt(port);
    }

    public String getDatabaseUrl() {
        return config.getString(databaseUrl);
    }

    public String getDatabaseUsername() {
        return config.getString(databaseUsername);
    }

    public String getDatabasePassword() {
        return config.getString(databasePassword);
    }

    public boolean isFlywayMigrate() {
        return config.getBoolean(flywayMigrate);
    }

    public String getAppPath() {
        return config.getString(appPath);
    }

    public String getFullDatasourceName() {
        return config.getString(fullDatasourceName);
    }

    public boolean isDumpRequest() {
        return config.getBoolean(dumpRequest);
    }

}
