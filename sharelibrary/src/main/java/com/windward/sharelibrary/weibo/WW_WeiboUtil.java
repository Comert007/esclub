package com.windward.sharelibrary.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.windward.sharelibrary.ShareMedia;
import com.windward.sharelibrary.SharePlatConfig;

public class WW_WeiboUtil extends Activity implements WbShareCallback {

    private Activity mActivity;
//
//    private IWeiboShareAPI mIWeiboShareAPI;

    private String title;
    private String text;
    private String pageUrl;

    public static final int SHARE_CLIENT = 1;
    public static final int SHARE_ALL_IN_ONE = 2;

    private WbShareHandler shareHandler;
    private int mShareType = SHARE_CLIENT;

    int flag = 0;
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    public static String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";


    public static void startShare(Context context, String title, String text, String pageUrl) {
        Intent intent = new Intent(context, WW_WeiboUtil.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        intent.putExtra("pageUrl", pageUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        initWbShareHandler();
        initShareData();


    }

    public static boolean isTokenValid(Context context){

        try {
            WbSdk.checkInit();
        }catch (Exception e){
            return false;
        }

        Oauth2AccessToken accessToken = AccessTokenKeeper
                .readAccessToken(context.getApplicationContext());
        if (accessToken.isSessionValid()){
            return true;
        }else {
            return false;
        }
    }

    private void initWbShareHandler() {
        SharePlatConfig.SinaWeibo sinaWeibo = (SharePlatConfig.SinaWeibo) SharePlatConfig.configs
                .get(ShareMedia.SINA);
        Log.e("APPKEY", sinaWeibo.appKey);

        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();

    }

    private void initShareData() {
        title = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        pageUrl = getIntent().getStringExtra("pageUrl");

        sendMultiMessage(title, text, null, pageUrl);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent, this);
    }



    private void sendMultiMessage(String title, String text, Bitmap bitmap,
                                  String pageUrl) {

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (!TextUtils.isEmpty(text)) {
            weiboMessage.textObject = getTextObj(text);
        }

        if (bitmap != null) {
            weiboMessage.imageObject = getImageObj(bitmap);
        }

        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        if (!TextUtils.isEmpty(pageUrl)) {
            weiboMessage.mediaObject = getWebpageObj(title, text, bitmap,
                    pageUrl, text);
        }


        // 3. 发送请求消息到微博，唤起微博分享界面
        shareHandler.shareMessage(weiboMessage, mShareType == SHARE_CLIENT);
//        SharePlatConfig.SinaWeibo sinaWeibo = (SharePlatConfig.SinaWeibo) SharePlatConfig.configs
//                .get(ShareMedia.SINA);
//        Log.e("SINA","sinaWeibo:"+sinaWeibo.appKey+", "+sinaWeibo.appSecret);
//        AuthInfo authInfo = new AuthInfo(mActivity, sinaWeibo.appKey,
//                WW_WeiboUtil.REDIRECT_URL,
//                WW_WeiboUtil.SCOPE);
//        Oauth2AccessToken accessToken = AccessTokenKeeper
//                .readAccessToken(mActivity.getApplicationContext());
//        Log.e("SINA","sina token:"+accessToken.getToken());
//        String token = "";
//        token = accessToken.getToken();
//        Log.e("SINA","sina share begin");
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj( String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    public ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(String title, String description,
                                        Bitmap thumpBitmap, String actionUrl, String
                                                webpageDefaultText) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;
        if (thumpBitmap != null) {
            mediaObject.setThumbImage(Bitmap.createScaledBitmap(thumpBitmap,
                    200, 200, true));
        }
        mediaObject.actionUrl = actionUrl;
        return mediaObject;
    }


    @Override
    public void onWbShareSuccess() {
        showToast("分享成功");
        finish();
    }

    @Override
    public void onWbShareCancel() {
        showToast("分享取消");
        finish();
    }

    @Override
    public void onWbShareFail() {
        showToast("分享失败");
        finish();
    }


    private void showToast(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

}
