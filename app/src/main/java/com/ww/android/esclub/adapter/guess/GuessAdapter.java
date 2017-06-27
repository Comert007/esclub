package com.ww.android.esclub.adapter.guess;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.guess.MatchBean;
import com.ww.android.esclub.listener.OnItemClickListener;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/21.
 */

public class GuessAdapter extends RvAdapter<MatchBean>{

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
    protected RvViewHolder<MatchBean> getViewHolder(int i, View view) {
        return new GuessViewHolder(view);
    }

    class  GuessViewHolder extends RvViewHolder<MatchBean>{
        @BindView(R.id.riv_team_one)
        RoundImageView rivTeamOne;
        @BindView(R.id.tv_team_one)
        TextView tvTeamOne;
        @BindView(R.id.riv_team_two)
        RoundImageView rivTeamTwo;
        @BindView(R.id.tv_team_two)
        TextView tvTeamTwo;
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
        public void onBindData(int i, MatchBean bean) {
            String tip;
            int color ;
             if ("1".equals(bean.getStatus())){
                color = getContext().getResources().getColor(R.color.color_fb4e44);
                tip = getContext().getResources().getString(R.string.guess_visiting);
            }else {
                color = getContext().getResources().getColor(R.color.color_b3b3b3);
                tip = getContext().getResources().getString(R.string.guess_completed);
            }
            tvGuessTip.setTextColor(color);
            tvGuessTip.setText(tip);

            ImageLoader.getInstance().displayImage(bean.getA_team_avatar(),rivTeamOne,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default));

            ImageLoader.getInstance().displayImage(bean.getB_team_avatar(),rivTeamTwo,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default));

            tvTeamOne.setText(bean.getA_team_name());
            tvTeamTwo.setText(bean.getB_team_name());

        }
    }
}
