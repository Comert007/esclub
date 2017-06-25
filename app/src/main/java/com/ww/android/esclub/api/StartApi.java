package com.ww.android.esclub.api;

import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.start.SystemFlagBean;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/6/22.
 * 起始接口
 */

public class StartApi extends BaseApi{

    public static final Observable<ResponseBean> onLogin(String code) {

        String url = getActionUrl("/user/loginBySNS");
        AjaxParams params = new AjaxParams();
        params.addParameters("code",code);

        return onPost(url,params);
    }

    //初始化配置
    public static final Observable<ResponseBean> sysParams(SystemFlagBean flagBean){
        String url = getActionUrl("/sys/initParam");
        AjaxParams params =  new AjaxParams();
        if (flagBean!=null){
            params.addParameters("version_flag",flagBean.version_flag);
            params.addParameters("table_area_flag",flagBean.table_area_flag);
//            params.addParameters("goods_type_flag",flagBean.goods_type_flag);
            params.addParameters("book_table_flag",flagBean.book_table_flag);
            params.addParameters("webview_flag",flagBean.webview_flag);
        }

        return onPost(url,params);
    }
}
