package com.ww.android.esclub.api.convert;

import android.text.TextUtils;

import com.ww.android.esclub.api.BaseApi;
import com.ww.android.esclub.bean.ResponseBean;

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

}
