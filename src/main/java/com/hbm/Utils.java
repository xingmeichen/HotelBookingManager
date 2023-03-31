package com.hbm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @project: HotelBookingManager
 * @description:
 * @author: Mabel.Chen
 * @create: 2023-03-31
 **/
public class Utils {

    private static final ThreadLocal<DateFormat> dateFormatter = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    /***
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (null == date) {
            throw new RuntimeException("Invalid date");
        }
        return dateFormatter.get().format(date);
    }

    /***
     * The date is of targetDate is before baseDate, then return true, else return false
     * @param baseDate
     * @param targetDate
     * @return
     */
    public static boolean isBeforeDate(Date baseDate, Date targetDate) {
        if (null == baseDate || null == targetDate) {
            throw new RuntimeException("Invalid date");
        }
        String baseDateStr = format(baseDate);
        String targetDateStr = format(targetDate);
        return targetDateStr.compareTo(baseDateStr) < 0;
    }
}