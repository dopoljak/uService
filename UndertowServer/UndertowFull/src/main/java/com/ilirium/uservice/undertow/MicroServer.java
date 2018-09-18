package com.ilirium.uservice.undertow;


import com.ilirium.basic.config.*;
import com.ilirium.basic.db.*;
import com.zaxxer.hikari.*;
import io.undertow.*;
import io.undertow.server.handlers.resource.*;
import io.undertow.servlet.*;
import io.undertow.servlet.api.*;
import org.jboss.resteasy.plugins.server.undertow.*;
import org.jboss.resteasy.spi.*;
import org.jnp.server.*;

import javax.enterprise.inject.spi.*;
import javax.naming.Context;
import javax.naming.*;
import javax.sql.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

public class MicroServer {

    private final UndertowJaxrsServer server = new UndertowJaxrsServer();

    private DataSource dataSource;
    private Integer port;

    private SysConf systemConf;

    public MicroServer(SysConf sysConf) {
        this.systemConf = sysConf;
        this.port = systemConf.getPort();
    }

    public void setTransactionTempFolders() {
        String tmp = System.getProperty("java.io.tmpdir");
        System.setProperty("com.arjuna.ats.arjuna.objectstore.objectStoreDir", tmp);
        System.setProperty("com.arjuna.ats.arjuna.common.ObjectStoreEnvironmentBean.objectStoreDir", tmp);
    }

    public void createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(systemConf.getDatabaseUrl());
        hikariConfig.setUsername(systemConf.getDatabaseUsername());
        hikariConfig.setPassword(systemConf.getDatabasePassword());

        this.dataSource = new HikariDataSource(hikariConfig);
    }

    public void createDataSourceContext() throws NamingException {
        final Context context = new InitialContext();
        try {
            context.createSubcontext("java:jboss");
            context.createSubcontext("java:jboss/datasources");
            context.bind(systemConf.getDatasource(), dataSource);
        } finally {
            context.close();
        }
    }

    public void createJndiServer() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        NamingBeanImpl namingBean = new NamingBeanImpl();
        namingBean.start();
    }

    public void startListener(String host) {
        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
        server.start(serverBuilder);
    }

    public DeploymentInfo createDeployment(String appPath, Class<? extends Application> applicationClass, ClassLoader classLoader) {
        ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
        resteasyDeployment.setInjectorFactoryClass(org.jboss.resteasy.cdi.CdiInjectorFactory.class.getName());
        resteasyDeployment.setApplicationClass(applicationClass.getName());

        DeploymentInfo deploymentInfo = server.undertowDeployment(resteasyDeployment, appPath);
        deploymentInfo.addWelcomePage("index.html");
        deploymentInfo.setResourceManager(new ClassPathResourceManager(classLoader, "static"));
        deploymentInfo.setClassLoader(classLoader);
        deploymentInfo.setContextPath("/");
        deploymentInfo.setDeploymentName("Application");
        deploymentInfo.addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
                // TODO .addListeners(Servlets.listener(BackgroundJobs.class));
        deploymentInfo.addInitParameter("NodeId", String.valueOf("1"));
        return deploymentInfo;
    }

    public void deploy(DeploymentInfo deploymentInfo) {
        server.deploy(deploymentInfo);
    }

    public void callTestResource() {
        Client client = ClientBuilder.newClient();
        String schemaEndpoint = String.format("http://%s:%s/api/system/schema_version", "localhost", String.valueOf(port));
        System.out.println("Making test call to: " + schemaEndpoint);
        Response response = client.target(schemaEndpoint).request().get();
        String responseString = response.readEntity(String.class);
        System.out.println("---------------");
        System.out.println("Schema response: " + responseString);
        System.out.println("---------------");
    }

    public void migrate() {
        if (systemConf.getDatabaseMigrate()) {
            DatabaseMigrator databaseMigrator = CDI.current().select(DatabaseMigrator.class).get();
            databaseMigrator.migrate();
        }
    }

    public void stop() {
        server.stop();
    }


}
