package com.ww.android.esclub.fragment;

import android.view.View;

import com.trello.rxlifecycle.FragmentEvent;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.activity.guess.GuessDetailActivity;
import com.ww.android.esclub.activity.guess.GuessHistoryActivity;
import com.ww.android.esclub.adapter.guess.GuessAdapter;
import com.ww.android.esclub.bean.guess.MatchBean;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.models.GuessModel;
import com.ww.android.esclub.vm.views.cart.GuessView;

import java.util.List;

/**
 * Created by feng on 2017/6/7.
 * 竞猜
 */

public class GuessFragment extends BaseFragment<GuessView, GuessModel> implements
        OnItemClickListener {

    private GuessAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_guess;
    }

    @Override
    protected void init() {

        adapter = new GuessAdapter(getContext());
        v.getCrv().setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        v.showInfo();

        onMatch();
    }

    @Override
    public void onTitleRight() {
        super.onTitleRight();
        GuessHistoryActivity.start(getContext());
    }

    @Override
    public void onItemClick(int position, View v) {
        GuessDetailActivity.start(getContext(),GuessDetailActivity.MATCH,adapter.getItem(position));
    }

    private void onMatch() {
        m.onMatch(bindUntilEvent(FragmentEvent.DESTROY), new HttpSubscriber<List<MatchBean>>(getContext(),true) {
            @Override
            public void onNext(List<MatchBean> matchBeen) {
                if (matchBeen != null && matchBeen.size() > 0) {
                    adapter.addList(matchBeen);
                }
            }
        });
    }
}
