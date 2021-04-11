package io.walkers.planes.fundhelper.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * {@link RestTemplate} 工具类
 *
 * @author planeswalker23
 */
@Slf4j
public class RestTemplateUtil {

    private static final RestTemplate REST_CLIENT = new RestTemplate();

    /**
     * GET 请求
     *
     * @param url    请求地址
     * @param clazz  返回类型
     * @param params 参数
     * @param <T>    泛型
     * @return 返回对象
     */
    public static <T> T doGet(String url, Class<T> clazz, Map<String, Object> params) {
        if (!CollectionUtils.isEmpty(params)) {
            StringBuilder stringBuilder = new StringBuilder(url).append("?");
            params.forEach((K, V) -> stringBuilder.append(K).append("=").append(V).append("&"));
            url = stringBuilder.toString();
        }
        return REST_CLIENT.getForObject(url, clazz, params);
    }
}
