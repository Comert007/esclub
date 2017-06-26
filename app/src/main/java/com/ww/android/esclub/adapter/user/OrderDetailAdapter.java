package com.ww.android.esclub.adapter.user;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.user.OrderDetailBean;
import com.ww.android.esclub.bean.user.OrderDetailGoodsBean;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderDetailAdapter extends RvAdapter<OrderDetailBean> {

    public OrderDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_commit_order;
    }

    @Override
    protected RvViewHolder<OrderDetailBean> getViewHolder(int i, View view) {
        return new OrderDetailViewHolder(view);
    }

    class OrderDetailViewHolder extends RvViewHolder<OrderDetailBean>{
        @BindView(R.id.riv_thumb)
        RoundImageView rivThumb;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_num)
        TextView tvNum;


        public OrderDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, OrderDetailBean bean) {
            OrderDetailGoodsBean goods=  bean.getGoods();
            ImageLoader.getInstance().displayImage(goods.getCover(),rivThumb,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default));
            tvName.setText(goods.getName());
            tvPrice.setText("￥ "+goods.getPrice());
            tvNum.setText("x"+bean.getNum());
        }
    }
}
