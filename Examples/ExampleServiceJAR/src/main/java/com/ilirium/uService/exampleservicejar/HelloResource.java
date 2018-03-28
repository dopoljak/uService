package com.ilirium.uService.exampleservicejar;

import io.swagger.annotations.Api;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


@Api(value = "/", tags = "HelloResource")
@Path("/hello")
@RequestScoped
public class HelloResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HelloResource.class);

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDefault() {
        LOGGER.info(">> getDefault()");

        final Map<String, Object> response = new HashMap<>();
        response.put("getenv", System.getenv());
        response.put("getProperties", System.getProperties());

        LOGGER.info("<< getDefault({})", response);
        return (response);
    }
}
