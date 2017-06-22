package com.ww.android.esclub.fragment.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.ww.mvp.model.IModel;
import com.ww.mvp.presenter.IPresenter;
import com.ww.mvp.view.IView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/7.
 */

public abstract class PresenterFragment<V extends IView, M extends IModel> extends RxFragment implements IPresenter {

    private Activity activity;
    protected V v;
    protected M m;


    public PresenterFragment() {
        try {
            v = getClassView().newInstance();
            m = getClassModel().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View rootView =inflater.inflate(getLayoutResId(),container,false);
        ScreenUtil.scale(rootView);
        this.v.onAttach(activity, rootView);
        this.m.onAttach(activity);
        ButterKnife.bind(this,rootView);
        this.init();

        return rootView;
    }

    protected abstract int getLayoutResId();

    protected abstract void init();

    @Override
    public void onResume() {
        super.onResume();
        this.v.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.v.onDestroy();
    }

    public void hideSoftKeyBord() {
        View v = getActivity().getCurrentFocus();
        if(v != null) {
            try {
                InputMethodManager e = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                e.hideSoftInputFromWindow(v.getWindowToken(), 0);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    protected Class<V> getClassView() {
        return getClassType(0);
    }

    protected Class<M> getClassModel() {
        return getClassType(1);
    }

    private Class getClassType(int _index) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        return (Class) types[_index];
    }

    @Override
    public Activity getPresenterActivity() {
        return activity;
    }

    @Override
    public IModel getModelModule() {
        return m;
    }

    @Override
    public void setModelModule(IModel iModel) {
        m = (M) iModel;
    }

    @Override
    public IView getViewModule() {
        return v;
    }

    @Override
    public void setViewModule(IView iView) {

    }
}
