package com.ww.android.esclub.pay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.ww.android.esclub.pay.alipay.utils.OrderInfoUtil2_0;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by fighter on 2016/10/26.
 */

public class WWAlipayLocal {

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016101902241684";

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE =
            "MIICWwIBAAKBgQCmlmww5W6n9WyKEwuuMPLyGFpXl42YZjzt6EFhG55k80yqlMa9\n" +
                    "6JtjUWbqqx7iP7oM9N73iiQB5m4d6joCgKO6bHggzGlUeMhIdDx8zj2kEZg146W2\n" +
                    "bm+Asi3Ue8o0e4fXIiJf2UlDeLIDv7VlmGyq71f4Kh9KyqcNbQt+KVcXfQIDAQAB\n" +
                    "AoGAEGXQIoQC/29uDq1PQgc5ctEo22RkoLK3nqd1AA0K617zlfrtETZ7TSxvF/xe\n" +
                    "HuEs/CeCZxVm8/Tts+4hZaup8gAOJ/isqFZlSzk7FtrzkngySRGhtx4z9+sJ7mJ2\n" +
                    "gRQTHa6WV5sWNJg7LS5ywwtF9CFz+v9DD2Dr06cMVkP7zQECQQDbgkvKbbQV4D9u\n" +
                    "YweX8TNq1zOjnVajJZzZr4QZ5gODY6RBupVKCsbOW9kWRGf8CVmcylHB9pw78tFx\n" +
                    "aJSTwQRBAkEAwkfvzQ861V53e00NosbtWNMiPRCjayGlV+yJ6IDNOJSDckgx/4RX\n" +
                    "0k6maSfpzqrZ+ixT2uHmeP0merRIDwQUPQJAdepPVmWJ+LpmuKCf4qIiMcF6ruiF\n" +
                    "MsywrktXliX2Oy1afiiBq62QUCgMvKZwiXZoWg5gzuXv1VBB7D72mUx1QQJAOCrZ\n" +
                    "/jvP843bFy+gqJXAWrS3qlXcGQXiKng9KpXKPvhYxDpxEgEt7qREYhRi+RfV6wjT\n" +
                    "uW0j14jAjir+xjCUtQJAVqPAY/kN0Xhx8zt6hotC8GIMPlHsJGL9edjyW4G6x6iq\n" +
                    "TozGPjqPRnjLhxHZeDhzZWZxj8idP1XXhr7X2w6QuA==";
    private static final int SDK_PAY_FLAG = 1;

    private AlipayListener mListener;
    private Activity mActy;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        mListener.onPaySuccess(resultInfo);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        mListener.onPayFail(resultStatus, resultInfo);
                    }
                    break;
                }

                default:
                    break;
            }
        }
    };


    private WWAlipayLocal(Activity acty, AlipayListener listener) {
        this.mActy = acty;
        this.mListener = listener;
    }

    public static WWAlipayLocal cretateAlipay(Activity acty, AlipayListener listener) {
        return new WWAlipayLocal(acty, listener);
    }

    /**
     * 支付宝支付业务
     */
    public void pay(String payNo, String total, String notify_url, Map<String, String> params) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */

        String sign= params.get("sign");
        params.remove("sign");


//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, payNo, notify_url);
//        params = OrderInfoUtil2_0.buildOrderParamMap(APPID, payNo, notify_url);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign2 = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        System.out.println(sign2);

        try {
            sign = "sign=" + URLEncoder.encode(sign, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String orderInfo = orderParam + "&" + sign;
        System.out.println("--------::\n" + orderInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActy);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static interface AlipayListener {
        void onPaySuccess(String info);

        void onPayFail(String status, String errInfo);
    }
}
