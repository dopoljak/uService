package com.ilirium.uservice.undertowserver.commons;

public class Config {

    //static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Config.class);

    private String fullDatasourceName;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private Integer port;
    private boolean flywayMigrate;



    /*
    public static Config create() {
        Config config = new Config();
        config.defaults();
        config.resolveHttpPort();
        return config;
    }

    void defaults() {
        databaseUrl = "jdbc:h2:file:./h2_database";
        databaseUsername = "sa";
        databasePassword = "sa";
        port = 8080;
    }*/

    /*
    void resolveHttpPort() throws NumberFormatException {
        if (System.getProperty("port") != null && !System.getProperty("port").isEmpty()) {
            port = Integer.valueOf(System.getProperty("port"));
        }
        System.out.println("Resolved HTTP PORT = " + port);
        LOGGER.info("Resolved HTTP PORT = {}", port);
    }*/

    public int getPort() {
        return port;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public boolean isFlywayMigrate() {
        return flywayMigrate;
    }

    public String getFullDatasourceName() {
        return fullDatasourceName;
    }

    public static final class Builder {

        String url;
        String user;
        String pass;
        int port;
        boolean flywayMigrate;
        String fullDatasourceName;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder fullDatasourceName(String fullDatasourceName) {
            this.fullDatasourceName = fullDatasourceName;
            return this;
        }

        public Builder flywayMigrate(boolean flywayMigrate) {
            this.flywayMigrate = flywayMigrate;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder pass(String pass) {
            this.pass = pass;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Config build() {
            Config config = new Config();
            config.databasePassword = pass;
            config.databaseUrl = url;
            config.databaseUsername = user;
            config.port = port;
            config.flywayMigrate = flywayMigrate;
            config.fullDatasourceName = fullDatasourceName;
            return config;
        }
    }
}
