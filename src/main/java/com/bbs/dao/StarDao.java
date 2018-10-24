package com.bbs.dao;

import com.bbs.pojo.Star;
import org.apache.ibatis.annotations.Param;

public interface StarDao {
    /**
     * 文章点赞功能
     * @param star
     * @return
     */
    public Integer addStar(Star star);

    /**
     * 检查是否点赞
     * @param star
     * @return
     */
    public Star getStatus(Star star);

    /**
     * 取消点赞
     * @param id
     * @return
     */
    public Integer delStar(@Param("id") Integer id);
}
