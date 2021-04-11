package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.VirtualUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Dao of {@link VirtualUserModel}
 *
 * @author planeswalker23
 */
@Mapper
public interface VirtualUserDao {
    /**
     * 根据 account 查询
     *
     * @param account 用户名
     * @return VirtualUserModel
     */
    @Select("select * from virtual_user where account=#{account} limit 1")
    VirtualUserModel selectByAccount(@Param("account") String account);

    /**
     * 插入数据
     *
     * @param virtualUser 待插入数据
     */
    void insert(@Param("virtualUser") VirtualUserModel virtualUser);
}
