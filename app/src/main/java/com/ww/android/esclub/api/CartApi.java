package com.ww.android.esclub.api;

import com.ww.android.esclub.bean.ResponseBean;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/6/25.
 */

public class CartApi extends BaseApi {

    //商品列表
    public static final Observable<ResponseBean> onGoods(){

        AjaxParams params = new AjaxParams();

        return onPost(getActionUrl("/goods/list"),params);
    }
}
