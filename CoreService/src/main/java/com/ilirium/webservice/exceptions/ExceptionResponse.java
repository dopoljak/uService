package com.ilirium.webservice.exceptions;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"correlation_id", "http_code", "http_message"})
public class ExceptionResponse {

    private String correlation_id;
    private Integer http_code;
    private String http_message;
    //TODO stack trace data should never be sent
//    private String error_details;

    public ExceptionResponse() {}

    public ExceptionResponse(AppException appException) {
        this.http_code = appException.getHttpStatusCode();
        this.http_message = appException.getMessage();
//        this.error_details = Throwables.getStackTraceAsString(appException);
    }

    public String getCorrelation_id() {
        return correlation_id;
    }

    public void setCorrelation_id(String correlation_id) {
        this.correlation_id = correlation_id;
    }

    public Integer getHttp_code() {
        return http_code;
    }

    public void setHttp_code(Integer http_code) {
        this.http_code = http_code;
    }

    public String getHttp_message() {
        return http_message;
    }

    public void setHttp_message(String http_message) {
        this.http_message = http_message;
    }

//    public String getError_details() {
//        return error_details;
//    }
//
//    public void setError_details(String error_details) {
//        this.error_details = error_details;
//    }
}
