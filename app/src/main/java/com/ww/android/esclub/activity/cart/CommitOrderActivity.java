package com.ww.android.esclub.activity.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.trello.rxlifecycle.ActivityEvent;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.adapter.cart.CommitOrderAdapter;
import com.ww.android.esclub.bean.cart.GoodsItem;
import com.ww.android.esclub.bean.pay.AlipayBean;
import com.ww.android.esclub.bean.pay.WechatPayBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.pay.alipay.WWAlipay;
import com.ww.android.esclub.pay.wxpay.WxPayUtils;
import com.ww.android.esclub.vm.models.CartModel;
import com.ww.android.esclub.vm.views.CommitOrderView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * Created by feng on 2017/6/21.
 * 订单提交支付界面
 */

public class CommitOrderActivity extends BaseActivity<CommitOrderView,CartModel> {

    private CommitOrderAdapter adapter;
    private ArrayList<GoodsItem> goodsItems;
    private String test_id = "1";
    private String payNo;
    public static int requestCode = 0x11;

    public static void start(Activity context, ArrayList<GoodsItem> goodsItems) {
        Intent intent = new Intent(context, CommitOrderActivity.class);
        intent.putExtra("goods",goodsItems);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commit_order;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }

    @Override
    protected void init() {
        adapter = new CommitOrderAdapter(this);
        v.getCrv().setAdapter(adapter);

        goodsItems = (ArrayList<GoodsItem>) getIntent().getSerializableExtra("goods");
        if (goodsItems!=null&&goodsItems.size()>0){
            adapter.addList(goodsItems);
        }

        v.setTotalPrice(totalPrice(goodsItems)+"");
        v.setSeat();
    }


    @OnClick({R.id.tv_commit})
    public void onCommit(){
        String seatNo = v.getEtSeat().getText().toString().trim();
        if (TextUtils.isEmpty(seatNo)){
            showToast("请输入桌位编号");
            return;
        }

       if (TextUtils.equals(Constant.ALIPAY,v.getPayway())){
           onAlipay();
       }else {
           onWeChat();
       }
    }

    private void onAlipay(){
        m.onAlipay(test_id, goodsItems, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<AlipayBean>(this,true) {

            @Override
            public void onNext(AlipayBean alipayBean) {
//                alipay(alipayBean);
                payComplete();
            }
        });
    }

    private void alipay(AlipayBean alipayBean){
        payNo= alipayBean.getPayment_no();
        WWAlipay.cretateAlipay(CommitOrderActivity.this, new WWAlipay.AlipayListener() {
            @Override
            public void onPaySuccess(String info) {
                payComplete();
            }

            @Override
            public void onPayFail(String status, String errInfo) {
                showToast(getString(R.string.toast_recharge_fail));
                finish();

            }
        }).pay(payNo);
    }



    private void onWeChat(){
        m.onWeChat(test_id, goodsItems, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<WechatPayBean>(this,true) {
                    @Override
                    public void onNext(WechatPayBean wechatPayBean) {
//                        weChat(wechatPayBean);
                        payComplete();
                    }
                });
    }

    private void weChat(WechatPayBean wechatPayBean){
        payNo = wechatPayBean.getPayment_no();
        WxPayUtils.pay(CommitOrderActivity.this, wechatPayBean.getPayment_info());
    }



    private int totalPrice(ArrayList<GoodsItem> goodsItems){
        int total =0;
        for (GoodsItem goodsItem : goodsItems) {
           total+= goodsItem.getNum()*Integer.valueOf(goodsItem.getPrice());
        }

        return total;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postEvent(Bundle bundle) {
        if ("wxpay".equals(bundle.getString("action"))) {
            final boolean status = bundle.getBoolean("status", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (status) {
                       payComplete();
                    } else {
                        showToast(getString(R.string.toast_recharge_fail));
                        finish();
                    }
                }
            }, 500);
        }
    }


    public void payComplete(){
        showToast(getString(R.string.toast_recharge_success));
        Intent intent = new Intent();
        CommitOrderActivity.this.setResult(Activity.RESULT_OK,intent);
        finish();
    }

}
