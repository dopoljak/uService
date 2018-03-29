package com.ilirium.uService.exampleservicejar.light.resources.commons;

import io.undertow.server.HttpServerExchange;

/**
 *
 * @author DoDo
 */
public abstract class AbstractResponseResource extends AbstractRequestResponseResource {

    @Override
    public Object handle(HttpServerExchange exchange, Object requestDTO) {
        return handle(exchange);
    }

    public abstract Object handle(HttpServerExchange exchange);

}
