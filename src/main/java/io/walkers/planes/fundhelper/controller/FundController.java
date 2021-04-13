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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Fund 控制层
 *
 * @author planeswalker23
 */
@Validated
@RestController
@RequestMapping("/fund")
public class FundController {

    @Resource
    private WriteFundService writeFundService;
    @Resource
    private OptionalFundRelationService optionalFundRelationService;

    /**
     * 创建基金
     *
     * @param code 基金代码
     * @return Response
     */
    @PostMapping("/create")
    public Response<FundModel> createFund(@RequestParam("code") @NotBlank String code) {
        return Response.success(writeFundService.createFundByCode(code));
    }

    /**
     * 添加自选基金
     *
     * @param code 基金代码
     * @return Response
     */
    @PostMapping("/addOptionalFund")
    public Response<FundModel> addOptionalFund(@RequestParam("code") @NotBlank String code) {
        return Response.success(writeFundService.addOptionalFund(code));
    }

    /**
     * 批量添加自选基金
     *
     * @param codes 基金代码列表
     * @return Response
     */
    @PostMapping("/batchAddOptionalFund")
    public Response<String> batchAddOptionalFund(@RequestParam("codes") @NotBlank String codes) {
        writeFundService.batchAddOptionalFund(codes);
        return Response.success();
    }

    /**
     * 取消自选基金
     *
     * @param fundId 基金ID
     * @return Response
     */
    @PostMapping("/cancelOptionalFund")
    public Response<String> cancelOptionalFund(@RequestParam("fundId") @NotNull Long fundId) {
        optionalFundRelationService.cancelOptionalFund(fundId);
        return Response.success();
    }
}
