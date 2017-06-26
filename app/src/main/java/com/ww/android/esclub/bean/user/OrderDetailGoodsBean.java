package com.ww.android.esclub.bean.user;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderDetailGoodsBean implements Serializable {
    private static final long serialVersionUID = -202044173017254282L;


    /**
     * id : 1
     * name : 二锅头
     * cover : http://
     * price : 12
     * point : 12
     */

    private String id;
    private String name;
    private String cover;
    private String price;
    private String point;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
