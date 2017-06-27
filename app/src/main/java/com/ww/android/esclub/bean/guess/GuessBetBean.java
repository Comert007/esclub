package com.ww.android.esclub.bean.guess;

/**
 * Created by feng on 2017/6/27.
 */

public class GuessBetBean {

    /**
     * id : 1
     * option : 1
     * point : 1
     * profit : 10
     * created : 2017-06-23 20:22:22
     */

    private String id;
    private String option;
    private String point;
    private String profit;
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
