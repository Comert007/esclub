package com.windward.sharelibrary.qq;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.windward.sharelibrary.ShareMedia;
import com.windward.sharelibrary.SharePlatConfig;
import com.windward.sharelibrary.ShareResponse;

import java.util.ArrayList;
import java.util.List;

public class WW_QQUtil {

    private static Tencent mTencent = null;
    private static String QQ_APP_REDIRECTURL = "http://114.215.208.188:8082/share/";

    public static Tencent getInstance(Context context) {
        if (mTencent == null) {
            SharePlatConfig.QQZone qq = (SharePlatConfig.QQZone) SharePlatConfig.configs.get
                    (ShareMedia.QQ);
            mTencent = Tencent.createInstance(qq.appId, context
                    .getApplicationContext());
        }
        return mTencent;
    }

    public static void loginWithQQ(final Activity context,
                                   IUiListener listener) {

        Tencent tencent = getInstance(context.getApplicationContext());
        tencent.login(context, "all", listener);
    }

    /**
     * 检查是否配置QQ_APPID
     *
     * @param context
     * @return
     */
    private static boolean isConfig(Context context) {
        SharePlatConfig.QQZone qq = (SharePlatConfig.QQZone) SharePlatConfig.configs.get
                (ShareMedia.QQ);
        if (!TextUtils.isEmpty(qq.appId) && !TextUtils.isEmpty(qq.appKey))
            return true;
        else {
            return false;
        }
    }

    /**
     * 判断QQ是否安装
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判定分享内容是否为空
     *
     * @param context
     * @param shareResponse
     */
    private static void requestNull(Activity context, ShareResponse shareResponse) {
        if (shareResponse == null) {
            Toast.makeText(context, "分享内容不能为空",
                    Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * 判定是否能够分享
     *
     * @param context
     */
    private static void canShare(Activity context) {
        if (!isQQClientAvailable(context)) {
            Toast.makeText(context, "您还未安装QQ客户端,请先下载安装最新的QQ手机客户端。",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!isConfig(context)) {
            Toast.makeText(context, "QQ_APPID未配置", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * 分享图文到QQ
     */
    public static void shareImageTextToQQ(Activity context,
                                          ShareResponse shareResponse,
                                          IUiListener listener) {
        requestNull(context, shareResponse);

        shareImageTextToQQ(context,
                shareResponse.getTitle(),
                shareResponse.getDescription(),
                shareResponse.getImage_url(),
                shareResponse.getTarget_url(),
                shareResponse.getApp_name(),
                listener);
    }

    /**
     * 分享图文到QQ
     */
    public static void shareImageTextToQQ(Activity context,
                                          String title,
                                          String summary,
                                          String image_url,
                                          String target_url,
                                          String app_name,
                                          IUiListener listener) {
        canShare(context);

        Tencent tencent = getInstance(context.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, TextUtils
                .isEmpty(target_url) ? QQ_APP_REDIRECTURL :
                target_url);
        if (image_url != null) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                    image_url);
        }
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, app_name);
        tencent.shareToQQ(context, params, listener);
    }


    /**
     * 分享图片信息到QQ
     */

    public static void shareImageToQQ(Activity context,
                                      ShareResponse shareResponse,
                                      IUiListener listener) {
        requestNull(context, shareResponse);

        shareImageToQQ(context,
                shareResponse.getImage_url(),
                shareResponse.getApp_name(),
                listener);
    }


    /**
     * 分享图片信息到QQ
     *
     * @param image_url 图片路径为本地路径，不能为网络图片否则会报非法
     */
    public static void shareImageToQQ(Activity context,
                                      String image_url,
                                      String app_name,
                                      IUiListener listener) {
        canShare(context);

        Tencent tencent = getInstance(context.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, app_name);
        if (image_url != null) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,
                    image_url);
        }

        tencent.shareToQQ(context, params, listener);
    }

    /**
     * 分享音乐
     *
     */
    public static void shareMusciToQQ(Activity context,
                                      ShareResponse shareResponse,
                                      IUiListener listener){
        requestNull(context, shareResponse);

        shareMusicToQQ(context,shareResponse.getTitle(),
                shareResponse.getDescription(),
                shareResponse.getTarget_url(),
                shareResponse.getImage_url(),
                shareResponse.getAudio_url(),
                shareResponse.getApp_name(),listener);
    }

    /**
     * 分享音乐
     *
     */
    public static void shareMusicToQQ(Activity context,
                                      String title,
                                      String summary,
                                      String target_url,
                                      String image_url,
                                      String audio_url,
                                      String app_name,
                                      IUiListener listener) {
        canShare(context);

        Tencent tencent = getInstance(context.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  target_url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, image_url);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, audio_url);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  app_name);
        tencent.shareToQQ(context, params, listener);
    }


    // http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif

    /**
     * 分享应用为当前应用在“应用宝”的下载地址
     */
    public static void shareAppToQQ(Activity context,
                                    ShareResponse shareResponse,
                                    IUiListener listener){
        requestNull(context, shareResponse);

        shareAppToQQ(context
                ,shareResponse.getTitle(),
                shareResponse.getDescription(),
                shareResponse.getImage_url(),
                shareResponse.getApp_name(),
                listener);
    }

    /**
     * 分享应用为当前应用在“应用宝的下载地址”
     */
    public static void shareAppToQQ(Activity context,
                                    String title,
                                    String summary,
                                    String image_url,
                                    String app_name,
                                    IUiListener listener){
        canShare(context);

        Tencent tencent = getInstance(context.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);

        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, image_url);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  app_name);

        tencent.shareToQQ(context, params, listener);
    }


    /**
     * 分享到QQ空间
     */
    public static void shareImageTextToQzone(Activity context,
                                             ShareResponse shareResponse,
                                             IUiListener listener){
        requestNull(context, shareResponse);

        shareImageTextToQzone(context, shareResponse.getTitle(), shareResponse.getDescription(),
                shareResponse.getTarget_url(), shareResponse.getImage_url(), listener);
    }

    /**
     * 分享到QQ空间
     */
    public static void shareImageTextToQzone(Activity context,
                                             String title,
                                             String summary,
                                             String target_url,
                                             String image_url,
                                             IUiListener listener){
        canShare(context);

        mTencent = getInstance(context.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);// 选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, TextUtils
                .isEmpty(target_url) ? QQ_APP_REDIRECTURL :
                target_url);
        // 必填;
        ArrayList imageUrls = new ArrayList();
        imageUrls.add(image_url);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQzone(context, params, listener);
    }


    /**
     * 分享到QQ空间
     */
    public static void shareImageTextToQzone(Activity context,
                                             String title,
                                             String summary,
                                             String target_url,
                                             ArrayList<String> image_urls,
                                             IUiListener listener){
        canShare(context);

        mTencent = getInstance(context.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);// 选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, TextUtils
                .isEmpty(target_url) ? QQ_APP_REDIRECTURL :
                target_url);

        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, image_urls);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQzone(context, params, listener);
    }

}
