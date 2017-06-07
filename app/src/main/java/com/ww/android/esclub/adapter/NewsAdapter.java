package com.ww.android.esclub.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.listener.OnItemClickListener;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/7.
 */

public class NewsAdapter extends RvAdapter<String> {

    private OnItemClickListener onItemClickListener;


    public NewsAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_news;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int i, View view) {
        return new NewsViewHolder(view);
    }


    class NewsViewHolder extends RvViewHolder<String> {
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_explain)
        TextView tvExplain;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_read)
        TextView tvRead;

        public NewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, String s) {

            ImageLoader.getInstance().displayImage
                    ("http://img0.178.com/overwatch/201706/290815621776/290816386308.jpg", ivThumb,
                    BaseApplication.getDisplayImageOptions(R.mipmap.test));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(getAdapterPosition(),v);
                    }
                }
            });
        }
    }
}
