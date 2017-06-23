package com.ww.android.esclub.vm.models.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.api.HomeApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.home.NewsItem;
import com.ww.mvp.model.IModel;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by feng on 2017/6/23.
 */

public class HomeModel implements IModel {
    @Override
    public void onAttach(@NonNull Context context) {

    }

    public void onNews(String page,
                       String perpage,
                       String type,
                       LifecycleTransformer transformer,
                       HttpSubscriber<ListBean<NewsItem>> httpSubscriber){

        HomeApi.onNews(page, perpage, type)
                .map(new Func1<ResponseBean, ListBean<NewsItem>>() {
                    @Override
                    public ListBean<NewsItem> call(ResponseBean responseBean) {
                        try{
                            ListBean<NewsItem> listBean = JSON.parseObject(responseBean.getData(),ListBean.class);
                            return listBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }

                    }
                }).compose(RxHelper.<ListBean<NewsItem>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

}
