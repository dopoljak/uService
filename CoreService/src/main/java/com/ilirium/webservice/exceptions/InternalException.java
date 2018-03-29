package com.ilirium.webservice.exceptions;

import javax.ws.rs.core.Response;

public interface InternalException {

    Integer getHttpStatusCode();

    String getMessage();

    String getDescription();

    default InternalException fromResponseStatus(Response.Status responseStatus) {
        return new InternalException() {
            @Override
            public String getMessage() {
                return responseStatus.name();
            }

            @Override
            public Integer getHttpStatusCode() {
                return responseStatus.getStatusCode();
            }

            @Override
            public String getDescription() {
                return responseStatus.getReasonPhrase();
            }
        };
    }

}
