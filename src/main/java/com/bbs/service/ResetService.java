package com.bbs.service;

import com.bbs.dao.ResetDao;
import com.bbs.pojo.Reset;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("ResetService")
public class ResetService {
    @Autowired
    private ResetDao resetDao;


    /**
     * 忘记密码
     * @param email
     * @return
     */
    public String forgetPwd(String email){
        return resetDao.forgetPwd(email);
    }

    /**
     * 获取用户姓名和token
     * @param userId
     * @return
     */
    public Map<String,String> getUserAndToken(String userId){
        Map<String,String> map = new HashMap<String, String>();
        Reset reset=resetDao.getUserAndToken(userId);
        map.put("token",reset.getToken());
        map.put("name",reset.getMember().getName());
        return map;
    }

    /**
     * 根据token获取信息
     * @param token
     * @return
     */
    public Map<String,String> getResetByToken(String token){
        Map<String,String> map = new HashMap<String, String>();
        Reset reset=resetDao.getResetByToken(token);
        if(reset!=null){
            map.put("userId",reset.getUser());
            map.put("resetTime",reset.getResetTime().substring(0,reset.getResetTime().length()-2));
        }
        return map;
    }

    public Integer delToken(String token){
        return resetDao.delToken(token);
    }
}
