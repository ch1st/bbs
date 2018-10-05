package com.bbs.service;

import com.bbs.dao.ArticleDao;
import com.bbs.pojo.Article;
import com.bbs.utils.getUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
@Service("ArticleService")
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    /**
     * 增加帖子
     * @param article
     * @return
     */
    public Integer addArticle(Article article){
        Date now = new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        article.setAddTime(sdf.format(now));
        article.setId(getUUID.getUUID());
        article.setStar(0);
        article.setView(0);
        System.out.println(article.toString());
        return articleDao.addArticle(article);
    }
}
