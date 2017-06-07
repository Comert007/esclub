package com.ww.android.esclub.fragment.translate;

import android.view.View;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.home.EsNewsActivity;
import com.ww.android.esclub.adapter.NewsAdapter;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.views.BannerView;
import com.ww.mvp.model.VoidModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by feng on 2017/6/7.
 * 最新
 */

public class NewsFragment extends BaseFragment<BannerView, VoidModel> implements OnItemClickListener{

    private List<String> urls;

    private NewsAdapter adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void init() {
        if (urls==null){
            urls = new ArrayList<>();
        }
        urls.add("http://ossweb-img.qq.com/upload/adw/image/1496714012/1496714012.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq.com/upload/adw/image/1496822855/1496822855.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq.com/upload/adw/image/1496800468/1496800468.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq.com/upload/adw/image/1496727632/1496727632.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq.com/upload/adw/image/1496718634/1496718634.jpg?_r=1496822856");

        v.setUrls(urls);
        v.startBanner();

        initData();
    }

    private void initData(){
        adapter = new NewsAdapter(getContext());
        adapter.setOnItemClickListener(this);
        v.getCrv().setAdapter(adapter);

        adapter.addList(Arrays.asList("1","2","3","4","5"));
    }

    @Override
    public void onItemClick(int position, View v) {
        EsNewsActivity.start(getContext());
    }
}
