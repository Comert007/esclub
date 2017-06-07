package com.ww.android.esclub.fragment.translate;

import com.ww.android.esclub.R;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.vm.views.BannerView;
import com.ww.mvp.model.VoidModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2017/6/7.
 */

public class EventFragment extends BaseFragment<BannerView,VoidModel> {
    private List<String> urls;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_event;
    }

    @Override
    protected void init() {
        if (urls==null){
            urls = new ArrayList<>();
        }
        urls.add("http://ossweb-img.qq.com/images/clientpop/act/lol_1496312163_sSmallImgUrl.jpg");
        urls.add("http://ossweb-img.qq.com/images/clientpop/act/lol_1496236579_sSmallImgUrl.jpg");
        urls.add("http://ossweb-img.qq.com/images/clientpop/act/lol_1495867585_sSmallImgUrl.jpg");
        urls.add("http://ossweb-img.qq.com/images/clientpop/act/lol_1476945174_sSmallImgUrl.jpg");

        v.setUrls(urls);
        v.startBanner();
    }
}
