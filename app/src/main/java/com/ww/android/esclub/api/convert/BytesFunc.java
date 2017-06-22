package com.ww.android.esclub.api.convert;


import com.ww.android.esclub.api.ApiException;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by 10142 on 2016/10/10.
 */
public class BytesFunc implements Func1<ResponseBody, byte[]> {
    @Override
    public byte[] call(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                return responseBody.bytes();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiException("数据转换出错，请联系开发！");
            }
        } else {
            throw new ApiException("获取数据失败，请检查网络是否正常！");
        }
    }
}