package com.ww.android.esclub.bean.pay;

/**
 * Created by feng on 2017/6/26.
 */

public class AlipayBean {

    /**
     * id : 1
     * order_no : ADSA1EWs12
     * payment_no : e10adc3949ba59abbe56e057f20f883e
     * price : 1.00
     * payment_info : 3231432115dsafwrwqre2r32
     */

    private String id;
    private String order_no;
    private String payment_no;
    private String price;
    private String payment_info;

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

    public String getPayment_no() {
        return payment_no;
    }

    public void setPayment_no(String payment_no) {
        this.payment_no = payment_no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(String payment_info) {
        this.payment_info = payment_info;
    }
}
