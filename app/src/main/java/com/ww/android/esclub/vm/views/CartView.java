package com.ww.android.esclub.vm.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ww.android.esclub.R;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ww.com.core.widget.CustomRecyclerView;

/**
 * Created by feng on 2017/6/19.
 */

public class CartView implements IView {
    @BindView(R.id.crv_classify)
    CustomRecyclerView crvClassify; //一级分类
    @BindView(R.id.crv_items)
    CustomRecyclerView crvItems; //二级分类

    private LinearLayoutManager classifyManager;
    private LinearLayoutManager itemManager;

    private Context context;

    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this,view);
        context = view.getContext();

        deployCrv();
    }

    private void deployCrv(){
        classifyManager = new LinearLayoutManager(context);
        itemManager = new LinearLayoutManager(context);
        crvClassify.setLayoutManager(classifyManager);
        crvItems.setLayoutManager(itemManager);

        crvClassify.setItemAnimator(new DefaultItemAnimator());
        crvItems.setItemAnimator(new DefaultItemAnimator());
    }


    public CustomRecyclerView getCrvClassify() {
        return crvClassify;
    }

    public CustomRecyclerView getCrvItems() {
        return crvItems;
    }

    public LinearLayoutManager getClassifyManager() {
        return classifyManager;
    }

    public LinearLayoutManager getItemManager() {
        return itemManager;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }


}
