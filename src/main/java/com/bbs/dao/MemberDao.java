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
    public boolean login(Member member);
}
