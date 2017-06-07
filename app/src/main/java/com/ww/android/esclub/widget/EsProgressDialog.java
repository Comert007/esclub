package com.ww.android.esclub.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ww.android.esclub.R;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/6.
 */

public class EsProgressDialog extends Dialog{
    private View rootView;
    private TextView tvMessage;

    public EsProgressDialog(Context context) {
        super(context, R.style.CustomerDialogStyle);

        rootView = LayoutInflater.from(context)
                .inflate(R.layout.view_progress_dialog, null);
        ScreenUtil.scale(rootView);
        tvMessage = ButterKnife.findById(rootView, R.id.tv_message);

        setContentView(rootView);
    }

    public void setMessage(CharSequence charSequence) {
        tvMessage.setText(charSequence);
    }
}
