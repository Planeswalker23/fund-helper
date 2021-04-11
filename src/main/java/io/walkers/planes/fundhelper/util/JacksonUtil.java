package io.walkers.planes.fundhelper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.walkers.planes.fundhelper.entity.dict.TimeFormatDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ObjectMapper} jackson工具类
 *
 * @author planeswalker23
 */
@Slf4j
@Component
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    /**
     * 将任意对象转换成json格式
     *
     * @param object 任意对象
     * @return String
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Transform json to string failed, source object is: " + object);
        }
    }

    /**
     * 将字符串转换成指定类型的对象
     *
     * @param json  被转换的字符串
     * @param clazz 指定对象的类型
     * @param <T>   泛型
     * @return T
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Transform json from String to " + clazz + " failed, source string is: " + json);
        }
    }

    /**
     * 将字符串转换成list对象
     *
     * @param json  被转换的字符串
     * @param clazz 指定对象的类型
     * @param <T>   泛型
     * @return List
     */
    public static <T> List<T> parseArray(String json, Class<? extends T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Transform json from String to array of " + clazz + " failed, source string is: " + json);
        }
    }

    /**
     * setter注入static对象objectMapper
     *
     * @param objectMapperFromSpring 将spring容器自动根据配置文件new的ObjectMapper对象注入给util类中的objectMapper
     *                               否则会因为静态类对象的NPE
     *                               FAIL_ON_UNKNOWN_PROPERTIES-false 忽略String中存在而实体类不存在的字段，防止报错
     */
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapperFromSpring) {
        // String转化为实体类时，忽略String中存在而实体类不存在的字段，防止报错
        objectMapperFromSpring.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化时，自定义Date类型的格式化方式
        objectMapperFromSpring.setDateFormat(TimeFormatDict.YYYY_MM_DD_HH_MM_SS);
        objectMapper = objectMapperFromSpring;
    }
}
