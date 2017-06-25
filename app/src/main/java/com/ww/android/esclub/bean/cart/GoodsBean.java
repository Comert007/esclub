package com.ww.android.esclub.bean.cart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2017/6/25.
 */

public class GoodsBean implements Serializable{

    private static final long serialVersionUID = 3921205804168391411L;
    /**
     * id : -1
     * name : 热销
     */

    private String id;
    private String name;
    private List<GoodsItem> goods;

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

    public List<GoodsItem> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsItem> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", goods=" + goods +
                '}';
    }
}
