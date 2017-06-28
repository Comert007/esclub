package com.ww.android.esclub.vm.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.api.convert.UserApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.pay.AlipayBean;
import com.ww.android.esclub.bean.pay.WechatPayBean;
import com.ww.android.esclub.bean.start.TableAreaInfoEntity;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.android.esclub.bean.user.OrderCentreBean;
import com.ww.android.esclub.bean.user.OrderDetailBean;
import com.ww.mvp.model.IModel;

import java.util.List;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by feng on 2017/6/26.
 */

public class UserModel implements IModel {

    @Override
    public void onAttach(@NonNull Context context) {

    }

    public void onOrders(String page, String perpage, LifecycleTransformer transformer,
                         HttpSubscriber<ListBean<OrderCentreBean>> httpSubscriber) {

        UserApi.onOrders(page, perpage)
                .map(new Func1<ResponseBean, ListBean<OrderCentreBean>>() {
                    @Override
                    public ListBean<OrderCentreBean> call(ResponseBean responseBean) {
                        try {
                            ListBean<OrderCentreBean> listBean = JSON.parseObject(responseBean
                                    .getData(), ListBean.class);
                            return listBean;
                        } catch (Exception e) {
                            throw new ApiException(responseBean);
                        }
                    }
                })
                .compose(RxHelper.<ListBean<OrderCentreBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void onOrders(String page, LifecycleTransformer transformer,
                         HttpSubscriber<ListBean<OrderCentreBean>> httpSubscriber) {
        onOrders(page, null, transformer, httpSubscriber);
    }

    public void onOrderDetail(String id, LifecycleTransformer transformer,
                              HttpSubscriber<ListBean<OrderDetailBean>> httpSubscriber) {
        UserApi.onOrderDetail(id)
                .map(new Func1<ResponseBean, ListBean<OrderDetailBean>>() {
                    @Override
                    public ListBean<OrderDetailBean> call(ResponseBean responseBean) {
                        try {
                            ListBean<OrderDetailBean> listBean = JSON.parseObject(responseBean
                                    .getData(), ListBean.class);
                            return listBean;

                        } catch (Exception e) {
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<ListBean<OrderDetailBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void onWeChat(String price, LifecycleTransformer transformer,
                         HttpSubscriber<WechatPayBean> httpSubscriber){

        UserApi.onWeChat(price)
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

    public void onAlipay(String price, LifecycleTransformer transformer,
                         HttpSubscriber<AlipayBean> httpSubscriber){
        UserApi.onAliPay(price)
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

    public void onModify(String path, String nickname, LifecycleTransformer transformer,
                         HttpSubscriber<UserBean> httpSubscriber){
        UserApi.onModify(path,nickname)
                .map(new Func1<ResponseBean, UserBean>() {
                    @Override
                    public UserBean call(ResponseBean responseBean) {
                        try {
                            JSONObject json = JSON.parseObject(responseBean.getData());
                            UserBean userBean = JSONObject.parseObject(json.getString("obj"),UserBean.class);
                            return userBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                })
                .compose(RxHelper.<UserBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void onLoginOut(LifecycleTransformer transformer,HttpSubscriber<Boolean> httpSubscriber){
        UserApi.onLoginOut()
                .map(new Func1<ResponseBean, Boolean>() {
                    @Override
                    public Boolean call(ResponseBean responseBean) {
                        try {
                            return true;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<Boolean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void onLeastTable(String id, LifecycleTransformer transformer, 
                             HttpSubscriber<List<TableAreaInfoEntity>> httpSubscriber){
        
        UserApi.onLastetTable(id)
                .map(new Func1<ResponseBean, List<TableAreaInfoEntity>>() {
                    @Override
                    public List<TableAreaInfoEntity> call(ResponseBean responseBean) {
                        try {
                            JSONObject data = JSON.parseObject(responseBean.getData());
                            List<TableAreaInfoEntity> entities = JSONArray.parseArray(data.getString("items"),
                                    TableAreaInfoEntity.class);

                            return entities;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<List<TableAreaInfoEntity>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void onBookTable(String name,
                            String num,
                           List<String> ids,
                            String arrive_time,
                            String mobile,
                            LifecycleTransformer transformer,
                            HttpSubscriber<Boolean> httpSubscriber){
        UserApi.onBookTable(name, num, ids,arrive_time, mobile)
                .map(new Func1<ResponseBean, Boolean>() {
                    @Override
                    public Boolean call(ResponseBean responseBean) {
                        try {
                            JSONObject data = JSON.parseObject(responseBean.getData());
                            JSONObject obj = JSON.parseObject(data.getString("obj"));
                            String id = obj.getString("id");
                            if (!TextUtils.isEmpty(id)){
                                return true;
                            }else {
                                return false;
                            }
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<Boolean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void onUserInfo(LifecycleTransformer transformer,HttpSubscriber<UserBean> httpSubscriber){
        UserApi.onUserInfo()
                .map(new Func1<ResponseBean, UserBean>() {
                    @Override
                    public UserBean call(ResponseBean responseBean) {
                        try {
                            JSONObject json = JSON.parseObject(responseBean.getData());
                            UserBean userBean = JSONObject.parseObject(json.getString("obj"),UserBean.class);
                            return userBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<UserBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

}
