package com.ww.android.esclub.activity.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.ActivityEvent;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.BaseActivity;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.bean.start.SystemConfigBean;
import com.ww.android.esclub.bean.start.TableAreaInfoBean;
import com.ww.android.esclub.bean.start.TableAreaInfoEntity;
import com.ww.android.esclub.bean.start.UserBean;
import com.ww.android.esclub.vm.models.UserModel;
import com.ww.android.esclub.vm.views.user.BookSeatView;
import com.ww.android.esclub.widget.EsDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.OnClick;
import ww.com.core.Debug;

/**
 * Created by feng on 2017/6/26.
 */

public class BookSeatActivity extends BaseActivity<BookSeatView, UserModel> implements
        TimePickerDialog.OnTimeSetListener {

    private List<TableAreaInfoBean> areaItems;
    private List<TableAreaInfoEntity> tableItems;
    private EsDialog areaDialog;
    private EsDialog tableDialog;
    private String areaId;
    private String tableId;
    private TimePickerDialog timePicker;
    private String timestamp;
    private int  index = -1;

    private List<String> ids = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, BookSeatActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_book_seat;
    }

    @Override
    protected void init() {


        initOptionPicker();
        initTimePicker();

    }

    private void initOptionPicker() {
        SystemConfigBean configBean = BaseApplication.getInstance().getSystemConfigBean();
        if (configBean != null && configBean.table_area_info != null && configBean
                .table_area_info.size() > 0) {
            areaItems = new ArrayList<>();
            tableItems = new ArrayList<>();
            ArrayList<TableAreaInfoBean> been = configBean.table_area_info;

            for (TableAreaInfoBean areaInfoBean : been) {
                areaItems.add(areaInfoBean);
                tableItems.addAll(areaInfoBean.getTables());
            }

            initAreaDialog();

        }
    }

    private void initAreaDialog() {
        areaDialog = new EsDialog(this, areaItems, 1);


        areaDialog.setOnSelectedListener(new EsDialog.onSelectedListener<TableAreaInfoBean>() {
            @Override
            public void onSeleted(int type, TableAreaInfoBean entity, int position) {

                onLeast(entity, position);
            }
        });


    }

    private void initTableDialog(int position) {
        tableDialog = new EsDialog(this, areaItems.get(position).getTables(), 2);

        tableDialog.setOnSelectedListener(new EsDialog.onSelectedListener<TableAreaInfoEntity>() {
            @Override
            public void onSeleted(int type, TableAreaInfoEntity entity, int position) {
                if ("1".equals(entity.getStatus())) {
                    index = position;
                    ids.clear();
                    tableId = entity.getId();
                    ids.add(tableId);
                    v.setTable(entity.getName());
                } else {
                    showToast("当前座位号已被预约或已被使用");
                }
            }
        });
    }

    private void onLeast(final TableAreaInfoBean entity, final int position) {
        m.onLeastTable(entity.getId(), bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<List<TableAreaInfoEntity>>(BookSeatActivity.this, true) {

                    @Override
                    public void onNext(List<TableAreaInfoEntity> tableAreaInfoEntities) {
                        TableAreaInfoBean bean = areaItems.get(position);
                        bean.setTables(tableAreaInfoEntities);
                        areaItems.set(position, bean);
                        initTableDialog(position);
                        Debug.d(areaItems.toString());

                        areaId = entity.getId();
                        v.setArea(entity.getName());
                    }

                });
    }

    @OnClick({R.id.ll_book_area, R.id.ll_book_table, R.id.ll_arrive_time, R.id.btn_book})
    public void onOptionPicker(View view) {
        switch (view.getId()) {
            case R.id.ll_book_area:
                if (areaDialog != null) {
                    areaDialog.show();
                }
                break;
            case R.id.ll_book_table:
                if (TextUtils.isEmpty(areaId)) {
                    showToast("请先选择区域");
                    return;
                }
                if (tableDialog != null) {
                    tableDialog.show();
                }
                break;
            case R.id.ll_arrive_time:
                timePicker.show(getFragmentManager(), "Timepickerdialog");
                break;
            case R.id.btn_book:
                String name = v.getEtName().getText().toString().trim();
                String num = v.getEtNum().getText().toString().trim();
                String phone = v.getEtPhone().getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(num)) {
                    showToast("请输入人数");
                    return;
                }

                if (TextUtils.isEmpty(phone) || phone.length() != 11) {
                    showToast("请输入正确的电话");
                    return;
                }
                if (TextUtils.isEmpty(areaId)) {
                    showToast("请选择区域");
                    return;
                }

                if (TextUtils.isEmpty(tableId)) {
                    showToast("请选择座号");
                    return;
                }

                if (TextUtils.isEmpty(timestamp)) {
                    showToast("请选择时间");
                    return;
                }


                onBookTable(name, num, phone);
                break;
        }
    }


    private void onBookTable(String name, String num, String phone) {
        m.onBookTable(name, num, ids,timestamp, phone, bindUntilEvent
                        (ActivityEvent.DESTROY),
                new HttpSubscriber<Boolean>(this, true) {

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            onUserInfo();
                        } else {
                            showToast("预订失败");
                        }
                    }
                });
    }

    private void onUserInfo(){
        m.onUserInfo(bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<UserBean>(BookSeatActivity.this,true) {
            @Override
            public void onNext(UserBean userBean) {
                showToast("预定成功");
                BaseApplication.getInstance().setUserBean(userBean);
                finish();
            }
        });
    }


    private void initTimePicker() {
        Calendar now = Calendar.getInstance();
        timePicker = TimePickerDialog.newInstance(
                BookSeatActivity.this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true
        );
        timePicker.setVersion(TimePickerDialog.Version.VERSION_2);

    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        onBackPressed();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Debug.d("hour:" + hourOfDay + ", minute:" + minute + ", second:" + second);
        Calendar now = Calendar.getInstance();
        String time = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now
                .get(Calendar.DAY_OF_MONTH) + " " + hourOfDay + ":" + minute + ":" + second;
        v.setTvArriveTime(time);
        timestamp = date2TimeStamp(time,"yyyy-MM-dd HH:mm:ss");
        Debug.d("time:" + timestamp);

    }

    public String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
