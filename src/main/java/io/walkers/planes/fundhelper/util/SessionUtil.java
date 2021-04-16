package io.walkers.planes.fundhelper.util;

import io.walkers.planes.fundhelper.config.FundHelperException;
import io.walkers.planes.fundhelper.entity.dict.MessageDict;
import io.walkers.planes.fundhelper.entity.model.VirtualUserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * session工具类
 *
 * @author planeswalker23
 */
@Slf4j
public class SessionUtil {

    /**
     * 获取 session
     *
     * @return {@link HttpSession}
     */
    private static HttpSession getSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }

    /**
     * 获取已登录的 user 信息
     *
     * @return {@link HttpSession}
     */
    public static VirtualUserModel getLoginUser() {
        VirtualUserModel user = (VirtualUserModel) getSession().getAttribute("loginUser");
        if (user == null) {
            throw new FundHelperException(MessageDict.NOT_LOGIN);
        }
        return user;
    }

    /**
     * 更新 session 信息
     *
     * @param key   键
     * @param value 值
     */
    public static void updateAttribute(String key, Object value) {
        HttpSession httpSession = getSession();
        httpSession.setAttribute(key, value);
        // 默认过期时间为 60 分钟
        httpSession.setMaxInactiveInterval(60);
    }
}
