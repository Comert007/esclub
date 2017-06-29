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
import com.ww.android.esclub.adapter.guess.GuessDetailAdapter;
import com.ww.android.esclub.bean.PagingBean;
import com.ww.android.esclub.bean.guess.GuessDetailBean;
import com.ww.android.esclub.bean.guess.MatchBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.vm.models.GuessModel;
import com.ww.android.esclub.vm.views.cart.GuessDetailView;

import java.util.List;

import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by feng on 2017/6/21.
 * 竞猜详情
 */

public class GuessDetailActivity extends BaseActivity<GuessDetailView,GuessModel>
        implements GuessDetailAdapter.OnVoteListener{

    private GuessDetailAdapter adapter;
    private int currentPage = Constant.PAGE_ONE;
    private View footView;
    private String id;
    private int matchType = MATCH;
    private MatchBean matchBean;

    public static final int MATCH=1;
    public static final int MATCH_HISTORY=2;


    public static void start(Context context,int type,MatchBean matchBean) {
        Intent intent = new Intent(context, GuessDetailActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("match",matchBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guess_detail;
    }

    @Override
    protected void init() {
        footView = LayoutInflater.from(this).inflate(R.layout.layout_foot_view, null);
        ScreenUtil.scale(footView);


        matchType = getIntent().getIntExtra("type",0);
        matchBean = (MatchBean) getIntent().getSerializableExtra("match");
        id = matchBean.getId();
        adapter = new GuessDetailAdapter(this,matchType);
        adapter.setOnVoteListener(this);
        v.getCrv().setAdapter(adapter);
        v.getCsr().setEnableRefresh(true);

        initData();
    }


    private void initData(){
        v.onInitInfo(matchBean);
        v.getCsr().setOnSwipeRefreshListener(
                new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {

                    @Override
                    public void onHeaderRefreshing() {
                        currentPage = Constant.PAGE_ONE;
                        onGuessDetail(false);
                    }

                    @Override
                    public void onFooterRefreshing() {
                        v.getCrv().addFooterView(footView);
                        onGuessDetail(false);
                    }
                });

        onGuessDetail(true);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }


    //获取投注项目列表
    private void onGuessDetail(boolean showDialog){
        if (MATCH== matchType) {

            m.onMatchDetail(id, currentPage + "", bindUntilEvent(ActivityEvent.DESTROY),
                    new HttpSubscriber<ListBean<GuessDetailBean>>(this, showDialog) {

                        @Override
                        public void onNext(ListBean<GuessDetailBean> bean) {

                            onGuessContent(bean);
                        }
                    });
        }else {
            m.onGuessHistoryDetail(id, currentPage + "", bindUntilEvent(ActivityEvent.DESTROY),
                    new HttpSubscriber<ListBean<GuessDetailBean>>(this, showDialog) {

                        @Override
                        public void onNext(ListBean<GuessDetailBean> bean) {
                            onGuessContent(bean);
                        }
                    });
        }
    }

    private void onGuessContent(ListBean<GuessDetailBean> bean){
        v.getCsr().setRefreshFinished();
        Debug.d(bean.toString());
        PagingBean pagingBean = bean.getPaging();
        List<GuessDetailBean> items = bean.getItems();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onVote(int position, View view) {
        GuessDetailBean bean = adapter.getItem(position);
        onGuessBet(bean);
    }

    //投注
    private void onGuessBet(GuessDetailBean bean){
        m.onGuessBet(bean.getId(), bean.getOption(), bean.getPoint(),
                bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<Boolean>(this,true) {

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean){
                    showToast(getString(R.string.vote_success));
                }else {
                    showToast(getString(R.string.vote_fail));
                }
            }
        });
    }
}
