package com.ilirium.webservice.filter;

import com.google.common.base.*;
import com.ilirium.basic.*;
import com.ilirium.webservice.generator.*;
import org.slf4j.*;

import javax.inject.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response.*;
import java.io.*;
import java.util.concurrent.*;

import static com.google.common.base.Strings.*;

public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    private Logger LOGGER;
    @Inject
    private CorrelationIdGenerator correlationIdGenerator;

    private Long nodeId = 0L;

    public static String getCorrelationId() {
        return MDC.get(Const.CORRELATION_ID);
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

        String correlationId = request.getHeader(Const.CORRELATION_ID);
        if (isNullOrEmpty(correlationId)) {
            correlationId = nextCorrelationId();
            String xCorrelationId = request.getHeader(Const.X_CORRELATION_ID);
            if (!isNullOrEmpty(xCorrelationId)) {
                correlationId = new StringBuilder(correlationId).append("-").append(xCorrelationId).toString();
            }
            MDC.put(Const.CORRELATION_ID, correlationId);
            LOGGER.info("No correlationId found in Header. Generated new = '{}'", correlationId);
        } else {
            MDC.put(Const.CORRELATION_ID, correlationId);
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
