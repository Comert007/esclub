package com.ww.android.esclub.activity.cart;

import android.content.Context;
import android.content.Intent;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.adapter.cart.CommitOrderAdapter;
import com.ww.android.esclub.vm.views.CommitOrderView;
import com.ww.mvp.model.VoidModel;

import java.util.Arrays;

/**
 * Created by feng on 2017/6/21.
 * 订单提交支付界面
 */

public class CommitOrderActivity extends BaseActivity<CommitOrderView,VoidModel> {

    private CommitOrderAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CommitOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commit_order;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }

    @Override
    protected void init() {

        adapter = new CommitOrderAdapter(this);
        v.getCrv().setAdapter(adapter);
        adapter.addList(Arrays.asList("1","1"));

    }
}
