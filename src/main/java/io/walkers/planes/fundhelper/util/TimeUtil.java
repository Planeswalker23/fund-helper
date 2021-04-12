package io.walkers.planes.fundhelper.util;

import io.walkers.planes.fundhelper.entity.dict.TimeFormatDict;
import lombok.extern.slf4j.Slf4j;

/**
 * @author planeswalker23
 */
@Slf4j
public class TimeUtil {

    /**
     * 时间格式化 java.util.Date to String
     *
     * @param time 时间入参
     * @return String
     */
    public static String date2String(java.util.Date time) {
        return TimeFormatDict.YYYY_MM_DD_HH_MM_SS.format(time);
    }

    /**
     * 时间格式化 String to java.sql.Date
     * @param timeString 时间入参
     * @return java.sql.Date
     */
    public static java.sql.Date string2Date(String timeString) {
        java.util.Date time;
        try {
            // 取字符串前10位
            time = TimeFormatDict.YYYY_MM_DD.parse(timeString.substring(0, 10));
        } catch (Exception e) {
            log.error("Date format failed, source parameter is: {}, caused by: ", timeString, e);
            throw new RuntimeException("Date format failed");
        }
        return new java.sql.Date(time.getTime());
    }
}
