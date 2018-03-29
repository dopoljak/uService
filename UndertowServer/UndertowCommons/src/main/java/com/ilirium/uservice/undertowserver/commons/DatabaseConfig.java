package com.ilirium.uservice.undertowserver.commons;

public class DatabaseConfig {

    private String fullDatasourceName;
    private boolean flywayMigrate;
    private String url;
    private String user;
    private String password;

    public String getFullDatasourceName() {
        return fullDatasourceName;
    }

    public boolean isFlywayMigrate() {
        return flywayMigrate;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public DatabaseConfig(String fullDatasourceName, boolean flywayMigrate, String url, String user, String password) {
        this.fullDatasourceName = fullDatasourceName;
        this.flywayMigrate = flywayMigrate;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static class DatabaseConfigBuilder {

        private String fullDatasourceName;
        private boolean flywayMigrate;
        private String url;
        private String user;
        private String password;

        public DatabaseConfigBuilder setFullDatasourceName(String fullDatasourceName) {
            this.fullDatasourceName = fullDatasourceName;
            return this;
        }

        public DatabaseConfigBuilder setFlywayMigrate(boolean flywayMigrate) {
            this.flywayMigrate = flywayMigrate;
            return this;
        }

        public DatabaseConfigBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public DatabaseConfigBuilder setUser(String user) {
            this.user = user;
            return this;
        }

        public DatabaseConfigBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public DatabaseConfig createDatabaseConfig() {
            return new DatabaseConfig(fullDatasourceName, flywayMigrate, url, user, password);
        }
    }
}
