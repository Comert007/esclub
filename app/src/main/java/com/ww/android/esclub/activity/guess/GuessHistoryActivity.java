package com.ww.android.esclub.activity.guess;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.adapter.guess.GuessAdapter;
import com.ww.android.esclub.bean.PagingBean;
import com.ww.android.esclub.bean.guess.MatchBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.models.GuessModel;
import com.ww.android.esclub.vm.views.guess.GuessHistoryView;

import java.util.List;

import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by feng on 2017/6/27.
 * 竞猜历史
 */

public class GuessHistoryActivity extends BaseActivity<GuessHistoryView,GuessModel> implements
        OnItemClickListener {

    private GuessAdapter adapter;
    private int currentPage = Constant.PAGE_ONE;
    private View footView;

    public static void start(Context context) {
        Intent intent = new Intent(context, GuessHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guess_history;
    }

    @Override
    protected void init() {

        footView = LayoutInflater.from(this).inflate(R.layout.layout_foot_view, null);
        ScreenUtil.scale(footView);


        adapter = new GuessAdapter(this);
        v.getCsr().setEnableRefresh(true);
        v.getCrv().setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        initData();
    }

    private void initData(){
        v.getCsr().setOnSwipeRefreshListener(
                new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {

                    @Override
                    public void onHeaderRefreshing() {
                        currentPage = Constant.PAGE_ONE;
                        onMatchHistory(false);
                    }

                    @Override
                    public void onFooterRefreshing() {
                        v.getCrv().addFooterView(footView);
                        onMatchHistory(false);
                    }
                });

        onMatchHistory(true);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }

    @Override
    public void onItemClick(int position, View v) {
        GuessDetailActivity.start(this,GuessDetailActivity.MATCH_HISTORY,adapter.getItem(position));
    }

    private void onMatchHistory(boolean showDialog) {
        m.onMatchHistory(currentPage + "", bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<ListBean<MatchBean>>(this,showDialog) {
                    @Override
                    public void onNext(ListBean<MatchBean> bean) {
                        v.getCsr().setRefreshFinished();
                        Debug.d(bean.toString());
                        PagingBean pagingBean = bean.getPaging();
                        List<MatchBean> items = bean.getItems();
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
                                v.getCsr().setFooterRefreshAble(false);
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
}
