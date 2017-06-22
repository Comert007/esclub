package com.ww.android.esclub.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ww.android.esclub.config.AppConfig;
import com.ww.android.esclub.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public  class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例

        api = WXAPIFactory.createWXAPI(this, AppConfig.WECHAT_APPID, true);
//        api.registerApp(AppConfig.WECHAT_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:

                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:

                break;
            default:
                break;
        }
    }



    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String tag = "微信";
        if (resp instanceof  SendAuth.Resp){
            tag = "微信登录";
        }else if (resp instanceof SendMessageToWX.Resp){
            tag = "微信分享";
        }

        String result = "";
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
            {
                if (resp instanceof SendAuth.Resp) {
                    EventBus.getDefault().post(resp);
                    finish();
                    return;
                }
            }
            result = tag + "成功";
            break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = tag + "取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = tag + "被拒绝";
                break;
            default:
                result = tag+"返回";
                break;
        }

        ToastUtil.showToast(result);
        finish();
    }

}