package com.ww.android.esclub.bean.home;

/**
 * Created by feng on 2017/6/25.
 */

public class CommentBean {

    /**
     * id : 1
     * nickname : 大呆
     * avatar : http://
     * rank : 白金
     * comment : 去年买了个表
     * created : 2012-12-12 23:00:11
     */

    private String id;
    private String nickname;
    private String avatar;
    private String rank;
    private String comment;
    private String created;

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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
