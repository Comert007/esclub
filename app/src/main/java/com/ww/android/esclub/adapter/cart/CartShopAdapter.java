package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.cart.GoodsItem;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/21.
 */

public class CartShopAdapter extends RvAdapter<GoodsItem> {

    private OnShopAction onShopAction;

    public void setOnShopAction(OnShopAction onShopAction) {
        this.onShopAction = onShopAction;
    }

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

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_minus)
        Button btnMinus;
        @BindView(R.id.btn_add)
        Button btnAdd;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.ll_left)
        LinearLayout llLeft;// 减号部分

        public CartShopViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, final GoodsItem item) {
            tvName.setText(item.getName());
            tvPrice.setText("￥"+item.getPrice());
            tvNum.setText(item.getNum()+"");


            if (0==item.getNum()) {
                llLeft.setVisibility(View.GONE);
            } else {
                llLeft.setVisibility(View.VISIBLE);
            }


            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setNum(item.getNum()+1);
                    tvNum.setText(item.getNum()+"");
                    if (item.getNum()>=1){
                        llLeft.setVisibility(View.VISIBLE);
                    }
                    if (onShopAction!=null){
                        onShopAction.onShopAdd(position,v);
                    }
                }
            });

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    item.setNum(item.getNum()-1);
                    tvNum.setText(item.getNum()+"");

                    if (0 ==item.getNum()) {
                        llLeft.setVisibility(View.GONE);
                    }
                    if (onShopAction!=null){
                        onShopAction.onShopMinus(position,v);
                    }
                }
            });
        }
    }

    public interface OnShopAction {
        void onShopAdd(int position, View view);

        void onShopMinus(int position, View view);
    }
}
