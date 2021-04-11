package io.walkers.planes.fundhelper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Fund 数据源配置类
 *
 * @author planeswalker23
 */
@Data
@Component
@ConfigurationProperties(prefix = "fund.datasource")
public class FundDataSource {
    /**
     * 基金净值 URL
     */
    private String valuePath;
    /**
     * 基金详情 URL 前缀
     */
    private String detailPathPrefix;
    /**
     * 基金详情 URL 后缀
     */
    private String detailPathSuffix;
}
