package com.ww.android.esclub.bean.guess;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/27.
 */

public class MatchBean implements Serializable{


    private static final long serialVersionUID = -3772040452579398549L;
    /**
     * id : 1
     * a_team_name : JDG
     * a_team_avatar : http://
     * b_team_name : WE
     * b_team_avatar : http://
     * status : 1
     * created : 2017-06-23 20:22:22
     */

    private String id;
    private String a_team_name;
    private String a_team_avatar;
    private String b_team_name;
    private String b_team_avatar;
    private String status;
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getA_team_name() {
        return a_team_name;
    }

    public void setA_team_name(String a_team_name) {
        this.a_team_name = a_team_name;
    }

    public String getA_team_avatar() {
        return a_team_avatar;
    }

    public void setA_team_avatar(String a_team_avatar) {
        this.a_team_avatar = a_team_avatar;
    }

    public String getB_team_name() {
        return b_team_name;
    }

    public void setB_team_name(String b_team_name) {
        this.b_team_name = b_team_name;
    }

    public String getB_team_avatar() {
        return b_team_avatar;
    }

    public void setB_team_avatar(String b_team_avatar) {
        this.b_team_avatar = b_team_avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
