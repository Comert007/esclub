package com.ww.android.esclub.activity.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.adapter.user.OrderCentreAdapter;
import com.ww.android.esclub.bean.PagingBean;
import com.ww.android.esclub.bean.user.OrderCentreBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.models.UserModel;
import com.ww.android.esclub.vm.views.user.OrderCentreView;

import java.util.List;

import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by feng on 2017/6/26.
 */

public class OrderCentreActivity extends BaseActivity<OrderCentreView,UserModel> implements OnItemClickListener{
    private OrderCentreAdapter adapter;

    private int currentPage = Constant.PAGE_ONE;
    private View footView;

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderCentreActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_centre;
    }

    @Override
    protected void init() {

        footView = LayoutInflater.from(this).inflate(R.layout.layout_foot_view, null);
        ScreenUtil.scale(footView);


        adapter = new OrderCentreAdapter(this);
        adapter.setOnItemClickListener(this);
        v.getCrv().setAdapter(adapter);
        v.getCsr().setEnableRefresh(true);

        initData();
    }

    private void initData(){
        v.getCsr().setOnSwipeRefreshListener(
                new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {

            @Override
            public void onHeaderRefreshing() {
                currentPage = Constant.PAGE_ONE;
                onOrders(false);
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(footView);
                onOrders(false);
            }
        });

        onOrders(true);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }


    private void onOrders(boolean showDialog){
        m.onOrders(currentPage + "", bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<ListBean<OrderCentreBean>>(this,showDialog) {

            @Override
            public void onNext(ListBean<OrderCentreBean> orderCentreBeanListBean) {
                v.getCsr().setRefreshFinished();
                Debug.d(orderCentreBeanListBean.toString());
                PagingBean pagingBean = orderCentreBeanListBean.getPaging();
                List<OrderCentreBean> items = orderCentreBeanListBean.getItems();

                int totalPages = Integer.valueOf(pagingBean.getTotal_page());
                int nowPage = Integer.valueOf(pagingBean.getCurrent_page());

                if (items != null && items.size() > 0) {
                    if (nowPage== Constant.PAGE_ONE){
                        adapter.addList(items);
                    }else {
                        adapter.appendList(items);
                    }

                    if (currentPage != totalPages && currentPage == nowPage) {
                        v.getCsr().setFooterRefreshAble(true);
                        currentPage++;

                    } else {
                        v.getCsr().setEnableRefresh(false);
                    }

                    if (nowPage != Constant.PAGE_ONE) {
                        v.getCrv().removeFooterView(footView);
                    }
                }else {
                    v.getCsr().setEnableRefresh(false);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, View v) {
        OrderDetailActivity.start(this,adapter.getItem(position));
    }
}
