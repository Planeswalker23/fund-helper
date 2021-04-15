package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.FundValueModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Dao of {@link FundValueModel}
 *
 * @author planeswalker23
 */
@Mapper
public interface FundValueDao {

    /**
     * 批量插入(不带日增长率)
     *
     * @param list 待插入数据
     */
    void insertBatchWithoutIncreaseRate(@Param("list") List<FundValueModel> list);

    /**
     * 根据 fundId 查询所有净值
     *
     * @param fundId 基金ID
     * @return List
     */
    @Select("select * from fund_value where fund_id = #{fundId} order by value_date desc")
    List<FundValueModel> selectByFundId(@Param("fundId") Long fundId);

    /**
     * 根据 fundId 查询所有净值
     *
     * @param fundId      基金ID
     * @param targetDate  目标日期
     * @param currentDate 当前日期
     * @return List
     */
    @Select("select * from fund_value where fund_id = #{fundId} and value_date between #{targetDate} and #{currentDate} order by value_date")
    List<FundValueModel> selectByFundIdAndDate(@Param("fundId") Long fundId, @Param("targetDate") java.sql.Date targetDate, @Param("currentDate") java.sql.Date currentDate);
}
