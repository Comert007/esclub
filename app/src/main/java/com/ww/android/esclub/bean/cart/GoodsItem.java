package com.ww.android.esclub.bean.cart;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/25.
 */

public class GoodsItem implements Serializable{

    private static final long serialVersionUID = 2727301786427029286L;
    /**
     * id : 1
     * name : 红酒
     * cover : http://
     * price : 12.00
     * month_sale : 12
     */

    private String id;
    private String name;
    private String cover;
    private String price;
    private String month_sale;
    private String classifyId;
    private String classifyName;
    private int cartType = 1;
    private int num =0;
    private int position=-1;//商品位置

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

    public String getMonth_sale() {
        return month_sale;
    }

    public void setMonth_sale(String month_sale) {
        this.month_sale = month_sale;
    }

    public int getCartType() {
        return cartType;
    }

    public void setCartType(int cartType) {
        this.cartType = cartType;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
