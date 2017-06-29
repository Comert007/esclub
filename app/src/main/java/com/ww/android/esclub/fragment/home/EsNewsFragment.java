package com.ww.android.esclub.fragment.home;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.trello.rxlifecycle.FragmentEvent;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.vm.models.home.HomeModel;
import com.ww.mvp.view.VoidView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by feng on 2017/6/7.
 */

public class EsNewsFragment extends BaseFragment<VoidView, HomeModel> implements TextView
        .OnEditorActionListener {
    private String url = "http://news.maxjia.com/maxnews/app/detail/ow/71579";
    private String id;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.et_comment)
    EditText etComment;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_esnews;
    }

    @Override
    protected void init() {
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

        etComment.setOnEditorActionListener(this);

        Bundle bundle = getArguments();
        url = bundle.getString("content_url", url);
        id = bundle.getString("id","0");

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.btn_send)
    public void onSend(){
        onComment();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            onComment();
        }
        return true;
    }

    private void onComment(){
        String text = etComment.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            m.onComment(id, text, bindUntilEvent(FragmentEvent.DESTROY), new HttpSubscriber<String>(getContext(),true) {
                @Override
                public void onNext(String s) {
                    if (!TextUtils.isEmpty(s)){
                        showToast("评论成功");
                    }
                }
            });
        } else {
            showToast("评论不能为空");
        }
    }
}
