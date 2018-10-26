package com.bbs.controller;

import com.bbs.exception.CustomerException;
import com.bbs.pojo.Article;
import com.bbs.pojo.Member;
import com.bbs.pojo.Type;
import com.bbs.pojo.Word;
import com.bbs.service.*;
import com.bbs.utils.Captcha;
import com.bbs.utils.ReturnJson;
import com.bbs.utils.GetUUID;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class IndexController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WordService wordService;
    @Autowired
    private TypeService typeService;

    @Autowired
    private StarService starService;

    @ModelAttribute
    public void init(Model model){
        List<Map<String, String>> top=articleService.topArticles();
        List<Type> t=typeService.getTypes();
        List<Member> members=memberService.getIndexMember();
        model.addAttribute("top",top);
        model.addAttribute("type",t);
        model.addAttribute("members",members);
    }


    @RequestMapping("/index")
    public ModelAndView index(
            @RequestParam(value="page",defaultValue="1",required = false) Integer pageNum,
            @RequestParam(value="tab",defaultValue = "0",required = false) String tab,
            HttpServletRequest request
    ) {
        ModelAndView modelAndView = new ModelAndView();
        String member=(String)request.getSession().getAttribute("member");
        if(!StringUtils.isEmpty(member)){
            modelAndView.addObject("user",memberService.getUserInfo(member));
        }
        List<Map<String, String>> indexArticle = articleService.getIndexArticle(pageNum,tab);
        Map<String,String> count=new HashMap<String, String>();
        count.put("count",indexArticle.get(indexArticle.size()-1).get("count"));
        count.put("pageNum",indexArticle.get(indexArticle.size()-1).get("pageNum"));
        indexArticle.remove(indexArticle.size()-1);
        modelAndView.addObject("index",indexArticle);
        modelAndView.addObject("count",count);
        modelAndView.addObject("viewArticle",articleService.getTopViewArticle());
        modelAndView.addObject("tab",tab);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /*
     * 登陆
     * */
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /*
     * 登陆验证
     * */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson doLogin(HttpServletRequest request, @RequestBody Member member) {
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
            Member status = memberService.login(member);
            if (status != null) {
                request.getSession().setAttribute("member", status.getId());
                memberService.updateLoginTimeAndIP(request.getRemoteAddr(),status.getId());
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
    public ModelAndView reg() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
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
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:index");
        return modelAndView;
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
                szNewFileName= GetUUID.getUUID()+szFileName.substring(szFileName.lastIndexOf("."));
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

    @RequestMapping(value = "/t/{id}",method = RequestMethod.GET)
    public ModelAndView viewArticle(HttpServletRequest request,@PathVariable(value="id")String id) throws CustomerException {
        ModelAndView modelAndView = new ModelAndView();
        String member= (String) request.getSession().getAttribute("member");
        Map<String,String> map=articleService.getArticleByID(id,member);
        List<Map<String, String>> list = wordService.Words(id);
        //访问量增加
        Integer view = Integer.parseInt(map.get("view"));
        if (view == null) {
            view = 0;
        }
        articleService.addView(id, view + 1);
        map.put("count", list.size() + "");
        modelAndView.addObject("article",map);
        modelAndView.addObject("word",list);
        if(map!=null){
            modelAndView.setViewName("context");
        }else{
            modelAndView.setViewName("404");
        }
        return modelAndView;
    }

    /**
     * 我的主页
     * @return
     */
    @RequestMapping("/u/{id}")
    public ModelAndView info(
            HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestParam(value="page",defaultValue="1",required = false) Integer pageNum,
            @RequestParam(value="tab",defaultValue = "1",required = false) String tab
    ){
        ModelAndView modelAndView  = new ModelAndView();
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        //添加总数信息
        if(tab.equals("1")){
            list=memberService.getInfo(pageNum,id);
        }else{
            //获取所有回复
            list=starService.getStarByUserId(pageNum,id);
        }
        Map<String,String> map2=new HashMap<String, String>();
        map2.put("count",list.get(list.size()-1).get("count"));
        map2.put("pageNum",list.get(list.size()-1).get("pageNum"));
        list.remove(list.size()-1);
        modelAndView.addObject("list",list);
        modelAndView.addObject("page",map2);
        modelAndView.addObject("tab",tab);
        modelAndView.addObject("user",memberService.getUserInfo(id));
        modelAndView.setViewName("userinfo-2");
        return modelAndView;
    }
}
