package com.ww.android.esclub.api.convert;

import com.alibaba.fastjson.JSONObject;
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.config.Constant;

import rx.functions.Func1;

/**
 * Created by 10142 on 2016/10/10.
 */
public class ResponseFunc implements Func1<String, ResponseBean> {
    @Override
    public ResponseBean call(String string) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        ResponseBean bean = ResponseBean.parseObject(jsonObject);
        if (bean != null) {
            if (Constant.STATUS_OK.equals(bean.getStatus()))
                return bean;
            else {
                throw new ApiException(bean);
            }
        } else {
            throw new ApiException("获取数据失败，请检查网络是否正常！");
        }

    }
}