package io.walkers.planes.fundhelper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置 添加拦截器
 *
 * @author planeswalker23
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加登录校验拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                // 静态资源
                .excludePathPatterns("/images/*", "/css/*", "/fonts/*", "/js/*")
                // 接口 or 页面(不需要经过登录校验的)
                .excludePathPatterns("/", "/login", "/error", "/h2");
    }
}
