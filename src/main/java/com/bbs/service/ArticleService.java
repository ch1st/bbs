package com.bbs.service;

import com.bbs.dao.ArticleDao;
import com.bbs.exception.CustomerException;
import com.bbs.pojo.Article;
import com.bbs.pojo.Word;
import com.bbs.utils.GetTime;
import com.bbs.utils.GetUUID;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service("ArticleService")
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private WordService wordService;


    /**
     * 增加帖子
     * @param article
     * @return
     */
    public Integer addArticle(Article article) throws CustomerException {
        article.setAddTime(GetTime.getNow());
        article.setUpdateTime(GetTime.getNow());
        article.setId(GetUUID.getUUID());
        article.setStar(0);
        article.setView(0);
        article.setStatus(0);
        try{
            return articleDao.addArticle(article);
        }catch (DuplicateKeyException e){
            throw  new CustomerException("标题已存在");
        }
    }

    /**
     * 根据帖子ID查询内容
     * @param ArticleID
     * @return
     */
    public Map<String, String> getArticleByID(String ArticleID, String member) throws CustomerException {
        try {
            Article article=articleDao.getArticleByID(ArticleID);
            String userId=article.getMember().getId();
            if(article!=null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", userId);
                map.put("avatar", article.getMember().getAvatar());
                map.put("name", article.getMember().getName());
                map.put("title", article.getTitle());
                String convert = article.getUpdateTime();
                map.put("updateTime", convert.substring(0, convert.length() - 2));
                map.put("view", article.getView().toString());
                map.put("type", article.getTypes().getName());
                map.put("content", article.getContent());
                map.put("id", ArticleID);
                map.put("view",article.getView().toString());
                map.put("star", article.getStar().toString());
                map.put("signature", article.getMember().getSignature());
                if (userId.equals(member)) {
                    map.put("isUser", "1");
                }
                if (member == null) {
                    map.put("isLogin", "1");
                }
                return map;
            }
            return null;
        }catch (NullPointerException e){
            throw new CustomerException("文章ID不存在");
        }
    }

    /**
     * 根据点击查询内容2
     * @param ArticleID
     * @return
     */
    public Article getArticleId(String ArticleID){
        return articleDao.getArticleByID(ArticleID);
    }
    /**
     * 文章点赞
     * @param id
     * @param star
     * @return
     */
    public Integer addStar(String id,Integer star){
        return articleDao.addStar(id,star);
    }

    /**
     * 取消点赞
     * @param id
     * @param star
     * @return
     */
    public Integer subStar(String id,Integer star){
        return articleDao.addStar(id,star);
    }

    /**
     * 增加访问量
     * @param id
     * @param view
     * @return
     */
    public Integer addView(String id,Integer view){
        return articleDao.addView(id,view);
    }

    /**
     * 获取首页TOP4文章
     * @return
     */
    public List<Map<String, String>> topArticles(){
        List<Article> topArticle =articleDao.topArticles();
        List<Map<String,String>> top=new ArrayList<Map<String, String>>();

        for (Article a:topArticle
        ) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("avatar",a.getMember().getAvatar());
            map.put("username",a.getMember().getName());
            map.put("userId",a.getContent());
            map.put("title",a.getTitle());
            map.put("articleId",a.getId());
            Word words=wordService.getCountWordByArticle(a.getId());
            map.put("words",words.getStar().toString());
            map.put("updateTime",a.getUpdateTime().substring(0,a.getUpdateTime().length()-2));
            map.put("star",a.getStar().toString());
            top.add(map);
        }
        return top;
    }

    /**
     * 获取首页主要文章
     * @return
     */

    public List<Map<String, String>> getIndexArticle(Integer pageNum,String tab){
        PageHelper.startPage(pageNum, 10);
        List<Article> topArticle=articleDao.getIndexArticle(tab);
        PageInfo page = new PageInfo(topArticle);
        Map<String,String> count = new HashMap<String, String>();
        List<Map<String,String>> top=new ArrayList<Map<String, String>>();
        for (int i=0;i<page.getList().size();i++
        ) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("avatar",topArticle.get(i).getMember().getAvatar());
            map.put("username",topArticle.get(i).getMember().getName());
            map.put("userId",topArticle.get(i).getMember().getCode());
            map.put("title",topArticle.get(i).getTitle());
            map.put("articleId",topArticle.get(i).getId());
            Word words=wordService.getCountWordByArticle(topArticle.get(i).getId());
            map.put("words",words.getStar().toString());
            map.put("updateTime",topArticle.get(i).getUpdateTime().substring(0,topArticle.get(i).getUpdateTime().length()-2));
            map.put("star",topArticle.get(i).getStar().toString());
            map.put("view",topArticle.get(i).getView().toString());
            map.put("typeName",topArticle.get(i).getTypes().getName());
            top.add(map);
        }
        count.put("count",page.getTotal()+"");
        count.put("pageNum",pageNum+"");
        top.add(count);
        return top;
    }

    /**
     * 搜索文章
     * @param pageNum
     * @param keyword
     * @return
     */
    public List<Map<String,String>> searchArticle(Integer pageNum,String keyword){
        PageHelper.startPage(pageNum,10);
        List<Article> articles=articleDao.searchArticle(keyword);
        PageInfo page = new PageInfo(articles);
        Map<String,String> count = new HashMap<String, String>();
        List<Map<String,String>> data=new ArrayList<Map<String, String>>();
        for (int i=0;i<page.getList().size();i++
        ) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("avatar",articles.get(i).getMember().getAvatar());
            map.put("username",articles.get(i).getMember().getName());
            map.put("userId",articles.get(i).getMember().getCode());
            map.put("title",articles.get(i).getTitle());
            map.put("articleId",articles.get(i).getId());
            Word words=wordService.getCountWordByArticle(articles.get(i).getId());
            map.put("words",words.getStar().toString());
            map.put("updateTime",articles.get(i).getUpdateTime().substring(0,articles.get(i).getUpdateTime().length()-2));
            map.put("star",articles.get(i).getStar().toString());
            map.put("view",articles.get(i).getView().toString());
            map.put("typeName",articles.get(i).getTypes().getName());
            data.add(map);
        }
        count.put("count",page.getTotal()+"");
        count.put("pageNum",pageNum+"");
        data.add(count);
        return data;
    }
    /**
     * 获取首页点赞量最多的文章
     * @return
     */
    public List<Article> getTopViewArticle(){
        return articleDao.getTopViewArticle();
    }

    /**
     * 编辑文章
     * @param id
     * @param userId
     * @return
     */
    public Map<String, String> getArticle(String id, String userId){
        Map<String,String> map = new HashMap<String, String>();
        Article article=articleDao.getArticle(id,userId);
        map.put("title",article.getTitle());
        map.put("content",article.getContent());
        map.put("typeName",article.getTypes().getName());
        map.put("id",article.getId());
        map.put("articleId",article.getUser());
        return map;
    }

    /**
     * 编辑文章发表
     * @param article
     * @return
     */
    public Integer updateArticle(Article article) {
        article.setUpdateTime(GetTime.getNow());
        return articleDao.updateArticle(article);
    }

    /**
     * 我的帖子
     * @param pageNum
     * @param userId
     * @return
     */
    public List<Map<String,String>> getArticleByUserId(Integer pageNum,String userId){
        PageHelper.startPage(pageNum, 10);
        List<Article> article=articleDao.getArticleByUserId(userId);
        PageInfo page = new PageInfo(article);
        Map<String,String> count = new HashMap<String, String>();
        List<Map<String,String>> top=new ArrayList<Map<String, String>>();
        for (int i=0;i<page.getList().size();i++
        ) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("id",article.get(i).getId());
            map.put("title",article.get(i).getTitle());
            map.put("updateTime",article.get(i).getUpdateTime().substring(0,article.get(i).getUpdateTime().length()-2));
            map.put("typeName",article.get(i).getTypes().getName());
            map.put("status",article.get(i).getStatus()+"");
            top.add(map);
        }
        count.put("count",page.getTotal()+"");
        count.put("pageNum",pageNum+"");
        top.add(count);
        return top;
    }

    /**
     * 更改文章状态
     * @param status
     * @param id
     * @param userId
     * @return
     */
    public Integer updateArticleStatusByUserId(Integer status,String id,String userId){
        return articleDao.updateArticleStatusByUserId(status,id,userId);
    }


}
