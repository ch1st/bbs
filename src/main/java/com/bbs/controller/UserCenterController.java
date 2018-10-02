package com.bbs.controller;

import com.bbs.pojo.Member;
import com.bbs.service.MemberService;
import com.bbs.utils.ReturnJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserCenterController {

    @Autowired
    private MemberService memberService;


    /**
     * 用户中心
     */
    @RequestMapping("/setting")
    public String setting(HttpServletRequest request) {
        String id=(String) request.getSession().getAttribute("member");
        Member m= memberService.getMemberById(id);
        request.setAttribute("info",m);
        return "userSetting";
    }

    /**
     * 更新用户自定义信息
     */
    @RequestMapping(value="updateInfo",method = RequestMethod.PATCH)
    @ResponseBody
    public ReturnJson updateMember(HttpServletRequest request, @RequestBody Member member){
        member.setId((String) request.getSession().getAttribute("member"));
        Integer i = memberService.update(member);
        if(i>0){
            return new ReturnJson(0,"更新成功",0,"");
        }
        return new ReturnJson(1,"更新失败",0,"");
    }
}
