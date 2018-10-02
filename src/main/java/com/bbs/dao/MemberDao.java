package com.bbs.dao;

import com.bbs.pojo.Member;


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
}
