package com.ilirium.webservice.exceptions;

import com.google.common.base.Throwables;
import com.ilirium.webservice.filters.LoggingFilter;
import org.slf4j.MDC;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author dpoljak
 */
@Provider
public class JaxRSExceptionProvider implements ExceptionMapper<Exception> {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JaxRSExceptionProvider.class);

    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(Exception e) {

        final MediaType mediaType = resolveMediaType();
        LOGGER.trace("MessageType = {}", mediaType);

        Status status = Status.INTERNAL_SERVER_ERROR;
        final ExceptionResponse dto = new ExceptionResponse();

        if (e instanceof AppException) {
            final AppException ex = (AppException) e;

            LOGGER.error("AppException:  http_code = {}, error_message = {}, error_extended_message = {}", ex.getExceptionEnum().getHttpStatusCode(), ex.getExceptionEnum().getEnumName(), ex.getDetails());

            status = Status.fromStatusCode(ex.getExceptionEnum().getHttpStatusCode());

            dto.error_message = ex.getExceptionEnum().getEnumName();
            //dto.error_extended_message = e.getMessage();
            dto.error_extended_message = Throwables.getStackTraceAsString(e);
        } else {
            LOGGER.error("Exception: ", e);
            //Status status = resolveStatusCode(e);
            dto.error_message = CommonException.INTERNAL_ERROR.getEnumName();
            dto.error_extended_message = Throwables.getStackTraceAsString(e);
        }

        dto.http_code = status.getStatusCode();
        dto.http_message = status.getReasonPhrase();
        dto.correlation_id = LoggingFilter.getCorrelationId();

        return Response.status(dto.http_code).entity(dto).type(mediaType).build();
    }

    /*
    private Status resolveStatusCode(Exception e) {
        Status status = Status.INTERNAL_SERVER_ERROR;
        if (e instanceof WebApplicationException) {
            status = Status.fromStatusCode(((WebApplicationException) e).getResponse().getStatus());
        }
        return status;
    }*/

    // DEFAULT IS JSON - otherwise XML if json is not defined!!
    private MediaType resolveMediaType() {
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        if (headers.getMediaType() != null && headers.getMediaType() == MediaType.APPLICATION_ATOM_XML_TYPE) {
            mediaType = headers.getMediaType();
        } else if (!headers.getAcceptableMediaTypes().isEmpty()) {
            for (final MediaType mt : headers.getAcceptableMediaTypes()) {
                if (mt == MediaType.APPLICATION_XML_TYPE || mt == MediaType.APPLICATION_JSON_TYPE) {
                    mediaType = mt;
                    break;
                }
            }
        }
        return mediaType;
    }
}
