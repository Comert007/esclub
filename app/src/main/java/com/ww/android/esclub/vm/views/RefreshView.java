package com.ww.android.esclub.vm.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.ww.android.esclub.R;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by feng on 2017/6/7.
 */

public class RefreshView implements IView {

    @Nullable
    @BindView(R.id.crv)
    CustomRecyclerView crv;
    @Nullable
    @BindView(R.id.csr)
    CustomSwipeRefreshLayout csr;

    protected Context context;

    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this, view);
        context = view.getContext();
        attach();
    }


    @Optional
    public void attach() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        crv.setLayoutManager(manager);
        crv.setItemAnimator(new DefaultItemAnimator());

        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, null);
        ScreenUtil.scale(emptyView);
        crv.addEmpty(emptyView);

        csr.setRefreshView(crv);
        csr.setEnableRefresh(false);

    }

    public CustomRecyclerView getCrv() {
        return crv;
    }

    public CustomSwipeRefreshLayout getCsr() {
        return csr;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

}
