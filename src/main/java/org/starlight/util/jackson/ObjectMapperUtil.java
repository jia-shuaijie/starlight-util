package org.starlight.util.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.starlight.util.enums.DateFormatEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 黑色的小火苗
 */
public class ObjectMapperUtil {
    public static ObjectMapper om;

    static {
        om = new ObjectMapper();
        setConfig();
        om.registerModule(getJavaTimeModule());
    }


    private static void setConfig() {
        //对象的所有字段全部列入
        om.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.registerModule(getJavaTimeModule());
    }

    private static JavaTimeModule getJavaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter localDateTimeFormat = DateFormatEnum.ISO_LOCAL_DATE_TIME.getFormatter();
        DateTimeFormatter localDateFormat = DateFormatEnum.ISO_LOCAL_DATE.getFormatter();
        DateTimeFormatter localTimeFormat = DateFormatEnum.ISO_LOCAL_DATE_TIME.getFormatter();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormat));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormat));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(localDateFormat));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(localDateFormat));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(localTimeFormat));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(localDateTimeFormat));
        return javaTimeModule;
    }

}

