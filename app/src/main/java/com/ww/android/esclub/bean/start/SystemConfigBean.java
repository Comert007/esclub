package com.ww.android.esclub.bean.start;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by feng on 2017/6/25.
 */

public class SystemConfigBean implements Serializable{

    private static final long serialVersionUID = 4958835834119770581L;

    public VersionInfoBean version_info;

    public ArrayList<TableAreaInfoBean> table_area_info;
//    public ArrayList<GoodTypeInfoBean> goods_type_info;

    public BookTableInfoBean book_table_info;
    public WebViewInfoBean webview_info;


}
