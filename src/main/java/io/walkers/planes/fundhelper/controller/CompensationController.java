package io.walkers.planes.fundhelper.controller;

import io.walkers.planes.fundhelper.entity.pojo.Response;
import io.walkers.planes.fundhelper.listener.CalculateIncreaseRateEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private ApplicationContext applicationContext;

    @PostMapping("/increaseRate")
    public Response<String> recalculateIncreaseRate(String code) {
        applicationContext.publishEvent(new CalculateIncreaseRateEvent(code));
        return Response.success();
    }
}
