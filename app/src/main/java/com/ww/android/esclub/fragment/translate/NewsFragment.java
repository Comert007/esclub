package com.ww.android.esclub.fragment.translate;

import android.view.LayoutInflater;
import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.activity.home.EsNewsActivity;
import com.ww.android.esclub.adapter.NewsAdapter;
import com.ww.android.esclub.bean.PagingBean;
import com.ww.android.esclub.bean.home.NewsItem;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.models.home.HomeModel;
import com.ww.android.esclub.vm.views.BannerView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by feng on 2017/6/7.
 * 最新
 */

public class NewsFragment extends BaseFragment<BannerView, HomeModel> implements
        OnItemClickListener {

    private List<String> urls;
    private int currentPage = Constant.PAGE_ONE;

    private NewsAdapter adapter;
    private View footView;
    private String type;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void init() {
        if (urls == null) {
            urls = new ArrayList<>();
        }


        footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_foot_view, null);
        ScreenUtil.scale(footView);

        initData();
    }

    private void initData() {
        type = getArguments().getString("type");
        adapter = new NewsAdapter(getContext());
        adapter.setOnItemClickListener(this);
        v.getCsr().setEnableRefresh(true);
//        v.getCsr().setFooterRefreshAble(true);
        v.getCsr().setOnSwipeRefreshListener(new CustomSwipeRefreshLayout
                .OnSwipeRefreshLayoutListener() {

            @Override
            public void onHeaderRefreshing() {
                currentPage = Constant.PAGE_ONE;
                onNews(false);
            }

            @Override
            public void onFooterRefreshing() {

                v.getCrv().addFooterView(footView);
                onNews(false);
            }
        });
        v.getCrv().setAdapter(adapter);

        onNews(true);
    }


    private void startBanner(final List<NewsItem> banner) {
        urls.clear();
        for (NewsItem item : banner) {
            urls.add(item.getBanner_cover());
        }

        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (banner.size()>0) {
                    NewsItem item = banner.get(position);
                    EsNewsActivity.start(getContext(),item);
                }
            }
        });
        v.addBanner();

    }

    private void onNews(boolean showDialog) {
        m.onNews(currentPage + "", null, type, bindUntilEvent(FragmentEvent
                        .DESTROY),
                new HttpSubscriber<ListBean<NewsItem>>(getContext(), showDialog) {
                    @Override
                    public void onNext(ListBean<NewsItem> newsItemListBean) {
                        v.getCsr().setRefreshFinished();
                        Debug.d(newsItemListBean.toString());
                        PagingBean pagingBean = newsItemListBean.getPaging();
                        List<NewsItem> banner = newsItemListBean.getBanner();
                        List<NewsItem> items = newsItemListBean.getItems();
                        int totalPages = Integer.valueOf(pagingBean.getTotal_page());
                        int nowPage = Integer.valueOf(newsItemListBean.getPaging()
                                .getCurrent_page());

                        if (items != null && items.size() > 0) {

                            startBanner(banner);
                            adapter.appendList(items);

                            if (currentPage != totalPages && currentPage == nowPage) {
                                v.getCsr().setFooterRefreshAble(true);
                                currentPage++;

                            } else {
                                v.getCsr().setEnableRefresh(false);
                            }

                            if (nowPage != Constant.PAGE_ONE) {
                                v.getCrv().removeFooterView(footView);
                            }

                        } else {
                            v.getCsr().setEnableRefresh(false);
                        }
                    }
                });
    }


    @Override
    public void onItemClick(int position, View v) {
        if (position< adapter.getItemCount()) {
            EsNewsActivity.start(getContext(), adapter.getItem(position));
        }
    }
}
