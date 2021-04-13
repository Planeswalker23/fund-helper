package io.walkers.planes.fundhelper;

import com.github.pagehelper.PageInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * SpringBoot starter
 *
 * @author planeswalker23
 */
@EnableAsync
@SpringBootApplication
public class FundHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundHelperApplication.class, args);
    }

    /**
     * PageHelper 分页插件
     *
     * @return PageInterceptor
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}
