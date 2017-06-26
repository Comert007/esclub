package com.ww.android.esclub.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.fragment.CartFragment;
import com.ww.android.esclub.fragment.GuessFragment;
import com.ww.android.esclub.fragment.HomeFragment;
import com.ww.android.esclub.fragment.UserFragment;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import ww.com.core.adapter.MenuTabAdapter;

public class MainActivity extends BaseActivity<VoidView, VoidModel> {
    @BindViews({R.id.tab_home_layout, R.id.tab_guess_layout, R.id.tab_cart_layout, R.id
            .tab_user_layout})
    List<View> menus;
    @BindViews({R.id.tab_home_image, R.id.tab_guess_image, R.id.tab_cart_image, R.id
            .tab_user_image})
    List<View> images;
    @BindViews({R.id.tab_home_text, R.id.tab_guess_text, R.id.tab_cart_text, R.id.tab_user_text})
    List<View> texts;

    private MenuTabAdapter adapter;
    private List<Fragment> fragments;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new GuessFragment());
        fragments.add(new CartFragment());
        fragments.add(new UserFragment());

        adapter = new MenuTabAdapter(this, menus, fragments, R.id.main_content);
        adapter.setOnMenuClickListener(new MenuTabAdapter.OnMenuClickListener() {
            @Override
            public void onDoubleClick(View view) {

            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tab_home_layout:
                        adapter.changeMenuStatus(0);
                        changeMenuStatus(0);
                        adapter.changeMenu(0);
                        break;
                    case R.id.tab_guess_layout:
                        adapter.changeMenuStatus(1);
                        changeMenuStatus(1);
                        adapter.changeMenu(1);
                        break;
                    case R.id.tab_cart_layout:
                        adapter.changeMenuStatus(2);
                        changeMenuStatus(2);
                        adapter.changeMenu(2);
                        break;
                    case R.id.tab_user_layout:
                        adapter.changeMenuStatus(3);
                        adapter.changeMenu(3);
                        changeMenuStatus(3);
                        break;
                }
            }
        });


        adapter.changeMenuStatus(0);
        changeMenuStatus(0);
        adapter.changeMenu(0);
    }

    public void changeMenuStatus(int index) {
        int imageSize = images.size();
        for (int i = 0; i < imageSize; i++) {
            if (i == index) {
                this.images.get(i).setSelected(true);
                this.texts.get(i).setSelected(true);
            } else {
                this.images.get(i).setSelected(false);
                this.texts.get(i).setSelected(false);
            }
        }
    }

}
