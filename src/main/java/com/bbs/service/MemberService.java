package com.bbs.service;

import com.bbs.dao.MemberDao;
import com.bbs.exception.CustomerException;
import com.bbs.exception.GlobalExceptionHandler;
import com.bbs.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.bbs.utils.getUUID;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        member.setRegTime(sdf.format(now));
        member.setId(getUUID.getUUID());
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
        return memberDao.login(member);
    }

    /**
     * 用户自定义信息更新
     * @param member
     * @return
     */
    public Integer update(Member member){
        return memberDao.update(member);
    }

    /**
     * 根据ID查找当前用户的信息
     * @param id
     * @return
     */
    public Member getMemberById(String id){
        return memberDao.getMemberById(id);
    }
}
