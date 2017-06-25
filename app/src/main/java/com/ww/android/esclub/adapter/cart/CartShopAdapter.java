package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.view.View;

import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.cart.GoodsItem;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/21.
 */

public class CartShopAdapter extends RvAdapter<GoodsItem> {

    public CartShopAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_cart_shopping;
    }

    @Override
    protected RvViewHolder<GoodsItem> getViewHolder(int i, View view) {
        return new CartShopViewHolder(view);
    }

    class CartShopViewHolder  extends RvViewHolder<GoodsItem>{

        public CartShopViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, GoodsItem item) {

        }
    }
}
