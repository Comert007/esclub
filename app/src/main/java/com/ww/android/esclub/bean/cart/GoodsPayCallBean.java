package com.ww.android.esclub.bean.cart;

/**
 * Created by feng on 2017/6/26.
 */

public class GoodsPayCallBean {

    /**
     * id : 1
     * order_no : ADSA1EWs12
     * point : 300
     */

    private String id;
    private String order_no;
    private String point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "GoodsPayCallBean{" +
                "id='" + id + '\'' +
                ", order_no='" + order_no + '\'' +
                ", point='" + point + '\'' +
                '}';
    }
}
