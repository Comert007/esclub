package com.ww.android.esclub.bean.start;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2017/6/25.
 */

public class TableAreaInfoBean implements Serializable{

    private static final long serialVersionUID = -365688654444520208L;
    /**
     * id : 1
     * name : A区
     */

    private String id;
    private String name;
    private List<TableAreaInfoEntity> tables;
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

    public List<TableAreaInfoEntity> getTables() {
        return tables;
    }

    public void setTables(List<TableAreaInfoEntity> tables) {
        this.tables = tables;
    }


    @Override
    public String toString() {
        return "TableAreaInfoBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tables=" + tables +
                '}';
    }
}
