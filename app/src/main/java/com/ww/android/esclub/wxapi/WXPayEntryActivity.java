package com.ww.android.esclub.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ww.android.esclub.config.AppConfig;

import org.greenrobot.eventbus.EventBus;


/**
 * 微信回调页面
 * 需要使用 applicationId.wxapi.WXPayEntryActivity 进行使用</br>
 * 在manifest声明的时候，需要添加
 * android:exported="true"
 * android:launchMode="singleTop"
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, getAppId());
        api.handleIntent(getIntent(), this);
    }

    protected String getAppId(){
        return AppConfig.WECHAT_APPID;
    }

    protected  void onSuccess(String code){
        Bundle bundle = new Bundle();
        bundle.putString("action", "wxpay");
        if ("0".equals(code)) {
            bundle.putBoolean("status", true);
        } else {
            bundle.putBoolean("status", false);
        }
        EventBus.getDefault().post(bundle);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            onSuccess(String.valueOf(resp.errCode));
        }
    }
}