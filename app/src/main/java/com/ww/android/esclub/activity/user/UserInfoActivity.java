package com.ww.android.esclub.activity.user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.ActivityEvent;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.activity.guess.GuessEditActivity;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.android.esclub.utils.ImagePick;
import com.ww.android.esclub.vm.models.UserModel;
import com.ww.android.esclub.vm.views.user.UserInfoView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.exception.StorageSpaceException;
import ww.com.core.utils.PermissionDispose;

/**
 * Created by feng on 2017/6/27.
 */

public class UserInfoActivity extends BaseActivity<UserInfoView,UserModel> implements ImagePick.OnImageListener,
        PermissionDispose.OnPermissionListener{
    private final int PERMISSION_REQUEST_CODE = 0x14;

    private ImagePick pick;
    private PermissionDispose dispose;
    private String path;
    private String nickname;
    private BottomSheetDialog bottomDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init() {
        v.showInfo();
        pick = ImagePick.init(this, this);
        // 初始化permissionDispose
        dispose = PermissionDispose.init(this, this);
    }

    @OnClick({R.id.ll_header,R.id.ll_nickname,R.id.btn_save})
    public void onModify(View view){
        switch (view.getId()){
            case R.id.ll_nickname:
                GuessEditActivity.start(this);
                break;
            case R.id.ll_header:
//                onChange();
                showBottomDialog();
                break;
            case R.id.btn_save:
                if (TextUtils.isEmpty(path)&& TextUtils.isEmpty(nickname)){
                    showToast("暂无更改信息");
                    return;
                }else {
                    onSave();
                }
                break;
        }
    }

    private void onSave(){
        m.onModify(path, nickname, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<UserBean>(this,true) {

            @Override
            public void onNext(UserBean userBean) {
                if (userBean!=null){
                    BaseApplication.getInstance().setUserBean(userBean);
                    showToast("更改个人信息成功");
                }
            }
        });
    }


    private void showBottomDialog(){
        if (bottomDialog==null) {
            bottomDialog = new BottomSheetDialog(this,R.style.CustomerDialogStyle);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet_dialog,null);
            ScreenUtil.scale(view);
            TextView tvDialogCamera = ButterKnife.findById(view,R.id.tv_dialog_camera);
            TextView tvDialogPhoto = ButterKnife.findById(view,R.id.tv_dialog_photo);
            TextView tvDialogCancel = ButterKnife.findById(view,R.id.tv_dialog_cancel);
            tvDialogCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        pick.startCamera();
                    } catch (StorageSpaceException e) {
                        e.printStackTrace();
                    }
                }
            });

            tvDialogPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChange();
                }
            });

            tvDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomDialog.dismiss();
                }
            });
            bottomDialog.setContentView(view);
        }
        bottomDialog.show();
    }

    private void onChange(){
        int version =Build.VERSION.SDK_INT;
        if (version>20) {
            dispose.requestPermission(PERMISSION_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }else {
            pick.startAlbumSingle();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode== Activity.RESULT_OK){
            if (requestCode ==GuessEditActivity.REQUEST_EDIT_CODE ) {
                String nickname = data.getStringExtra("text");
                this.nickname = nickname;
                v.setNickname(nickname);
            }else {
                try {
                    Debug.d("--------->>>>>>>onActivityResult");
                    pick.onActivityResult(requestCode, resultCode, data);
                } catch (StorageSpaceException e) {
                    e.printStackTrace();
                    showToast(getString(R.string.toast_sdcard_exception));
                    throw new ApiException(getString(R.string.toast_sdcard_exception));
                }
            }
        }else {
            Debug.d("the data is null");
        }
    }

    @Override
    public void onSinglePath(String path) {
        if (bottomDialog.isShowing()){
            bottomDialog.dismiss();
        }
        Debug.d(path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        this.path = path;
        v.setHeader(path);
    }

    @Override
    public void onMultiPaths(ArrayList<String> arrayList) {

    }

    @Override
    public void onSuccess(int i, Map<String, Integer> map) {
        pick.startAlbumSingle();
    }

    @Override
    public void onFinal(int i, Map<String, Integer> map) {
        showToast("权限获取失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImagePick.clearImgCache(this);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }

}
