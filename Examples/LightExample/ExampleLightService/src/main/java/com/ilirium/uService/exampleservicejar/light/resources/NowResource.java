package com.ilirium.uService.exampleservicejar.light.resources;

import com.ilirium.uservice.undertow.voidpack.base.AbstractResponseResource;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Methods;

/**
 *
 * @author dpoljak
 */
public class NowResource extends AbstractResponseResource {

    @Override
    public Object handle(HttpServerExchange exchange) {

        if (exchange.getRequestMethod().equals(Methods.GET)) {
        }

        return System.currentTimeMillis();
    }

}
