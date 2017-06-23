package com.ww.android.esclub.api;

import com.ww.android.esclub.bean.ResponseBean;

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
    public static final Observable<ResponseBean> sysParams(String version_flag,String seat_flag,String goods_type_flag){
        String url = getActionUrl("/sys/initParam");
        AjaxParams params =  new AjaxParams();
        params.addParameters("version_flag",version_flag);
        params.addParameters("seat_flag",seat_flag);
        params.addParameters("goods_type_flag",goods_type_flag);

        return onPost(url,params);
    }
}
