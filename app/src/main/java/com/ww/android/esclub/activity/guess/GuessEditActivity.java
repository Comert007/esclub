package com.ww.android.esclub.activity.guess;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.mvp.model.VoidModel;
import com.ww.mvp.view.VoidView;

import butterknife.BindView;

/**
 * Created by feng on 2017/6/22.
 */

public class GuessEditActivity extends BaseActivity<VoidView,VoidModel> {

    public final static int REQUEST_EDIT_CODE =0x22;

    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.et_scale_num)
    EditText etScaleNum;


    public static void start(Activity context,int requestCode,int position) {
        Intent intent = new Intent(context, GuessEditActivity.class);
        intent.putExtra("position",position);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void init() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scale = etScaleNum.getText().toString().trim();
                if (TextUtils.isEmpty(scale)){
                    showToast("请确认是否输入了积分");
                    return;
                }
                if (TextUtils.equals("10",scale)||TextUtils.equals("20",scale)||
                        TextUtils.equals("30",scale)||TextUtils.equals("40",scale)){
                    showToast("输入积分值可直接进行选择");
                    return;
                }
                if (TextUtils.equals("0",scale)){
                    scale="其他";
                }
                Intent intent = getIntent();
                intent.putExtra("scale",scale);
                GuessEditActivity.this.setResult(Activity.RESULT_OK,intent);
                onBackPressed();
            }
        });
    }
}
