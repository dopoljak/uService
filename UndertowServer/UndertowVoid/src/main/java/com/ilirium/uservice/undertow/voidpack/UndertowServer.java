package com.ilirium.uservice.undertow.voidpack;

import com.ilirium.database.flyway.migration.FlywayMigrator;
import com.ilirium.repository.sql2o.repository.commons.Sql2oSingleton;
import com.ilirium.uservice.undertow.voidpack.commons.*;
import com.ilirium.uservice.undertowserver.commons.Config;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.handlers.PathHandler;
import org.sql2o.Sql2o;


/**
 * @author DoDo
 */
public abstract class UndertowServer {

    static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UndertowServer.class);

    static {
        TinyLogProvider.initialize();
    }

    protected abstract PathHandler createResources();


    /*
    PathHandler createResources() {
        PathHandler pathHandler = new PathHandler();//(new BaseResource());
        pathHandler.addPrefixPath("api/now", new NowResource());
        pathHandler.addPrefixPath("api/system", new SystemResource());
        pathHandler.addPrefixPath("api/endusers", new EndUserResource());
        pathHandler.addPrefixPath("/", Handlers.resource(new ClassPathResourceManager(MainServer.class.getClassLoader(), "webapp")).addWelcomeFiles("index.html").setDirectoryListingEnabled(true));
        return pathHandler;
    }*/

    void createSingletons(Config config) throws Exception {

        Singletons.setConfig(config);
        Sql2oSingleton.INSTANCE.set(new Sql2o(config.getDatabaseUrl(), config.getDatabaseUsername(), config.getDatabasePassword()));

        /*
        Singletons.setDatabase(Database.builder()
                .url(Singletons.getConfig().getDatabaseUrl())
                .user(Singletons.getConfig().getDatabaseUsername())
                .pass(Singletons.getConfig().getDatabasePassword())
                .build());
        */

        //Sql2oSingleton.INSTANCE.set(Singletons.getDatabase().getSql2o());

        //Singletons.getDatabase().flyway();

        //Singletons.setJobs(new BackgroundJobs(Executors.newScheduledThreadPool(2)));

//        Singletons.getJobs().start(BackgroundJobs.job()
//                .runnable(new UpdateExternalIpDnsRecord(Singletons.getConfig().getDuckDnsToken(), Singletons.getConfig().getDuckDnsDomain()))
//                .initialDelaySeconds(2)
//                .delaySeconds(10));

//        // DISABLE SENSOR ON DEV MACHINE
//        if (!checkIfStartedFromIDE()) {
//            initializeGpio();
//            Singletons.getJobs().start(BackgroundJobs.job()
//                    .runnable(new ResolveDataFromSensorsAndSendPush())
//                    .initialDelaySeconds(30)
//                    .delaySeconds(10));
//        }
    }

    void shutdownHook(Undertow server) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutdown hook received ...");
                LOGGER.info("Shutdown hook received ...");
                //Singletons.getJobs().stop();
                server.stop();
            }
        });
    }

    void callService() {
        LOGGER.info(">> self colling rest service ...");
        new RestClient().call("http://localhost:8080/api/endusers", null);
        LOGGER.info("<< self colling rest service ...");
    }


    public void start(Config config) throws Exception {
        StopWatch sw = StopWatch.start();

        LOGGER.info("### Parameters = {}", config.getDatabasePassword());
        LOGGER.info("### Parameters = {}", config.getDatabaseUrl());
        LOGGER.info("### Parameters = {}", config.getDatabaseUsername());
        LOGGER.info("### Parameters = {}", config.getPort());
        LOGGER.info("### Parameters = {}", config.getFullDatasourceName());

        String buildDate = ManifestReader.buildDate();
        LOGGER.info("Starting Undertow server, buildDate = '{}' ...", buildDate);
        System.out.println("Starting Undertow server buildDate = '" + buildDate + "' ...");
        //LOGGER.info("checkIfStartedFromIDE = {}", checkIfStartedFromIDE());

        // singletons
        createSingletons(config);

        // migrate flyway
        FlywayMigrator.migrate(Sql2oSingleton.INSTANCE.getSql2o().getDataSource());

        // http server
        Undertow server = Undertow.builder()
                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .addHttpListener(Singletons.getConfig().getPort(), "0.0.0.0")
                .setIoThreads(Runtime.getRuntime().availableProcessors() * 2)
                .setWorkerThreads(2)
                .setHandler(createResources())
                .build();
        server.start();

        // shutdown hook
        shutdownHook(server);

        // eager load rest service
        //callService();

        System.out.println("Undertow server successfully started in '" + sw.getTotalTimeMillis() + "ms'.");
        LOGGER.info("Undertow server successfully started in '{}ms'.", sw.getTotalTimeMillis());
    }

}
