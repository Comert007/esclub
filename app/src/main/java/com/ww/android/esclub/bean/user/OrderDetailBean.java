package com.ww.android.esclub.bean.user;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderDetailBean implements Serializable {
    private static final long serialVersionUID = -8945485565390155734L;

    private String id;
    private OrderDetailGoodsBean goods;
    private String num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderDetailGoodsBean getGoods() {
        return goods;
    }

    public void setGoods(OrderDetailGoodsBean goods) {
        this.goods = goods;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
