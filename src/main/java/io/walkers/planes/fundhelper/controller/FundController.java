package io.walkers.planes.fundhelper.controller;

import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.Response;
import io.walkers.planes.fundhelper.service.OptionalFundRelationService;
import io.walkers.planes.fundhelper.service.WriteFundService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Fund 控制层
 *
 * @author planeswalker23
 */
@RestController
@RequestMapping("/fund")
public class FundController {

    @Resource
    private WriteFundService writeFundService;
    @Resource
    private OptionalFundRelationService optionalFundRelationService;

    /**
     * 创建基金 & 基金净值
     *
     * @param fundModel 基金模型
     * @return Response
     */
    @PostMapping("/create")
    public Response<FundModel> createFund(@Validated FundModel fundModel) {
        return Response.success(writeFundService.createFundByCode(fundModel.getCode()));
    }

    /**
     * 添加自选基金
     *
     * @param fundModel 基金模型
     * @return Response
     */
    @PostMapping("/addOptionalFund")
    public Response<FundModel> addOptionalFund(@Validated FundModel fundModel) {
        return Response.success(writeFundService.addOptionalFund(fundModel.getCode()));
    }

    /**
     * 取消自选基金
     *
     * @param fundId 基金ID
     * @return Response
     */
    @PostMapping("/cancelOptionalFund")
    public Response<String> cancelOptionalFund(@RequestParam("fundId") Long fundId) {
        optionalFundRelationService.cancelOptionalFund(fundId);
        return Response.success();
    }
}
