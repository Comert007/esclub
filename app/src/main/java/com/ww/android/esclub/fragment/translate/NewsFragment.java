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

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void init() {
        if (urls == null) {
            urls = new ArrayList<>();
        }
        urls.add("http://ossweb-img.qq" +
                ".com/upload/adw/image/1496714012/1496714012.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq" +
                ".com/upload/adw/image/1496822855/1496822855.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq" +
                ".com/upload/adw/image/1496800468/1496800468.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq" +
                ".com/upload/adw/image/1496727632/1496727632.jpg?_r=1496822856");
        urls.add("http://ossweb-img.qq" +
                ".com/upload/adw/image/1496718634/1496718634.jpg?_r=1496822856");


        footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_foot_view,null);
        ScreenUtil.scale(footView);

        initData();
    }

    private void initData() {
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


    private void startBanner(List<NewsItem> banner) {
        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showToast("banner's onclick in position of " + position);
            }
        });
        v.addBanner();

    }

    private void onNews(boolean showDialog) {
        m.onNews(currentPage + "", null, Constant.NEWS_LATEST, bindUntilEvent(FragmentEvent
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
                        int  nowPage = Integer.valueOf(newsItemListBean.getPaging().getCurrent_page());

                        startBanner(banner);
                        if (items != null && items.size() > 0) {

//                            startBanner(banner);
                            adapter.appendList(items);

                            if (currentPage != totalPages && currentPage== nowPage) {
                                v.getCsr().setFooterRefreshAble(true);
                                currentPage++;

                            } else {
                                v.getCsr().setEnableRefresh(false);
                            }

                            if (nowPage!=Constant.PAGE_ONE){
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
        EsNewsActivity.start(getContext());
    }
}
