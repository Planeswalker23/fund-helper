package io.walkers.planes.fundhelper.service;

import io.walkers.planes.fundhelper.dao.OptionalFundRelationDao;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.OptionalFundRelationModel;
import io.walkers.planes.fundhelper.entity.model.VirtualUserModel;
import io.walkers.planes.fundhelper.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * OptionalFundRelation 服务层
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class OptionalFundRelationService {
    @Resource
    private OptionalFundRelationDao optionalFundRelationDao;

    /**
     * 自选基金绑定操作
     *
     * @param fund 待自选基金
     */
    public void addOptionalFundRelation(FundModel fund) {
        VirtualUserModel virtualUser = SessionUtil.getLoginUser();
        OptionalFundRelationModel optionalFundRelation = OptionalFundRelationModel.builder()
                .fundId(fund.getId())
                .virtualUserId(virtualUser.getId())
                .build();
        optionalFundRelationDao.insert(optionalFundRelation);
    }
}
