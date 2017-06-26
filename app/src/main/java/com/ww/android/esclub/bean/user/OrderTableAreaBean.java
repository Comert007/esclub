package com.ww.android.esclub.bean.user;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderTableAreaBean implements Serializable{

    private static final long serialVersionUID = 6252399862987106256L;

    private String id;
    private String name;
    private TableBean table;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableBean getTable() {
        return table;
    }

    public void setTable(TableBean table) {
        this.table = table;
    }
}
