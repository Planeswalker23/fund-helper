package io.walkers.planes.fundhelper.controller;

import com.github.pagehelper.PageInfo;
import io.walkers.planes.fundhelper.entity.dict.PageNameDict;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.PageEntity;
import io.walkers.planes.fundhelper.entity.pojo.PageModel;
import io.walkers.planes.fundhelper.service.FundService;
import io.walkers.planes.fundhelper.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理后台控制层
 *
 * @author planeswalker23
 */
@Slf4j
@Controller
public class PageController {

    @Resource
    private FundService fundService;
    @Resource
    private List<PageModel> pageModels;

    /**
     * 注入页面元素
     *
     * @param model           页面对象
     * @param result          查询结果
     * @param currentPageName 当前页面名称
     */
    private void injectModel(Model model, Object result, String currentPageName) {
        model.addAttribute("pages", pageModels);
        model.addAttribute("result", result);
        model.addAttribute("currentPageName", currentPageName);
        model.addAttribute("loginUser", SessionUtil.getLoginUser());
    }

    /**
     * 自选基金页面
     *
     * @param model 页面 Model 对象
     * @return String
     */
    @GetMapping({"optionalFund", "index"})
    public String optionalFundPage(Model model, PageEntity pageEntity) {
        PageInfo<FundModel> list = fundService.selectAllOptionalFund(pageEntity);
        this.injectModel(model, list, PageNameDict.OPTIONAL_FUND_NAME);
        return PageNameDict.OPTIONAL_FUND;
    }

    /**
     * 自选基金页面(搜索)
     *
     * @param model 页面 Model 对象
     * @return String
     */
    @GetMapping({"searchOptionalFund"})
    public String searchOptionalFundPage(Model model, String keyword, PageEntity pageEntity) {
        PageInfo<FundModel> list = fundService.selectAllOptionalFundByKeyword(keyword, pageEntity);
        this.injectModel(model, list, PageNameDict.OPTIONAL_FUND_NAME);
        return PageNameDict.OPTIONAL_FUND;
    }

    /**
     * 登录页面
     *
     * @return String
     */
    @GetMapping({"login", "/"})
    public String loginPage() {
        // 清空 session 登录状态
        SessionUtil.updateAttribute("loginUser", null);
        return PageNameDict.LOGIN;
    }
}
