package com.ww.android.esclub.vm.views.cart;

import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.android.esclub.vm.views.RefreshView;

import butterknife.BindView;

/**
 * Created by feng on 2017/6/21.
 */

public class GuessView extends RefreshView {

    @BindView(R.id.iv_level)
    ImageView ivLevel;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_exp)
    TextView tvExp;
    @BindView(R.id.tv_critical_exp)
    TextView tvCriticalExp;
    @BindView(R.id.tv_guess_num)
    TextView tvGuessNum;
    @BindView(R.id.tv_guess_win_rate)
    TextView tvGuessWinRate;
    @BindView(R.id.tv_point)
    TextView tvPoint;


    @Override
    public void attach() {
        super.attach();

    }

    public void showInfo(){
        UserBean user = BaseApplication.getInstance().getUserBean();
        if (user!=null){
            showLevelIcon(user);
            tvRank.setText(user.getRank());
            tvExp.setText(user.getExp());
            tvCriticalExp.setText("/"+user.getCritical_exp());
            tvGuessNum.setText(user.getGuess_num());
            tvGuessWinRate.setText(user.getGuess_win_rate()+"%");
            tvPoint.setText(user.getPoint());
        }
    }

    private void showLevelIcon(UserBean user){
        String level = user.getLevel();
        if ("1".equals(level)){
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.vip_levellarge1,
                    ivLevel);
        }else if ("2".equals(level)){
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.vip_levellarge2,
                    ivLevel);
        }else if ("3".equals(level)){
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.vip_levellarge3,
                    ivLevel);
        }else if ("4".equals(level)){
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.vip_levellarge4,
                    ivLevel);
        }else if ("5".equals(level)){
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.vip_levellarge5,
                    ivLevel);
        }else if ("6".equals(level)){
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.vip_levellarge6,
                    ivLevel);
        }else if ("7".equals(level)){
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.vip_levellarge7,
                    ivLevel);
        }
    }
}
