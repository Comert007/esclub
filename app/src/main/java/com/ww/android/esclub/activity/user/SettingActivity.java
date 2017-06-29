package com.ww.android.esclub.activity.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.ActivityEvent;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.activity.start.LoginActivity;
import com.ww.android.esclub.bean.start.SystemConfigBean;
import com.ww.android.esclub.bean.start.VersionInfoBean;
import com.ww.android.esclub.config.Constant;
import com.ww.android.esclub.utils.DialogUtil;
import com.ww.android.esclub.vm.models.UserModel;
import com.ww.mvp.view.VoidView;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.utils.AppUtils;

/**
 * Created by feng on 2017/6/26.
 */

public class SettingActivity extends BaseActivity<VoidView,UserModel> {
    @BindView(R.id.tv_version)
    TextView tvVersion;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        tvVersion.setText("版本更新("+ AppUtils.getAppInfo(this).getVersionName()+")");
    }

    @OnClick({R.id.ll_contact_us,R.id.ll_about_es,R.id.ll_guess_rules,R.id.ll_use_term,
            R.id.ll_update_version,R.id.tv_login_out})
    public void onSetting(View view){
        switch (view.getId()){
            case R.id.ll_contact_us:
                WebViewActivity.start(this, Constant.CONTACT_US);
                break;
            case R.id.ll_about_es:
                WebViewActivity.start(this, Constant.ABOUT_US);
                break;
            case R.id.ll_guess_rules:
                WebViewActivity.start(this, Constant.GUESS_RULES);
                break;
            case R.id.ll_use_term:
                WebViewActivity.start(this, Constant.TERMS_OF_USE);
                break;
            case R.id.ll_update_version:
                onUpdate();
                break;
            case R.id.tv_login_out:
                onLoginOut();
                break;
        }
    }


    private void onLoginOut(){
        DialogUtil.showDialog(SettingActivity.this, getString(R.string.dialog_title_tip),
                getString(R.string.dialog_login_out), getString(R.string.dialog_btn_sure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m.onLoginOut(bindUntilEvent(ActivityEvent.DESTROY),
                                new HttpSubscriber<Boolean>(SettingActivity.this,true) {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                showToast(getString(R.string.login_out_success));
                                BaseApplication.getInstance().setUserBean(null);
                                BaseApplication.getInstance().clearTopTask(SettingActivity.this);
                                LoginActivity.start(SettingActivity.this);
                                SettingActivity.this.finish();
                            }
                        });
                    }
                }, getString(R.string.dialog_btn_cancel), new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },false);
    }


    private void onUpdate(){
        SystemConfigBean configBean = BaseApplication.getInstance().getSystemConfigBean();
        if (configBean!=null && configBean.version_info!=null){
            VersionInfoBean versionInfoBean = configBean.version_info;
            String netVersion = versionInfoBean.getApp_version();
            String nowVersion = AppUtils.getAppInfo(this).getVersionName();
            if (netVersion.compareToIgnoreCase(nowVersion)>0){
                com.ww.android.esclub.utils.AppUtils.startBrowser(SettingActivity.this,
                        versionInfoBean.getUpdate_uri());
            }else {
                showToast("暂无版本更新");
            }
        }
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }
}
