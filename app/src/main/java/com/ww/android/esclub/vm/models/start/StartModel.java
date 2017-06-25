package com.ww.android.esclub.vm.models.start;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ww.android.esclub.BaseApplication;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.api.StartApi;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.start.BookTableInfoBean;
import com.ww.android.esclub.bean.start.SystemConfigBean;
import com.ww.android.esclub.bean.start.SystemFlagBean;
import com.ww.android.esclub.bean.start.TableAreaInfoBean;
import com.ww.android.esclub.bean.start.VersionInfoBean;
import com.ww.android.esclub.bean.start.WebViewInfoBean;
import com.ww.mvp.model.IModel;

import java.util.ArrayList;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by feng on 2017/6/25.
 */

public class StartModel implements IModel {
    @Override
    public void onAttach(@NonNull Context context) {

    }

    public void onSysParams(SystemFlagBean flagBean,
                            LifecycleTransformer lifecycleTransformer,
                            HttpSubscriber<SystemConfigBean> httpSubscriber) {

        StartApi.sysParams(flagBean)
                .map(new Func1<ResponseBean, SystemConfigBean>() {
                    @Override
                    public SystemConfigBean call(ResponseBean responseBean) {
                        SystemFlagBean flagBean = BaseApplication.getInstance().getSystemFlagBean();
                        if (flagBean == null) {
                            flagBean = new SystemFlagBean();
                        }
                        SystemConfigBean configBean = BaseApplication.getInstance()
                                .getSystemConfigBean();
                        if (configBean == null) {
                            configBean = new SystemConfigBean();
                        }

                        JSONObject dataJson = JSONObject.parseObject(responseBean.getData());

                        if (dataJson.containsKey("version_info")) {
                            JSONObject versionInfoJson = dataJson.getJSONObject("version_info");
                            if (!versionInfoJson.isEmpty()) {
                                VersionInfoBean viewInfoBean = versionInfoJson
                                        .getObject("obj", VersionInfoBean.class);
                                flagBean.version_flag = versionInfoJson.getString("version_flag");
                                configBean.version_info = viewInfoBean;
                            }
                        }

                        if (dataJson.containsKey("table_area_info")) {
                            JSONObject tableAreaInfoJson = dataJson.getJSONObject
                                    ("table_area_info");
                            if (!tableAreaInfoJson.isEmpty()) {
                                ArrayList<TableAreaInfoBean> tableAreaInfoBeen =
                                        (ArrayList<TableAreaInfoBean>) JSONArray.parseArray
                                                (tableAreaInfoJson.getString("items"),
                                                        TableAreaInfoBean.class);
                                flagBean.table_area_flag = tableAreaInfoJson.getString
                                        ("table_area_flag");
                                configBean.table_area_info = tableAreaInfoBeen;
                            }
                        }
//
//                        if (dataJson.containsKey("goods_type_info")) {
//                            JSONObject goodsTypeInfoJson = dataJson.getJSONObject
//                                    ("goods_type_info");
//                            if (!goodsTypeInfoJson.isEmpty()) {
//                                ArrayList<GoodTypeInfoBean> goodTypeInfoBeen =
//                                        (ArrayList<GoodTypeInfoBean>) JSONArray.parseArray
//                                                (goodsTypeInfoJson.getString("items"),
//                                                        GoodTypeInfoBean.class);
//                                flagBean.goods_type_flag = goodsTypeInfoJson.getString
//                                        ("goods_type_flag");
//                                configBean.goods_type_info = goodTypeInfoBeen;
//                            }
//                        }

                        if (dataJson.containsKey("book_table_info")) {
                            JSONObject bookTableInfoJson = dataJson.getJSONObject
                                    ("book_table_info");
                            if (!bookTableInfoJson.isEmpty()) {
                                BookTableInfoBean bookTableInfoBean = bookTableInfoJson.getObject
                                        (bookTableInfoJson.getString("obj"), BookTableInfoBean
                                                .class);

                                flagBean.book_table_flag = bookTableInfoJson.getString
                                        ("book_table_flag");
                                configBean.book_table_info = bookTableInfoBean;
                            }
                        }


                        if (dataJson.containsKey("webview_info")) {
                            JSONObject webviewInfoJson = dataJson.getJSONObject
                                    ("webview_info");
                            if (!webviewInfoJson.isEmpty()) {
                                WebViewInfoBean webviewInfoBean = webviewInfoJson.getObject
                                        (webviewInfoJson.getString("obj"), WebViewInfoBean
                                                .class);

                                flagBean.webview_flag = webviewInfoJson.getString("webview_flag");
                                configBean.webview_info = webviewInfoBean;
                            }
                        }

                        BaseApplication.getInstance().setSystemFlagBean(flagBean);
                        BaseApplication.getInstance().setSystemConfigBean(configBean);
                        return configBean;
                    }
                }).compose(RxHelper.<SystemConfigBean>cutMain())
                .compose(lifecycleTransformer)
                .subscribe(httpSubscriber);
    }
}
