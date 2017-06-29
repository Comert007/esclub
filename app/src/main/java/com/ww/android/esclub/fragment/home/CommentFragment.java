package com.ww.android.esclub.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.FragmentEvent;
import com.ww.android.esclub.ListBean;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.adapter.home.CommentAdapter;
import com.ww.android.esclub.bean.PagingBean;
import com.ww.android.esclub.bean.home.CommentBean;
import com.ww.android.esclub.bean.home.NewsItem;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.vm.models.home.HomeModel;
import com.ww.android.esclub.vm.views.RefreshView;

import java.util.List;

import butterknife.BindView;
import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by feng on 2017/6/25.
 * 评论
 */

public class CommentFragment extends BaseFragment<RefreshView,HomeModel> {
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    private CommentAdapter adapter;

    private int currentPage = Constant.PAGE_ONE;
    private View footView;
    private String id; //评论内容id

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void init() {
        NewsItem item = (NewsItem) getArguments().getSerializable("newsItem");
        if (item!=null) {
            id = item.getId();
        }

        footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_foot_view, null);
        ScreenUtil.scale(footView);

        initData();
    }

    private void initData(){
        adapter = new CommentAdapter(getContext());
        v.getCrv().setAdapter(adapter);
//
        v.getCsr().setEnableRefresh(true);
        v.getCsr().setOnSwipeRefreshListener(new CustomSwipeRefreshLayout
                .OnSwipeRefreshLayoutListener() {

            @Override
            public void onHeaderRefreshing() {
                currentPage = Constant.PAGE_ONE;
                onComment(false);
            }

            @Override
            public void onFooterRefreshing() {

                v.getCrv().addFooterView(footView);
                onComment(false);
            }
        });


    }

    private void onComment(boolean showDialog){

        m.onCommentList(currentPage+"", null, id, bindUntilEvent(FragmentEvent
                .DESTROY), new HttpSubscriber<ListBean<CommentBean>>(getContext(),showDialog) {
            @Override
            public void onNext(ListBean<CommentBean> commentBeanListBean) {

                v.getCsr().setRefreshFinished();
                Debug.d(commentBeanListBean.toString());
                PagingBean pagingBean = commentBeanListBean.getPaging();
                String totalNum =pagingBean.getTotal_items();
                List<CommentBean> items = commentBeanListBean.getItems();

                int totalPages = Integer.valueOf(pagingBean.getTotal_page());
                int nowPage = Integer.valueOf(commentBeanListBean.getPaging()
                        .getCurrent_page());

                if (items!=null&& items.size()>0){
                    tvCommentNum.setText(totalNum+"条评论");
                    if (nowPage ==Constant.PAGE_ONE){
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
                    v.getCsr().setFooterRefreshAble(false);
                }

            }

//            @Override
//            public void onEnd() {
//                super.onEnd();
//                v.getCsr().setEnableRefresh(false);
//            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onComment(true);
    }
}
