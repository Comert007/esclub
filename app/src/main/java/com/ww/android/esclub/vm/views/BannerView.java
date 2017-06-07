package com.ww.android.esclub.vm.views;

import android.content.Context;
import android.widget.ImageView;

import com.ww.android.esclub.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;

/**
 * Created by feng on 2017/6/7.
 */

public class BannerView extends RefreshView{
    @BindView(R.id.banner)
    Banner banner;

    private List<String> urls;

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }


    public void startBanner(){
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(
                        (String) path, imageView);

            }
        });

        if (urls!=null&& urls.size()>0){
            banner.setImages(urls);
            banner.start();
        }
    }
}
