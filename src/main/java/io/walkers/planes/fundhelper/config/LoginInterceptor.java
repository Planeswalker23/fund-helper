package io.walkers.planes.fundhelper.config;

import io.walkers.planes.fundhelper.entity.model.VirtualUserModel;
import io.walkers.planes.fundhelper.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，拦截除指定路由外其余未登录的请求
 *
 * @author planeswalker23
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor, EnvironmentAware {

    private static boolean isTest = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (isTest) {
                forTest();
            }
            SessionUtil.getLoginUser();
        } catch (RuntimeException e) {
            log.error("访问{{}}页面出错，原因是：{}", request.getServletPath(), e.getMessage());
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
        return true;
    }

    /**
     * 本地测试时使用，免登陆
     */
    private void forTest() {
        VirtualUserModel virtualUser = VirtualUserModel.builder()
                .id(1L)
                .account("root")
                .password("password")
                .build();
        SessionUtil.updateAttribute("loginUser", virtualUser);
    }

    @Override
    public void setEnvironment(Environment environment) {
        String[] activeProfiles = environment.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("prod".equals(profile)) {
                isTest = false;
            }
        }
    }
}
