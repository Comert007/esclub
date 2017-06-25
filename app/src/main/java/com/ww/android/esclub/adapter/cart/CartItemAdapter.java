package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.cart.GoodsItem;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/20.
 */

public class CartItemAdapter extends RvAdapter<GoodsItem> {

    public static int CART_LINE = 0;
    public static int CART_ITEM = 1;


    private OnCartAction onCartAction;

    public void setOnCartAction(OnCartAction onCartAction) {
        this.onCartAction = onCartAction;
    }

    public CartItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return getList().get(position).getCartType();
    }


    @Override
    protected int getItemLayoutResId(int viewType) {
        if (viewType == CART_LINE) {
            return R.layout.item_cart_line;
        } else if (viewType == CART_ITEM) {
            return R.layout.item_cart_classify;
        } else throw new RuntimeException("the layout xml is not exist.");
    }

    @Override
    protected RvViewHolder<GoodsItem> getViewHolder(int viewType, View view) {
        if (viewType == CART_LINE)
            return new CartLineViewHolder(view);
        else if (viewType == CART_ITEM) {
            return new CartItemViewHolder(view);
        } else
            throw new RuntimeException("Cart Item Adapter is wrong! Please check if the view or " +
                    "viewHolder is exist.");
    }

    class CartItemViewHolder extends RvViewHolder<GoodsItem> {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_month_num)
        TextView tvMonthNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.riv_thumb)
        RoundImageView rivThumb;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.btn_minus)
        Button btnMinus;
        @BindView(R.id.btn_add)
        Button btnAdd;
        @BindView(R.id.ll_left)
        LinearLayout llLeft;// 减号部分

        public CartItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, GoodsItem item) {

            ImageLoader.getInstance().displayImage(item.getCover(),rivThumb,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default));

            tvName.setText(item.getName());
            tvMonthNum.setText("月销 "+item.getMonth_sale());
            tvPrice.setText("￥ "+item.getPrice());

            String text = tvNum.getText().toString();
            if ("0".equals(text)) {
                llLeft.setVisibility(View.GONE);
            } else {
                llLeft.setVisibility(View.VISIBLE);
            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = tvNum.getText().toString();
                    int originalNum = Integer.valueOf(text);
                    tvNum.setText((originalNum + 1) + "");
                    llLeft.setVisibility(View.VISIBLE);
                    if (onCartAction != null) {
                        onCartAction.onAdd(position, v);
                    }
                }
            });

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = tvNum.getText().toString();
                    int originalNum = Integer.valueOf(text);
                    tvNum.setText((originalNum - 1) + "");
                    if ("1".equals(text)) {
                        llLeft.setVisibility(View.GONE);
                    }

                    if (onCartAction != null) {
                        onCartAction.onMinus(position, v);
                    }
                }
            });
        }

    }

    class CartLineViewHolder extends RvViewHolder<GoodsItem> {
        @BindView(R.id.tv_classify)
        TextView tvClassify;

        public CartLineViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, GoodsItem item) {
            tvClassify.setText(item.getClassifyName());
        }
    }

    public interface OnCartAction {
        void onAdd(int position, View view);

        void onMinus(int position, View view);
    }
}
