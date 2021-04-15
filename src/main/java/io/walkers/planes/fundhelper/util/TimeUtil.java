package io.walkers.planes.fundhelper.util;

import io.walkers.planes.fundhelper.entity.dict.TimeFormatDict;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author planeswalker23
 */
@Slf4j
public class TimeUtil {

    private static ThreadLocal<DateFormat> SQL_DATE_FORMAT_THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(TimeFormatDict.FORMAT_YYYY_MM_DD));

    /**
     * 时间格式化 String to {@link java.sql.Date}
     * @param timeString 时间入参
     * @return java.sql.Date
     */
    public static java.sql.Date string2JavSqlDate(String timeString) {
        return new java.sql.Date(TimeUtil.string2JavaUtilDate(timeString).getTime());
    }

    /**
     * 时间格式化 String to {@link java.util.Date}
     * @param timeString 时间入参
     * @return java.util.Date
     */
    public static java.util.Date string2JavaUtilDate(String timeString) {
        java.util.Date time;
        try {
            // 取字符串前10位
            time = SQL_DATE_FORMAT_THREAD_LOCAL.get().parse(timeString.substring(0, 10));
        } catch (Exception e) {
            log.error("Date format failed, source parameter is: {}, caused by: ", timeString, e);
            throw new RuntimeException("Date format failed");
        }
        return time;
    }
}
