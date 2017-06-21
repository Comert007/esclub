package com.ww.android.esclub.adapter.guess;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.guess.GuessEditActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/21.
 */

public class GuessDetailAdapter extends RvAdapter<String> {

    private String OTHER="其他";
    private int BUBBLE_NUM=5;
    private GuessDetailViewHolder viewHolder;



    public GuessDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_guess_detail;
    }

    public GuessDetailViewHolder getViewHolder() {
        return viewHolder;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int i, View view) {
        return new GuessDetailViewHolder(view);
    }

   public class GuessDetailViewHolder extends RvViewHolder<String>{
        @BindView(R.id.iv_arrow)
        ImageView ivArrow; //指向
        @BindView(R.id.ll_guess_content)
        LinearLayout llGuessContent; //竞猜item
        @BindView(R.id.ll_guess_detail)
        LinearLayout llGuessDetail; //竞猜隐藏部分
        @BindView(R.id.ll_guess_title)
        LinearLayout llGuessTitle; //竞猜title
        @BindView(R.id.ll_bubble)
        LinearLayout llBubble;  //积分选择，最多五个包括自定义

        private boolean visible;



        public GuessDetailViewHolder(View itemView) {
            super(itemView);
            ivArrow.setBackgroundResource(R.mipmap.ic_arrow_right);
            llGuessTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionManager.beginDelayedTransition(llGuessContent);
                    visible = !visible;
                    if (visible){
                        ivArrow.setBackgroundResource(R.mipmap.ic_arrow_down);
                    }else {
                        ivArrow.setBackgroundResource(R.mipmap.ic_arrow_right);
                    }
                    llGuessDetail.setVisibility(visible? View.VISIBLE : View.GONE);
                }
            });

            createBubble(BUBBLE_NUM);
        }

        @Override
        public void onBindData(int i, String s) {
        }



        public void changeBubble(String num){
            int size = llBubble.getChildCount();
            if (size==BUBBLE_NUM){
                View vi = llBubble.getChildAt(size-1);
                TextView tvScale =ButterKnife.findById(vi,R.id.tv_scale);
                tvScale.setText(num);
                if (TextUtils.equals(num,OTHER)){
                    recovery();
                }
            }
        }

        private void recovery(){
            for (int j = 0; j < BUBBLE_NUM; j++) {
                View vi = llBubble.getChildAt(j);
                TextView tvScale =ButterKnife.findById(vi,R.id.tv_scale);
                tvScale.setSelected(false);
            }
        }

        //构建气泡
        private void createBubble(final int size){
            for (int i = 0; i < size; i++) {
                View bubbleView = LayoutInflater.from(getContext()).inflate(R.layout.item_bubble,null);
                final TextView tvScale =ButterKnife.findById(bubbleView,R.id.tv_scale);
                ScreenUtil.scale(bubbleView);
                if (i<size-1){
                    tvScale.setText(((i+1)*10)+"");
                }else {
                    tvScale.setText(OTHER);
                }


                llBubble.addView(bubbleView);

            }
            final int count =  llBubble.getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = llBubble.getChildAt(i);

                final int current = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (int j = 0; j < count; j++) {
                            View vi = llBubble.getChildAt(j);
                            TextView tvScale =ButterKnife.findById(vi,R.id.tv_scale);
                            tvScale.setSelected(false);
                        }
                        TextView tvScale =ButterKnife.findById(view,R.id.tv_scale);
                        tvScale.setSelected(true);
                        if (current ==size-1){
                            GuessEditActivity.start((Activity) getContext(),GuessEditActivity.REQUEST_EDIT_CODE,position);
                            viewHolder = GuessDetailViewHolder.this;
                        }

                    }
                });
            }



        }

    }
}
