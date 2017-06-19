package com.ww.android.esclub.activity.start;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.Debug;

/**
 * Created by feng on 2017/6/11.
 * 开始页面，用于登录和注册
 */

public class StartActivity extends BaseActivity<VoidView,VoidModel> {
    @BindView(R.id.item_login)
    LinearLayout itemLogin;
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
//                MainActivity.start(this);
                itemLogin.setVisibility(View.VISIBLE);
                float endX = itemLogin.getX();
                float endY = itemLogin.getY();
                Debug.d("endX:"+endX+", endY:"+endY);
                ViewPropertyAnimator animator = itemLogin.animate();
                animator.setDuration(2000);
                animator.translationX(endX);
                animator.translationXBy(0);
                animator.start();
                break;
            case R.id.btn_register:
                break;
        }
    }
}
