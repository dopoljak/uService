package com.ilirium.webservice.exceptions;

import javax.ws.rs.core.Response;

public class AppException extends RuntimeException implements InternalException {

    private Integer code;
    private String message;
    private String description;

    public AppException(Integer code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public AppException(Throwable cause) {
        this.code = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        this.message = cause.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getHttpStatusCode() {
        return getCode();
    }

    @Override
    public String toString() {
        return "AppException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
