package io.walkers.planes.fundhelper.config;

import com.google.common.collect.Lists;
import io.walkers.planes.fundhelper.entity.dict.PageNameDict;
import io.walkers.planes.fundhelper.entity.pojo.PageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 页面信息 配置类
 *
 * @author planeswalker23
 */
@Configuration
public class PageConfig {

    /**
     * 页面信息列表
     * @return List
     */
    @Bean
    public List<PageModel> pageModels() {
        List<PageModel> list = Lists.newArrayList();
        // 自选基金页面
        list.add(PageModel.builder()
                .pageName(PageNameDict.OPTIONAL_FUND_NAME)
                .pageCode(PageNameDict.OPTIONAL_FUND)
                .iconClass("mdi mdi-tag-plus")
                .build());
        // 测试页面
        list.add(PageModel.builder()
                .pageName("TestName")
                .pageCode("TestCode")
                .iconClass("mdi mdi-database")
                .build());
        return list;
    }
}
