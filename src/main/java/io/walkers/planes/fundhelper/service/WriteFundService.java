package io.walkers.planes.fundhelper.service;

import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import io.walkers.planes.fundhelper.listener.DownloadFundValueEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 写入基金数据服务
 *
 * @author planeswalker23
 * @see FundModel
 * @see FundValueModel
 */
@Slf4j
@Service
public class WriteFundService {

    @Resource
    private FundDao fundDao;
    @Resource
    private DownloadDataService downloadDataService;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private OptionalFundRelationService optionalFundRelationService;

    /**
     * 创建基金 & 基金净值
     *
     * @param code 基金代码
     * @return FundModel
     */
    @Transactional(rollbackFor = Exception.class)
    public FundModel createFundByCode(String code) {
        // 根据 code 查询基金实体
        FundModel fund = fundDao.selectByCode(code);
        if (fund != null) {
            log.info("基金{{}}数据已存在", code);
            return fund;
        }
        // 获取基金数据
        fund = downloadDataService.fetchFundByCode(code);
        // 持久化基金信息
        fundDao.insertSelective(fund);
        log.info("基金{{}}数据创建成功", code);

        // 发布事件：获取基金净值
        applicationContext.publishEvent(new DownloadFundValueEvent(this, fund));
        return fund;
    }

    /**
     * 添加自选基金
     *
     * @param code 基金代码
     * @return FundModel
     */
    @Transactional(rollbackFor = Exception.class)
    public FundModel addOptionalFund(String code) {
        FundModel fund = fundDao.selectByCode(code);

        // 不存在基金数据，创建
        if (fund == null) {
            fund = this.createFundByCode(code);
        }

        // 新增自选基金关联数据
        optionalFundRelationService.addOptionalFundRelation(fund);
        return fund;
    }

    /**
     * 批量添加自选基金
     *
     * @param codes 基金代码列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchAddOptionalFund(String codes) {
        String[] codeArray = codes.split(",");
        for (String code : codeArray) {
            this.addOptionalFund(code);
        }
    }
}
