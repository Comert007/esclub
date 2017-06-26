package com.ww.android.esclub.activity.user;

import android.content.Context;
import android.content.Intent;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

/**
 * Created by feng on 2017/6/26.
 */

public class BookSeatActivity extends BaseActivity<VoidView,VoidModel> {
    public static void start(Context context) {
        Intent intent = new Intent(context, BookSeatActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_book_seat;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }
}
