package com.ww.android.esclub.vm.views.cart;

import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.guess.MatchBean;
import com.ww.android.esclub.vm.views.RefreshView;

import butterknife.BindView;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/21.
 */

public class GuessDetailView extends RefreshView {

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

    public void onInitInfo(MatchBean bean){
        String tip;
        int color ;
        if ("1".equals(bean.getStatus())){
            color = context.getResources().getColor(R.color.color_fb4e44);
            tip = context.getResources().getString(R.string.guess_visiting);
        }else {
            color = context.getResources().getColor(R.color.color_b3b3b3);
            tip = context.getResources().getString(R.string.guess_completed);
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
