package com.ww.android.esclub.bean.start;

import com.ww.android.esclub.bean.user.BookTableBean;

import java.io.Serializable;

/**
 * Created by feng on 2017/6/22.
 */

public class UserBean implements Serializable{

    /**
     * id : 1
     * nickname : 大呆
     * avatar : http://
     * point : 100
     * rank : 白金
     *
     * exp : 8888
     * level : 4
     * critical_exp : 10000
     * member_type : 1
     * guess_num : 100
     * guess_win_rate : 80
     *
     */
    private String id;
    private String nickname;
    private String avatar;
    private String point;
    private String rank;
    private String exp;
    private String level;
    private String critical_exp;
    private String member_type;
    private String guess_num;
    private String guess_win_rate;
    private String token;

    private BookTableBean book_table;

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

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCritical_exp() {
        return critical_exp;
    }

    public void setCritical_exp(String critical_exp) {
        this.critical_exp = critical_exp;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public String getGuess_num() {
        return guess_num;
    }

    public void setGuess_num(String guess_num) {
        this.guess_num = guess_num;
    }

    public String getGuess_win_rate() {
        return guess_win_rate;
    }

    public void setGuess_win_rate(String guess_win_rate) {
        this.guess_win_rate = guess_win_rate;
    }


    public BookTableBean getBook_table() {
        return book_table;
    }

    public void setBook_table(BookTableBean book_table) {
        this.book_table = book_table;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
