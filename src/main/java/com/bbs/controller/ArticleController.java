package com.bbs.controller;

import com.bbs.exception.CustomerException;
import com.bbs.pojo.Article;
import com.bbs.pojo.Star;
import com.bbs.pojo.Type;
import com.bbs.pojo.Word;
import com.bbs.service.*;
import com.bbs.utils.ReturnJson;
import com.bbs.utils.GetUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/post")
public class ArticleController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WordService wordService;

    @Autowired
    private StarService starService;

    @Autowired
    private MemberService memberService;
    @ModelAttribute
    public void init(Model model,HttpServletRequest request){
        model.addAttribute("user",memberService.getUserInfo((String)request.getSession().getAttribute("member")));
        model.addAttribute("type",typeService.getTypes());
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        //获取文章分类
        ModelAndView modelAndView = new ModelAndView();
        List<Type> type = typeService.getTypes();
        modelAndView.addObject("type",type);
        modelAndView.setViewName("edit");
        return modelAndView;
    }
    @RequestMapping(value="/uploadImg",method=RequestMethod.POST)
    @ResponseBody
    public ReturnJson upImg(HttpServletRequest request, MultipartFile file){
        String szFileName=file.getOriginalFilename();
        String szDateFolder="";
        String szNewFileName="";
        String szFilePath="";
        String fileType = "jpg,gif,png,bmp,jpeg";
        String ext="";
        Map<String,String> map = new HashMap<String, String>();
        try{
            if(file!=null&&szFileName!=null&&szFileName.length()>0) {
                Date date = new Date();
                szDateFolder=new SimpleDateFormat("yyyy/MM/dd").format(date);
                szFilePath=request.getServletContext().getRealPath("/WEB-INF/upload/")+szDateFolder;
                File f = new File(szFilePath);
                ext=szFileName.substring(szFileName.lastIndexOf(".")+1).toLowerCase().trim();
                if(!Arrays.asList(fileType.split(",")).contains(ext)){
                    return new ReturnJson(1,"上传格式不正确,请上传后缀为[.jpg,.gif,.png,.bmp,.jpeg]",0,"");
                }                if(!f.exists()) {
                    f.mkdirs();
                }
                szNewFileName= GetUUID.getUUID()+szFileName.substring(szFileName.lastIndexOf("."));
                File newFile = new File(szFilePath+"//"+szNewFileName);
                file.transferTo(newFile);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("src","/upload/"+szDateFolder+"/"+szNewFileName);
        return new ReturnJson(0,"上传成功",0,map);
    }

    /**
     * 发表帖子
     * @param request
     * @param article
     * @return
     */
    @RequestMapping(value="/doAdd",method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson doAdd(HttpServletRequest request, HttpServletResponse response, @RequestBody Article article) throws CustomerException, ServletException, IOException {
        article.setUser((String)request.getSession().getAttribute("member"));
        Integer i =null;
        if(!StringUtils.isEmpty(article.getId())){
            i=articleService.updateArticle(article);
        }else{
            i=articleService.addArticle(article);
        }
         if(i>0){
             return new ReturnJson(0,"发表成功",0,"");
         }
        return new ReturnJson(1,"发表失败",0,"");
    }

    /**
     * 增加留言
     * @param request
     * @param word
     * @return
     */
    @RequestMapping(value="/addWord",method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson addWord(HttpServletRequest request, @RequestBody Word word){
        word.setUser((String) request.getSession().getAttribute("member"));
        Integer i =wordService.addWord(word);
        if(i>0){
            return new ReturnJson(0,"回复成功",0,"");
        }
        return new ReturnJson(1,"回复失败",0,"");
    }


    @RequestMapping(value="/star",method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson star(HttpServletRequest request,@RequestBody Star star) throws CustomerException {
        Article article = articleService.getArticleId(star.getArticleId());
        String originUser= (String) request.getSession().getAttribute("member");
        Star s=starService.getStatus(star);
        Integer starNum=article.getStar();
        if(starNum==null){
            starNum=0;
        }
        if(originUser.equals(star.getUserId())){
            return new ReturnJson(1,"不能自己给自己点赞哦～",0,"");
        }
        if(s==null){
            starService.addStar(star);

            articleService.addStar(star.getArticleId(),starNum+1);
            return new ReturnJson(0,"点赞成功",0,"");
        }else if(s!=null){
            starService.delStar(s.getId());
            articleService.subStar(star.getArticleId(),starNum-1);
            return new ReturnJson(0,"取消赞成功",0,"");
        }
        return new ReturnJson(1,"点赞失败",0,"");
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editAritcle(@PathVariable("id")String id,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("article", articleService.getArticle(id,(String)request.getSession().getAttribute("member")));
        modelAndView.setViewName("edit");
        return modelAndView;
    }
}
