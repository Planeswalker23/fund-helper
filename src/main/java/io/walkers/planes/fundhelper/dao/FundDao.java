package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.FundModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Dao of {@link io.walkers.planes.fundhelper.entity.model.FundModel}
 *
 * @author planeswalker23
 */
@Mapper
public interface FundDao {
    /**
     * 根据 Code 查询基金
     *
     * @param code 基金代码
     * @return FundModel
     */
    @Select("select * from fund where code = #{code} limit 1")
    FundModel selectByCode(@Param("code") String code);

    /**
     * 插入 Fund 数据
     *
     * @param fund 待插入数据
     */
    void insert(@Param("fund") FundModel fund);

    /**
     * 插入 Fund 数据，数据为空不赋值
     *
     * @param fund 待插入数据
     */
    void insertSelective(@Param("fund") FundModel fund);

    /**
     * 查询所有基金数据
     *
     * @return List
     */
    @Select("select * from fund order by code")
    List<FundModel> selectAll();

    /**
     * 根据 FundId 列表查询基金
     *
     * @param ids ID 列表
     * @return List
     */
    List<FundModel> selectByIds(@Param("list") List<Long> ids);

    /**
     * 根据关键词查询
     *
     * @param keyword 关键词
     * @return List
     */
    @Select("select * from fund where (name like CONCAT('%', #{keyword}, '%') or code like CONCAT('%', #{keyword}, '%') or manager like CONCAT('%', #{keyword}, '%')) order by code")
    List<FundModel> selectByKeyword(String keyword);
}
