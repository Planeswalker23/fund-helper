package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.OptionalFundRelationModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Dao of {@link OptionalFundRelationModel}
 *
 * @author planeswalker23
 */
@Mapper
public interface OptionalFundRelationDao {

    /**
     * 自选基金绑定
     *
     * @param optionalFundRelation 关联关系
     */
    @Insert("insert into optional_fund_relation(fund_id, virtual_user_id, create_date, update_date) values (#{relation.fundId}, #{relation.virtualUserId}, now(), now())")
    void insert(@Param("relation") OptionalFundRelationModel optionalFundRelation);

    /**
     * 取消自选基金
     *
     * @param fundId        基金ID
     * @param virtualUserId 用户ID
     */
    @Delete("delete from optional_fund_relation where fund_id=#{fundId} and virtual_user_id=#{virtualUserId}")
    void delete(@Param("fundId") Long fundId, @Param("virtualUserId") Long virtualUserId);
}
