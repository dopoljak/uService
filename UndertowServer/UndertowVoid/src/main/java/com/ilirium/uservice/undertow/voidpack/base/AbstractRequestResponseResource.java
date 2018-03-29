package com.ilirium.uService.exampleservicejar.light.resources.commons;

import com.ilirium.repository.sql2o.repository.commons.Pagination;
import com.ilirium.uService.exampleservicejar.light.commons.*;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import java.lang.reflect.ParameterizedType;
import java.util.Deque;
import java.util.Map;

/**
 *
 * @author dpoljak
 */
public abstract class AbstractRequestResponseResource<REQUESTDTO> implements HttpHandler {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AbstractRequestResponseResource.class);
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";
    private final Class<REQUESTDTO> clazz;

    public AbstractRequestResponseResource() {
        this.clazz = requestClass();
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }

        try {
            StopWatch sw = StopWatch.start();
            resolveCorrelationId(exchange);
            
            LOGGER.info(">> REQ: {}", exchange.getRequestURL());
            LOGGER.debug("Instance HashCode = {}", this.hashCode());

            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, APPLICATION_JSON);

            if (exchange.getRequestMethod().equals(Methods.POST)) {
                exchange.getRequestReceiver().receiveFullString((HttpServerExchange exchange2, String message) -> {
                    REQUESTDTO requestDTO = Converter.getInstance().fromJson(message, clazz);
                    exchange2.getResponseSender().send(Converter.getInstance().toJson(handle(exchange2, requestDTO)));
                });
            } else {
                exchange.getResponseSender().send(Converter.getInstance().toJson(handle(exchange, null)));
            }

            LOGGER.info("<< RES: {}, executed in '{}ms'", exchange.getResolvedPath(), sw.getTotalTimeMillis());
        } catch (Exception e) {
            LOGGER.error("AbstractJsonResource : ", e);
            sendError(e, exchange);
        }
    }

    public void sendError(Exception e, HttpServerExchange exchange) {
        try {
            AppException appException;
            if (e instanceof AppException) {
                appException = (AppException) e;
            } else {
                appException = new AppException(ExceptionEnum.INTERNAL_ERROR);
                appException.setExceptionMessage(e.getMessage());
            }
            appException.setCorrelationId(IdentityGenerator.getCorrelationId());

            // TODO: send exception only of channel is available!!!
            if (exchange.isResponseChannelAvailable()) {
                LOGGER.info("Response channel is available");
            } else {
                LOGGER.info("Response channel is not available !!!! ");
            }
            String json = Converter.getInstance().toJson(appException);
            LOGGER.error("Error in JSON = {}", json);
            exchange.setStatusCode(appException.getHttpStatusCode());
            exchange.getResponseSender().send(json);
        } catch (Exception ex) {
            LOGGER.error("ERROR WHILE SENDING AppException response : ", ex);
        }
    }

    String resolveCorrelationId(HttpServerExchange exchange) throws IllegalArgumentException {
        if (exchange.getRequestHeaders().contains(IdentityGenerator.CORRELATION_ID)) {
            HeaderValues correlationId = exchange.getRequestHeaders().get(IdentityGenerator.CORRELATION_ID);
            String cid = correlationId.getFirst();
            IdentityGenerator.setCorrelationId(cid);
            return cid;
        } else {
            return IdentityGenerator.resolveCorrelationId();
        }
    }

    protected Pagination pagination(Map<String, Deque<String>> queryParameters) {
        Pagination paging;
        if (queryParameters.containsKey(Pagination.LIMIT) && queryParameters.containsKey(Pagination.OFFSET)) {
            long limit = Long.valueOf(queryParameters.get(Pagination.LIMIT).getFirst());
            long offset = Long.valueOf(queryParameters.get(Pagination.OFFSET).getFirst());
            paging = Pagination.create(limit, offset);
            LOGGER.info("Parsed Query string : Limit = {}, Offset = {}", paging.limit(), paging.offset());
        } else {
            paging = Pagination.createDefault();
            LOGGER.info("Using default : Limit = {}, Offset = {}", paging.limit(), paging.offset());
        }
        return paging;
    }

    private Class<REQUESTDTO> requestClass() {
        try {
            final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            return (Class<REQUESTDTO>) genericSuperclass.getActualTypeArguments()[0];
        } catch (Exception ex) {
        }
        return null;
    }

    public abstract Object handle(HttpServerExchange exchange, REQUESTDTO requestDTO);

}
