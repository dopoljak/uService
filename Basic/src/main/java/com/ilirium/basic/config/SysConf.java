package com.ilirium.basic.config;

public class SysConf extends Conf {

    public static final String appPath = "app.path";
    public static final String datasource = "db.datasource";
    public static final String databaseUrl = "db.url";
    public static final String databaseUsername = "db.username";
    public static final String databasePassword = "db.password";
    public static final String databaseMigrate = "db.migrate";
    public static final String port = "server.port";
    public static final String dumpRequest = "dumpRequest";

    public String getAppPath() {
        return config.getString(appPath);
    }

    public String getDatasource() {
        return config.getString(datasource);
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

    public Boolean getDatabaseMigrate() {
        return config.getBoolean(databaseMigrate);
    }

    public Integer getPort() {
        return config.getInt(port);
    }

    public Boolean getDumpRequest() {
        return config.getBoolean(dumpRequest);
    }
}
