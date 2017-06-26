package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.cart.GoodsItem;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

import static com.ww.android.esclub.R.id.tv_num;

/**
 * Created by feng on 2017/6/21.
 */

public class CommitOrderAdapter extends RvAdapter<GoodsItem> {

    public CommitOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_commit_order;
    }

    @Override
    protected RvViewHolder<GoodsItem> getViewHolder(int i, View view) {
        return new CommitOrderViewHolder(view);
    }

    class CommitOrderViewHolder extends RvViewHolder<GoodsItem>{
        @BindView(R.id.riv_thumb)
        RoundImageView rivThumb;
        @BindView(tv_num)
        TextView tvNum;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public CommitOrderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, GoodsItem item) {
            ImageLoader.getInstance().
                    displayImage(item.getCover(), rivThumb, BaseApplication.getDisplayImageOptions(R.mipmap.ic_default));
            tvPrice.setText("￥ "+item.getPrice());
            tvName.setText(item.getName());
            tvNum.setText("x"+item.getNum());
        }
    }
}
