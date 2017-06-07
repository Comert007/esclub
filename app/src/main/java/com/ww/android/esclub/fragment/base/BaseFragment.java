package com.ww.android.esclub.fragment.base;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
 * Created by feng on 2017/6/7.
 */

public abstract class BaseFragment<V extends IView,M extends IModel> extends PresenterFragment<V,M> {

    private EsProgressDialog progressDialog;
    @Nullable
    @BindView(R.id.tv_title)
    TextView tvTitle;


    public void showToast(CharSequence text) {
        ToastUtil.showToast(text);
    }

    public void showDialog(String message) {
        if (progressDialog == null) {
            progressDialog = DialogUtil.obtainProgressDialog(getContext());
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

    @Optional
    public void onTitleLeft() {

    }

    @Optional
    public void onTitleRight() {

    }

    @Optional
    public void setTitleText(String titleText){
        if (tvTitle!=null){
            tvTitle.setText(titleText);
        }
    }
}
