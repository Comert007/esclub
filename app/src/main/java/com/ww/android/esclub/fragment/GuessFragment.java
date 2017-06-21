package com.ww.android.esclub.fragment;

import android.view.View;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.guess.GuessDetailActivity;
import com.ww.android.esclub.adapter.guess.GuessAdapter;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.views.cart.GuessView;
import com.ww.mvp.model.VoidModel;

import java.util.Arrays;

/**
 * Created by feng on 2017/6/7.
 * 竞猜
 */

public class GuessFragment extends BaseFragment<GuessView,VoidModel> implements OnItemClickListener{

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

        adapter.addList(Arrays.asList("1"));
    }

    @Override
    public void onItemClick(int position, View v) {
        GuessDetailActivity.start(getContext());
    }
}
