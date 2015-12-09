package com.kinesio.web.response;

/**
 * Created by mlischetti on 12/8/15.
 */
public class Paging {
    private Integer offset;
    private Integer limit;
    private Long total;

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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
