package io.walkers.planes.fundhelper.entity.pojo;

import io.walkers.planes.fundhelper.entity.dict.MessageDict;
import io.walkers.planes.fundhelper.util.JacksonUtil;
import lombok.Data;

/**
 * 返回公用类
 *
 * @author planeswalker23
 */
@Data
public class Response<T> {
    /**
     * 返回结果是否成功
     */
    private boolean success;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static Response<String> success() {
        Response<String> res = new Response<>();
        res.setSuccess(true);
        res.setMessage(MessageDict.SUCCESS);
        return res;
    }

    public static <T> Response<T> success(T data) {
        Response<T> res = new Response<T>();
        res.setSuccess(true);
        res.setMessage(MessageDict.SUCCESS);
        res.setData(data);
        return res;
    }

    public static Response<String> failed(String reason) {
        Response<String> res = new Response<>();
        res.setSuccess(false);
        res.setMessage(reason);
        return res;
    }

    @Override
    public String toString() {
        return JacksonUtil.toJson(this);
    }
}
