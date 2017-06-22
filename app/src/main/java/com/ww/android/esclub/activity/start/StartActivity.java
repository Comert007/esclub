package com.ww.android.esclub.activity.start;

import android.view.View;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import butterknife.OnClick;

/**
 * Created by feng on 2017/6/11.
 * 开始页面，用于登录和注册
 */

public class StartActivity extends BaseActivity<VoidView,VoidModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.btn_login,R.id.btn_register})
    public void start(View v){
        switch (v.getId()){
            case R.id.btn_login:
                LoginActivity.start(this);
                break;
            case R.id.btn_register:
                break;
        }
    }
}
