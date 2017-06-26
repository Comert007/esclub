package com.ww.android.esclub.bean.user;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/26.
 */

public class TableBean implements Serializable{
    private static final long serialVersionUID = 1960538688647238507L;


    /**
     * id : 1
     * name : 黃金2桌
     */

    private String id;
    private String name;

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
}
