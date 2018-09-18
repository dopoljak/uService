package com.ilirium.uservice.undertow;

import com.arjuna.ats.jta.utils.*;
import com.ilirium.basic.config.*;
import com.ilirium.uservice.undertowserver.commons.*;
import com.typesafe.config.*;
import io.undertow.servlet.api.*;

import javax.ws.rs.core.*;

public class MicroServerFactory {

    public static MicroServer createStarted(ClassLoader classLoader, Class<? extends Application> applicationClass, Config config) throws Exception {

        SysConf systemConfig = new SysConf();
        systemConfig.setConfig(config);

        MicroServer microServer = new MicroServer(systemConfig);
        setup(microServer, classLoader, applicationClass);

        return microServer;
    }

    private static void setup(MicroServer microServer, ClassLoader classLoader, Class<? extends Application> applicationClass) throws Exception{
        BenchmarkHandler.benchmark("Total server startup time", () -> {

            microServer.setTransactionTempFolders();

            BenchmarkHandler.benchmark("JNDI/JTA", () -> {
                microServer.createJndiServer();
                microServer.createDataSource();
                microServer.createDataSourceContext();
                JNDIManager.bindJTAImplementation();
            });

            BenchmarkHandler.benchmark("Database migration", () -> {
                microServer.migrate();
            });

            BenchmarkHandler.benchmark("Start Listener", () -> {
                microServer.startListener("0.0.0.0");
            });
            BenchmarkHandler.benchmark("JaxRS App Deploy", () -> {
                DeploymentInfo deploymentInfo = microServer.createDeployment("/api", applicationClass, classLoader);
                microServer.deploy(deploymentInfo);
            });
            BenchmarkHandler.benchmark("Call resource", () -> {
                microServer.callTestResource();
            });

        });
    }


}
