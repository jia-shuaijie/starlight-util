package org.starlight.util;

import java.time.*;
import java.util.Date;

/**
 * @author 黑色的小火苗
 */
public class DateUtil {
    /**
     * 将LocalDateTime转换为Date
     *
     * @param now LocalDateTime
     * @return Date
     */
    public static Date toDate(LocalDateTime now) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将Date转换为LocalTime
     *
     * @param date 日期时间
     * @return LocalDate
     */
    public static LocalTime toLocalTime(Date date) {
        return getZone(date).toLocalTime();
    }

    /**
     * 将Date转换为LocalDate
     *
     * @param date 日期时间
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return getZone(date).toLocalDate();
    }

    /**
     * 将Date转换为LocalDateTime
     *
     * @param date 日期时间
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return getZone(date).toLocalDateTime();
    }


    public static ZonedDateTime getZone(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }


}
