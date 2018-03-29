package com.ilirium.uService.exampleservicejar.light.resources;

import com.ilirium.uservice.undertow.voidpack.commons.SystemInfo;
import com.ilirium.uservice.undertow.voidpack.base.AbstractResponseResource;
import io.undertow.server.HttpServerExchange;

/**
 * @author DoDo
 */
public class SystemResource extends AbstractResponseResource {

    @Override
    public Object handle(HttpServerExchange exchange) {
        return SystemInfo.dump();
    }

}
