package com.huy.util.format;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.util.ObjectUtils;

/**
 * json格式化/解析器
 *
 * @author: huyong
 * @since: 2018/2/8 11:08
 */
public class FormatJson {

    /**
     * 数据转化为json格式（默认为jackjson）
     *
     * @param data 待转换的数据
     * @return
     */
    public static String toJson(Object data) {
        return toJsonJackJson(data);
    }

    /**
     * 把json串转化为对象
     *
     * @param json  json数据
     * @param clazz 生成对象的类型
     * @param <T>
     * @return
     */
    public static <T> T parseJson(String json, Class<T> clazz) {

        try {
            return readValueToObject(json, clazz);
        } catch (Exception e) {

        }

        return null;

    }

    public static String writeObjectToJson(Object object) throws Exception {
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T readValueToObject(String json, Class<T> tClass) throws Exception {
        if (ObjectUtils.isEmpty(json)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, tClass);
    }

    /**
     * json化
     *
     * @param data 数据
     * @param type json化方式
     * @return
     */
    public static String toJson(Object data, JsonType type) {

        switch (type) {

            case jackJson:
                return toJsonJackJson(data);

            default:
                return toJsonAlibaba(data);

        }

    }

    /**
     * 使用alibaba进行json化
     *
     * @param data
     * @return
     */
    private static String toJsonAlibaba(Object data) {
        try {
            return JSONObject.toJSONString(data);
        } catch (Exception e) {

        }
        return toString(data);
    }

    /**
     * 使用jackjson进行json化
     *
     * @param data
     * @return
     */
    private static String toJsonJackJson(Object data) {
        if (ObjectUtils.isEmpty(data)) {
            return "";
        }

        try {
            return writeObjectToJson(data);
        } catch (Exception e) {
        }
        return toString(data);
    }

    /**
     * Object 转 String
     *
     * @param data
     * @return
     */
    public static String toString(Object data) {
        return data != null ? data.toString() : null;
    }

    /**
     * json缩进
     *
     * @param data  待缩进的数据
     * @param clazz 数据对应的封装类型
     * @return
     */
    public static <T> String pretty(String data, Class<T> clazz) {

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        try {
            T value = mapper.readValue(data, clazz);
            return mapper.writeValueAsString(value);
        } catch (Exception e) {

        }

        return data;

    }

    /**
     * json类型
     */
    public enum JsonType {
        alibaba, jackJson;
    }


}
