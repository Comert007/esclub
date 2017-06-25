package com.ww.android.esclub.vm.models;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.api.CartApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.cart.GoodsBean;
import com.ww.mvp.model.IModel;

import java.util.List;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by feng on 2017/6/25.
 */

public class CartModel implements IModel {
    @Override
    public void onAttach(@NonNull Context context) {

    }

    public void onGoods(LifecycleTransformer transformer, HttpSubscriber<List<GoodsBean>> httpSubscriber){
        CartApi.onGoods()
                .map(new Func1<ResponseBean, List<GoodsBean>>() {
                    @Override
                    public List<GoodsBean> call(ResponseBean responseBean) {
                        try {
                            JSONObject data = JSON.parseObject(responseBean.getData());
                            List<GoodsBean> goodsBeen = JSONArray.parseArray(data.getString("items"),GoodsBean.class);
                            return goodsBeen;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }

                    }
                }).compose(RxHelper.<List<GoodsBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


}
