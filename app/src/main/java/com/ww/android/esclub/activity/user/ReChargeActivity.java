package com.ww.android.esclub.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.trello.rxlifecycle.ActivityEvent;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.bean.pay.AlipayBean;
import com.ww.android.esclub.bean.pay.WechatPayBean;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.vm.models.UserModel;
import com.ww.android.esclub.vm.views.user.ReChargeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * Created by feng on 2017/6/26.
 */

public class ReChargeActivity extends BaseActivity<ReChargeView,UserModel> {
    private String payNo;
    public static int requestCode = 0x12;

    private int payPrice;

    public static void start(Activity context) {
        Intent intent = new Intent(context, ReChargeActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.tv_recharge_explain)
    public void onExplain(){
        WebViewActivity.start(this,Constant.RECHARGE_EXPLAIN);
    }

    @OnClick(R.id.btn_recharge)
    public void onReCharge(){
        String price="0";
        String moneyWay = v.getMoneyWay();
        if ("1".equals(moneyWay)){
            price = v.getTvPrice().getText().toString();
        }else {
            price = v.getEtPrice().getText().toString();
            if (!v.check()){
                showToast("最小充值金额为1元");
                return;
            }
        }

        payPrice = Integer.valueOf(price);
        if (Constant.ALIPAY.equals(v.getPayWay())){
            onAlipay(price);
        }else {
            onWeChat(price);
        }

    }

    private void onAlipay(String price){
        m.onAlipay(price, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<AlipayBean>(this,true) {
            @Override
            public void onNext(AlipayBean alipayBean) {
                onUserInfo();
//                payNo= alipayBean.getPayment_no();
//                WWAlipay.cretateAlipay(ReChargeActivity.this, new WWAlipay.AlipayListener() {
//                    @Override
//                    public void onPaySuccess(String info) {
//                        payComplete();
//                    }
//
//                    @Override
//                    public void onPayFail(String status, String errInfo) {
//                        showToast(getString(R.string.toast_recharge_fail));
//                        finish();
//
//                    }
//                }).pay(payNo);
            }
        });
    }

    private void onWeChat(String price){
        m.onWeChat(price, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<WechatPayBean>(this,true) {

            @Override
            public void onNext(WechatPayBean wechatPayBean) {
                onUserInfo();
//                payNo = wechatPayBean.getPayment_no();
//                WxPayUtils.pay(ReChargeActivity.this, wechatPayBean.getPayment_info());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postEvent(Bundle bundle) {
        if ("wxpay".equals(bundle.getString("action"))) {
            final boolean status = bundle.getBoolean("status", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (status) {
                        onUserInfo();
                    } else {
                        showToast(getString(R.string.toast_recharge_fail));
                        finish();
                    }
                }
            }, 500);
        }
    }


    private void onUserInfo(){
        m.onUserInfo(bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<UserBean>(this,true) {
            @Override
            public void onNext(UserBean userBean) {
                BaseApplication.getInstance().setUserBean(userBean);
                payComplete();
            }
        });
    }

    public void payComplete(){

        showToast(getString(R.string.toast_recharge_success));
        Intent intent = new Intent();
        ReChargeActivity.this.setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }
}
