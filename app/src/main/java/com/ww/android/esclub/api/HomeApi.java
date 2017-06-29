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

    public static final Observable<ResponseBean> onCommentList(String page,String perpage,String id){
        AjaxParams params = new AjaxParams();
        params.addParameters("page",page);
        if (!TextUtils.isEmpty(perpage)) {
            params.addParameters("perpage", perpage);
        }
        params.addParameters("id",id);

        return onPost(getActionUrl("/news/commentList"),params);
    }

    public static final Observable<ResponseBean> onComment(String id,String comment){
        AjaxParams params = new AjaxParams();
        params.addParameters("id",id);
        params.addParameters("comment",comment);

        return onPost(getActionUrl("/news/comment"),params);
    }

    public static final Observable<String> onYouKuAddress(String url){
        AjaxParams params = new AjaxParams();
        return onGet(url,params);
    }
}
