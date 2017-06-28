package com.ww.android.esclub.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.start.TableAreaInfoBean;
import com.ww.android.esclub.bean.start.TableAreaInfoEntity;
import com.ww.android.esclub.utils.DialogUtil;

import java.util.List;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/28.
 */

public class EsDialog<T> {
    Dialog dialog;
    List<T> catBeen;
    int type;  // 1 一级, 2 二级
    Context context;
    String title;

    private onSelectedListener<T> onSelectedListener;

    public void setOnSelectedListener(EsDialog.onSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public EsDialog(Context context, List<T> catBeen, int type) {
        this.catBeen = catBeen;
        this.type = type;
        this.context = context;
        if (1 == type) {
            title = "预定区域";
        } else {
            title = "预定座号";
        }
    }

    public void show() {
        if (catBeen == null || catBeen.isEmpty()) {
            return;
        }
        dialog = DialogUtil.showDialogList(context, "",
                new ArrayAdapter<T>(context,
                        R.layout.item_text_1, catBeen) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        parent.setMinimumHeight(ScreenUtil.getScalePxValue(300));
                        View view = super.getView(position, convertView, parent);
                        TextView tv =ButterKnife.findById(view,R.id.tv_es);
                        if (type==1){
                           List<TableAreaInfoBean> been= (List<TableAreaInfoBean>) catBeen;
                            tv.setText(been.get(position).getName());
                        }else {
                            List<TableAreaInfoEntity> been= (List<TableAreaInfoEntity>) catBeen;
                            tv.setText(been.get(position).getName());
                        }

                        ScreenUtil.scale(view);
                        return view;
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        T fcb = catBeen.get(which);
                        if (onSelectedListener!=null){
                            onSelectedListener.onSeleted(type,fcb,which);
                        }

                        dialog.dismiss();
                    }
                });
    }

    public  interface onSelectedListener<T>{
        void onSeleted(int type, T entity,int position);
    }



}
