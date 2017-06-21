package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.view.View;

import com.ww.android.esclub.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/21.
 */

public class CommitOrderAdapter extends RvAdapter<String> {

    public CommitOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_commit_order;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int i, View view) {
        return new CommitOrderViewHolder(view);
    }

    class CommitOrderViewHolder extends RvViewHolder<String>{
        public CommitOrderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, String s) {

        }
    }
}
