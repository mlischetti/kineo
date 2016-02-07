package com.kineo.web.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlischetti on 12/8/15.
 */
public class PaginationResponse<T> {

    private List<T> items = new ArrayList<>();

    private Paging paging;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public void addItem(T item) {
        this.items.add(item);
    }
}
