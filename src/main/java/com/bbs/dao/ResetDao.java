package com.bbs.dao;

import com.bbs.pojo.Reset;
import org.apache.ibatis.annotations.Param;

public interface ResetDao {
    /**
     * 忘记密码
     * @param identity
     * @return
     */
    public String forgetPwd(String identity);

    /**
     * 获取用户名字和重置密码的token
     * @param userId
     * @return
     */
    public Reset getUserAndToken(@Param("userId")String userId);

    /**
     * 根据token获取信息
     */
    public Reset getResetByToken(@Param("token")String token);

    /**
     * 根据token删除记录
     * @param token
     * @return
     */
    public Integer delToken(@Param("token")String token);
}
