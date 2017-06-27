package com.ww.android.esclub.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.ww.android.esclub.R;
import com.ww.android.esclub.adapter.TranslateTabAdapter;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.fragment.translate.NewsFragment;
import com.ww.android.esclub.vm.models.home.HomeModel;
import com.ww.android.esclub.widget.TranslateTabBar;
import com.ww.mvp.view.VoidView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by feng on 2017/6/7.
 */

public class HomeFragment extends BaseFragment<VoidView,HomeModel> {

    @BindView(R.id.translate_tab_home)
    TranslateTabBar translateTabBar;
    @BindView(R.id.viewpager_tab_home)
    ViewPager viewPager;

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private TranslateTabAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
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
        fragmentManager = getChildFragmentManager();
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

        fragments.add(createFragment(Constant.NEWS_LATEST));
        fragments.add(createFragment(Constant.NEWS_ACTIVITIES));
        fragments.add(createFragment(Constant.NEWS_VIDEO));
    }

    private NewsFragment createFragment(String type){
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newsFragment.setArguments(bundle);
        return newsFragment;
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
