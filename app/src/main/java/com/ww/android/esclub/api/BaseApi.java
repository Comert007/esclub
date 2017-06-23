package com.ww.android.esclub.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.BuildConfig;
import com.ww.android.esclub.api.convert.ResponseFunc;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.config.AppConfig;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import ww.com.core.utils.PhoneUtils;
import ww.com.http.OkHttpRequest;
import ww.com.http.core.AjaxParams;
import ww.com.http.core.RequestMethod;
import ww.com.http.interfaces.DownloadProgressListener;
import ww.com.http.rx.RxHelper;
import ww.com.http.rx.StringFunc;

/**
 * Created by feng on 2017/6/22.
 */

public class BaseApi {

    private static final String BASE_URL = BuildConfig.BASEURL;

    protected static final String getActionUrl(String action) {
        return String.format("%s%s", BASE_URL, action);
    }

    private static Map<String, String> getHeader(String factor) {
        Map<String, String> params = new HashMap<>();
//        try {
//
//            String content = AppConfig.APP_ID + "," + factor;
//            Debug.d("encode content:" + content);
//            WWAESUtil wwaesUtil = new WWAESUtil(AppConfig.APP_IV, AppConfig.ENCRYPT_KEY);
//            int pad = 16 - (content.length() % 16);
//            StringBuffer buf = new StringBuffer(content);
//            for (int i = 0; i < pad; i++) {
//                buf.append((char) pad);
//            }
//            String strBuf = buf.toString();
//            Debug.d("encode buff:\n" + strBuf);
//            String encode = Base64.encode(wwaesUtil.encrypt(strBuf));
//            //7MxyMI153lcsaFyUiYFt1g==
//            params.put("APP_AUTH", encode);  // 加密后的数据
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        params.put("APP_AUTH", AppConfig.APP_ID);
        params.put("APP_AUTH_IV", AppConfig.APP_IV);  // 私钥
        params.put("APP_VERSION", PhoneUtils.getAppVer(BaseApplication.getInstance()));  // APP版本
        params.put("DEVICE_UUID", PhoneUtils.getDeviceId(BaseApplication.getInstance()));  //
        // 客户端UUID
        params.put("DEVICE_MODEL", "2");  // 设备(1:iphone;2:android)
        params.put("DEVICE_VERSION", PhoneUtils.getPhoneModel());  //客户端版本
        String token = BaseApplication.getInstance().getToken();
        if (!TextUtils.isEmpty(token))
            params.put("APP_TOKEN", token);  //登录后生成的会话token，注册流程，找回密码，登录等不传
        return params;


    }


    //jsonObject
    protected static Observable<String> onJson(String url, JSONObject json) {
        AjaxParams params = getBaseParams(url);
        params.addParametersJson(json);

        return json(url, params)
                .compose(RxHelper.<ResponseBody>cutMain())
                .map(new StringFunc());
    }

    //post
    protected static Observable<ResponseBean> onPost(String url,AjaxParams params) {

        String factor = url.replace(BASE_URL, "");
        Map<String, String> header = getHeader(factor);
        for (String key : header.keySet()) {
            params.addHeaders(key, header.get(key));
        }

        return post(url, params)
                .map(new StringFunc())
                .map(new ResponseFunc());
    }

    //get
    protected static Observable<String> onGet(String url,AjaxParams params) {

        String factor = url.replace(BASE_URL, "");
        Map<String, String> header = getHeader(factor);
        for (String key : header.keySet()) {
            params.addHeaders(key, header.get(key));
        }

        return get(url, params)
                .compose(RxHelper.<ResponseBody>cutMain())
                .map(new StringFunc());
    }

    /**
     * @param url
     * @param params
     * @return
     */
    private static Observable<ResponseBody> get(String url, AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.GET);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param params
     * @param progressListener
     * @return
     */
    private static Observable<ResponseBody> downFile(String url, AjaxParams params,
                                                     @NonNull DownloadProgressListener
                                                             progressListener) {
        params = params.setBaseUrl(url)
                .setDownloadProgressListener(progressListener)
                .setRequestMethod(RequestMethod.GET);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * @param url    请求的地址
     * @param params 请求的参数
     * @return
     */
    private static Observable<ResponseBody> post(String url,
                                                 AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.POST);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * @param url    请求的地址
     * @param params 请求的参数
     * @return
     */
    private static Observable<ResponseBody> json(String url,
                                                 AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.JSON);
        return OkHttpRequest.newObservable(params);
    }

    public static final AjaxParams getBaseParams(String url) {
        AjaxParams params = new AjaxParams();
        String factor = url.replace(BASE_URL, "");
        Map<String, String> header = getHeader(factor);
        for (String key : header.keySet()) {
            params.addHeaders(key, header.get(key));
        }
        return params;
    }

}
