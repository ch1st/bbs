package com.bbs.service;

import com.bbs.dao.WordDao;
import com.bbs.pojo.Word;
import com.bbs.utils.GetTime;
import com.bbs.utils.GetUUID;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("WordService")
public class WordService {
    @Autowired
    private WordDao wordDao;

    /**
     * 添加留言
     * @param word
     * @return
     */
    public Integer addWord(Word word){
        word.setId(GetUUID.getUUID());
        word.setStar(0);
        word.setDate(GetTime.getNow());
        System.out.println(word.toString());
        return wordDao.addWord(word);
    }

    /**
     * 查看回复
     * @param tid
     * @return
     */
    public List<Word> getWord(String tid){
        return wordDao.getWord(tid);
    }

    /**
     * 根据文章ID获取评论数量
     * @param tid
     * @return
     */
    public Word getCountWordByArticle(String tid){
        return wordDao.getCountWordByArticle(tid);
    }

    /**
     * 获取回复列表
     * @param id
     * @return
     */
    public List<Map<String, String>>  Words(String id){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<Word> getWord = wordDao.getWord(id);
        for (Word w : getWord
        ) {
            Map<String, String> word = new HashMap<String, String>();
            word.put("userId", w.getMember().getId());
            word.put("replyContent", w.getReplyContent());
            word.put("star", w.getStar().toString());
            word.put("date", w.getDate().substring(0, w.getDate().length() - 2));
            word.put("name", w.getMember().getName());
            word.put("avatar", w.getMember().getAvatar());
            word.put("signature",w.getMember().getSignature());
            list.add(word);
        }
        return list;
    }


}
