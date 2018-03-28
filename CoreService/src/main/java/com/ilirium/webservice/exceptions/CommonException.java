package com.ilirium.webservice.exceptions;

import static javax.ws.rs.core.Response.Status;

public enum CommonException implements IExceptionEnum {

    OK(Status.OK.getStatusCode()),
    INTERNAL_ERROR(Status.INTERNAL_SERVER_ERROR.getStatusCode()),
    ENDUSER_ID_MUST_BE_NULL(Status.BAD_REQUEST.getStatusCode());

    private final Integer httpStatusCode;

    CommonException(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getEnumName() {
        return this.name();
    }

    @Override
    public Integer getHttpStatusCode() {
        return null;
    }
}
