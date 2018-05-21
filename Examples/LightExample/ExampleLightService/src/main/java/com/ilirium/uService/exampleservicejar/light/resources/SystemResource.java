package com.ilirium.uService.exampleservicejar.light.resources;

import com.ilirium.uservice.undertow.commons.SystemInfo;
import com.ilirium.uservice.undertow.base.AbstractResponseResource;
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
