package com.ww.android.esclub.listener;

import com.ww.android.esclub.utils.ToastUtil;

import me.weyye.hipermission.PermissionCallback;

/**
 * Created by feng on 2017/6/29.
 */

public class ESPermissionCallback implements PermissionCallback {

    @Override
    public void onClose() {
        ToastUtil.showToast("权限请求取消");
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onDeny(String permission, int position) {

    }

    @Override
    public void onGuarantee(String permission, int position) {

    }
}
