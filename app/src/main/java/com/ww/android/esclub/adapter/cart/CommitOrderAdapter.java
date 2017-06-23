package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.R;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

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
        @BindView(R.id.riv_thumb)
        RoundImageView rivThumb;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public CommitOrderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, String s) {
            switch (i){
                case 0:
                    ImageLoader.getInstance().
                            displayImage("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy" +
                                    "/it/u=1095058939,3797564670&fm=26&gp=0.jpg", rivThumb);
                    tvPrice.setText("￥ 67");
                    tvName.setText("Dolcetto");
                    break;
                case 1:
                    ImageLoader.getInstance().
                            displayImage("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3735227461,2457058780&fm=26&gp=0.jpg", rivThumb);

                    tvPrice.setText("￥ "+47);
                    tvName.setText("Barbera");

                    break;

            }
        }
    }
}
