package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.WebsiteNoticeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Dao of {@link WebsiteNoticeModel}
 *
 * @author planeswalker23
 */
@Mapper
public interface WebsiteNoticeDao {

    /**
     * 插入 WebsiteNoticeModel 数据
     *
     * @param websiteNotice 待插入数据
     */
    void insert(@Param("websiteNotice") WebsiteNoticeModel websiteNotice);
}
