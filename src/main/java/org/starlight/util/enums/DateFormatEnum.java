package org.starlight.util.enums;

import java.time.format.DateTimeFormatter;

/**
 * @author 黑色的小火苗
 */

public enum DateFormatEnum {

    ISO_LOCAL_DATE_TIME(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    /**
     * 格式化样式: yyyy-MM-dd
     */
    ISO_LOCAL_DATE(DateTimeFormatter.ISO_LOCAL_DATE),
    /**
     * 格式化样式: HH:mm:ss
     */
    ISO_LOCAL_TIME(DateTimeFormatter.ISO_LOCAL_TIME);


    private final DateTimeFormatter formatter;

    DateFormatEnum(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
