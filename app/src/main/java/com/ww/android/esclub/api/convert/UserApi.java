package com.ww.android.esclub.api.convert;

import android.text.TextUtils;

import com.ww.android.esclub.api.BaseApi;
import com.ww.android.esclub.bean.ResponseBean;

import java.io.File;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/6/26.
 */

public class UserApi extends BaseApi {

    public static final Observable<ResponseBean> onOrders(String page,String perpage){

        AjaxParams params = new AjaxParams();
        params.addParameters("page",page);
        if (!TextUtils.isEmpty(perpage)) {
            params.addParameters("perpage", perpage);
        }

        return onPost(getActionUrl("/order/list"),params);
    }


    public static final Observable<ResponseBean> onOrderDetail(String id){
        AjaxParams params = new AjaxParams();
        params.addParameters("id",id);

        return onPost(getActionUrl("/order/detail"),params);
    }


    public static final Observable<ResponseBean> onAliPay(String price){

        AjaxParams params = new AjaxParams();
        params.addParameters("price",price);

        return onPost(getActionUrl("/payment/alipayForPoint"),params);
    }


    public static final Observable<ResponseBean> onWeChat(String price){

        AjaxParams params = new AjaxParams();
        params.addParameters("price",price);

        return onPost(getActionUrl("/payment/wechatForPoint"),params);
    }

    public static final Observable<ResponseBean> onModify(String path, String nickname){
        AjaxParams params = new AjaxParams();
        if (!TextUtils.isEmpty(path)) {
            params.addParametersPNG("avatar", new File(path));
        }
        if (!TextUtils.isEmpty(nickname)) {
            params.addParameters("nickname", nickname);
        }

        return onPost(getActionUrl("/user/modify"),params);
    }


    public static final Observable<ResponseBean> onLoginOut(){
        AjaxParams params = new AjaxParams();
        return onPost(getActionUrl("/user/logout"),params);
    }

    public static final Observable<ResponseBean> onLastetTable(String id){
        AjaxParams params = new AjaxParams();
        params.addParameters("id",id);

        return onPost(getActionUrl("/table/latestTable"),params);
    }

    public static final Observable<ResponseBean> onBookTable(String name,
                                                             String num,
                                                             String id,
                                                             String index,
                                                             String arrive_time,
                                                             String mobile){
        AjaxParams params = new AjaxParams();
        params.addParameters("name",name);
        params.addParameters("num",num);
        params.addParameters("id["+index+"]",id);
        params.addParameters("arrive_time",arrive_time);
        params.addParameters("mobile",mobile);

        return onPost(getActionUrl("/table/book"),params);
    }

    public static final Observable<ResponseBean> onUserInfo(){
        AjaxParams params = new AjaxParams();

        return onPost(getActionUrl("/user/info"),params);
    }

}
