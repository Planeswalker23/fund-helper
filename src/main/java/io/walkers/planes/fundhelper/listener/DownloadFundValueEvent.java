package io.walkers.planes.fundhelper.listener;

import io.walkers.planes.fundhelper.entity.model.FundModel;
import org.springframework.context.ApplicationEvent;

/**
 * 获取基金净值事件
 *
 * @author planeswalker23
 */
public class DownloadFundValueEvent extends ApplicationEvent {

    /**
     * 目标基金信息
     */
    private final FundModel fund;
    /**
     * 页码
     */
    private final Integer page;

    public DownloadFundValueEvent(Object source, FundModel fund) {
        super(source);
        this.fund = fund;
        this.page = Integer.MAX_VALUE;
    }

    public DownloadFundValueEvent(Object source, FundModel fund, Integer page) {
        super(source);
        this.fund = fund;
        // 若传入 page 为空，默认为最大值
        this.page = page == null ? Integer.MAX_VALUE : page;
    }

    public FundModel getFund() {
        return fund;
    }

    public Integer getPage() {
        return page;
    }
}
