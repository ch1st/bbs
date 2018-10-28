package com.bbs.service;

import com.bbs.dao.MessageDao;
import com.bbs.pojo.Message;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("MessageService")
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    /**
     * 获取所有回复
     * @return
     */
    public List<Map<String, String>> getMessageByUserId(Integer pageNum, String userId){
        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        PageHelper.startPage(pageNum, 10);
        List<Message> info = messageDao.getMessageByUserId(userId);
        PageInfo page = new PageInfo(info);
        for (int i=0;i<page.getList().size();i++
        ) {
            Map<String,String> map = new HashMap<String, String>();
            map.put("id",info.get(i).getId());
            map.put("name",info.get(i).getMember().getName());
            String convert=info.get(i).getDate();
            map.put("updateTime",convert.substring(0,convert.length()-2));
            map.put("title",info.get(i).getArticle().getTitle());
            map.put("articleId",info.get(i).getArticleId());
            map.put("msgId",info.get(i).getMember().getUsername());
            list.add(map);
        }
        Map<String,String> map2 = new HashMap<String, String>();
        map2.put("count",page.getTotal()+"");
        map2.put("pageNum",page.getPageNum()+"");
        list.add(map2);
        return list;

    }

    /**
     * 插入消息
     * @param message
     * @return
     */
    public Integer insertMessage(Message message){
        return  messageDao.insertMessage(message);
    }

    /**
     * 更新消息状态
     * @param id
     * @param userId
     * @return
     */
    public Integer updateStatus(Integer id,String userId){
        Integer status=1;
        return messageDao.updateStatus(id,userId,status);
    }

    /**
     *
     * @param userId
     * @return
     */
    public Integer getCountMessageByUserId(String userId){
        Integer count=Integer.parseInt(messageDao.getCountMessageByUserId(userId).getUser());
        return count;
    }

}
