package com.ww.android.esclub.bean.start;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/25.
 */

public class WebViewInfoBean implements Serializable{

    private static final long serialVersionUID = -7683431227088159147L;
    /**
     * contact_us : http://
     * about_us : http://
     * terms_of_use : http://
     * guess_rules : http://
     * recharge_explain : http://
     * book_table_explain : http://
     */

    private String contact_us;
    private String about_us;
    private String terms_of_use;
    private String guess_rules;
    private String recharge_explain;
    private String book_table_explain;

    public String getContact_us() {
        return contact_us;
    }

    public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getTerms_of_use() {
        return terms_of_use;
    }

    public void setTerms_of_use(String terms_of_use) {
        this.terms_of_use = terms_of_use;
    }

    public String getGuess_rules() {
        return guess_rules;
    }

    public void setGuess_rules(String guess_rules) {
        this.guess_rules = guess_rules;
    }

    public String getRecharge_explain() {
        return recharge_explain;
    }

    public void setRecharge_explain(String recharge_explain) {
        this.recharge_explain = recharge_explain;
    }

    public String getBook_table_explain() {
        return book_table_explain;
    }

    public void setBook_table_explain(String book_table_explain) {
        this.book_table_explain = book_table_explain;
    }

    @Override
    public String toString() {
        return "WebViewInfoBean{" +
                "contact_us='" + contact_us + '\'' +
                ", about_us='" + about_us + '\'' +
                ", terms_of_use='" + terms_of_use + '\'' +
                ", guess_rules='" + guess_rules + '\'' +
                ", recharge_explain='" + recharge_explain + '\'' +
                ", book_table_explain='" + book_table_explain + '\'' +
                '}';
    }
}
