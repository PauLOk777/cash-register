package com.paulok777.model.util;

public class Pageable {
    private int currentPage;
    private int sizeOfPage;

    public Pageable(int currentPage, int sizeOfPage) {
        this.currentPage = currentPage;
        this.sizeOfPage = sizeOfPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSizeOfPage() {
        return sizeOfPage;
    }

    public void setSizeOfPage(int sizeOfPage) {
        this.sizeOfPage = sizeOfPage;
    }
}
