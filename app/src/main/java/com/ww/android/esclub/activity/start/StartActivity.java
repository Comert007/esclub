package com.ww.android.esclub.activity.start;

import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import butterknife.BindView;

/**
 * Created by feng on 2017/6/11.
 * 开始页面，用于登录和注册
 */

public class StartActivity extends BaseActivity<VoidView,VoidModel> {

    @BindView(R.id.pathView)
    PathView pathView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {
        pathView.getPathAnimator().
                delay(100).
                duration(2000).
                interpolator(new AccelerateDecelerateInterpolator()).
                start();

        pathView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.start(StartActivity.this);
                finish();
            }
        },2200);

    }

}
