package com.ilirium.uservice.undertow.commons;

import com.google.gson.GsonBuilder;
import com.ilirium.uservice.undertow.commons.gson.AppExceptionGSONSerializier;

/**
 * @author DoDo
 */
public class Converter {

    private static final com.google.gson.Gson GSON;

    static {
        GSON = new GsonBuilder()
                //.setExclusionStrategies(new AnnotationExclusionStrategy())
                .registerTypeAdapter(AppException.class, new AppExceptionGSONSerializier())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .serializeNulls()
                .create();
    }

    private Converter() {
    }

    public String toJson(Object object) {
        return GSON.toJson(object);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static Converter get() {
        return INSTANCE;
    }

    private static final Converter INSTANCE = new Converter();
}
