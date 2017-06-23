package com.ww.android.esclub;

import android.support.annotation.Nullable;

import com.ww.android.esclub.bean.PagingBean;

import java.util.List;

import butterknife.Optional;

/**
 * Created by feng on 2017/6/23.
 */

public class ListBean <T>{

    private PagingBean paging;
    @Nullable
    private List<T> banner;
    private List<T> items;

    public PagingBean getPaging() {
        return paging;
    }

    public void setPaging(PagingBean paging) {
        this.paging = paging;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Optional
    public List<T> getBanner() {
        return banner;
    }

    @Optional
    public void setBanner(List<T> banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "paging=" + paging +
                ", banner=" + banner +
                ", items=" + items +
                '}';
    }
}
