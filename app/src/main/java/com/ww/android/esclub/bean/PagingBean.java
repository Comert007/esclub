package com.ww.android.esclub.bean;

/**
 * Created by feng on 2017/6/23.
 */

public class PagingBean {

    /**
     * total_page : 1
     * current_page : 1
     * perpage : 5
     * total_items : 2
     */

    private String total_page;
    private String current_page;
    private String perpage;
    private String total_items;

    public String getTotal_page() {
        return total_page;
    }

    public void setTotal_page(String total_page) {
        this.total_page = total_page;
    }

    public String getCurrent_page() {
        return current_page;

    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public String getTotal_items() {
        return total_items;
    }

    public void setTotal_items(String total_items) {
        this.total_items = total_items;
    }

    @Override
    public String toString() {
        return "PagingBean{" +
                "total_page='" + total_page + '\'' +
                ", current_page=" + current_page +
                ", perpage='" + perpage + '\'' +
                ", total_items='" + total_items + '\'' +
                '}';
    }
}
