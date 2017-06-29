package com.ww.android.esclub.vm.views.user;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bean.start.BookTableInfoBean;
import com.ww.android.esclub.bean.start.SystemConfigBean;
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
    @BindView(R.id.tv_notice)
    TextView tvNotice;

    public void showNotice(){
        SystemConfigBean configBean = BaseApplication.getInstance().getSystemConfigBean();
        if (configBean!=null && configBean.book_table_info!=null){
            BookTableInfoBean bean = configBean.book_table_info;
            tvNotice.setText("预定开始时间："+bean.getStart_time()+"。\n"+
            "预定截至时间："+bean.getEnd_time()+"。\n"+
            "提前预定时间：从当前时间到未来"+bean.getAdvance_time()+"小时之内。\n"+
            "迟到预留时间："+bean.getReserve_time()+"分钟");
        }
    }

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
