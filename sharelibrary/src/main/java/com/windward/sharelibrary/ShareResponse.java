package com.windward.sharelibrary;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by 10142 on 2016/7/8.
 */
public class ShareResponse implements Serializable{


    private String title;         //标题
    private String description;   //描述
    private String target_url;    //分享后打开的连接地址
    private String app_name;      //应用名称
    private String image_url;     //图片地址
    private String audio_url;     //音乐地址
    private Bitmap bitmap;        //图片
    private String text;
    private String music_url;     //音乐地址
    private String video_url;     //视频地址


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMusic_url() {
        return music_url;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @Override
    public String toString() {
        return "ShareResponse{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", target_url='" + target_url + '\'' +
                ", app_name='" + app_name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", audio_url='" + audio_url + '\'' +
                ", bitmap=" + bitmap +
                ", text='" + text + '\'' +
                ", music_url='" + music_url + '\'' +
                ", video_url='" + video_url + '\'' +
                '}';
    }
}
