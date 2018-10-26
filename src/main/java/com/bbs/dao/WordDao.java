package com.bbs.dao;

import com.bbs.pojo.Word;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WordDao {

    /**
     * 帖子回复
     * @param word
     * @return
     */
    public Integer addWord(Word word);

    /**
     * 查看回复
     * @param tid
     * @return
     */
    List<Word> getWord(@Param("tid")String tid);

    /**
     * 根据文章ID获取评论数量
     * @param tid
     * @return
     */
    Word getCountWordByArticle(@Param("tid") String tid);

    /**
     * 个人主页回复列表
     * @param userId
     * @return
     */
    List<Word> getReplyByUserId(@Param("userId") String userId);
}
