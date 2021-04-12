A widget for helping investors manage funds.

一款帮助投资者管理基金小工具

功能项:

- [x] 获取基金净值详情(基于 RestTemplate 调用新浪财经 API)
- [x] 获取基金基础信息(基于 Jsoup 爬取天天基金网数据)
- [x] 计算基金日增长率(基于 SpringBoot 事件机制)
- [x] 登录页面
    - [x] 登录参数校验提示信息
    - [x] 登录机制(基于 Session)
    - [x] 登录逻辑兼容自动注册功能
- [x] 自选基金页面(展示，搜索，分页)
    - [x] 展示基金(包含分页)
    - [x] 搜索功能
    - [x] 自选基金(新增，取消)
      - [ ] 新增自选基金支持下拉框批量选择

优化项:

- [x] 基于 @ConfigurationProperties 实现 application.yml 配置文件中路由配置项与 Bean 的映射
- [x] 通用返回类
- [x] 统一异常处理机制
- [x] 页面左侧导航栏根据 PageConfig Bean 动态展示
- [x] 登录拦截器，未登录转发至登录页面