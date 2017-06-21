package com.ww.android.esclub.adapter.guess;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.android.esclub.listener.OnItemClickListener;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/21.
 */

public class GuessAdapter extends RvAdapter<String>{

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GuessAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_guess;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int i, View view) {
        return new GuessViewHolder(view);
    }

    class  GuessViewHolder extends RvViewHolder<String>{
        @BindView(R.id.tv_guess_tip)
        TextView tvGuessTip;

        public GuessViewHolder(View itemView) {
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
        public void onBindData(int i, String s) {
            String tip;
            int color ;
            if (s.equals("1")){
                color = getContext().getResources().getColor(R.color.color_0b86ee);
                 tip = getContext().getResources().getString(R.string.guess_voting);
            }else if (s.equals("2")){
                color = getContext().getResources().getColor(R.color.color_fb4e44);
                tip = getContext().getResources().getString(R.string.guess_visiting);
            }else {
                color = getContext().getResources().getColor(R.color.color_b3b3b3);
                tip = getContext().getResources().getString(R.string.guess_completed);
            }

            tvGuessTip.setTextColor(color);
            tvGuessTip.setText(tip);
        }
    }
}
