package com.paulok777.model.util;

public class Pageable {
    private int currentPage;
    private int numberOfPages;

    public Pageable(int currentPage, int numberOfPages) {
        this.currentPage = currentPage;
        this.numberOfPages = numberOfPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
