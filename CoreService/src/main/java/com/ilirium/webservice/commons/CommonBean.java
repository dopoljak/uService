package com.ilirium.webservice.commons;

import javax.ws.rs.QueryParam;

/**
 * @author dpoljak
 */
public class CommonBean {

    @QueryParam(value = "offset")
    //@DefaultValue("0")
            Integer offset;

    @QueryParam(value = "limit")
    //@DefaultValue("50")
            Integer limit;

    @QueryParam(value = "page")
    Integer page;

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getPage() {
        return page;
    }

}
