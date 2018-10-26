package com.bbs.controller;

import com.bbs.exception.CustomerException;
import com.bbs.pojo.Article;
import com.bbs.pojo.Member;
import com.bbs.service.ArticleService;
import com.bbs.service.MemberService;
import com.bbs.service.StarService;
import com.bbs.service.WordService;
import com.bbs.utils.ReturnJson;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private WordService wordService;

    @Autowired
    private StarService starService;

    @Autowired
    private ArticleService articleService;

    @ModelAttribute
    public void init(Model model,HttpServletRequest request){
        model.addAttribute("user",memberService.getUserInfo((String)request.getSession().getAttribute("member")));
        model.addAttribute("members",memberService.getIndexMember());
    }
    /**
     * 用户中心
     */
    @RequestMapping("/setting")
    public ModelAndView setting(HttpServletRequest request) {
        ModelAndView modelAndView =new ModelAndView();
        String id=(String) request.getSession().getAttribute("member");
        Member m= memberService.getMemberById(id);
        modelAndView.addObject("info",m);
        modelAndView.setViewName("userSetting");
        return modelAndView;
    }

    /**
     * 更新用户自定义信息
     */
    @RequestMapping(value="updateInfo",method = RequestMethod.PATCH)
    @ResponseBody
    public ReturnJson updateMember(HttpServletRequest request, @RequestBody Member member) throws CustomerException {
        member.setId((String) request.getSession().getAttribute("member"));
        Integer i = memberService.update(member);
        if(i>0){
            return new ReturnJson(0,"更新成功",0,"");
        }
        return new ReturnJson(1,"更新失败",0,"");
    }

    @RequestMapping(value="changePwd",method = RequestMethod.PATCH)
    @ResponseBody
    public ReturnJson updatePwd(HttpServletRequest request,@RequestBody Map<String,String> data){
        Integer i=memberService.updatePwd((String)request.getSession().getAttribute("member"),data.get("oldPass"),data.get("newPass"));
        if(i==0){
            request.getSession().invalidate();
            return new ReturnJson(0,"修改密码成功",0,"");
        }
        return new ReturnJson(1,"修改密码失败",0,"");
    }

    /**
     * 我的帖子
     * @return
     */
    @RequestMapping("/topic")
    public ModelAndView topic(HttpServletRequest request,
                              @RequestParam(value="page",defaultValue="1",required = false) Integer pageNum,
                              @RequestParam(value="tab",defaultValue = "1",required = false) String tab){
        ModelAndView modelAndView = new ModelAndView();
        List<Map<String,String>> list = articleService.getArticleByUserId(pageNum,(String)request.getSession().getAttribute("member"));
        Map<String,String> map2=new HashMap<String, String>();
        map2.put("count",list.get(list.size()-1).get("count"));
        map2.put("pageNum",list.get(list.size()-1).get("pageNum"));
        list.remove(list.size()-1);
        modelAndView.addObject("list",list);
        modelAndView.addObject("page",map2);
        modelAndView.setViewName("userTopic");
        return modelAndView;
    }

    /**
     * 我的主页
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(
            HttpServletRequest request,
            @RequestParam(value="page",defaultValue="1",required = false) Integer pageNum,
            @RequestParam(value="tab",defaultValue = "1",required = false) String tab
        ){
        ModelAndView modelAndView  = new ModelAndView();
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        //添加总数信息
        if(tab.equals("1")){
            list=memberService.getInfo(pageNum,(String)request.getSession().getAttribute("member"));
        }else{
            //获取所有回复
            list=starService.getStarByUserId(pageNum,(String)request.getSession().getAttribute("member"));
        }
        Map<String,String> map2=new HashMap<String, String>();
        map2.put("count",list.get(list.size()-1).get("count"));
        map2.put("pageNum",list.get(list.size()-1).get("pageNum"));
        list.remove(list.size()-1);
        modelAndView.addObject("list",list);
        modelAndView.addObject("page",map2);
        modelAndView.addObject("tab",tab);
        modelAndView.setViewName("userinfo");
        return modelAndView;
    }

    @RequestMapping(value="/changeStatus",method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson changeStatus(@RequestBody Article article,HttpServletRequest request){
        Integer i=null;
        i=articleService.updateArticleStatusByUserId(article.getStatus(),article.getId(),(String)request.getSession().getAttribute("member"));
        if(i>0){
            return new ReturnJson(0,"更改成功",0,"");
        }
        return new ReturnJson(1,"更改失败",0,"");
    }

    /**
     * 我的消息
     * @return
     */
    @RequestMapping("message")
    public ModelAndView msg(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("message");
        return modelAndView;
    }


}
