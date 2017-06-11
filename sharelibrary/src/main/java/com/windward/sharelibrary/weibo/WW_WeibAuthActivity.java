package com.windward.sharelibrary.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.windward.sharelibrary.ShareMedia;
import com.windward.sharelibrary.SharePlatConfig;

public class WW_WeibAuthActivity extends Activity {

    private Context activity;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Context mContext;
    private Oauth2AccessToken mAccessToken;

    private String title;
    private String text;
    private String pageUrl;

    public static void start(Context context, String title, String text, String pageUrl) {
        Intent intent = new Intent(context, WW_WeibAuthActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        intent.putExtra("pageUrl", pageUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        mContext = this;

        initShareData();
        authWithWeibo();
    }

    private void initShareData() {
        title = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        pageUrl = getIntent().getStringExtra("pageUrl");

    }
    // 新浪微博认证
    private void authWithWeibo() {
        SharePlatConfig.SinaWeibo sinaWeibo = (SharePlatConfig.SinaWeibo) SharePlatConfig.configs
                .get(ShareMedia.SINA);
        mAuthInfo = new AuthInfo(this, sinaWeibo.appKey,
                WW_WeiboUtil.REDIRECT_URL, WW_WeiboUtil.SCOPE);

        WbSdk.install(this, mAuthInfo);

        mSsoHandler = new SsoHandler(WW_WeibAuthActivity.this);
        mSsoHandler.authorizeClientSso(new SelfWbAuthListener());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    private class SelfWbAuthListener implements WbAuthListener {

        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            WW_WeibAuthActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAccessToken = token;
                    if (mAccessToken.isSessionValid()) {
                        // 保存 Token 到 SharedPreferences
                        AccessTokenKeeper.writeAccessToken(WW_WeibAuthActivity.this, mAccessToken);
                        showToast("授权成功");
                        WW_WeiboUtil.startShare(WW_WeibAuthActivity.this, title, text, pageUrl);
                        finish();
                    }
                }
            });
        }

        @Override
        public void cancel() {
            showToast("授权取消");
            finish();
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            showToast("授权失败");
            finish();
        }
    }


    private void showToast(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
