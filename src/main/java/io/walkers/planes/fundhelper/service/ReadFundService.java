package io.walkers.planes.fundhelper.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.dao.JoinSubQueryDao;
import io.walkers.planes.fundhelper.dao.OptionalFundRelationDao;
import io.walkers.planes.fundhelper.entity.dict.FundTypeDict;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.PageEntity;
import io.walkers.planes.fundhelper.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询基金服务
 *
 * @author planeswalker23
 * @see FundModel
 */
@Slf4j
@Service
public class ReadFundService {
    @Resource
    private FundDao fundDao;
    @Resource
    private OptionalFundRelationDao optionalFundRelationDao;
    @Resource
    private JoinSubQueryDao joinSubQueryDao;

    /**
     * 查询所有 FundModel
     *
     * @param pageEntity 分页条件
     * @return PageInfo
     */
    public PageInfo<FundModel> selectAll(PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNum(), pageEntity.getPageSize());
        return new PageInfo<>(this.postFormatData(fundDao.selectAll()));
    }

    /**
     * 根据关键词查询所有 FundModel
     *
     * @param keyword    关键词
     * @param pageEntity 分页条件
     * @return PageInfo
     */
    public PageInfo<FundModel> selectAllByKeyword(String keyword, PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNum(), pageEntity.getPageSize());
        return new PageInfo<>(this.postFormatData(fundDao.selectByKeyword(keyword)));
    }

    /**
     * 查询所有自选基金
     *
     * @param pageEntity 分页条件
     * @return PageInfo
     */
    public PageInfo<FundModel> selectAllOptionalFund(PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNum(), pageEntity.getPageSize());
        List<FundModel> optionalFunds = joinSubQueryDao.selectByVirtualUserId(SessionUtil.getLoginUser().getId());
        return new PageInfo<>(this.postFormatData(optionalFunds));
    }

    /**
     * 根据关键词查询所有自选基金
     *
     * @param keyword    关键词
     * @param pageEntity 分页条件
     * @return PageInfo
     */
    public PageInfo<FundModel> selectAllOptionalFundByKeyword(String keyword, PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNum(), pageEntity.getPageSize());
        return new PageInfo<>(this.postFormatData(joinSubQueryDao.selectOptionalFundsByKeyword(SessionUtil.getLoginUser().getId(), keyword)));
    }

    /**
     * 后置处理：格式化
     *
     * @param sourceData 源数据
     * @return List
     */
    private List<FundModel> postFormatData(List<FundModel> sourceData) {
        if (CollectionUtils.isEmpty(sourceData)) {
            return sourceData;
        }
        sourceData.forEach(source -> {
            // 基金类型展示为中文
            source.setType(FundTypeDict.valueOf(source.getType()).getLabel());
        });
        return sourceData;
    }
}
