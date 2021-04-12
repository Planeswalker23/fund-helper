package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.FundModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 关联查询 DAO
 *
 * @author 范逸东
 */
@Mapper
public interface JoinSubQueryDao {

    /**
     * 根据关键词查询自选基金
     *
     * @param virtualUserId 用户ID
     * @param keyword       关键词
     * @return List
     */
    @Select("select fund.* from fund " +
            "left join optional_fund_relation on fund.id = optional_fund_relation.fund_id " +
            "where optional_fund_relation.virtual_user_id = #{virtualUserId} " +
            "and (fund.name like CONCAT('%', #{keyword}, '%') or fund.code like CONCAT('%', #{keyword}, '%') or fund.manager like CONCAT('%', #{keyword}, '%')) " +
            "order by code")
    List<FundModel> selectOptionalFundsByKeyword(Long virtualUserId, String keyword);

    /**
     * 根据 virtualUserId 查询自选基金
     *
     * @param virtualUserId 用户ID
     * @return List
     */
    @Select("select fund.* from fund left join optional_fund_relation on fund.id = optional_fund_relation.fund_id " +
            "where virtual_user_id=#{virtualUserId} order by optional_fund_relation.create_date")
    List<FundModel> selectByVirtualUserId(@Param("virtualUserId") Long virtualUserId);
}
