package com.ww.android.esclub.bean.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2017/6/26.
 */

public class TableAreaBean implements Serializable {
    private static final long serialVersionUID = -520658100611468623L;

    private String id;
    private String name;
    private List<TableBean> tables;

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

    public List<TableBean> getTables() {
        return tables;
    }

    public void setTables(List<TableBean> tables) {
        this.tables = tables;
    }

}
