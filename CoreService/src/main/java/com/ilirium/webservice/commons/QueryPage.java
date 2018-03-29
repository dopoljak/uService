package com.ilirium.webservice.commons;

import javax.ws.rs.QueryParam;

public class QueryPage {

    @QueryParam(value = "offset")
    private Integer offset;

    @QueryParam(value = "limit")
    private Integer limit;

    @QueryParam(value = "page")
    Integer page;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
