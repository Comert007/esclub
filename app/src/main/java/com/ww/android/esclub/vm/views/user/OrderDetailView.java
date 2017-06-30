package com.ww.android.esclub.vm.views.user;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ww.com.core.widget.CustomRecyclerView;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderDetailView implements IView {
    private Context context;

    private String TYPE_WECHAT = "1";
    private String TYPE_ALIPAY ="2";
    private String STATUS_DEALING = "2";
    private String STATUS_COMPLETED = "3";


    @BindView(R.id.crv)
    CustomRecyclerView crv;
    @BindView(R.id.tv_title_content)
    TextView tvTitle;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_pay_status)
    TextView tvPayStatus;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_created)
    TextView tvCreated;

    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this,view);
        context =view.getContext();

        LinearLayoutManager manager = new LinearLayoutManager(context);
        crv.setLayoutManager(manager);
        crv.setItemAnimator(new DefaultItemAnimator());
    }


    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setTotalPrice(String totalPrice){
        tvTotalPrice.setText("￥ "+totalPrice);
    }

    public void setNum(String num){
        tvNum.setText("x "+num);
    }

    public void setPayWay(String way){
        if (TextUtils.equals(TYPE_WECHAT,way)){
            tvPayWay.setText("微信支付");
        }else if (TextUtils.equals(TYPE_ALIPAY,way)){
            tvPayWay.setText("支付宝支付");
        }else {
            tvPayWay.setText("积分支付");
        }
    }

    public void setPayStatus(String status){
        if (TextUtils.equals(STATUS_DEALING,status)){
            tvPayStatus.setText("处理中");
        }else {
            tvPayStatus.setText("已完结");
        }
    }

    public void setAddress(String address) {
        tvAddress.setText(address);
    }

    public void setCreated(String created){
        tvCreated.setText(created);
    }

    public CustomRecyclerView getCrv() {
        return crv;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
