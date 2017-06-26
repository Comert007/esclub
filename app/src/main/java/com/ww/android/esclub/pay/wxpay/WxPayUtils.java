package com.ww.android.esclub.pay.wxpay;

import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ww.android.esclub.R;

import java.util.Map;

/**
 * Created by feng on 2017/6/26.
 */

public class WxPayUtils {

    public static void pay(Context context, Map<String, String> params) {
        pay(context, params.get("appid"),
                params.get("partnerid"),
                params.get("prepayid"),
                params.get("noncestr"),
                params.get("timestamp"),
                params.get("package"),
                params.get("sign"));
    }

    /**
     * <pre>
     * PayReq req = new PayReq();
     * //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
     * req.appId			= json.getString("appid");
     * req.partnerId		= json.getString("partnerid");  // 商家id
     * req.prepayId		= json.getString("prepayid");  // 支付款 id
     * req.nonceStr		= json.getString("noncestr");
     * req.timeStamp		= json.getString("timestamp");
     * req.packageValue	= json.getString("package");
     * req.sign			= json.getString("sign");
     * req.extData			= "app data"; // optional
     * Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
     * // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
     * api.sendReq(req);
     * </pre>
     */
    public static void pay(Context context, String appId,
                           String partnerId,
                           String prepayId,
                           String nonceStr,
                           String timeStamp,
                           String packageValue,
                           String sign) {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, appId);
        if (!wxApi.isWXAppInstalled()) {
            Toast.makeText(context.getApplicationContext(), R.string.str_wx_no_install, Toast.LENGTH_SHORT).show();
            return;
        }

        wxApi.registerApp(appId);

        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.packageValue = packageValue;
        req.sign = sign;
        req.extData = "app data";
        wxApi.sendReq(req);
    }
}
