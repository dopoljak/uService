package com.ilirium.uservice.undertow.voidpack;

import com.ilirium.database.commons.FlywayUtils;
import com.ilirium.uservice.undertowserver.commons.BenchmarkHandler;
import com.ilirium.uservice.undertowserver.commons.Config;
import com.ilirium.webservice.filter.LoggingFilter;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import org.jnp.server.NamingBeanImpl;
import org.h2.jdbcx.JdbcDataSource;
import com.arjuna.ats.jta.utils.JNDIManager;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;


/**
 * @author DoDo
 */
public class UndertowServer {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();
    private DataSource dataSource;
    private int port;

    private UndertowServer(int port) {
        this.port = port;
    }

    public static UndertowServer createStarted(final ClassLoader classLoader, Class<? extends Application> applicationClass, Config config) throws Exception {

        final String resourceNameDatasource = "java:" + config.getFullDatasourceName();

        final UndertowServer myServer = new UndertowServer(config.getPort());

        BenchmarkHandler.benchmark("Total server startup time", () -> {

            myServer.setTransactionTempFolders();

            BenchmarkHandler.benchmark("JNDI/JTA", () -> {
                myServer.createJndiServer();
                myServer.createDataSource(config);
                myServer.createDataSourceContext(resourceNameDatasource);
                JNDIManager.bindJTAImplementation();
            });

            // TODO: refactor!
            if (config.isFlywayMigrate()) {
                BenchmarkHandler.benchmark("Flyway Migration", () -> {
                    myServer.migrate();
                });
            }

            BenchmarkHandler.benchmark("Start Listener", () -> {
                myServer.startListener("0.0.0.0");
            });
            BenchmarkHandler.benchmark("JaxRS App Deploy", () -> {
                DeploymentInfo deploymentInfo = myServer.deployApplication("/api", applicationClass)
                        .addWelcomePage("index.html")
                        .setResourceManager(new ClassPathResourceManager(classLoader, "static"))
                        .setClassLoader(classLoader)
                        .setContextPath("/")
                        .setDeploymentName("MyApplication")
                        .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class))
                        //.addListeners(Servlets.listener(BackgroundJobs.class))
                        // TODO: maybe implement SCAN filter classes ?
                        .addFilter(Servlets.filter("LoggingFilter", LoggingFilter.class))
                        .addFilterUrlMapping("LoggingFilter", "*", DispatcherType.REQUEST)
                        .addInitParameter("NodeId", String.valueOf("1"));

                myServer.deploy(deploymentInfo);
            });
            BenchmarkHandler.benchmark("Call resource", () -> {
                myServer.callTestResource();
            });

        });

        return myServer;
    }

    private void setTransactionTempFolders() {
        String tmp = System.getProperty("java.io.tmpdir");
        System.setProperty("com.arjuna.ats.arjuna.objectstore.objectStoreDir", tmp);
        System.setProperty("com.arjuna.ats.arjuna.common.ObjectStoreEnvironmentBean.objectStoreDir", tmp);
    }

    private void startListener(String host) {
        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
        server.start(serverBuilder);
    }

    private DeploymentInfo deployApplication(String appPath, Class<? extends Application> applicationClass) {
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setInjectorFactoryClass(org.jboss.resteasy.cdi.CdiInjectorFactory.class.getName());
        deployment.setApplicationClass(applicationClass.getName());
        return server.undertowDeployment(deployment, appPath);
    }

    private void deploy(DeploymentInfo deploymentInfo) throws ServletException {
        server.deploy(deploymentInfo);
    }

    private void createDataSource(Config dbConfig) throws NamingException {
        JdbcDataSource ds = new JdbcDataSource();
        //"jdbc:h2:mem:test;MODE=Oracle;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
        //"jdbc:h2:file:./h2_database;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
        ds.setUrl(dbConfig.getDatabaseUrl());
        ds.setUser(dbConfig.getDatabaseUsername());
        ds.setPassword(dbConfig.getDatabasePassword());
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
        final Client client = ClientBuilder.newClient();
        final String string = String.format("http://%s:%s/api/system/schema_version", "localhost", String.valueOf(port));
        final Response response = client.target(string).request().get();// .request(MediaType.APPLICATION_JSON_TYPE).get();
        final String responseString = response.readEntity(String.class);
        System.out.println("---------------");
        System.out.println(responseString);
        System.out.println("---------------");
    }

    private void createJndiServer() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        final NamingBeanImpl namingBean = new NamingBeanImpl();
        namingBean.start();
    }


    private void migrate() {
        FlywayUtils.flywayMigrate(dataSource);
    }

    public void stop() {
        server.stop();
    }


}
