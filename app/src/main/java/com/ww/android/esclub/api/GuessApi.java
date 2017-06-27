package com.ww.android.esclub.api;

import android.text.TextUtils;

import com.ww.android.esclub.bean.ResponseBean;

import java.io.File;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/6/27.
 */

public class GuessApi extends BaseApi {

    public static final Observable<ResponseBean> onMatch(){
        AjaxParams params = new AjaxParams();

        return onPost(getActionUrl("/guess/match"),params);
    }

    public static final Observable<ResponseBean> onGuessDetail(String id,String page,String perpage){
        AjaxParams params = new AjaxParams();
        params.addParameters("id",id);
        params.addParameters("page",page);
        if (!TextUtils.isEmpty(perpage)){
            params.addParameters("perpage",perpage);
        }

        return onPost(getActionUrl("/guess/itemList"),params);
    }

    public static final Observable<ResponseBean> onGuessBet(String id,String option,String point){
        AjaxParams params = new AjaxParams();
        params.addParameters("id",id);
        params.addParameters("option",option);
        params.addParameters("point",point);

        return onPost(getActionUrl("/guess/bet"),params);
    }


    //投注历史
    public static final Observable<ResponseBean> onMatchHistory(String page,String perpage){
        AjaxParams params = new AjaxParams();
        params.addParameters("page",page);
        if (!TextUtils.isEmpty(perpage)){
            params.addParameters("perpage",perpage);
        }

        return onPost(getActionUrl("/guess/userMatch"),params);
    }


    //投注历史详情
    public static final Observable<ResponseBean> onGuessHistoryDetail(String id,String page,String perpage){
        AjaxParams params = new AjaxParams();
        params.addParameters("id",id);
        params.addParameters("page",page);
        if (!TextUtils.isEmpty(perpage)){
            params.addParameters("perpage",perpage);
        }

        return onPost(getActionUrl("/guess/userItemList"),params);
    }


}
