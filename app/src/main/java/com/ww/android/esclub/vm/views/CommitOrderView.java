package com.ww.android.esclub.vm.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.android.esclub.config.Constant;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ww.com.core.widget.CustomRecyclerView;

/**
 * Created by feng on 2017/6/21.
 */

public class CommitOrderView implements IView {
    @NonNull
    @BindView(R.id.crv)
    CustomRecyclerView crv;
    @BindView(R.id.rb_weichat)
    RadioButton rbWeiChat;
    @BindView(R.id.rb_alipay)
    RadioButton rbAlipay;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.et_seat)
    EditText etSeat;
    @BindView(R.id.tv_seat_tip)
    TextView tvSeatTip;


    private Context context;



    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this,view);
        context = view.getContext();

        deployCrv();

        onRadioListener();
    }


    private void deployCrv(){
        LinearLayoutManager manager = new LinearLayoutManager(context);
        crv.setLayoutManager(manager);
        crv.setItemAnimator(new DefaultItemAnimator());
    }

    private void onRadioListener(){
        rbWeiChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayWay(Constant.WEICHAT);
            }
        });

        rbAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayWay(Constant.ALIPAY);
            }
        });
    }

    public void setSeat(){
        UserBean user = BaseApplication.getInstance().getUserBean();
        if (user!=user && user.getBook_table()!=null){
            tvSeatTip.setVisibility(View.GONE);
            etSeat.setText(user.getBook_table().getId());
            etSeat.setEnabled(false);
        }else {
            tvSeatTip.setVisibility(View.VISIBLE);
            etSeat.setEnabled(true);
        }
    }

    public CustomRecyclerView getCrv() {
        return crv;

    }

    public EditText getEtSeat() {
        return etSeat;
    }

    public void setPayWay(String payWay){
        if (TextUtils.equals(payWay,Constant.ALIPAY)){
            rbWeiChat.setChecked(false);
            rbAlipay.setChecked(true);
        }else {
            rbWeiChat.setChecked(true);
            rbAlipay.setChecked(false);
        }
    }

    public String getPayway(){
        if (rbWeiChat.isChecked()){
            return Constant.WEICHAT;
        }else {
            return Constant.ALIPAY;
        }
    }

    public void setTotalPrice(String totalPrice){
        tvTotalPrice.setText(totalPrice);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }


}
