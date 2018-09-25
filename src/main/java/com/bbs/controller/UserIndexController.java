package com.bbs.controller;

import com.bbs.pojo.Member;
import com.bbs.service.MemberService;
import com.bbs.utils.Captcha;
import com.bbs.utils.ReturnJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;


@Controller
public class UserIndexController {
    @Autowired
    private MemberService memberService;
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /*
     * 登陆
     * */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /*
     * 登陆验证
     * */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson doLogin(HttpServletRequest request, @RequestBody Member member) {
        System.out.println(member.toString());
        String code = member.getCode();
        String loginIP = request.getRemoteAddr().toString();
        Object c = request.getSession().getAttribute("simpleCaptcha");
        request.getSession().removeAttribute("simpleCaptcha");
        if (c == null) {
            return new ReturnJson(1, "验证码已失效", 0, "");
        }
        String validCode = c.toString();
        if (StringUtils.isEmpty(code) || validCode == null || !(code.equalsIgnoreCase(validCode))) {
            return new ReturnJson(1, "验证码错误", 0, "");
        } else {
            // 在这里可以处理自己需要的事务，比如验证登陆等
            return new ReturnJson(0, "验证码正确", 0, "");

        }


    }


    /**
     * 注册
     */
    @RequestMapping("/register")
    public String reg() {
        return "register";
    }


    @RequestMapping(value = "/doReg", method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson doReg(HttpServletRequest request, @RequestBody Member member) {
        member.setRegIP(request.getRemoteAddr().toString());
        int i=memberService.insertMember(member);
        System.out.println("i:"+i);
        return null;
    }

    /*
     * 请求验证码
     * */
    @RequestMapping("/getImage")
    @ResponseBody
    public String getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        Map<String, Object> map = Captcha.getImageCode(86, 37, os);
        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", new Date().getTime());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
        } catch (IOException e) {
            return "";
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return null;
    }


}
