package com.ilirium.uservice.undertowserver.commons;

public class Config {

    private String fullDatasourceName;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private Integer port;
    private boolean flywayMigrate;
    private boolean dumpRequest;

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

    public boolean isDumpRequest() {
        return dumpRequest;
    }

    public static final class Builder {

        String url;
        String user;
        String pass;
        int port;
        boolean flywayMigrate;
        String fullDatasourceName;
        private boolean dumpRequest = false;

        public Builder dumpRequest() {
            this.dumpRequest = true;
            return this;
        }

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
            config.dumpRequest = dumpRequest;
            return config;
        }
    }

    @Override
    public String toString() {
        return "Config{" +
                "fullDatasourceName='" + fullDatasourceName + '\'' +
                ", databaseUrl='" + databaseUrl + '\'' +
                ", databaseUsername='" + databaseUsername + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", port=" + port +
                ", flywayMigrate=" + flywayMigrate +
                ", dumpRequest=" + dumpRequest +
                '}';
    }
}
