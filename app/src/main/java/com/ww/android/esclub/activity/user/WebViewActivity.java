package com.ww.android.esclub.activity.user;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.bean.start.SystemConfigBean;
import com.ww.android.esclub.bean.start.WebViewInfoBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.widget.TitleView;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import ww.com.core.utils.PhoneUtils;

/**
 * Created by feng on 2017/6/27.
 */

public class WebViewActivity extends BaseActivity<VoidView, VoidModel> {
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.web_view)
    WebView webView;
    private String url;


    public static void start(Context context, String webpage) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("webpage", webpage);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init() {
        titleView.setTitle(getPageTitle());


        webView.loadUrl(url);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                view.loadUrl(request.getUrl().toString(), getCustomHeaders());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url, getCustomHeaders());
                return true;
            }
        });
    }


    private Map<String, String> getCustomHeaders()
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("APP_VERSION", PhoneUtils.getAppVer(BaseApplication.getInstance()));
        return headers;
    }

    private String getPageTitle() {
        String page = getIntent().getStringExtra("webpage");
        if (TextUtils.isEmpty(page)) {
            return "说明";
        }

        SystemConfigBean configBean = BaseApplication.getInstance().getSystemConfigBean();
        WebViewInfoBean webViewInfoBean = configBean.webview_info;

        switch (page) {
            case Constant.CONTACT_US:
                if (webViewInfoBean != null)
                    url = webViewInfoBean.getContact_us();
                return "联系我们";
            case Constant.ABOUT_US:
                if (webViewInfoBean != null)
                    url = webViewInfoBean.getAbout_us();
                return "关于我们";
            case Constant.TERMS_OF_USE:
                if (webViewInfoBean != null)
                    url = webViewInfoBean.getTerms_of_use();
                return "使用条款";
            case Constant.GUESS_RULES:
                if (webViewInfoBean != null)
                    url = webViewInfoBean.getGuess_rules();
                return "竞猜规则";
            case Constant.RECHARGE_EXPLAIN:
                if (webViewInfoBean != null)
                    url = webViewInfoBean.getRecharge_explain();
                return "充值说明";
            case Constant.BOOK_TABLE_EXPLAIN:
                if (webViewInfoBean != null)
                    url = webViewInfoBean.getBook_table_explain();
                return "订座说明";
            default:
                return "说明";
        }

    }

}
