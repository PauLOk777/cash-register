package com.paulok777.model.util;

import java.util.List;

public class Page<T> {
    private int totalPages;
    List<T> content;

    public Page(int totalPages, List<T> content) {
        this.totalPages = totalPages;
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
