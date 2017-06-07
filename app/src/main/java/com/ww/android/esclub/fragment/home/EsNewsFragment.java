package com.ww.android.esclub.fragment.home;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ww.android.esclub.R;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import butterknife.BindView;

/**
 * Created by feng on 2017/6/7.
 */

public class EsNewsFragment extends BaseFragment<VoidView,VoidModel> {
    private String url="http://news.maxjia.com/maxnews/app/detail/ow/71579";

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_esnews;
    }

    @Override
    protected void init() {
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
