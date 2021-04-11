package io.walkers.planes.fundhelper.config;

import io.walkers.planes.fundhelper.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，拦截除指定路由外其余未登录的请求
 *
 * @author planeswalker23
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("================== Validate Login state interceptor ================");
        try {
            SessionUtil.getLoginUser();
        } catch (RuntimeException e) {
            log.error("Visit [{}] page goes wrong, message is: {}", request.getServletPath(), e.getMessage());
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
        return true;
    }
}
