package com.ww.android.esclub.fragment;

import com.ww.android.esclub.R;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

/**
 * Created by feng on 2017/6/7.
 */

public class GuessFragment extends BaseFragment<VoidView,VoidModel> {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_guess;
    }

    @Override
    protected void init() {

    }
}
