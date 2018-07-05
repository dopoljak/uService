package com.ilirium.webservice.filters;

import com.google.common.base.Stopwatch;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Strings.isNullOrEmpty;

@WebFilter(urlPatterns = "/*", description = "LoggingFilter", displayName = "LoggingFilter", filterName = "LoggingFilter")
public class LoggingFilter implements Filter {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(LoggingFilter.class);
    private static final CorrelationIdGenerator IDENTITY_GENERATOR = new CorrelationIdGenerator();
    public static final String CORRELATION_ID = "CorrelationId";
    public static final String X_CORRELATION_ID = "X-Correlation-ID";
    public static final String NODE_ID = "NodeId";
    private long node_id = 0;

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String parametarNodeId = filterConfig.getInitParameter(NODE_ID);
        if (parametarNodeId != null && !parametarNodeId.isEmpty()) {
            node_id = Long.valueOf(parametarNodeId);
        }
        LOGGER.info("Initializing LogginfFilter : Resolved nodeId = '{}'", node_id);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final Stopwatch stopWatch = Stopwatch.createStarted();

        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        resolveCorrelationId(httpRequest);

        LOGGER.info(">> REQ: Method = {}, Path = {}, Query = {}, IP = {}:{}", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getQueryString(), httpRequest.getRemoteAddr(), httpRequest.getRemotePort());

        if(httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            LOGGER.info("OPTIONS REQUEST, setting status to 200 ....");
            addCorsHeader(httpResponse);
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        LOGGER.info("<< RES: Code = {}, status = {}, total execution time [{} ms]", httpResponse.getStatus(), Status.fromStatusCode(httpResponse.getStatus()), stopWatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private void addCorsHeader(HttpServletResponse response){
        //TODO: externalize the Allow-Origin
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "-1");
    }

    void resolveCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID);
        if (!currentRequestIsAsyncDispatcher(request)) {
            if (isNullOrEmpty(correlationId)) {
                String xCorrelationId = request.getHeader(X_CORRELATION_ID);
                if (isNullOrEmpty(xCorrelationId)) {
                    correlationId = nextCorrelationId();
                } else {
                    correlationId = new StringBuilder(nextCorrelationId()).append("-").append(xCorrelationId).toString();
                }
                MDC.put(CORRELATION_ID, correlationId);
                LOGGER.info("No correlationId found in Header. Generated new = '{}'", correlationId);
            } else {
                MDC.put(CORRELATION_ID, correlationId);
                LOGGER.info("Found correlationId in Header = '{}'", correlationId);
            }
        } else {
            LOGGER.debug("Request is ASYNC");
        }
    }

    boolean currentRequestIsAsyncDispatcher(HttpServletRequest request) {
        return request.getDispatcherType().equals(DispatcherType.ASYNC);
    }

    String nextCorrelationId() {
        return IDENTITY_GENERATOR.getNextId(node_id);
    }

}
