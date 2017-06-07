package com.ww.android.esclub.activity.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.utils.DialogUtil;
import com.ww.android.esclub.utils.ToastUtil;
import com.ww.android.esclub.widget.EsProgressDialog;
import com.ww.mvp.model.IModel;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by feng on 2017/6/6.
 */

public abstract class BaseActivity<V extends IView,M extends IModel> extends PresenterActivity<V,M> {

    protected BaseApplication baseApp;

    private EsProgressDialog progressDialog;

    @Nullable
    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 设置屏幕固定为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        baseApp = (BaseApplication) getApplication();

        super.onCreate(savedInstanceState);

    }


    public void showToast(CharSequence text) {
        ToastUtil.showToast(text);
    }


    public void showDialog(String message) {
        if (progressDialog == null) {
            progressDialog = DialogUtil.obtainProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();

        if (!TextUtils.isEmpty(message)) {
            progressDialog.setMessage(message);
        } else {
            progressDialog.setMessage(getString(R.string.dialog_loding));
        }
    }

    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    @Optional
    @OnClick({R.id.btn_title_left, R.id.btn_title_right})
    public void onTitleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_title_left:
                onTitleLeft();
                break;
            case R.id.btn_title_right:
                onTitleRight();
                break;
        }
    }

    public void onTitleLeft() {

    }

    public void onTitleRight() {

    }

    @Optional
    public void setTitleText(String titleText){
        if (tvTitle!=null){
            tvTitle.setText(titleText);
        }
    }
}
