package io.walkers.planes.fundhelper.entity.dict;

import java.text.SimpleDateFormat;

/**
 * 时间格式
 *
 * @author planeswalker23
 */
public interface TimeFormatDict {

    SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
}
