package com.ilirium.uService.exampleservicejar.light;

import com.ilirium.uService.exampleservicejar.light.resources.EndUserResource;
import com.ilirium.uService.exampleservicejar.light.resources.NowResource;
import com.ilirium.uService.exampleservicejar.light.resources.SystemResource;
import com.ilirium.uservice.undertow.voidpack.UndertowServer;
import com.ilirium.uservice.undertowserver.commons.Config;
import io.undertow.Handlers;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;

public class MainServer extends UndertowServer {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MainServer.class);

    @Override
    protected PathHandler createResources() {
        PathHandler pathHandler = new PathHandler();//(new BaseResource());
        pathHandler.addPrefixPath("api/now", new NowResource());
        pathHandler.addPrefixPath("api/system", new SystemResource());
        pathHandler.addPrefixPath("api/endusers", new EndUserResource());
        pathHandler.addPrefixPath("/", Handlers.resource(
                new ClassPathResourceManager(MainServer.class.getClassLoader(), "webapp"))
                .addWelcomeFiles("index.html")
                .setDirectoryListingEnabled(true));
        return pathHandler;
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
