package com.ww.android.esclub.adapter.guess;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.guess.GuessDetailActivity;
import com.ww.android.esclub.bean.guess.GuessBetBean;
import com.ww.android.esclub.bean.guess.GuessDetailBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2017/6/21.
 */

public class GuessDetailAdapter extends RvAdapter<GuessDetailBean> {

    private String OTHER = "其他";
    private int BUBBLE_NUM = 5;
    private int matchType=0;
    private GuessDetailViewHolder viewHolder;

    private OnVoteListener onVoteListener;
    public String point5 = "5";
    public String point10 = "10";
    public String point15="15";
    public String point20 ="20";

    public void setOnVoteListener(OnVoteListener onVoteListener) {
        this.onVoteListener = onVoteListener;
    }

    public GuessDetailAdapter(Context context,int type) {
        super(context);
        matchType = type;
    }

    @Override
    protected int getItemLayoutResId(int i) {
        return R.layout.item_guess_detail;
    }

    public GuessDetailViewHolder getViewHolder() {
        return viewHolder;
    }

    @Override
    protected RvViewHolder<GuessDetailBean> getViewHolder(int i, View view) {
        return new GuessDetailViewHolder(view);
    }

    public class GuessDetailViewHolder extends RvViewHolder<GuessDetailBean> implements View.OnFocusChangeListener{
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_total_num)
        TextView tvTotalNum;
        @BindView(R.id.tv_team_one)
        TextView tvTeamOne;
        @BindView(R.id.tv_team_two)
        TextView tvTeamTwo;
        @BindView(R.id.pb_guess)
        ProgressBar pbGuess;
        @BindView(R.id.tv_team_one_favorer)
        TextView tvTeamOneFavorer;
        @BindView(R.id.tv_team_two_favorer)
        TextView tvTeamTwoFavorer;
        @BindView(R.id.rg_team)
        RadioGroup rgTeam;
        @BindView(R.id.rb_team1)
        RadioButton rbTeam1;
        @BindView(R.id.rb_team2)
        RadioButton rbTeam2;
        @BindView(R.id.tv_guess_tip)
        TextView tvGuessTip;
//        @BindView(R.id.tv_vote1)
//        TextView tvVote1;
//        @BindView(R.id.tv_vote2)
//        TextView tvVote2;
//        @BindView(R.id.tv_vote3)
//        TextView tvVote3;
//        @BindView(R.id.tv_vote4)
//        TextView tvVote4;
        @BindView(R.id.et_vote)
        EditText etVote;
        @BindView(R.id.btn_vote)
        Button btnVote;


        @BindViews({R.id.tv_vote1,R.id.tv_vote2,R.id.tv_vote3,R.id.tv_vote4})
        List<TextView> votes;

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
                    if (visible) {
                        ivArrow.setBackgroundResource(R.mipmap.ic_arrow_down);
                    } else {
                        ivArrow.setBackgroundResource(R.mipmap.ic_arrow_right);
                    }
                    llGuessDetail.setVisibility(visible ? View.VISIBLE : View.GONE);
                }
            });

        }

        @Override
        public void onBindData(int i, final GuessDetailBean bean) {
            if (matchType == GuessDetailActivity.MATCH_HISTORY){
                btnVote.setVisibility(View.GONE);
                GuessBetBean bet =bean.getBet();
                String option = bet.getOption();
                if (Constant.OPTION_A.equals(option)){
                    rbTeam1.setChecked(true);
                }else {
                    rbTeam2.setChecked(true);
                }
                rbTeam1.setEnabled(true);
                rbTeam2.setEnabled(true);

                String point = bet.getPoint();
                tvGuessTip.setText("投注积分");

                onSelectHistory(point);
            }

            int pointA = Integer.valueOf(bean.getA_point());
            int pointB = Integer.valueOf(bean.getB_point());
            int rateA = pointA*100/pointB;
            int rateB = 100-rateA;
            tvId.setText((i+1)+"");
            tvTitle.setText(bean.getTitle());
            tvTotalNum.setText(""+(pointA+pointB));
            tvTeamOne.setText(bean.getOption_a());
            tvTeamTwo.setText(bean.getOption_b());
            pbGuess.setMax(pointA+pointB);
            pbGuess.setSecondaryProgress(pointA+pointB);
            pbGuess.setProgress(pointA);
            tvTeamOneFavorer.setText(rateA+"%");
            tvTeamTwoFavorer.setText(rateB+"%");
            for (TextView vote : votes) {
                vote.setOnFocusChangeListener(this);
            }
            etVote.setOnFocusChangeListener(this);
            etVote.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    if (TextUtils.equals("0",text)){
                        ToastUtil.showToast("投注积分最小为1");
                        return;
                    }
                    if (!TextUtils.isEmpty(text)){
                        bean.setPoint(text);
                    }
                }
            });

            btnVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onVoteListener!=null){
                        onVoteListener.onVote(position,v);
                    }
                }
            });

            rgTeam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId){
                        case R.id.rb_team1:
                            bean.setOption(Constant.OPTION_A);
                            break;
                        case R.id.rb_team2:
                            bean.setOption(Constant.OPTION_B);
                            break;
                    }
                }
            });
        }

        private void onSelectHistory(String point){
            if (point5.equals(point)){
                onSelectText(votes.get(0));
            }else if (point10.equals(point)){
                onSelectText(votes.get(1));
            }else if (point15.equals(point)){
                onSelectText(votes.get(2));
            }else if (point20.equals(point)){
                onSelectText(votes.get(3));
            }else {
                onSelectEdit();
            }

            for (TextView vote : votes) {
                vote.setEnabled(true);
            }
            etVote.setEnabled(true);
        }

        private void onSelectText(View v){
            for (int i = 0; i < votes.size(); i++) {
                TextView tv = votes.get(i);
                if (tv == v){
                    tv.setSelected(true);
                }
            }
        }

        private void onSelectEdit(){
            for (TextView vote : votes) {
                vote.setSelected(false);
            }
            etVote.setSelected(true);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (hasFocus){
                if (v instanceof TextView){
                    onSelectText(v);
                    bean.setPoint(((TextView) v).getText().toString());
                }

                if (v instanceof EditText){
                    onSelectEdit();
                }
            }
        }

    }

    public interface OnVoteListener{
        void onVote(int position ,View view);
    }
}
