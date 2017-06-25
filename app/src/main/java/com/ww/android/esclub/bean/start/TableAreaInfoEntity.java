package com.ww.android.esclub.bean.start;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/25.
 */

public class TableAreaInfoEntity implements Serializable{


    private static final long serialVersionUID = -1538555430123455504L;
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

    @Override
    public String toString() {
        return "TableAreaInfoEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
