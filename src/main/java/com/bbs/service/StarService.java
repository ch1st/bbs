package com.bbs.service;

import com.bbs.dao.StarDao;
import com.bbs.pojo.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("StarService")
public class StarService {

    @Autowired
    private StarDao starDao;

    /***
     * 文章点赞功能
     * @param star
     * @return
     */
    public Integer addStar(Star star){
        return starDao.addStar(star);
    }

    /**
     * 检查是否点赞
     * @param star
     * @return
     */
    public Star getStatus(Star star){
        return starDao.getStatus(star);
    }

    /**
     * 取消点赞
     * @param id
     * @return
     */
    public Integer delStar(Integer id){
        return starDao.delStar(id);
    }
}
