package com.ilirium.uService.exampleservicejar.light.commons.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ilirium.uService.exampleservicejar.light.commons.AppException;

import java.lang.reflect.Type;

/**
 *
 * @author DoDo
 */
public class AppExceptionGSONSerializier implements JsonSerializer<AppException> {

    @Override
    public JsonElement serialize(AppException src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("exceptionCode", src.getExceptionCode().name());
        obj.addProperty("exceptionMessage", src.getExceptionMessage());
        obj.addProperty("correlationId", src.getCorrelationId());
        obj.addProperty("httpStatusCode", src.getHttpStatusCode());
        return obj;
    }

}
