package com.ilirium.repository.sql2o;


import org.sql2o.Sql2o;

/**
 * @author dpoljak
 */
public class Database {

    private final Sql2o sql2o;

    private Database(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Sql2o getSql2o() {
        return sql2o;
    }

    void close(AutoCloseable... closable) {
        for (AutoCloseable autoCloseable : closable) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        String url;
        String user;
        String pass;

        public Builder url(String url) {
            this.url = url;
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

        public Database build() {
            Database database = new Database(new Sql2o(url, user, pass));
            return database;
        }
    }

}
