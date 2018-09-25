package com.bbs.service;

import com.bbs.dao.MemberDao;
import com.bbs.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param member
     * @return
     */
    public Integer insertMember(Member member){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        member.setRegTime(sdf.format(now));
        member.setId(getUUID.getUUID());
        member.setName(member.getUsername());
        member.setStatus(0); //0是允许登陆
        System.out.println(member.toString());
        Integer i=memberDao.insert(member);
        return i;
    }
}
