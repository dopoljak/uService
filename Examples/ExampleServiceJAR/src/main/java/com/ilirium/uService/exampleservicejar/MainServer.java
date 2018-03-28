package com.ilirium.uService.exampleservicejar;

import com.ilirium.uservice.undertow.UndertowServer;

public class MainServer {

    public static void main(String... args) throws Exception {
        UndertowServer server = UndertowServer.createStarted(MainServer.class.getClassLoader(), Activator.class, 8080);
    }
}
