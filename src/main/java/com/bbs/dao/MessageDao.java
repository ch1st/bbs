package com.bbs.dao;

import com.bbs.pojo.Member;
import com.bbs.pojo.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageDao {

    /**
     * 获取全部消息
     * @return
     */
    public List<Message> getMessageByUserId(@Param("userId")String userId);

    /**
     * 插入消息
     * @param message
     * @return
     */
    public Integer insertMessage(Message message);

    /**
     * 更新状态
     * @param id
     * @param user
     * @return
     */
    public Integer updateStatus(@Param("id")Integer id,@Param("user")String user,@Param("status")Integer status);

    /**
     * 获取消息条数
     * @param userId
     * @return
     */
    public Message getCountMessageByUserId(@Param("userId")String userId);
}
