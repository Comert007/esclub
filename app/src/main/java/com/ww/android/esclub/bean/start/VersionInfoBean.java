package com.ww.android.esclub.bean.start;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/25.
 */

public class VersionInfoBean implements Serializable {
    private static final long serialVersionUID = -5805401931634262857L;
    /**
     * app_version : 2.0.0
     * update_uri : http://
     */

    private String app_version;
    private String update_uri;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getUpdate_uri() {
        return update_uri;
    }

    public void setUpdate_uri(String update_uri) {
        this.update_uri = update_uri;
    }

    @Override
    public String toString() {
        return "SystemConfigBean{" +
                "app_version='" + app_version + '\'' +
                ", update_uri='" + update_uri + '\'' +
                '}';
    }
}
