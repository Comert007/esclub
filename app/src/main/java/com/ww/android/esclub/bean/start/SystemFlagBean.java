package com.ww.android.esclub.bean.start;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/25.
 */

public class SystemFlagBean implements Serializable{

    private static final long serialVersionUID = 4605525087103456646L;

    public String version_flag;
    public String table_area_flag;
//    public String goods_type_flag;
    public String book_table_flag;
    public String webview_flag;
    public String appVer;


    @Override
    public String toString() {
        return "SystemFlagBean{" +
                "version_flag='" + version_flag + '\'' +
                ", table_area_flag='" + table_area_flag + '\'' +
                ", book_table_flag='" + book_table_flag + '\'' +
                ", webview_flag='" + webview_flag + '\'' +
                ", appVer='" + appVer + '\'' +
                '}';
    }
}
