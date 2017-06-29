package com.ww.android.esclub.vm.models.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.api.HomeApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.home.CommentBean;
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
                            ListBean<NewsItem> listBean = JSON.parseObject(responseBean.getData(),
                                    new TypeReference<ListBean<NewsItem>>(){});

                            return listBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }

                    }
                }).compose(RxHelper.<ListBean<NewsItem>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void onCommentList(String page,
                          String perpage,
                          String id,
                          LifecycleTransformer transformer,
                          HttpSubscriber<ListBean<CommentBean>> httpSubscriber){

        HomeApi.onCommentList(page, perpage, id)
                .map(new Func1<ResponseBean, ListBean<CommentBean>>() {
                    @Override
                    public ListBean<CommentBean> call(ResponseBean responseBean) {
                        try {

                            ListBean<CommentBean> listBean = JSON.parseObject(responseBean.getData(),
                                    new TypeReference<ListBean<CommentBean>>(){});
                            return listBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<ListBean<CommentBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    //评论
    public void onComment(String id,String comment,LifecycleTransformer transformer,HttpSubscriber<String> httpSubscriber){

        HomeApi.onComment(id, comment)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        try {
                            String id = JSON.parseObject(JSON.parseObject(responseBean.getData()).getString("obj"),String.class);
                            return id;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void onYouKuAddress(String url,LifecycleTransformer transformer,HttpSubscriber<String> httpSubscriber){
        HomeApi.onYouKuAddress(url)
                .compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

}
