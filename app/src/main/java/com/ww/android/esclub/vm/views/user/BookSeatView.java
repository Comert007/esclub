package com.ww.android.esclub.vm.views.user;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ww.android.esclub.R;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feng on 2017/6/28.
 */

public class BookSeatView implements IView {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_book_table)
    TextView tvBookTable;
    @BindView(R.id.tv_book_area)
    TextView tvBookArea;
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;

    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this,view);
    }


    public EditText getEtName() {
        return etName;
    }

    public EditText getEtNum() {
        return etNum;
    }

    public EditText getEtPhone() {
        return etPhone;
    }

    public TextView getTvBookTable() {
        return tvBookTable;
    }

    public void setName(String name){
        etName.setText(name);
    }

    public void setNum(String num){
        etNum.setText(num);
    }

    public void setPhone(String phone){
        etPhone.setText(phone);
    }

    public void setTable(String table){
        tvBookTable.setText(table);
    }

    public void setArea(String area){
        tvBookArea.setText(area);
    }

    public void setTvArriveTime(String time){
        tvArriveTime.setText(time);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
