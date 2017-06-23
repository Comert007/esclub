package com.ww.android.esclub.activity.start;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.trello.rxlifecycle.ActivityEvent;
import com.windward.sharelibrary.wxapi.WW_WXUtils;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.MainActivity;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.android.esclub.config.AppConfig;
import com.ww.android.esclub.vm.models.start.LoginModel;
import com.ww.mvp.view.VoidView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import ww.com.core.Debug;

/**
 * Created by feng on 2017/6/22.
 * 登录界面
 */

public class LoginActivity extends BaseActivity<VoidView, LoginModel> {

    @BindView(R.id.ll_weixin_login)
    LinearLayout llWxLogin;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        llWxLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWeChatLogin();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(SendAuth.Resp resp){
        String code =resp.code;
        Debug.d("code:"+code);

        m.onLogin(code,bindUntilEvent(ActivityEvent.DESTROY),new HttpSubscriber<UserBean>(this,true) {
            @Override
            public void onNext(UserBean userBean) {

                MainActivity.start(LoginActivity.this);
                BaseApplication.getInstance().setUserBean(userBean);
            }
        });
    }

    //微信授权
    private void onWeChatLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = AppConfig.WECHAT_SCOPE;
        req.state = AppConfig.WECHAT_STATE;
        IWXAPI api = WW_WXUtils.getInstance(this);
        api.sendReq(req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
