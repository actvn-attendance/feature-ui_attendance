package com.example.attendanceqrcode.modelapi;

import java.util.List;

public class Notifications {
    private int page;
    private int size;
    private int total_elements;
    private int total_pages;
    private boolean last;
    private List<Data> data;

    public Notifications(int page, int size, int total_elements, int total_pages, boolean last, List<Data> data) {
        this.page = page;
        this.size = size;
        this.total_elements = total_elements;
        this.total_pages = total_pages;
        this.last = last;
        this.data = data;
    }

    public Notifications() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal_elements() {
        return total_elements;
    }

    public void setTotal_elements(int total_elements) {
        this.total_elements = total_elements;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
