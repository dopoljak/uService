package com.ilirium.database.request;

/**
 *
 * @author dpoljak
 */
public class PageRequest {

    private static final int OFFSET_DEFAULT = 0;
    private static final int OFFSET_MIN = 1;
    private static final int LIMIT_DEFAULT = 50;
    private static final int LIMIT_MIN = 0;
    private static final int LIMIT_MAX = 100;
    private static final int PAGE_DEFAULT = 0;
    private static final int PAGE_MIN = 0;

    private Integer offset;
    private Integer limit;
    private Integer page;

    public Integer getLimit() {
        return limit;
    }

    public int getCalculatedOffset() {
        if (isNullOrZero(page)) {
            return offset;
        }
        return (page - 1) * limit;
    }

    static boolean isNullOrZero(Integer value) {
        return value == null || value == 0;
    }

    static int defaultIfNull(Integer nullAbleInteger, Integer defaultValue) {
        return nullAbleInteger == null ? defaultValue : nullAbleInteger;
    }

    public static PageRequest defaultIfWrongConf(final Integer offset, final Integer limit, final Integer page) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.limit = defaultIfNull(limit, LIMIT_DEFAULT);
        pageRequest.offset = defaultIfNull(offset, OFFSET_DEFAULT);
        pageRequest.page = defaultIfNull(page, PAGE_DEFAULT);

        if (pageRequest.limit > LIMIT_MAX) {
            //LOGGER.info("Requested page limit '{}' is greater than max '{}'. Setting limit to max '{}'.", pageRequest.limit, LIMIT_MAX, LIMIT_MAX);
            pageRequest.limit = LIMIT_MAX;
        }
        if (pageRequest.limit < LIMIT_MIN) {
            //LOGGER.info("Requested page limit '{}' is lower than min '{}'. Setting limit to default '{}'.", pageRequest.limit, LIMIT_MIN, LIMIT_DEFAULT);
            pageRequest.limit = LIMIT_DEFAULT;
        }
        if (pageRequest.page < PAGE_MIN) {
            //LOGGER.info("Requested page '{}' is lower than min '{}'. Setting page to default '{}'.", pageRequest.page, PAGE_MIN, PAGE_DEFAULT);
            pageRequest.page = PAGE_DEFAULT;
        }
        if (pageRequest.offset > pageRequest.limit) {
            //LOGGER.info("Requested offset '{}' is greater than page limit '{}'. Setting offset to default '{}'.", pageRequest.offset, pageRequest.limit, OFFSET_DEFAULT);
            pageRequest.offset = OFFSET_DEFAULT;
        }
        if (pageRequest.offset < OFFSET_MIN) {
            //LOGGER.info("Requested offset '{}' is lower than min '{}'. Setting offset to default '{}'.", pageRequest.offset, OFFSET_MIN, OFFSET_DEFAULT);
            pageRequest.offset = OFFSET_DEFAULT;
        }

        return pageRequest;
    }
}
