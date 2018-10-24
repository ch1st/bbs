package com.bbs.service;

import com.bbs.dao.MemberDao;
import com.bbs.exception.CustomerException;
import com.bbs.pojo.Article;
import com.bbs.pojo.Member;
import com.bbs.utils.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.bbs.utils.GetUUID;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("MemberService")
public class MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 注册会员
     *
     * @param member
     * @return
     */
    public Integer insertMember(Member member) throws CustomerException {
        member.setRegTime(GetTime.getNow());
        member.setId(GetUUID.getUUID());
        member.setName(member.getUsername());
        member.setStatus(0); //0是允许登陆
        System.out.println(member.toString());
        Integer i = null;
        try {
            i = memberDao.insert(member);
        } catch (DuplicateKeyException e) {
            throw  new CustomerException("用户名重复");
        }
        return i;
    }

    /**
     * 前台用户登陆
     *
     * @param member
     * @return
     */
    public Member login(Member member) {
        member.setLoginTime(GetTime.getNow());
        return memberDao.login(member);
    }

    /**
     * 用户自定义信息更新
     * @param member
     * @return
     */
    public Integer update(Member member) throws CustomerException {
        Integer i=null;
        try {
             i = memberDao.update(member);
        }catch (DuplicateKeyException e){
            throw new CustomerException("昵称重复");
        }
        return i;
    }

    /**
     * 根据ID查找当前用户的信息
     * @param id
     * @return
     */
    public Member getMemberById(String id){
        return memberDao.getMemberById(id);
    }

    /**
     * 更新头像地址
     * @param member
     */
    public void updateAvatar(Member member) {
         memberDao.updateAvatar(member);
    }

    /**
     * 用户自行更改密码
     * @param id
     * @param oldPass
     * @param newPass
     * @return
     */
    public Integer updatePwd(String id,String oldPass,String newPass){
        return memberDao.updatePwd(id,oldPass,newPass);
    }

    /**
     * 个人中心获取帖子
     * @param userId
     * @return
     */
    public List<Article> getInfo(String userId){
        return memberDao.getInfo(userId);
    }

    /**
     * 获取首页会员
     * @return
     */
    public List<Member> getIndexMember(){
        return memberDao.getIndexMember();
    }

    /**
     * 追加登录时间和登录IP
     * @param id
     */
    public void updateLoginTimeAndIP(String loginIP,String id){
        String loginTime=GetTime.getNow();
        memberDao.updateLoginTimeAndIP(loginIP,loginTime,id);
    }

    /**
     * 获取首页会员信息
     * @param id
     * @return
     */
    public Member getUserInfo(String id){
        return memberDao.getUserInfo(id);
    }
}
