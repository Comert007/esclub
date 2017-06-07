package com.ww.android.esclub.activity.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.ww.mvp.WWApplication;
import com.ww.mvp.model.IModel;
import com.ww.mvp.presenter.IPresenter;
import com.ww.mvp.presenter.PresenterActivityHelper;
import com.ww.mvp.view.IView;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/7.
 */

public abstract class PresenterActivity<V extends IView, M extends IModel> extends RxAppCompatActivity implements IPresenter {

    WWApplication wwApp;
    protected V v;
    protected M m;

    public PresenterActivity() {
        PresenterActivityHelper.bind(this);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wwApp = (WWApplication)this.getApplication();
        this.wwApp.addRunActivity(this);
        View rootView = LayoutInflater.from(this).inflate(getLayoutResId(),null);
        ScreenUtil.scale(rootView);
        this.setContentView(rootView);
        this.v.onAttach(this, rootView);
        ButterKnife.bind(this);
        this.init();
    }

    protected abstract int getLayoutResId();

    protected abstract void init();

    protected void onResume() {
        super.onResume();
        this.v.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.v.onDestroy();
        this.wwApp.removeRunActivity(this);
    }

    public Activity getPresenterActivity() {
        return this;
    }

    public M getModelModule() {
        return this.m;
    }

    public void setModelModule(IModel modelModule) {
        this.m = (M) modelModule;
    }

    public V getViewModule() {
        return this.v;
    }

    public void setViewModule(IView viewModule) {
        this.v = (V) viewModule;
    }

    public void hideSoftKeyBord() {
        View v = this.getCurrentFocus();
        if(v != null) {
            try {
                InputMethodManager e = (InputMethodManager)this.getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                e.hideSoftInputFromWindow(v.getWindowToken(), 0);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }
}
