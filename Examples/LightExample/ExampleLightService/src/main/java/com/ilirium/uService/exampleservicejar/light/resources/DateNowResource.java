package com.ilirium.uService.exampleservicejar.light.resources;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateNowResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DateNowResource.class);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss-SSS");

    public static void handle(HttpServerExchange exchange) {
        LOGGER.info(">> DateNowResource()");
        String dateNow = SIMPLE_DATE_FORMAT.format(new Date());
        LOGGER.info("dateNow = {}", dateNow);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send(dateNow);
        LOGGER.info("<< DateNowResource()");
    }
}
