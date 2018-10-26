package com.bbs.service;

import com.bbs.dao.StarDao;
import com.bbs.pojo.Star;
import com.bbs.pojo.Word;
import com.bbs.utils.GetTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        star.setStarTime(GetTime.getNow());
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

    /**
     * 个人主页点赞文章列表
     * @param id
     * @return
     */
    public List<Map<String,String>> getStarByUserId(Integer pageNum, String id){
        PageHelper.startPage(pageNum,10);
        List<Star> star=starDao.getStarByUserId(id);
        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        Map<String,String> count = new HashMap<String, String>();
        PageInfo page = new PageInfo(star);
        for (int i=0;i<page.getList().size();i++
        ) {
            IdentityHashMap<String,String> map = new IdentityHashMap<String, String>();
            map.put("id",star.get(i).getArticleId());
            map.put("name",star.get(i).getMember().getName());
            String convert=star.get(i).getStarTime();
            map.put("updateTime",convert.substring(0,convert.length()-2));
            map.put("title",star.get(i).getArticle().getTitle());
            map.put("avatar",star.get(i).getMember().getAvatar());
            list.add(map);
        }
        count.put("count",page.getTotal()+"");
        count.put("pageNum",page.getPageNum()+"");
        list.add(count);
        return list;
    }
}
