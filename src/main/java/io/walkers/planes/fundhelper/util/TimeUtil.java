package io.walkers.planes.fundhelper.util;

import io.walkers.planes.fundhelper.entity.dict.DateTypeDict;
import io.walkers.planes.fundhelper.entity.dict.MessageDict;
import io.walkers.planes.fundhelper.entity.dict.TimeFormatDict;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;

/**
 * @author planeswalker23
 */
@Slf4j
public class TimeUtil {

    private static final ThreadLocal<DateFormat> SQL_DATE_FORMAT_THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(TimeFormatDict.FORMAT_YYYY_MM_DD));

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
            log.error("日期格式化失败，源数据为：{}，错误原因：{}", timeString, e);
            throw new RuntimeException(MessageDict.DATE_FORMAT_FAILED);
        }
        return time;
    }

    /**
     * 根据日期类型计算新日期
     * Example: formerDate("MONTH") -> return Date of a month ago
     * @param dateType 日期类型
     * @return java.sql.Date
     */
    public static java.sql.Date formerDate(String dateType) {
        LocalDate currentDate = LocalDate.now();
        // 根据入参判断计算间隔
        Period period = Period.ZERO;
        if (DateTypeDict.WEEK.name().equals(dateType)) {
            period = Period.ofWeeks(1);
        } else if (DateTypeDict.MONTH.name().equals(dateType)) {
            period = Period.ofMonths(1);
        } else if (DateTypeDict.THREE_MONTH.name().equals(dateType)) {
            period = Period.ofMonths(3);
        } else if (DateTypeDict.HALF_YEAR.name().equals(dateType)) {
            period = Period.ofMonths(6);
        } else if (DateTypeDict.YEAR.name().equals(dateType)) {
            period = Period.ofYears(1);
        }
        currentDate = currentDate.minus(period);
        return new java.sql.Date(currentDate.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }
}
