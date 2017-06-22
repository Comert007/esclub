package com.ww.android.esclub.bean.start;

/**
 * Created by feng on 2017/6/22.
 */

public class UserBean {

    /**
     * id : 1
     * nickname : 大呆
     * avatar : http://
     * point : 100
     * rank : 白金
     */

    private String id;
    private String nickname;
    private String avatar;
    private String point;
    private String rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", point='" + point + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
