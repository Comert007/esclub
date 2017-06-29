package com.ww.android.esclub.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.home.CommentBean;
import com.ww.android.esclub.bean.start.UserBean;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/25.
 */

public class CommentAdapter extends RvAdapter<CommentBean> {

    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_comment;
    }

    @Override
    protected RvViewHolder<CommentBean> getViewHolder(int i, View view) {
        return new CommentViewHolder(view);
    }

    class CommentViewHolder extends RvViewHolder<CommentBean>{

        @BindView(R.id.riv_header)
        RoundImageView rivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int i, CommentBean bean) {
            ImageLoader.getInstance().
                    displayImage(bean.getAvatar(),rivHeader, BaseApplication.getDisplayImageOptions(R.mipmap.ic_header_default));
            tvName.setText(bean.getNickname());
            UserBean user = BaseApplication.getInstance().getUserBean();
            if (user!=null) {
                tvLevel.setText("lv." +user.getLevel());
            }
            tvTime.setText(bean.getCreated());
            tvComment.setText(bean.getComment());
        }
    }
}
