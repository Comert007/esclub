package com.ww.android.esclub.vm.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.ww.mvp.view.IView;

import butterknife.ButterKnife;

/**
 * Created by feng on 2017/6/21.
 */

public abstract class BaseView implements IView {

    private Context context;
    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this,view);
        context = view.getContext();
        attach(activity, view);
    }

    public abstract void attach(@NonNull Activity activity, @NonNull View view);
}
