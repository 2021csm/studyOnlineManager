package com.example.study.util;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private Long totoal;
    private List<T> rows;

    public PageResult(long totoal, List<T> rows) {
        this.totoal = totoal;
        this.rows = rows;
    }

    public long getTotoal() {
        return totoal;
    }

    public void setTotoal(long totoal) {
        this.totoal = totoal;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
