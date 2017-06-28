package com.ww.android.esclub.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.adapter.TranslateTabAdapter;
import com.ww.android.esclub.fragment.home.CommentFragment;
import com.ww.android.esclub.fragment.home.EsNewsFragment;
import com.ww.android.esclub.widget.TranslateTabBar;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by feng on 2017/6/7.
 * Es新闻页面
 */

public class EsNewsActivity extends BaseActivity<VoidView,VoidModel> {


    @BindView(R.id.translate_tab_home)
    TranslateTabBar translateTabBar;
    @BindView(R.id.viewpager_tab_home)
    ViewPager viewPager;

    private String contentUrl;
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private TranslateTabAdapter adapter;

    public static void start(Context context,String url,String id) {
        Intent intent = new Intent(context, EsNewsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("content_url",url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_esnews;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }

    @Override
    public void onTitleRight() {
        super.onTitleRight();
        ShareActivity.start(this,contentUrl);
    }

    @Override
    protected void init() {
        translateTabBar.setOnTabChangeListener(new TranslateTabBar.OnTabChangeListener() {
            @Override
            public void onChange(int index) {
                viewPager.setCurrentItem(index);
            }
        });
//
        fragmentManager = getSupportFragmentManager();
        addFragment();
        adapter = new TranslateTabAdapter(fragmentManager, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    private void addFragment(){
        if (fragments==null){
            fragments = new ArrayList<>();
        }
        String id = getIntent().getStringExtra("id");
        contentUrl = getIntent().getStringExtra("content_url");
        fragments.add(createFragment(new EsNewsFragment(),id,contentUrl));
        fragments.add(createFragment(new CommentFragment(),id,contentUrl));
    }

    private Fragment createFragment(Fragment fragment,String id,String content_url){
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("content_url",content_url);
        fragment.setArguments(bundle);
        return fragment;
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int
                positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            translateTabBar.setCurrentIndex(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
