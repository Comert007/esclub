package com.ww.android.esclub.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;

import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.bean.start.SystemConfigBean;
import com.ww.android.esclub.bean.start.VersionInfoBean;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ww.com.core.utils.Base64;
import ww.com.core.utils.PhoneUtils;

/**
 * Created by fighter on 2016/9/30.
 */

public class AppUtils {
    public static String PRICE_SYMBOL = "¥";

    public static void hideSoftKeyBord(@NonNull View view) {
        View v = view;
        if (v == null)
            return;
        try {
            InputMethodManager imm = (InputMethodManager) v.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取二维码信息
     *
     * @param scanCode
     * @return
     */
    public static String getScanCodeByUrl(String scanCode) {
        if (TextUtils.isEmpty(scanCode)) {
            return scanCode;
        }

        if (URLUtil.isHttpUrl(scanCode) || URLUtil.isHttpsUrl(scanCode)) {
            Uri url = Uri.parse(scanCode);
            String code = url.getQueryParameter("code");
            if (!TextUtils.isEmpty(code)) {
                return code;
            }
        }

        return scanCode;
    }




    public static boolean isNewVer(Context context) {
        SystemConfigBean configBean = BaseApplication.getInstance().getSystemConfigBean();
        if (configBean != null) {
            VersionInfoBean verInfo = configBean.version_info;
            if (verInfo != null) {
                String serVer = verInfo.getApp_version();  // 服务器对应的版本号
                String locVer = PhoneUtils.getAppVer(context);
                if (TextUtils.isEmpty(serVer) || TextUtils.isEmpty(locVer)) {
                    return false;
                }

                final String updateUrl = verInfo.getUpdate_uri();
                if (TextUtils.isEmpty(updateUrl)) {
                    return false;
                }
                int compare = serVer.compareTo(locVer);
                return compare > 0;
            }
        }
        return false;
    }

    public static String getNewVerDownUrl() {
        SystemConfigBean configBean = BaseApplication.getInstance().getSystemConfigBean();
        if (configBean != null) {
            VersionInfoBean verInfo = configBean.version_info;
            if (verInfo != null) {
                return verInfo.getUpdate_uri();
            }
        }
        return "";
    }

    public static void startBrowser(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static List<Object> getTestObjs() {
        List<Object> objList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            objList.add(new Object());
        }
        return objList;
    }

    public static Bitmap stringToBitmap(String data) {
        byte[] imgDatas;
        try {
            imgDatas = Base64.decode(data);
            return BitmapFactory.decodeByteArray(
                    imgDatas, 0, imgDatas.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap stringToBitmap(String data, int width, int height) {
        byte[] imgDatas;
        try {
            imgDatas = Base64.decode(data);
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    imgDatas, 0, imgDatas.length);
            return Bitmap.createScaledBitmap(bitmap, width, height, false);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空
            if (networkInfo != null)
                return networkInfo.isAvailable();
        }
        return false;
    }
}
