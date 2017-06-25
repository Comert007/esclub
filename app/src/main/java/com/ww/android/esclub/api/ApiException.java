package com.ww.android.esclub.api;


import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.config.Constant;

/**
 * Created by feng on 2016/10/8.
 */
public class ApiException extends RuntimeException {

    private String msg;
    private ResponseBean response;

    public ApiException(String detailMessage) {
        super(detailMessage);
        msg = detailMessage;
    }

    public ApiException(ResponseBean response) {
        this.response = response;
    }

    public ApiException(Exception e) {
        super(e);
    }

    @Override
    public String getMessage() {
        if (response == null)
            return msg;
        else
            return response.getMsg();
    }

    public ResponseBean getResponseBean() {
        return response;
    }

    public String getCode() {
        if (response == null)
            return Constant.CODE_ERROR+"";
        else
            return response.getCode();
    }
}
