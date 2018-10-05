package com.bbs.controller;

import com.bbs.pojo.Article;
import com.bbs.pojo.Type;
import com.bbs.service.ArticleService;
import com.bbs.service.TypeService;
import com.bbs.utils.ReturnJson;
import com.bbs.utils.getUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/post")
public class ArticleController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletRequest request) {
        //获取文章分类
        List<Type> type = typeService.getTypes();
        request.setAttribute("type",type);
        return "edit";
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
                szNewFileName= getUUID.getUUID()+szFileName.substring(szFileName.lastIndexOf("."));
                File newFile = new File(szFilePath+"//"+szNewFileName);
                file.transferTo(newFile);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("src","/upload/"+szDateFolder+"/"+szNewFileName);
        return new ReturnJson(0,"上传成功",0,map);
    }

    @RequestMapping(value="/doAdd",method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson doAdd(HttpServletRequest request, @RequestBody Article article){
         article.setUser((String)request.getSession().getAttribute("member"));
         Integer i =articleService.addArticle(article);

         if(i>0){
             return new ReturnJson(0,"发表成功",0,"");
         }
        return new ReturnJson(1,"发表失败",0,"");
    }
}
