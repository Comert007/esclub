package com.ww.android.esclub.vm.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.api.GuessApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.guess.GuessDetailBean;
import com.ww.android.esclub.bean.guess.MatchBean;
import com.ww.mvp.model.IModel;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by feng on 2017/6/27.
 */

public class GuessModel implements IModel{
    @Override
    public void onAttach(@NonNull Context context) {

    }

    public void onMatch(LifecycleTransformer transformer, HttpSubscriber<List<MatchBean>> httpSubscriber){

        GuessApi.onMatch()
                .map(new Func1<ResponseBean, List<MatchBean>>() {
                    @Override
                    public List<MatchBean> call(ResponseBean responseBean) {
                        try {
                            JSONObject json = JSON.parseObject(responseBean.getData());
                            MatchBean matchBean = JSON.parseObject(json.getString("obj"),MatchBean.class);
                            List<MatchBean> matchBeanList = new ArrayList<>();
                            //&& !TextUtils.isEmpty(matchBean.getId())
                            if (matchBean!=null) {
                                matchBeanList.add(matchBean);
                            }
                            return matchBeanList;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }

                    }
                }).compose(RxHelper.<List<MatchBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void onMatchDetail(String id, String page, LifecycleTransformer transformer,
                              HttpSubscriber<ListBean<GuessDetailBean>> httpSubscriber){

        GuessApi.onGuessDetail(id,page,null)
                .map(new Func1<ResponseBean, ListBean<GuessDetailBean>>() {
                    @Override
                    public ListBean<GuessDetailBean> call(ResponseBean responseBean) {
                        try {
                            ListBean<GuessDetailBean> listBean = JSONObject.parseObject(responseBean.getData(),ListBean.class);
                            return listBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<ListBean<GuessDetailBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void onGuessBet(String id,String option,String point,LifecycleTransformer transformer,
                           HttpSubscriber<Boolean> httpSubscriber){
        GuessApi.onGuessBet(id, option, point)
                .map(new Func1<ResponseBean, Boolean>() {
                    @Override
                    public Boolean call(ResponseBean responseBean) {
                        try{
                            boolean isVote= false;
                            JSONObject json = JSON.parseObject(responseBean.getData());
                            JSONObject obj = JSON.parseObject(json.getString("obj"));
                            String id = obj.getString("id");
                            if (!TextUtils.isEmpty(id)){
                                isVote = true;
                            }
                            return isVote;

                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<Boolean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void onMatchHistory(String page,LifecycleTransformer transformer,
                               HttpSubscriber<ListBean<MatchBean>> httpSubscriber){
        GuessApi.onMatchHistory(page,null)
                .map(new Func1<ResponseBean, ListBean<MatchBean>>() {
                    @Override
                    public ListBean<MatchBean> call(ResponseBean responseBean) {
                        try {
                            ListBean<MatchBean> listBean = JSON.parseObject(responseBean.getData(),ListBean.class);
                            return listBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<ListBean<MatchBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void onGuessHistoryDetail(String id, String page, LifecycleTransformer transformer,
                              HttpSubscriber<ListBean<GuessDetailBean>> httpSubscriber){

        GuessApi.onGuessHistoryDetail(id,page,null)
                .map(new Func1<ResponseBean, ListBean<GuessDetailBean>>() {
                    @Override
                    public ListBean<GuessDetailBean> call(ResponseBean responseBean) {
                        try {
                            ListBean<GuessDetailBean> listBean = JSONObject.parseObject(responseBean.getData(),ListBean.class);
                            return listBean;
                        }catch (Exception e){
                            throw new ApiException(responseBean);
                        }
                    }
                }).compose(RxHelper.<ListBean<GuessDetailBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }



}
