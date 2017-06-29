package com.ww.android.esclub.fragment.home;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.trello.rxlifecycle.FragmentEvent;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.activity.home.VideoActivity;
import com.ww.android.esclub.bean.home.NewsItem;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.vm.models.home.HomeModel;
import com.ww.mvp.view.VoidView;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.Debug;

/**
 * Created by feng on 2017/6/7.
 */

public class EsNewsFragment extends BaseFragment<VoidView, HomeModel> implements TextView
        .OnEditorActionListener {
    private String url = "http://news.maxjia.com/maxnews/app/detail/ow/71579";
    private String id;
    private String type;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.fl_video)
    FrameLayout flVideo;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_esnews;
    }

    @Override
    protected void init() {
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

        etComment.setOnEditorActionListener(this);

        Bundle bundle = getArguments();
        final NewsItem item = (NewsItem) bundle.getSerializable("newsItem");
        type = bundle.getString("type");
        if (item!=null) {
            url = item.getContent_url();
            id = item.getId();
        }
        if (Constant.NEWS_VIDEO.equals(type)){
            flVideo.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(item.getBanner_cover(),ivVideo);
            ivVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String vid = onParseId(item.getVideo_url());
                    if (TextUtils.isEmpty(vid)){
                        VideoActivity.start(getContext(),item.getVideo_url());
                    }else {
                        onParseVideo(deployUrl(vid));
                    }
                }
            });
        }

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }


    //解析优酷视频地址
    private void onParseVideo(String url) {
        m.onYouKuAddress(url, bindUntilEvent(FragmentEvent.DESTROY),
                new HttpSubscriber<String>(getContext(), true) {
                    @Override
                    public void onNext(String s) {
                        Debug.d("s:" + s);
                        JSONObject json = JSON.parseObject(s);
                        JSONObject data = json.getJSONObject("data");
                        JSONArray stream= data.getJSONArray("stream");
                        JSONObject one = (JSONObject) stream.get(0);
                        JSONArray segs =one.getJSONArray("segs");
                        JSONObject seg = (JSONObject) segs.get(0);
                        String cdn_url = seg.getString("cdn_url");
                        if (TbsVideo.canUseTbsPlayer(getContext())){
                            TbsVideo.openVideo(getContext(),cdn_url);
                        }
                    }
                });
    }

    //合成视频地址
    private String  deployUrl(String path){
        String url = "https://ups.youku.com/ups/get" +
                ".json?vid="+path+"&ccode=0401&client_ip=192.168.1.1&utid" +
                "=iPMOEU7K4zsCAbTVE5gQjsU7&client_ts=1496141317&playlist_id=49981133&ob=1";
       return url;
    }

    //获取视频id
    private String onParseId(String path){
        Pattern p = Pattern.compile(".*id_(\\w+==)\\.html");
        Matcher m = p.matcher(path);
        String id = "";
        if (m.find()) {
            id = m.group(1);
        }
       return id;
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
                        etComment.setText("");
                        showToast("评论成功");
                        EventBus.getDefault().post(Constant.COMMENT_SUCCESS);
                    }
                }
            });
        } else {
            showToast("评论不能为空");
        }
    }


}
