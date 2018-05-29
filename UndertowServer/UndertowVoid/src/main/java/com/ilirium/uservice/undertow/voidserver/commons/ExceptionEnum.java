package com.ilirium.uservice.undertow.voidserver.commons;

import io.undertow.util.StatusCodes;

public enum ExceptionEnum {

    OK(StatusCodes.OK),
    INTERNAL_ERROR(StatusCodes.INTERNAL_SERVER_ERROR),
    ENDUSER_ID_MUST_BE_NULL(StatusCodes.BAD_REQUEST),
    REQUEST_OBJECT_MUST_BE_SET(StatusCodes.BAD_REQUEST),
    FCM_TOKEN_MUST_BE_SET(StatusCodes.BAD_REQUEST),
    DEVICE_WITH_PROVIDED_FCM_TOKEN_ALREADY_EXIST_IN_DATABASE(StatusCodes.CONFLICT),;

    final int httpStatusCode;

    private ExceptionEnum(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

}
