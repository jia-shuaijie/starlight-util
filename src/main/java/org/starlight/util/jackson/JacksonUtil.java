package org.starlight.util.jackson;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.starlight.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * jackson json格式化工具类
 *
 * @author 黑色的小火苗
 */
public class JacksonUtil {
    public static ObjectMapper om = ObjectMapperUtil.om;


    /**
     * Object to json string.
     *
     * @param obj obj
     * @return json string
     */
    public static String toJson(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param cls  class of object
     * @param <T>  General type
     * @return object
     */
    public static <T> T toObj(byte[] json, Class<T> cls) {
        try {
            return toObj(StringUtils.newStringForUtf8(json), cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param cls  {@link Type} of object
     * @param <T>  General type
     * @return object
     */
    public static <T> T toObj(byte[] json, Type cls) {
        try {
            return toObj(StringUtils.newStringForUtf8(json), cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param cls         class of object
     * @param <T>         General type
     * @return object
     */
    public static <T> T toObj(InputStream inputStream, Class<T> cls) {
        try {
            return om.readValue(inputStream, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string byte array
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object
     */
    public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
        try {
            return toObj(StringUtils.newStringForUtf8(json), typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param cls  class of object
     * @param <T>  General type
     * @return object
     */
    public static <T> T toObj(String json, Class<T> cls) {
        try {
            return om.readValue(json, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param type {@link Type} of object
     * @param <T>  General type
     * @return object

     */
    public static <T> T toObj(String json, Type type) {
        try {
            return om.readValue(json, om.constructType(type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object

     */
    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return om.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param type        {@link Type} of object
     * @param <T>         General type
     * @return object

     */
    public static <T> T toObj(InputStream inputStream, Type type) {
        try {
            return om.readValue(inputStream, om.constructType(type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Json string deserialize to Jackson {@link JsonNode}.
     *
     * @param json json string
     * @return {@link JsonNode}
     */
    public static JsonNode toObj(String json) {
        try {
            return om.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
