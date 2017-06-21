package com.ww.android.esclub.vm.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import com.ww.android.esclub.R;
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

    public String ALIPAY ="alipay";
    public String WEICHAT ="weichat";

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
                setPayWay(WEICHAT);
            }
        });

        rbAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayWay(ALIPAY);
            }
        });
    }

    public CustomRecyclerView getCrv() {
        return crv;

    }

    public void setPayWay(String payWay){
        if (TextUtils.equals(payWay,ALIPAY)){
            rbWeiChat.setChecked(false);
            rbAlipay.setChecked(true);
        }else {
            rbWeiChat.setChecked(true);
            rbAlipay.setChecked(false);
        }
    }

    public String getPayway(){
        if (rbWeiChat.isChecked()){
            return WEICHAT;
        }else {
            return ALIPAY;
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }


}
