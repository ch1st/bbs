package com.bbs.dao;

import com.bbs.pojo.Article;
import com.bbs.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MemberDao {
    /**
     * 插入用户
     * @param member
     * @return
     */
    public Integer insert (Member member);

    /**
     * 登陆用户
     * @param member
     * @return
     */
    public Member login(Member member);

    /**
     * 更新当前用户的自定义信息
     * @param member
     * @return
     */
    public Integer update(Member member);


    /**
     * 根据ID查找用户的所有信息
     * @param id
     * @return
     */
    public Member getMemberById(String id);

    /**
     * 更新头像地址
     * @param member
     */
    public void updateAvatar(Member member);

    /**
     * 用户自行修改密码
     * @param id
     * @param oldPass
     * @param newPass
     * @return
     */
    public Integer updatePwd(@Param("id")String id,@Param("oldPass")String oldPass,@Param("newPass")String newPass);

    /**
     * 个人中心获取帖子
     * @param userId
     * @return
     */
    public List<Article> getInfo(@Param("user") String userId);

    /**
     * 获取首页会员
     * @return
     */
    public List<Member> getIndexMember();

    /**
     * 追加登录时间和IP
     * @param id
     */
    public void updateLoginTimeAndIP(@Param("loginIP") String loginIP,@Param("loginTime") String loginTime,@Param("id")String id);

    /**
     * 获取首页会员简介
     * @param id
     * @return
     */
    public Member getUserInfo(@Param("id")String id);
}
