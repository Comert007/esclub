package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.R;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/20.
 */

public class CartItemAdapter extends RvAdapter<String> {

    public int CART_LINE = 0;
    public int CART_ITEM = 1;


    private OnCartAction onCartAction;

    public void setOnCartAction(OnCartAction onCartAction) {
        this.onCartAction = onCartAction;
    }

    public CartItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 10 == 0 ? CART_LINE : CART_ITEM;
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
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        if (viewType == CART_LINE)
            return new CartLineViewHolder(view);
        else if (viewType == CART_ITEM) {
            return new CartItemViewHolder(view);
        } else
            throw new RuntimeException("Cart Item Adapter is wrong! Please check if the view or " +
                    "viewHolder is exist.");
    }

    class CartItemViewHolder extends RvViewHolder<String> {
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
        public void onBindData(int i, String s) {
            switch (i) {

                case 2:
                    ImageLoader.getInstance().
                            displayImage("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy" +
                                    "/it/u=1095058939,3797564670&fm=26&gp=0.jpg", rivThumb);
                    tvMonthNum.setText("月售 "+34);
                    tvPrice.setText("￥ "+67);
                    tvName.setText("Dolcetto");
                    break;
                case 3:
                    //https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3735227461,2457058780&fm=26&gp=0.jpg
                    ImageLoader.getInstance().
                            displayImage("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3735227461,2457058780&fm=26&gp=0.jpg", rivThumb);
                    tvMonthNum.setText("月售 "+15);
                    tvPrice.setText("￥ "+47);
                    tvName.setText("Barbera");
                    break;
                case 4:
                    ImageLoader.getInstance().
                            displayImage("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=21558253,3349323006&fm=26&gp=0.jpg", rivThumb);
                    tvMonthNum.setText("月售 "+25);
                    tvPrice.setText("￥ "+57);
                    tvName.setText("Brunello");
                    break;
                case 5:
                    ImageLoader.getInstance().
                            displayImage("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4266070482,2976323236&fm=26&gp=0.jpg", rivThumb);

                    tvMonthNum.setText("月售 "+34);
                    tvPrice.setText("￥ "+63);
                    tvName.setText("Cabernet Franc");
                    break;
            }
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

    class CartLineViewHolder extends RvViewHolder<String> {

        public CartLineViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, String s) {

        }
    }

    public interface OnCartAction {
        void onAdd(int position, View view);

        void onMinus(int position, View view);
    }
}
