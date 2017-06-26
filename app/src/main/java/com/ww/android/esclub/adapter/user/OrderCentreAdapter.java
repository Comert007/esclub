package com.ww.android.esclub.adapter.user;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.user.OrderCentreBean;
import com.ww.android.esclub.listener.OnItemClickListener;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderCentreAdapter extends RvAdapter<OrderCentreBean> {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OrderCentreAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_user_order_centre;
    }

    @Override
    protected RvViewHolder<OrderCentreBean> getViewHolder(int i, View view) {
        return new OrderCentreViewHolder(view);
    }

    class OrderCentreViewHolder extends RvViewHolder<OrderCentreBean>{
        @BindView(R.id.riv_thumb)
        RoundImageView rivThumb;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public OrderCentreViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(position,v);
                    }
                }
            });
        }

        @Override
        public void onBindData(int i, OrderCentreBean bean) {
            ImageLoader.getInstance().displayImage(bean.getCover(),rivThumb,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default));
            tvTitle.setText(bean.getTitle());
            tvNum.setText("x"+bean.getType_num());
            tvPrice.setText("￥ "+bean.getPrice());
        }
    }
}
