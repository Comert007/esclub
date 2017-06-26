package com.ww.android.esclub.fragment;

import android.content.Intent;
import android.view.View;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.user.AboutUsActivity;
import com.ww.android.esclub.activity.user.BookSeatActivity;
import com.ww.android.esclub.activity.user.OrderCentreActivity;
import com.ww.android.esclub.activity.user.ReChargeActivity;
import com.ww.android.esclub.activity.user.SettingActivity;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.vm.views.user.UserView;
import com.ww.mvp.model.VoidModel;

import butterknife.OnClick;
import ww.com.core.Debug;

/**
 * Created by feng on 2017/6/7.
 */

public class UserFragment extends BaseFragment<UserView,VoidModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.ll_order,R.id.ll_online,R.id.ll_recharge,R.id.ll_setting,R.id.ll_about_us})
    public void onUserClick(View view){
        switch (view.getId()){
            case R.id.ll_order:
                OrderCentreActivity.start(getContext());
                break;
            case R.id.ll_online:
                BookSeatActivity.start(getContext());
                break;
            case R.id.ll_recharge:
                Intent intent = new Intent(getContext(),ReChargeActivity.class);
                startActivityForResult(intent,0x12);
                break;
            case R.id.ll_setting:
                SettingActivity.start(getContext());
                break;
            case R.id.ll_about_us:
                AboutUsActivity.start(getContext());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Debug.d("--------------USerFragment--------");
        if (data != null) {

        }
    }
}
