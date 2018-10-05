package com.bbs.controller;

import com.bbs.exception.CustomerException;
import com.bbs.pojo.Member;
import com.bbs.service.MemberService;
import com.bbs.utils.Captcha;
import com.bbs.utils.ReturnJson;
import com.bbs.utils.getUUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.SimpleFormatter;


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
            Member status = memberService.login(member);
            if (status != null) {
                request.getSession().setAttribute("member", status.getId());
                return new ReturnJson(0, "登陆成功 ", 0, "");
            } else {
                return new ReturnJson(0, "用户名或密码错误", 0, "");
            }

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
    public ReturnJson doReg(HttpServletRequest request, @RequestBody Member member) throws CustomerException {
        member.setRegIP(request.getRemoteAddr().toString());
        int i = memberService.insertMember(member);
        if (i > 0) {
            return new ReturnJson(0, "注册成功", 0, "");
        }
        return new ReturnJson(1, "注册失败", 0, "");
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

    /**
     * 注销用户
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:index";
    }

    /**
     * 富文本上传图片
     * @param request
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public ReturnJson upload(HttpServletRequest request, MultipartFile file) throws IOException {
        String szFileName=file.getOriginalFilename();
        String szDateFolder="";
        String szNewFileName="";
        String szFilePath="";
        String fileType = "jpg,gif,png,bmp,jpeg";
        String ext="";
        try{
            if(file!=null&&szFileName!=null&&szFileName.length()>0) {
                Date date = new Date();
                szDateFolder=new SimpleDateFormat("yyyy/MM/dd").format(date);
                szFilePath=request.getServletContext().getRealPath("/WEB-INF/upload/")+szDateFolder;
                File f = new File(szFilePath);
                ext=szFileName.substring(szFileName.lastIndexOf(".")+1).toLowerCase().trim();
                if(!Arrays.asList(fileType.split(",")).contains(ext)){
                    return new ReturnJson(1,"上传格式不正确,请上传后缀为[.jpg,.gif,.png,.bmp,.jpeg]",0,"");
                }
                if(!f.exists()) {
                    f.mkdirs();
                }
                szNewFileName=getUUID.getUUID()+szFileName.substring(szFileName.lastIndexOf("."));
                File newFile = new File(szFilePath+"//"+szNewFileName);
                file.transferTo(newFile);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Member m = new Member();
        m.setId((String)request.getSession().getAttribute("member"));
        m.setAvatar("/upload/"+szDateFolder+"/"+szNewFileName);
        memberService.updateAvatar(m);
        return new ReturnJson(0,"上传成功",0,"/upload/"+szDateFolder+"/"+szNewFileName);
    }



}
