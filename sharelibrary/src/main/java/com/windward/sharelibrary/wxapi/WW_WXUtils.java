package com.windward.sharelibrary.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.windward.sharelibrary.BitmapUtils;
import com.windward.sharelibrary.ShareMedia;
import com.windward.sharelibrary.SharePlatConfig;
import com.windward.sharelibrary.ShareResponse;

public class WW_WXUtils {


    private static IWXAPI iwxapi = null;
    private static boolean isRegister = false;


    public static IWXAPI getInstance(Context context) {
        SharePlatConfig.WeiXin weiXin = (SharePlatConfig.WeiXin) SharePlatConfig.configs.get
                (ShareMedia.WEIXIN);
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context, weiXin.appId);
        }


        iwxapi.registerApp(weiXin.appId);
//        if (!isRegister) {
//            iwxapi.registerApp(weiXin.appId);
//            isRegister = true;
//        }

        return iwxapi;
    }

    /**
     * 检查是否配置WX_APPID
     *
     * @param context
     * @return
     */
    private static boolean isConfig(Context context) {
        SharePlatConfig.WeiXin weiXin = (SharePlatConfig.WeiXin) SharePlatConfig.configs.get
                (ShareMedia.WEIXIN);
        if (!TextUtils.isEmpty(weiXin.appId))
            return true;
        else {
            return false;
        }
    }


    public static void loginWithWeiXin(Context context) {
        iwxapi = getInstance(context.getApplicationContext());
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端,请先下载安装最新的微信手机客户端。",
                    Toast.LENGTH_LONG).show();
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "feno_weixin_login";
        iwxapi.sendReq(req);
    }


    /**
     * 判定分享内容是否为空
     */
    public static void requestNull(Context context, ShareResponse shareResponse) {
        if (shareResponse == null) {
            Toast.makeText(context, "分享的内容不能为空", Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * 判定是否能够分享
     */
    public static boolean canShare(Context context) {

        iwxapi = getInstance(context.getApplicationContext());

        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端,请先下载安装最新的微信手机客户端。",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isConfig(context)) {
            Toast.makeText(context, "WX_APPID未配置", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    /**
     * 分享文字到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareTextToWx(Context context, int type, ShareResponse
            shareResponse) {

        requestNull(context, shareResponse);

        shareTextToWx(context, type, shareResponse.getTitle(), shareResponse
                .getDescription(), shareResponse.getText(), shareResponse.getBitmap());
    }

    /**
     * 分享文字到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareTextToWx(Context context, int type, String title, String
            description, String text, Bitmap thumpBitmap) {

        if (!canShare(context)){
            return;
        }

        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;

        if (!TextUtils.isEmpty(title)) {
            msg.title = title;
        }
        if (!TextUtils.isEmpty(description)) {
            msg.description = description;
        }

        if (thumpBitmap != null) {
            msg.thumbData = BitmapUtils.bmpToByteArray(
                    Bitmap.createScaledBitmap(thumpBitmap, 150, 150, true),
                    true);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        if (type == 0) {// 微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == 1) {// 微信朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        iwxapi.sendReq(req);

    }

    /**
     * 分享音乐到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareMusicToWx(Context context,
                                      int type,
                                      ShareResponse shareResponse) {
        requestNull(context, shareResponse);

        shareMusicToWx(context,
                type,
                shareResponse.getTitle(),
                shareResponse.getDescription(),
                shareResponse.getMusic_url(),
                shareResponse.getBitmap());
    }

    /**
     * 分享音乐到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareMusicToWx(Context context,
                                      int type,
                                      String title,
                                      String description,
                                      String musicUrl,
                                      Bitmap thumpBitmap) {
        if (!canShare(context)){
            return;
        }

        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        if (!TextUtils.isEmpty(title)) {
            msg.title = title;
        }
        if (!TextUtils.isEmpty(description)) {
            msg.description = description;
        } else {
            Toast.makeText(context, "描述不能为空",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (thumpBitmap != null) {
            msg.thumbData = BitmapUtils.bmpToByteArray(
                    Bitmap.createScaledBitmap(thumpBitmap, 150, 150, true),
                    true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");

        req.message = msg;
        if (type == 0) {// 微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == 1) {// 微信朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        iwxapi.sendReq(req);

    }

    /**
     * 分享视频到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareVideoToWx(Context context,
                                      int type,
                                      ShareResponse shareResponse) {
        requestNull(context, shareResponse);

        shareVideoToWx(context,
                type,
                shareResponse.getTitle(),
                shareResponse.getDescription(),
                shareResponse.getVideo_url(),
                shareResponse.getBitmap());
    }


    /**
     * 分享视频到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareVideoToWx(Context context,
                                      int type,
                                      String title,
                                      String description,
                                      String videoUrl,
                                      Bitmap thumpBitmap) {
        if (!canShare(context)){
            return;
        }

        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = video;

        if (!TextUtils.isEmpty(title)) {
            msg.title = title;
        }
        if (!TextUtils.isEmpty(description)) {
            msg.description = description;
        } else {
            Toast.makeText(context, "描述不能为空",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (thumpBitmap != null) {
            msg.thumbData = BitmapUtils.bmpToByteArray(
                    Bitmap.createScaledBitmap(thumpBitmap, 150, 150, true),
                    true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = buildTransaction("video");

        req.message = msg;
        if (type == 0) {// 微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == 1) {// 微信朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        iwxapi.sendReq(req);

    }

    /**
     * 分享网页到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareWebToWx(Context context, int type, ShareResponse
            shareResponse) {
        requestNull(context, shareResponse);

        shareWebToWx(context, type, shareResponse.getTitle(), shareResponse
                .getDescription(), shareResponse.getTarget_url(), shareResponse.getBitmap());
    }


    /**
     * 分享网页到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareWebToWx(Context context, int type,
                                    String title, String description, String webpageUrl,
                                    Bitmap thumpBitmap) {
        if (!canShare(context)){
            return;
        }

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = webpageUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webpageObject;

        if (!TextUtils.isEmpty(title)) {
            msg.title = title;
        }
        if (!TextUtils.isEmpty(description)) {
            msg.description = description;
        } else {
            Toast.makeText(context, "描述不能为空",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (thumpBitmap != null) {
            msg.thumbData = BitmapUtils.bmpToByteArray(
                    Bitmap.createScaledBitmap(thumpBitmap, 150, 150, true),
                    true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if (type == 0) {// 微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == 1) {// 微信朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        if (iwxapi == null) {
            Toast.makeText(context, "iwxapi is null", Toast.LENGTH_SHORT).show();
        } else
            iwxapi.sendReq(req);
    }

    /**
     * 分享图片到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareImageToWx(Context context, int type, ShareResponse
            shareResponse) {
        requestNull(context, shareResponse);
        shareImageToWx(context, type, shareResponse.getBitmap(), shareResponse.getTitle
                (), shareResponse.getDescription());
    }

    /**
     * 分享图片到微信
     *
     * @param type 分享类型：0：微信好友，1：朋友圈
     */
    public static void shareImageToWx(Context context, int type,
                                      Bitmap bitmap, String title, String description) {

        if (!canShare(context)){
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.imagePath = BitmapUtils.saveTempBitmap(context, bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        if (!TextUtils.isEmpty(title)) {
            msg.title = title;
        }
        if (!TextUtils.isEmpty(description)) {
            msg.description = description;
        }
        if (bitmap != null) {
            msg.thumbData = BitmapUtils.bmpToByteArray(
                    Bitmap.createScaledBitmap(bitmap, 175, 175, true), true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("image");
        req.message = msg;
        if (type == 0) {// 微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == 1) {// 微信朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        iwxapi.sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }


}
