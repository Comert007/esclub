package com.ww.android.esclub.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/7.
 */

public class ShareActivity extends BaseActivity<VoidView,VoidModel> {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShareActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.anim_bottom_enter,R.anim.anim_bottom_exit);
    }

    @BindViews({R.id.tv_weixin_circle,R.id.tv_weixin,R.id.tv_sina,R.id.tv_qq,R.id.tv_qzone,R.id.tv_copy})
    List<TextView> viewList;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_share;
    }

    @Override
    protected void init() {
        for (TextView textView : viewList) {
            Drawable drawable = textView.getCompoundDrawables()[1];
            drawable.setBounds(0,0, ScreenUtil.getScalePxValue(120),ScreenUtil.getScalePxValue(120));
            textView.setCompoundDrawables(null,drawable,null,null);
        }

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_bottom_enter,R.anim.anim_bottom_exit);
    }
}
