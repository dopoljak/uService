package com.ilirium.repository.sql2o.repository.commons;

public class Pagination {

    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    private final long limit;
    private final long offset;

    Pagination(long limit, long offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public long limit() {
        return limit;
    }

    public long offset() {
        return offset;
    }

    public static Pagination createDefault() {
        return new Pagination(10L, 0L);
    }

    public static Pagination create(long limit, long offset) {
        return new Pagination(limit, offset);
    }

}