package com.windward.sharelibrary;

import android.text.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10142 on 2016/7/12.
 */
public class SharePlatConfig {

    public static Map<ShareMedia, Platform> configs = new HashMap<>();

    public SharePlatConfig() {
    }

    static {
        configs.put(ShareMedia.QQ, new QQZone(ShareMedia.QQ));
        configs.put(ShareMedia.QZONE, new QQZone(ShareMedia.QZONE));
        configs.put(ShareMedia.WEIXIN, new WeiXin(ShareMedia.WEIXIN));
        configs.put(ShareMedia.WEIXINCIRCLE, new WeiXin(ShareMedia.WEIXINCIRCLE));
        configs.put(ShareMedia.SINA,new SinaWeibo(ShareMedia.SINA));
    }
    public static void setQQZone(String appid,String appKey){
        SharePlatConfig.QQZone var1 = (QQZone) configs.get(ShareMedia.QZONE);
        var1.appId = appid;
        var1.appId = appKey;
        SharePlatConfig.QQZone var2 = (QQZone) configs.get(ShareMedia.QQ);
        var2.appId = appid;
        var2.appKey = appKey;
    }

    public static void setWeiXin(String appId, String appSecrect) {
        SharePlatConfig.WeiXin var1 = (WeiXin) configs.get(ShareMedia.WEIXIN);
        var1.appId = appId;
        var1.appSecret = appSecrect;
        SharePlatConfig.WeiXin var2 = (WeiXin) configs.get(ShareMedia.WEIXINCIRCLE);
        var2.appId = appId;
        var2.appSecret = appSecrect;
    }


    public static void setSinaWeibo(String appKey,String appSecret){
        SharePlatConfig.SinaWeibo sinaWeibo = (SinaWeibo) configs.get(ShareMedia.SINA);
        sinaWeibo.appKey = appKey;
        sinaWeibo.appSecret = appSecret;
    }


    public interface Platform {
        ShareMedia getName();

        void parse(JSONObject var1);

        boolean isConfigured();
    }

    public static class WeiXin implements SharePlatConfig.Platform {
        private final ShareMedia media;
        public String appId = null;
        public String appSecret = null;

        public WeiXin(ShareMedia media) {
            this.media = media;
        }

        @Override
        public ShareMedia getName() {
            return this.media;
        }

        @Override
        public void parse(JSONObject var1) {
        }

        @Override
        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.appId) && !TextUtils.isEmpty(this.appSecret);
        }
    }

    public static class QQZone implements Platform {
        private final ShareMedia media;

        public String appId = null;

        public String appKey = null;

        public QQZone(ShareMedia media) {
            this.media = media;
        }

        @Override
        public ShareMedia getName() {
            return this.media;
        }

        @Override
        public void parse(JSONObject var1) {
            this.appId = var1.optString("key");
            this.appKey = var1.optString("secret");
        }

        @Override
        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.appId) && !TextUtils.isEmpty(this.appKey);
        }
    }

    public static class SinaWeibo implements Platform{
        private final ShareMedia media;
        public String appKey = null;
        public String appSecret = null;

        public SinaWeibo(ShareMedia media) {
            this.media = media;
        }

        @Override
        public ShareMedia getName() {
            return this.media;
        }

        @Override
        public void parse(JSONObject var1) {
            this.appKey = var1.optString("key");
            this.appSecret = var1.optString("secret");
        }

        @Override
        public boolean isConfigured() {
            return !TextUtils.isEmpty(this.appKey) && !TextUtils.isEmpty(this.appSecret);
        }
    }

}


