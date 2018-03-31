package com.ilirium.webservice.filter;

import com.google.common.base.Stopwatch;
import com.ilirium.webservice.generator.CorrelationIdGenerator;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Strings.isNullOrEmpty;

public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public static final String CORRELATION_ID = "CorrelationId";
    public static final String X_CORRELATION_ID = "X-Correlation-ID";
    public static final String NODE_ID = "NodeId";

    @Inject
    private Logger LOGGER;
    @Inject
    private CorrelationIdGenerator correlationIdGenerator;

    private Long nodeId = 0L;

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Stopwatch stopWatch = Stopwatch.createStarted();
        requestContext.setProperty("timer", stopWatch);

        HttpServletRequest httpRequest = (HttpServletRequest) requestContext.getRequest();
        resolveCorrelationId(httpRequest);

        LOGGER.info(">> REQ: Method = {}, Path = {}, Query = {}, IP = {}:{}",
                httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getQueryString(), httpRequest.getRemoteAddr(), httpRequest.getRemotePort());

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        Stopwatch stopWatch = (Stopwatch)requestContext.getProperty("timer");
        LOGGER.info("<< RES: Code = {}, status = {}, total execution time [{} ms]", responseContext.getStatus(), Status.fromStatusCode(responseContext.getStatus()), stopWatch.elapsed(TimeUnit.MILLISECONDS));

    }

    void resolveCorrelationId(HttpServletRequest request) {

        if (currentRequestIsAsyncDispatcher(request)) {
            LOGGER.debug("Request is ASYNC");
        }

        String correlationId = request.getHeader(CORRELATION_ID);
        if (isNullOrEmpty(correlationId)) {
            correlationId = nextCorrelationId();
            String xCorrelationId = request.getHeader(X_CORRELATION_ID);
            if (!isNullOrEmpty(xCorrelationId)) {
                correlationId = new StringBuilder(correlationId).append("-").append(xCorrelationId).toString();
            }
            MDC.put(CORRELATION_ID, correlationId);
            LOGGER.info("No correlationId found in Header. Generated new = '{}'", correlationId);
        } else {
            MDC.put(CORRELATION_ID, correlationId);
            LOGGER.info("Found correlationId in Header = '{}'", correlationId);
        }
    }

    private boolean currentRequestIsAsyncDispatcher(HttpServletRequest request) {
        return request.getDispatcherType().equals(DispatcherType.ASYNC);
    }

    private String nextCorrelationId() {
        // TODO resolve nodeId somehow
        return correlationIdGenerator.getNextId(nodeId);
    }

}
