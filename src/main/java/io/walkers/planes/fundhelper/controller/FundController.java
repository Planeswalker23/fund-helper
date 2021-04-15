package io.walkers.planes.fundhelper.controller;

import io.walkers.planes.fundhelper.entity.dict.DateTypeDict;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.Response;
import io.walkers.planes.fundhelper.service.OptionalFundRelationService;
import io.walkers.planes.fundhelper.service.ReadFundService;
import io.walkers.planes.fundhelper.service.WriteFundService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

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
    private ReadFundService readFundService;
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

    /**
     * 获取基金净值数据
     *
     * @param fundId 基金ID
     * @param dateType 日期类型 {@link DateTypeDict}
     * @return Response
     */
    @GetMapping("/queryValueByDateType")
    public Response<Map<String, List<Object>>> queryValueByDateType(@RequestParam("fundId") @NotNull Long fundId, @RequestParam("dateType") @NotBlank String dateType) {
        return Response.success(readFundService.queryValueByDateType(fundId, dateType));
    }
}
