package com.ilirium.uService.exampleservicejar.light;

import com.ilirium.uService.exampleservicejar.light.resources.DateNowResource;
import com.ilirium.uService.exampleservicejar.light.resources.EndUserResource;
import com.ilirium.uService.exampleservicejar.light.resources.NowResource;
import com.ilirium.uService.exampleservicejar.light.resources.SystemResource;
import com.ilirium.uservice.undertow.voidpack.UndertowServer;
import com.ilirium.uservice.undertowserver.commons.Config;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;

public class MainServer extends UndertowServer {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MainServer.class);

    @Override
    protected HttpHandler createHandlers() {
        RoutingHandler handler = Handlers.routing();
        handler.get("api/now", new NowResource());
        handler.get("api/date-now", DateNowResource::handle);
        handler.get("api/system", new SystemResource());
        handler.get("api/endusers", new EndUserResource());
        handler.get("/", Handlers.resource(
                new ClassPathResourceManager(MainServer.class.getClassLoader(), "webapp"))
                .addWelcomeFiles("index.html")
                .setDirectoryListingEnabled(true));
        return handler;
    }

    public static void main(String[] args) throws Exception {
        Config config = new Config.Builder()
                .pass("sa")
                .user("sa")
                .url("jdbc:h2:file:./h2_database")
                .port(8080)
                .build();
        MainServer mainServer = new MainServer();
        mainServer.start(config);
    }
}
