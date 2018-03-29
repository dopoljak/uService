package com.ilirium.uservice.undertow.voidpack.base;

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
