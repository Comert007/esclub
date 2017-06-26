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
import com.ww.android.esclub.api.PayApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.cart.GoodsBean;
import com.ww.android.esclub.bean.cart.GoodsItem;
import com.ww.android.esclub.bean.cart.GoodsPayCallBean;
import com.ww.android.esclub.bean.pay.AlipayBean;
import com.ww.android.esclub.bean.pay.WechatPayBean;
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

    public void onGoods(LifecycleTransformer transformer, HttpSubscriber<List<GoodsBean>>
            httpSubscriber) {
        CartApi.onGoods()
                .map(new Func1<ResponseBean, List<GoodsBean>>() {
                    @Override
                    public List<GoodsBean> call(ResponseBean responseBean) {
                        try {
                            JSONObject data = JSON.parseObject(responseBean.getData());
                            List<GoodsBean> goodsBeen = JSONArray.parseArray(data.getString
                                    ("items"), GoodsBean.class);
                            return goodsBeen;
                        } catch (Exception e) {
                            throw new ApiException(responseBean);
                        }

                    }
                }).compose(RxHelper.<List<GoodsBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void onPay(String seat_id, List<GoodsItem> goodsItems, LifecycleTransformer transformer,
                      HttpSubscriber<GoodsPayCallBean> httpSubscriber) {

        CartApi.onPay(seat_id, goodsItems)
                .map(new Func1<ResponseBean, GoodsPayCallBean>() {
                    @Override
                    public GoodsPayCallBean call(ResponseBean responseBean) {
                        try {
                            JSONObject data = JSON.parseObject(responseBean.getData());
                            GoodsPayCallBean bean = data.getObject("obj", GoodsPayCallBean.class);
                            return bean;
                        }catch (Exception e){
                           throw  new ApiException(responseBean);
                        }
                    }
                })
                .compose(RxHelper.<GoodsPayCallBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }


    public void onWeChat(String seat_id, List<GoodsItem> goodsItems, LifecycleTransformer transformer,
                         HttpSubscriber<WechatPayBean> httpSubscriber){

        PayApi.onweChat(seat_id,goodsItems)
                .map(new Func1<ResponseBean, WechatPayBean>() {
                    @Override
                    public WechatPayBean call(ResponseBean responseBean) {
                        try {
                            JSONObject data = JSON.parseObject(responseBean.getData());
                            WechatPayBean wechatPayBean = data.getObject("obj",WechatPayBean.class);
                            return wechatPayBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<WechatPayBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void onAlipay(String seat_id, List<GoodsItem> goodsItems, LifecycleTransformer transformer,
                         HttpSubscriber<AlipayBean> httpSubscriber){
        PayApi.onAliPay(seat_id,goodsItems)
                .map(new Func1<ResponseBean, AlipayBean>() {
                    @Override
                    public AlipayBean call(ResponseBean responseBean) {
                        JSONObject data = JSON.parseObject(responseBean.getData());
                        AlipayBean alipayBean = data.getObject("obj",AlipayBean.class);
                        return alipayBean;
                    }
                }).compose(RxHelper.<AlipayBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }
}
