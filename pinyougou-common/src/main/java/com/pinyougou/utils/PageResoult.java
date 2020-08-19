package com.pinyougou.utils;

import java.io.Serializable;
import java.util.List;

public class PageResoult<T> implements Serializable {
    private long total;
    private List<T> rows;

    public PageResoult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResoult() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
