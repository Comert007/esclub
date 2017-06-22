package com.ww.android.esclub.api.convert;


import com.ww.android.esclub.api.ApiException;

import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by 10142 on 2016/10/10.
 */
public class BytesStreamFunc implements Func1<ResponseBody, InputStream> {
    @Override
    public InputStream call(ResponseBody responseBody) {
        if (responseBody != null)
            return responseBody.byteStream();
        else
            throw new ApiException("获取数据失败，请检查网络是否正常！");
    }
}