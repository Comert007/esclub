package com.ww.android.esclub.activity.user;

import android.content.Context;
import android.content.Intent;

import com.trello.rxlifecycle.ActivityEvent;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.adapter.user.OrderDetailAdapter;
import com.ww.android.esclub.bean.user.OrderCentreBean;
import com.ww.android.esclub.bean.user.OrderDetailBean;
import com.ww.android.esclub.bean.user.OrderTableAreaBean;
import com.ww.android.esclub.vm.models.UserModel;
import com.ww.android.esclub.vm.views.user.OrderDetailView;

import java.util.List;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderDetailActivity extends BaseActivity<OrderDetailView, UserModel> {

    private OrderCentreBean orderCentreBean;
    private OrderDetailAdapter adapter;

    public static void start(Context context, OrderCentreBean orderCentreBean) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("detail", orderCentreBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void init() {
        adapter = new OrderDetailAdapter(this);
        v.getCrv().setAdapter(adapter);
        initData();

        onOrderDetail();
    }

    private void initData() {
        orderCentreBean = (OrderCentreBean) getIntent().getSerializableExtra("detail");
        if (orderCentreBean != null) {
            v.setTitle("内容："+orderCentreBean.getTitle());
            v.setTotalPrice(orderCentreBean.getPrice());
            v.setNum(orderCentreBean.getType_num());
            v.setPayWay(orderCentreBean.getPay_type());
            v.setPayStatus(orderCentreBean.getStatus());
            v.setCreated(orderCentreBean.getCreated());
            OrderTableAreaBean orderTableAreaBean = orderCentreBean.getTable_area();
           String tablename = orderTableAreaBean.getTable().getName();
            v.setAddress(orderTableAreaBean.getName()+"区"+tablename+"座");
        }
    }

    private void onOrderDetail() {
        final String id = orderCentreBean.getId();
        m.onOrderDetail(id, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<ListBean<OrderDetailBean>>(this, true) {

                    @Override
                    public void onNext(ListBean<OrderDetailBean> bean) {
                        List<OrderDetailBean> items = bean.getItems();
                        if (items != null && items.size() > 0) {
                            adapter.addList(items);
                        }
                    }
                });
    }
}
