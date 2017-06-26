package com.ww.android.esclub.bean.pay;

import java.util.Map;

/**
 * Created by feng on 2017/6/26.
 */

public class WechatPayBean {

    /**
     * id : 1
     * order_no : ADSA1EWs12
     * payment_no : e10adc3949ba59abbe56e057f20f883e
     * price : 3.00
     * payment_info : {"appid":"wx2b6fdd0294e54036","partnerid":"1402478202",
     * "prepayid":"wx2016102716191788a8145edc0149582412","package":"Sign=WXPay",
     * "noncestr":"Z7H440wgmZ96Pbpq","timestamp":"1477556330",
     * "sign":"F0C27849A2DB675973A9C6E1A35734D3"}
     */

    private String id;
    private String order_no;
    private String payment_no;
    private String price;
    private Map<String, String> payment_info;

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

    public Map<String, String> getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(Map<String, String> payment_info) {
        this.payment_info = payment_info;
    }
}
