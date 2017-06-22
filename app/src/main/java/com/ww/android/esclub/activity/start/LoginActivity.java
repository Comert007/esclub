package com.ww.android.esclub.activity.start;

import android.content.Context;
import android.content.Intent;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

/**
 * Created by feng on 2017/6/22.
 * 登录界面
 */

public class LoginActivity extends BaseActivity<VoidView,VoidModel> {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }
}
