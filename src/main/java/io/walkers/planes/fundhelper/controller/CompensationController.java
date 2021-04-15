package io.walkers.planes.fundhelper.controller;

import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.Response;
import io.walkers.planes.fundhelper.listener.DownloadFundValueEvent;
import io.walkers.planes.fundhelper.listener.RecalculateNullIncreaseRateEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * 补偿接口控制层
 *
 * @author planeswalker23
 */
@Validated
@RestController
@RequestMapping("/compensation")
public class CompensationController {

    @Resource
    private FundDao fundDao;
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 拉取基金净值
     *
     * @param code 基金代码
     * @param page 页码(可选参数)
     * @return Response
     */
    @PostMapping("/downloadFundValue")
    public Response<String> downloadFundValue(@RequestParam("code") @NotBlank(message = "基金代码不能为空") String code, Integer page) {
        FundModel fundModel = fundDao.selectByCode(code);
        applicationContext.publishEvent(new DownloadFundValueEvent(this, fundModel, page));
        return Response.success();
    }

    /**
     * 重新计算日增长率为 null 的基金净值
     *
     * @param code 基金代码
     * @return Response
     */
    @PostMapping("/recalculateNullIncreaseRate")
    public Response<String> recalculateNullIncreaseRate(@RequestParam("code") @NotBlank(message = "基金代码不能为空") String code) {
        FundModel fundModel = fundDao.selectByCode(code);
        applicationContext.publishEvent(new RecalculateNullIncreaseRateEvent(this, fundModel));
        return Response.success();
    }
}
