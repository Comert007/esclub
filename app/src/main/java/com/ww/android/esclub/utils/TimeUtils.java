package com.ww.android.esclub.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feng on 2017/6/29.
 */

public class TimeUtils {
    private static final long MINUTE_SECONDS = 60; //1分钟多少秒
    private static final long HOUR_SECONDS = MINUTE_SECONDS*60;
    private static final long DAY_SECONDS = HOUR_SECONDS*24;
    private static final long YEAR_SECONDS = DAY_SECONDS*365;



    public static String passNowTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLong =0l;
        try {
            Date date = sdf.parse(time);
            timeLong = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getStandardDate(timeLong);
    }

    public static String getStandardDate(long timeLong) {

        StringBuffer sb = new StringBuffer();

        long time = System.currentTimeMillis() - timeLong;
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }
}
