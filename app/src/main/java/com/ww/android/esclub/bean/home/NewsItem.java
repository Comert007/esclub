package com.ww.android.esclub.bean.home;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/23.
 */

public class NewsItem implements Serializable{


    private static final long serialVersionUID = 4638759440899228798L;
    private String id;
    private String banner_cover;
    private String cover;
    private String title;
    private String content_url;
    private String video_url;
    private String newstime;
    private String view_num;
    private String share_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner_cover() {
        return banner_cover;
    }

    public void setBanner_cover(String banner_cover) {
        this.banner_cover = banner_cover;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    @Override
    public String toString() {
        return "BannerItem{" +
                "id='" + id + '\'' +
                ", banner_cover='" + banner_cover + '\'' +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", content_url='" + content_url + '\'' +
                ", video_url='" + video_url + '\'' +
                ", newstime='" + newstime + '\'' +
                ", view_num='" + view_num + '\'' +
                ", share_url='" + share_url + '\'' +
                '}';
    }
}
