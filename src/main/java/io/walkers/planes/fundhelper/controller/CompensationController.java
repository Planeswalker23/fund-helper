package io.walkers.planes.fundhelper.controller;

import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.Response;
import io.walkers.planes.fundhelper.listener.DownloadFundValueEvent;
import io.walkers.planes.fundhelper.listener.RecalculateNullIncreaseRateEvent;
import io.walkers.planes.fundhelper.service.notice.NoticeMessage;
import io.walkers.planes.fundhelper.service.notice.NoticeMethod;
import io.walkers.planes.fundhelper.service.notice.NotificationSelector;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private NotificationSelector notificationSelector;

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

    /**
     * 发送邮件
     *
     * @return
     */
    @Deprecated
    @GetMapping("/sendMail")
    public Response<String> sendMail() {
        NoticeMessage noticeMessage = new NoticeMessage();
        noticeMessage.setReceiver("fanyidong.fyd@alibaba-inc.com");
        noticeMessage.setTitle("测试标题");
        noticeMessage.setContent("测试内容");
        noticeMessage.setNoticeMethod(NoticeMethod.Mail);
        notificationSelector.doNotice(noticeMessage);
        return Response.success();
    }
}
