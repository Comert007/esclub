package com.ww.android.esclub.activity.guess;

import android.content.Context;
import android.content.Intent;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.adapter.guess.GuessDetailAdapter;
import com.ww.android.esclub.vm.views.cart.GuessDetailView;
import com.ww.mvp.model.VoidModel;

import java.util.Arrays;

import ww.com.core.Debug;

/**
 * Created by feng on 2017/6/21.
 * 竞猜详情
 */

public class GuessDetailActivity extends BaseActivity<GuessDetailView,VoidModel> {

    private GuessDetailAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, GuessDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guess_detail;
    }

    @Override
    protected void init() {
        adapter = new GuessDetailAdapter(this);
        v.getCrv().setAdapter(adapter);
        adapter.addList(Arrays.asList("1","2","3"));
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null) {
            String scale = data.getExtras().getString("scale");
            int position = data.getExtras().getInt("position");
            Debug.d("scale:" + scale+", postion:"+position);
            GuessDetailAdapter.GuessDetailViewHolder viewHolder =adapter.getViewHolder();
            viewHolder.changeBubble(scale);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
