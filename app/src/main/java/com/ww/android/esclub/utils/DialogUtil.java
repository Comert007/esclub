package com.ww.android.esclub.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;


import com.ww.android.esclub.widget.EsProgressDialog;

import ww.com.core.ScreenUtil;

/**
 * @author fighter
 * @version 1.0.1
 * @创建时间: 2017/3/14
 * @修改时间:
 */

public class DialogUtil {
    private DialogUtil() {
        throw new RuntimeException();
    }

    public static final EsProgressDialog obtainProgressDialog(Context context) {
        EsProgressDialog dialog = new EsProgressDialog(context);
        return dialog;
    }

    public static final Dialog showDialog(Context context, String title, CharSequence msg, String positive,
                                          DialogInterface.OnClickListener posiListener,
                                          String cancel, DialogInterface.OnClickListener cancelListener, boolean cancelable) {
        try {
            if (!TextUtils.isEmpty(msg)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(msg)
                        .setCancelable(cancelable)
                        .setPositiveButton(
                                positive, posiListener);
                if (!TextUtils.isEmpty(cancel)) {
                    builder.setNegativeButton(cancel, cancelListener);
                }
                return show(builder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static final Dialog showDialog(Context context, String title, CharSequence msg, String positive,
                                          DialogInterface.OnClickListener posiListener) {
        try {
            if (!TextUtils.isEmpty(msg)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton(
                                positive, posiListener);
                return showColor(builder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static AlertDialog show(AlertDialog.Builder builder) {
        AlertDialog dialog = builder.show();
        Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        int btnSize = ScreenUtil.getScalePxValue(42);
        int textSize = ScreenUtil.getScalePxValue(46);
        try {
            TextView title = (TextView) dialog.findViewById(android.support.v7.appcompat.R.id.alertTitle);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenUtil.getScalePxValue(60));
        } catch (Exception e) {
        }
        if (btnPositive != null) {
//            btnPositive.setTextColor(btnPositive.getResources().getColor(R.color.color_0b85b6));
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize);
        }

        Button btnNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (btnNegative != null) {
//            btnNegative.setTextColor(Color.parseColor("#ff4283"));
            btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize);
        }

        Button btnNeu = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        if (btnNeu != null) {
            btnNeu.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize);
        }

        TextView textView = (TextView) dialog
                .findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextColor(Color.parseColor("#747474"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        return dialog;
    }


    public static AlertDialog showColor(AlertDialog.Builder builder) {
        AlertDialog dialog = builder.show();
        Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        int btnSize = ScreenUtil.getScalePxValue(42);
        int textSize = ScreenUtil.getScalePxValue(46);

        if (btnPositive != null) {
            btnPositive.setTextColor(Color.parseColor("#53a9ff"));
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize);
        }

        Button btnNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (btnNegative != null) {
            btnNegative.setTextColor(Color.parseColor("#ff4283"));
            btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize);
        }

        TextView textView = (TextView) dialog
                .findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextColor(Color.parseColor("#747474"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        return dialog;
    }
}

