package com.ilirium.basic;

import java.util.*;

public class Cond {


    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNullOrZero(Integer value) {
        return value == null || value == 0;
    }

    public static boolean isNotNull(Object object) {
        return object != null;
    }

    public static boolean isNotNullNorEmpty(Collection<?> objects) {
        return objects != null && !objects.isEmpty();
    }

    public static boolean isNotNullNorEmpty(Object[] objects) {
        return objects != null && objects.length > 0;
    }

    public static boolean isNotNullNorEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isNotNullNorEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isNotNullOrZero(Integer value) {
        return value != null && value != 0;
    }

    public static boolean containsSingleRecord(Collection<?> collection) {
        return collection != null && collection.size() == 1;
    }

}
