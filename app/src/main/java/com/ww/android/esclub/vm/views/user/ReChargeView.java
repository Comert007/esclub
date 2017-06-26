package com.ww.android.esclub.vm.views.user;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.utils.AppUtils;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by feng on 2017/6/26.
 */

public class ReChargeView implements IView,View.OnFocusChangeListener {
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_price)
    TextView etPrice;
    @BindView(R.id.rb_alipay)
    RadioButton rbAliPay;
    @BindView(R.id.rb_weichat)
    RadioButton rbWeChat;


    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this,view);
        tvPrice.setOnFocusChangeListener(this);
        etPrice.setOnFocusChangeListener(this);

    }

    @Override
    public void onResume() {

    }

    @OnClick({R.id.rb_weichat,R.id.rb_alipay})
    public void onSelectPayWay(View view){
        switch (view.getId()){
            case R.id.rb_weichat:
                rbWeChat.setChecked(true);
                rbAliPay.setChecked(false);
                break;
            case R.id.rb_alipay:
                rbWeChat.setChecked(false);
                rbAliPay.setChecked(true);
                break;
        }
    }

    public String getMoneyWay(){
        if (tvPrice.isSelected()){
            return "1";
        }else {
            return "2";
        }
    }

    public String getPayWay(){
        if (rbAliPay.isChecked()){
            return Constant.ALIPAY;
        }else {
            return Constant.WEICHAT;
        }
    }

    public TextView getEtPrice() {
        return etPrice;
    }

    public TextView getTvPrice() {
        return tvPrice;
    }

    public boolean check() {
        boolean canReCharge = false;
        String text = etPrice.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            int price = Integer.valueOf(text);
            if (price < 1) {
                canReCharge = false;
            } else {
               canReCharge = true;
            }
        }
        return canReCharge;
    }


    private void onSelected(boolean selected){
        tvPrice.setSelected(selected);
        etPrice.setSelected(!selected);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            if (v==tvPrice){
                onSelected(true);
                AppUtils.hideSoftKeyBord(v);
                etPrice.setText("");
            }
            if (v==etPrice){
                onSelected(false);
            }
        }

    }
}
