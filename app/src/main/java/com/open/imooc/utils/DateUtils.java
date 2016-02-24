package com.open.imooc.utils;

/**
 * Created by xiangzhihong on 2015/12/28 on 14:15.
 */
public class DateUtils {
    public static String formatTimer(long millis) {
        long day = millis / (24 * 60 * 60 * 1000);
        long hour = (millis / (60 * 60 * 1000) - day * 24);
        long min = ((millis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (millis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return (day == 0 ? "" : day + ":") + (day == 0 ? "" : hour + ":") + min + ":" + sec ;
    }
}
