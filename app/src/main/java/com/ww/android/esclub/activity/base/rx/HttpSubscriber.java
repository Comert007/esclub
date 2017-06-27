package com.ww.android.esclub.activity.base.rx;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;

import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.activity.start.LoginActivity;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.utils.DialogUtil;
import com.ww.android.esclub.utils.ToastUtil;
import com.ww.android.esclub.widget.EsProgressDialog;

import java.util.List;

import rx.Subscriber;

/**
 * Created by feng on 2017/6/22.
 */

public abstract class HttpSubscriber<T> extends Subscriber<T> {
    private EsProgressDialog dialog;
    private Context context;

    private boolean showDialog;

    public HttpSubscriber(Context context, boolean showDialog) {
        this.context = context;
        this.showDialog = showDialog;
        initDialog();
    }

    private void initDialog() {
        dialog = DialogUtil.obtainProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (!isUnsubscribed()) {
                    unsubscribe();
                    onCancelRequest();
                }
            }
        });
    }

    private void showDialog() {
        dialog.show();
    }

    private void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            showDialog();
        }
    }

    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        try {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                // 不是主线程不坐处理.
                return;
            }
            onEnd();
            onFail(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void onFail(Throwable e) {
        if (e instanceof ApiException) {
            ResponseBean responseBean = ((ApiException) e).getResponseBean();
            if (responseBean.isNeedRelogin()){
                BaseApplication.getInstance().setUserBean(null);
                DialogUtil.showDialog(context, "温馨提示", responseBean.getMsg(), "确定",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<Activity> actys = BaseApplication
                                .getInstance().getRunActivity();
                        for (Activity activity : actys) {
                            if (!activity.isFinishing()&& !activity
                                    .getClass()
                                    .getCanonicalName()
                                    .equals(LoginActivity.class
                                            .getCanonicalName())) {
                                activity.finish();
                            }
                        }
                        LoginActivity.start(context);
                    }
                });
            }else {
                onFail(responseBean);
            }
        }else {
            ToastUtil.showToast(e.getMessage());
        }
    }

    public void onFail(ResponseBean responseBean) {
        ToastUtil.showToast(responseBean.getMsg());
    }

    public void onEnd() {
        dismissDialog();
    }

    /**
     * 取消请求
     */
    protected void onCancelRequest() {
        dismissDialog();
    }

    /**
     * 刷新请求
     */
    protected void onRefreshRequest() {
        dismissDialog();
    }
}
