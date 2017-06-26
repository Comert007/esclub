package com.ww.android.esclub.bean.user;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderCentreBean implements Serializable{

    private static final long serialVersionUID = -5980154750155875792L;
    /**
     * id : 1
     * title : 鸡尾酒等5件商品
     * cover : http://
     * order_no : JDGdsa12
     * type_num : 5
     * price : 100
     * point : 100
     * pay_type : 10
     * created : 2017-06-23 12:00:00
     * status : 2
     */

    private String id;
    private String title;
    private String cover;
    private String order_no;
    private String type_num;
    private String price;
    private String point;
    private String pay_type;
    private String created;
    private String status;
    private OrderTableAreaBean table_area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getType_num() {
        return type_num;
    }

    public void setType_num(String type_num) {
        this.type_num = type_num;
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

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderTableAreaBean getTable_area() {
        return table_area;
    }

    public void setTable_area(OrderTableAreaBean table_area) {
        this.table_area = table_area;
    }
}
