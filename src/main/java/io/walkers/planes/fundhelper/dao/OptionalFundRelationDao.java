package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.OptionalFundRelationModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Dao of {@link OptionalFundRelationModel}
 *
 * @author planeswalker23
 */
@Mapper
public interface OptionalFundRelationDao {
    /**
     * 根据 virtualUserId 查询自选基金
     *
     * @param virtualUserId 用户ID
     * @return List
     */
    @Select("select * from optional_fund_relation where virtual_user_id=#{virtualUserId}")
    List<OptionalFundRelationModel> selectByVirtualUserId(@Param("virtualUserId") Long virtualUserId);
}
