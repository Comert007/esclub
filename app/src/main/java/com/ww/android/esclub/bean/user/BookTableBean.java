package com.ww.android.esclub.bean.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2017/6/26.
 */

public class BookTableBean implements Serializable{


    private static final long serialVersionUID = 598607552713428492L;
    /**
     * id : 1
     * name : 大呆
     * num : 5
     * table_area : [{"id":"1","name":"A区","tables":[{"id":"1","name":"黃金2桌"},{"id":"2",
     * "name":"黃金2桌"}]}]
     * arrive_time : 2017-06-23 20:22:22
     * mobile : 13678129699
     * created : 2017-06-23 20:22:22
     */

    private String id;
    private String name;
    private String num;
    private String arrive_time;
    private String mobile;
    private String created;
    private List<TableAreaBean> table_area;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<TableAreaBean> getTable_area() {
        return table_area;
    }

    public void setTable_area(List<TableAreaBean> table_area) {
        this.table_area = table_area;
    }

}
