package io.walkers.planes.fundhelper.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.walkers.planes.fundhelper.dao.FundDao;
import io.walkers.planes.fundhelper.dao.FundValueDao;
import io.walkers.planes.fundhelper.dao.JoinSubQueryDao;
import io.walkers.planes.fundhelper.entity.dict.DateTypeDict;
import io.walkers.planes.fundhelper.entity.dict.FundTypeDict;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import io.walkers.planes.fundhelper.entity.pojo.PageEntity;
import io.walkers.planes.fundhelper.util.SessionUtil;
import io.walkers.planes.fundhelper.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    private FundValueDao fundValueDao;
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

    /**
     * 获取基金净值数据
     *
     * @param fundId   基金ID
     * @param dateType 日期类型 {@link DateTypeDict}
     * @return Map
     */
    public Map<String, List<Object>> queryValueByDateType(Long fundId, String dateType) {
        List<FundValueModel> daoResult = fundValueDao.selectByFundIdAndDate(fundId, TimeUtil.formerDate(dateType), TimeUtil.formerDate(DateTypeDict.NOW.name()));
        // build result
        List<Object> dateList = Lists.newArrayListWithExpectedSize(daoResult.size());
        List<Object> valueList = Lists.newArrayListWithExpectedSize(daoResult.size());
        daoResult.forEach(result -> {
            dateList.add(result.getValueDate().toString());
            valueList.add(result.getValue());
        });
        Map<String, List<Object>> mapResult = Maps.newHashMapWithExpectedSize(2);
        mapResult.put("date", dateList);
        mapResult.put("value", valueList);
        return mapResult;
    }
}
