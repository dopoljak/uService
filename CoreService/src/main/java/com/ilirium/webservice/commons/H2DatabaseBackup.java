package com.ilirium.webservice.commons;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

//import static com.ilirium.database.commons.DBMigrationInvoker.RESOURCE_NAME_DATASOURCE;

/**
 * @author dpoljak
 */
@ApplicationScoped
public class H2DatabaseBackup {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    //@Resource(mappedName = RESOURCE_NAME_DATASOURCE)
    //@Resource(mappedName = RESOURCE_NAME_DATASOURCE)
    //private DataSource dataSource;

    public void makeBackupH2(DataSource dataSource) {
        try {
            String SQL = String.format("BACKUP TO 'database_backup\\h2_backup_%s.zip'", SIMPLE_DATE_FORMAT.format(new Date()));
            Connection connection = dataSource.getConnection();
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
}
