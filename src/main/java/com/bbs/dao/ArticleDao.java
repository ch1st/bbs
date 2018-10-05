package com.bbs.dao;

import com.bbs.pojo.Article;

public interface ArticleDao {

    /**
     * 插入文章
     * @param article
     * @return
     */
    public Integer addArticle(Article article);
}
