package com.ww.android.esclub.bean.start;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/25.
 */

public class GoodTypeInfoBean implements Serializable{

    private static final long serialVersionUID = 9169295312170912968L;
    /**
     * id : 1
     * name : 红酒
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
        return "GoodTypeInfoBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
