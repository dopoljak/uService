package com.ilirium.uservice.undertow.voidpack.base;

public enum Mime {

    APPLICATION_JSON ("application/json"),
    TEXT_PLAIN("text/plain");

    private String value;

    Mime(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
