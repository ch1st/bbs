package com.bbs.controller;

import com.bbs.pojo.Type;
import com.bbs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/tz")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletRequest request) {
        //获取文章分类
        List<Type> type = articleService.getTypes();
        request.setAttribute("type",type);
        return "edit";
    }

}
