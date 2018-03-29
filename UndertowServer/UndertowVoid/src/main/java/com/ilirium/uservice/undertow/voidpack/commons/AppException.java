package com.ilirium.uService.exampleservicejar.light.commons;

public class AppException extends RuntimeException {

    private final ExceptionEnum exceptionCode;
    private String exceptionMessage;
    private String correlationId;
    private final Integer httpStatusCode;

    public AppException(ExceptionEnum exceptionCode) {
        this.exceptionCode = exceptionCode;
        this.httpStatusCode = exceptionCode.httpStatusCode;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public ExceptionEnum getExceptionCode() {
        return exceptionCode;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

}
