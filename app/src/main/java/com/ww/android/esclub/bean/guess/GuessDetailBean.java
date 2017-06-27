package com.ww.android.esclub.bean.guess;

/**
 * Created by feng on 2017/6/27.
 */

public class GuessDetailBean {


    /**
     * id : 1
     * title : 哪一队先拿一血?
     * option_a : WE
     * option_b : JDG
     * a_point : 200
     * b_point : 100
     * answer : 1
     * status : 2
     * bet : {"id":"1","option":"1","point":"1","profit":"10","created":"2017-06-23 20:22:22"}
     * created : 2017-06-23 20:22:22
     */

    private String id;
    private String title;
    private String option_a;
    private String option_b;
    private String a_point;
    private String b_point;
    private String answer;
    private String status;
    private GuessBetBean bet;
    private String created;

    //增加字段
    private String option; //下注项(1:A;2:B;)
    private String point; //下注积分

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getA_point() {
        return a_point;
    }

    public void setA_point(String a_point) {
        this.a_point = a_point;
    }

    public String getB_point() {
        return b_point;
    }

    public void setB_point(String b_point) {
        this.b_point = b_point;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GuessBetBean getBet() {
        return bet;
    }

    public void setBet(GuessBetBean bet) {
        this.bet = bet;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
