package com.ilirium.uservice.undertow;

import com.ilirium.database.flyway.migration.FlywayMigrator;
import com.ilirium.repository.sql2o.repository.commons.Sql2oSingleton;
import com.ilirium.uservice.undertow.commons.*;
import com.typesafe.config.Config;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.RequestDumpingHandler;
import org.sql2o.Sql2o;

import java.io.IOException;


/**
 * @author DoDo
 */
public abstract class UndertowServer {

    static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UndertowServer.class);

    static {
        TinyLogProvider.initialize();
    }

    protected abstract HttpHandler createHandlers();

    void createSingletons(BaseParameters config) {
        Sql2oSingleton.INSTANCE.set(new Sql2o(config.getDatabaseUrl(), config.getDatabaseUsername(), config.getDatabasePassword()));
    }

    void shutdownHook(Undertow server) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook received ...");
            LOGGER.info("Shutdown hook received ...");
            server.stop();
        }));
    }

    void callService() throws IOException {
        LOGGER.info(">> self colling rest service ...");
        //new RestClient().call("http://localhost:8080/api/endusers", null);
/*
        Retrofit retrofit = RestClientProvider.createRetrofit("http://localhost:8080/api/", GsonConverterFactory.create());

        ISystemService systemService = retrofit.create(ISystemService.class);

        Call<String> call = systemService.getSystem();
        Response<String> response = call.execute();

        System.out.println("" + response.code());
        System.out.println("" + response.isSuccessful());
        System.out.println("" + response.body());
*/
        LOGGER.info("<< self colling rest service ...");
    }


    public void start(Config config) throws Exception {
        StopWatch sw = StopWatch.start();

        LOGGER.info("Configuration parameters value : {}", config);

        String buildDate = ManifestReader.buildDate();
        LOGGER.info("Starting Undertow server, buildDate = '{}' ...", buildDate);
        System.out.println("Starting Undertow server buildDate = '" + buildDate + "' ...");

        BaseParameters baseParameters = new BaseParameters(config);

        // singletons
        createSingletons(baseParameters);

        // migrate flyway
        FlywayMigrator.migrate(Sql2oSingleton.INSTANCE.getSql2o().getDataSource());

        // http server
        Undertow server = Undertow.builder()
                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .addHttpListener(baseParameters.getPort(), "0.0.0.0")
                .setIoThreads(Runtime.getRuntime().availableProcessors() * 2)
                .setWorkerThreads(2)
                .setHandler(baseParameters.isDumpRequest() ? new RequestDumpingHandler(createHandlers()) : createHandlers())
                .build();
        server.start();

        // shutdown hook
        shutdownHook(server);

        // eager load rest service
        // TODO: it seems that server is not yet loaded at this point ! additional testing needed to conclude that
        // callService();

        System.out.println("Undertow server successfully started in '" + sw.getTotalExecutionMillis() + "ms'.");
        LOGGER.info("Undertow server successfully started in '{}ms'.", sw.getTotalExecutionMillis());
    }

}
