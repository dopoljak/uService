package com.ilirium.webservice.exceptions;

public class AppException extends RuntimeException {

    private IExceptionEnum exceptionEnum;
    private String httpMessage;
    private String details;

    public AppException( IExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public AppException(  IExceptionEnum exceptionEnum, String details) {
        this.exceptionEnum = exceptionEnum;
        this.details = details;
    }


    public String getDetails() {
        return details;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    public void setHttpMessage(String httpMessage) {
        this.httpMessage = httpMessage;
    }

    public IExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }


}
