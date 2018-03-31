package com.ilirium.webservice.exceptions;

import com.ilirium.webservice.filter.LoggingFilter;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JaxRSExceptionProvider implements ExceptionMapper<Exception> {

    @Context
    private HttpHeaders httpHeaders;
    @Inject
    private Logger LOGGER;

    @Override
    public Response toResponse(Exception e) {

        ExceptionResponse exceptionResponse = constructResponse(e);
        return Response.status(exceptionResponse.getHttp_code()).entity(e).type(resolveMediaType()).build();

    }

    private ExceptionResponse constructResponse(Exception e) {

        ExceptionResponse exceptionResponse = null;
        try {
            if (e instanceof AppException) {
                AppException appException = (AppException) e;
                LOGGER.error("Responding with exception", appException.toString());
                exceptionResponse = new ExceptionResponse(appException);
            } else {
                exceptionResponse = new ExceptionResponse();
                Status status = Status.INTERNAL_SERVER_ERROR;
                if (e instanceof WebApplicationException) {
                    status = Status.fromStatusCode(((WebApplicationException) e).getResponse().getStatus());
                }
                exceptionResponse.setHttp_code(status.getStatusCode());
                exceptionResponse.setHttp_message(status.getReasonPhrase());
            }
        } catch (Exception ex) {
            exceptionResponse = new ExceptionResponse();
            Status internalErrorStatus = Status.INTERNAL_SERVER_ERROR;
            exceptionResponse.setHttp_code(internalErrorStatus.getStatusCode());
            exceptionResponse.setHttp_message(internalErrorStatus.getReasonPhrase());
        }

        exceptionResponse.setCorrelation_id(LoggingFilter.getCorrelationId());

        return exceptionResponse;

    }

    // DEFAULT IS JSON - otherwise XML if json is not defined!!
    private MediaType resolveMediaType() {
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        if (httpHeaders.getMediaType() != null && httpHeaders.getMediaType() == MediaType.APPLICATION_ATOM_XML_TYPE) {
            mediaType = httpHeaders.getMediaType();
        } else if (!httpHeaders.getAcceptableMediaTypes().isEmpty()) {
            for (final MediaType mt : httpHeaders.getAcceptableMediaTypes()) {
                if (mt == MediaType.APPLICATION_XML_TYPE || mt == MediaType.APPLICATION_JSON_TYPE) {
                    mediaType = mt;
                    break;
                }
            }
        }
        return mediaType;
    }
}
