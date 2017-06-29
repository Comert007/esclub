package com.ww.android.esclub.activity.home;

import android.content.Context;
import android.content.Intent;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.vm.models.home.HomeModel;
import com.ww.mvp.view.VoidView;

import butterknife.BindView;


/**
 * Created by feng on 2017/6/29.
 */

public class VideoActivity extends BaseActivity<VoidView, HomeModel> {
    @BindView(R.id.wb_video)
    WebView wbVideo;

    private String videoUrl;

    public static void start(Context context, String video_url) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("video_url", video_url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video;
    }

    @Override
    protected void init() {
        videoUrl = getIntent().getStringExtra("video_url");

        onStartWeb(videoUrl);
    }

    private void onStartWeb(String videoUrl) {

        WebSettings settings = wbVideo.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadsImagesAutomatically(true);
        settings.setBuiltInZoomControls(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDisplayZoomControls(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);

        wbVideo.loadUrl(videoUrl);
        wbVideo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

        });
    }
}
