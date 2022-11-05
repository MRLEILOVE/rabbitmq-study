package com.leigq.shardingspherestudy.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The type Date utils.
 *
 * @author leiguoqing
 */
public class DateUtils {

    /**
     * To local date time local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        // Date转换为LocalDateTime
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * To local date time local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public static LocalDateTime toLocalDateTime(final String date) {
        String newDate = date;

        // 去除毫秒
        if (date.contains(".")) {
            newDate = date.substring(0, date.indexOf("."));
        }

        return LocalDateTime.parse(newDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * To date date.
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        final Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        // LocalDateTime转换为Date
        return Date.from(instant);
    }

    /**
     * To date date.
     *
     * @param date the date
     * @return the date
     */
    public static Date toDate(String date) {
        // LocalDateTime转换为Date
        return toDate(toLocalDateTime(date));
    }

    /**
     * Gets month.
     *
     * @param date the date
     * @return the month
     */
    public static String getMonth(LocalDateTime date) {
        return date.getMonthValue() < 10 ? String.format("%02d", date.getMonthValue()) : date.getMonthValue() + "";
    }

}
