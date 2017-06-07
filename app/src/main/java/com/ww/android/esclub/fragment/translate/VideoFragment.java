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

public class VideoFragment extends BaseFragment<BannerView,VoidModel> {
    List<String> urls;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void init() {
        if (urls==null){
            urls = new ArrayList<>();
        }
        urls.add("http://shp.qpic.cn/lolwebvideo/201406/e4faca732d8f134929c08a65add38c3f/0");
        urls.add("http://shp.qpic.cn/lolwebvideo/201406/969353dd35025658d9c2bfd532052930/0");
        urls.add("http://shp.qpic.cn/lolwebvideo/201406/eaa1a487faabf96af9c70768b1db3058/0");
        urls.add("http://shp.qpic.cn/lolwebvideo/201406/fc64a07934a194e7ddeb0286b1d8f39d/0");
        urls.add("http://shp.qpic.cn/lolwebvideo/201406/4e97759434994b14cacfb82143579d4a/0");

        v.setUrls(urls);
        v.startBanner();
    }
}
