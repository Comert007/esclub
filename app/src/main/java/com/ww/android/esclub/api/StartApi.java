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
        AjaxParams params = getBaseParams(url);
        params.addParameters("code",code);

        return onPost(url,params);
    }
}
