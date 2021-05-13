package io.walkers.planes.fundhelper.dao;

import io.walkers.planes.fundhelper.entity.model.WebsiteNoticeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 查询未激活数据
     *
     * @return List
     */
    @Select("select * from website_notice where active_date <= DATE_FORMAT(now(), 'yyyy-MM-dd 23:59:59')")
    List<WebsiteNoticeModel> selectActivatedWebsiteNotices();
}
