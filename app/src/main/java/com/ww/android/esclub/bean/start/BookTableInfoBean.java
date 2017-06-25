package com.ww.android.esclub.bean.start;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/25.
 */

public class BookTableInfoBean implements Serializable{

    private static final long serialVersionUID = -4587049497670942668L;
    /**
     * start_time : 2017-06-23 9:00:00
     * end_time : 2017-06-23 17:00:00
     * advance_time : 2
     * reserve_time : 30
     */

    private String start_time;
    private String end_time;
    private String advance_time;
    private String reserve_time;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAdvance_time() {
        return advance_time;
    }

    public void setAdvance_time(String advance_time) {
        this.advance_time = advance_time;
    }

    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    @Override
    public String toString() {
        return "BookTableInfoBean{" +
                "start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", advance_time='" + advance_time + '\'' +
                ", reserve_time='" + reserve_time + '\'' +
                '}';
    }
}
