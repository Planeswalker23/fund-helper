package io.walkers.planes.fundhelper.controller;

import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.Response;
import io.walkers.planes.fundhelper.listener.FetchFundValueEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 补偿接口控制层
 *
 * @author planeswalker23
 */
@RestController
@RequestMapping("/compensation")
public class CompensationController {

    @Resource
    private FundDao fundDao;
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 计算基金净值日增长率
     * @param code 基金代码
     * @return Response
     */
    @PostMapping("/increaseRate")
    public Response<String> recalculateIncreaseRate(@RequestParam("code") String code) {
        FundModel fundModel = fundDao.selectByCode(code);
        applicationContext.publishEvent(new FetchFundValueEvent(fundModel));
        return Response.success();
    }
}
