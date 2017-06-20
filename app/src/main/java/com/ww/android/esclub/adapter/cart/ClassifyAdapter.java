package com.ww.android.esclub.adapter.cart;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.CartClassifyBean;
import com.ww.android.esclub.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/19.
 */

public class ClassifyAdapter extends RvAdapter<CartClassifyBean> {

    private OnItemClickListener onItemClickListener;
    public ClassifyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_classify;
    }

    @Override
    protected RvViewHolder<CartClassifyBean> getViewHolder(int i, View view) {
        return new ClassifyViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ClassifyViewHolder extends RvViewHolder<CartClassifyBean>{
        @BindView(R.id.ll_classify)
        LinearLayout llClassify;
        @BindView(R.id.iv_classify)
        ImageView ivClassify;
        @BindView(R.id.tv_classify)
        TextView tvClassify;


        public ClassifyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(final int position, CartClassifyBean bean) {
            tvClassify.setSelected(bean.isSelected());
            llClassify.setSelected(bean.isSelected());

            ivClassify.setBackgroundResource(bean.getIcon());
            tvClassify.setText(bean.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recoverySelected(getAdapterPosition());
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(position,itemView);
                    }
                }
            });
//            if (position==0){
//                itemView.performClick();
//            }

        }
    }

    public void recoverySelected(int position){
        List<CartClassifyBean> been = getList();
        for (CartClassifyBean classifyBean : been) {
            classifyBean.setSelected(false);
        }
        CartClassifyBean bean =been.get(position);
        bean.setSelected(true);
        been.set(position,bean);

        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);

    }
}
