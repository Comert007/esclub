package com.ww.android.esclub.api;

import android.text.TextUtils;

import com.ww.android.esclub.bean.ResponseBean;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/6/23.
 */

public class HomeApi extends BaseApi {

    //新闻列表
    public static final Observable<ResponseBean> onNews(String page,String perpage,String type){
        AjaxParams params = new AjaxParams();
        params.addParameters("page",page);
        if (!TextUtils.isEmpty(perpage)) {
            params.addParameters("perpage", perpage);
        }
        params.addParameters("type",type);

        return onPost(getActionUrl("/news/list"),params);
    }
}
