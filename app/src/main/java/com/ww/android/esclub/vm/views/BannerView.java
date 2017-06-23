package com.ww.android.esclub.vm.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ww.android.esclub.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by feng on 2017/6/7.
 */

public class BannerView extends RefreshView{

    Banner banner;

    private View bannerView;

    private List<String> urls;

    @Override
    public void attach() {
        super.attach();
        bannerView = LayoutInflater.from(context).inflate(R.layout.view_banner,null);
        banner = ButterKnife.findById(bannerView,R.id.banner);

    }

    public void addBanner(){
        crv.addHeadView(bannerView);
    }

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

    public Banner getBanner() {
        return banner;
    }
}
