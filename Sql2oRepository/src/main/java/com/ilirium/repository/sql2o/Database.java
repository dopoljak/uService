package com.ilirium.repository.sql2o;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.flywaydb.core.Flyway;
import org.sql2o.Sql2o;

/**
 *
 * @author dpoljak
 */
public class Database {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
    private final Sql2o sql2o;

    private Database(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Sql2o getSql2o() {
        return sql2o;
    }

    public void flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(sql2o.getDataSource());
        flyway.migrate();
    }

    public void makeBackup() {
        try {
            String SQL = String.format("BACKUP TO 'h2_backup_%s.zip'", SIMPLE_DATE_FORMAT.format(new Date()));
            Connection connection = getSql2o().getDataSource().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
            close(statement, connection);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
