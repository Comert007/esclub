package com.ww.android.esclub.vm.views.user;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/6/27.
 */

public class UserInfoView implements IView {
    @BindView(R.id.riv_header)
    RoundImageView rivHeader;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_point)
    TextView tvPoint;

    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void showInfo(){
        UserBean user = BaseApplication.getInstance().getUserBean();
        if (user!=null){
            setHeader(user.getAvatar());
            setNickname(user.getNickname());
            setPoint(user.getPoint());
        }
    }

    public void setHeader(String path){
        if (path.startsWith("http://")||path.startsWith("https://")){
            ImageLoader.getInstance().displayImage(path,rivHeader,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_header_default));
        }else {
            ImageLoader.getInstance().displayImage("file://"+path,rivHeader,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_header_default));
        }

    }

    public void setPoint(String point){
        tvPoint.setText(point);
    }

    public void setNickname(String nickname){
        tvNickname.setText(nickname);
    }

    public TextView getTvNickname() {
        return tvNickname;
    }

    public RoundImageView getRivHeader() {
        return rivHeader;
    }
}
