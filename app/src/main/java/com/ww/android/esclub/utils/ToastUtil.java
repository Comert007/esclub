package com.ww.android.esclub.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.ww.android.esclub.BaseApplication;


/**
 * Created by fighter on 2016/9/20.
 */
public class ToastUtil {
    private static Toast sToast;

    public static final void showToast(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text,
                Toast.LENGTH_LONG);

        sToast.show();
    }

}
