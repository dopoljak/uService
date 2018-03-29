package com.ilirium.uservice.undertow;

import com.ilirium.database.commons.FlywayMigrationInvoker;
import com.ilirium.database.commons.FlywayUtils;
import com.ilirium.uservice.undertowserver.commons.BenchmarkHandler;
import com.ilirium.uservice.undertowserver.commons.DatabaseConfig;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * @author DoDo
 */
public class UndertowServer {

    private DataSource dataSource;
    private int port;

    private UndertowServer(int port) {
        this.port = port;
    }

    public static UndertowServer createStarted(final ClassLoader classLoader, int port, DatabaseConfig dbConfig) throws Exception {


        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World");
                    }
                }).build();
        server.start();

        final String resourceNameDatasource = "java:" + dbConfig.getFullDatasourceName();

        final UndertowServer myServer = new UndertowServer(port);

        /*

        BenchmarkHandler.benchmark("Total server startup time", () -> {

            myServer.setTransactionTempFolders();

            BenchmarkHandler.benchmark("JNDI/JTA", () -> {

            });

            // TODO: refactor!
            if (dbConfig.isFlywayMigrate()) {
                BenchmarkHandler.benchmark("Flyway Migration", () -> {
                    myServer.migrate();
                });
            }

            BenchmarkHandler.benchmark("Start Listener", () -> {

            });

            BenchmarkHandler.benchmark("JaxRS App Deploy", () -> {

            });

            BenchmarkHandler.benchmark("Call resource", () -> {
                myServer.callTestResource();
            });

        });
        */

        return myServer;

    }

    private void setTransactionTempFolders() {
        String tmp = System.getProperty("java.io.tmpdir");
        System.setProperty("com.arjuna.ats.arjuna.objectstore.objectStoreDir", tmp);
        System.setProperty("com.arjuna.ats.arjuna.common.ObjectStoreEnvironmentBean.objectStoreDir", tmp);
    }


    private void createDataSource(DatabaseConfig dbConfig) throws NamingException {
        JdbcDataSource ds = new JdbcDataSource();
        //"jdbc:h2:mem:test;MODE=Oracle;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
        //"jdbc:h2:file:./h2_database;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
        ds.setUrl(dbConfig.getUrl());
        ds.setUser(dbConfig.getUser());
        ds.setPassword(dbConfig.getPassword());
        this.dataSource = ds;
    }

    private void createDataSourceContext(String datasourceName) throws NamingException {
        final Context context = new InitialContext();
        try {
            context.createSubcontext("java:jboss");
            context.createSubcontext("java:jboss/datasources");
            context.bind(datasourceName, dataSource);
        } finally {
            context.close();
        }
    }

    private void callTestResource() throws Exception {
        /*
        final Client client = ClientBuilder.newClient();
        final String string = String.format("http://%s:%s/api/system/schema_version", "localhost", String.valueOf(port));
        final Response response = client.target(string).request().get();// .request(MediaType.APPLICATION_JSON_TYPE).get();
        final String responseString = response.readEntity(String.class);
        System.out.println("---------------");
        System.out.println(responseString);
        System.out.println("---------------");
        */
    }

    private void migrate() {
        FlywayUtils.flywayMigrate(dataSource);
    }

    public void stop() {

    }


}
