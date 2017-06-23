package com.ww.android.esclub.vm.models.start;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.api.StartApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.mvp.model.IModel;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by feng on 2017/6/22.
 */

public class LoginModel implements IModel {
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
    }

    public void onLogin(String code, LifecycleTransformer lifecycleTransformer,
                        HttpSubscriber<UserBean> httpSubscriber){

        StartApi.onLogin(code)
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
                .compose(lifecycleTransformer)
                .subscribe(httpSubscriber);

    }


}
