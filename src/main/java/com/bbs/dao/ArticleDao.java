package com.bbs.dao;

import com.bbs.pojo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {

    /**
     * 插入文章
     * @param article
     * @return
     */
    public Integer addArticle(Article article);

    /**
     * 根据文章ID查询内容
     * @param ArticleID
     * @return
     */
    public Article getArticleByID(@Param("ArticleID") String ArticleID);

    /**
     * 文章点赞
     * @param id
     * @param star
     * @return
     */
    public Integer addStar(@Param("id")String id,@Param("star")Integer star);

    /**
     * 取消点赞
     * @param id
     * @param star
     * @return
     */
    public Integer subStar(@Param("id")String id,@Param("star")Integer star);

    /**
     * 获取首先TOP4帖子
     * @return
     */
    public List<Article> topArticles();

    /**
     * 获取首页主要帖子
     * @return
     */
    List<Article> getIndexArticle(@Param("tab")String tab);

    /**
     * 增加访问量
     * @param id
     * @param view
     * @return
     */
    public Integer addView(@Param("id")String id,@Param("view")Integer view);

    /**
     * 获取首页点击量最多的文章
     * @return
     */
    public List<Article> getTopViewArticle();

    /**
     * 编辑文章
     * @param id
     * @param userId
     * @return
     */
    public Article getArticle(@Param("id")String id,@Param("userId")String userId);

    /**
     * 编辑文章发表
     * @param article
     * @return
     */
    Integer updateArticle(Article article);

    /**
     * 根据用户查询所有文章
     * @param userId
     * @return
     */
    List<Article> getArticleByUserId(@Param("userId")String userId);

    /**
     * 更改文章状态
     */
    public Integer updateArticleStatusByUserId(@Param("status")Integer status,@Param("id")String id,@Param("userId")String userId);
}
