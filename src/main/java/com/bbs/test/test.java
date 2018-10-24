package com.bbs.test;


import com.bbs.dao.MemberDao;
import com.bbs.exception.CustomerException;
import com.bbs.pojo.Article;
import com.bbs.pojo.Member;
import com.bbs.pojo.Word;
import com.bbs.service.ArticleService;
import com.bbs.service.MemberService;
import com.bbs.service.WordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mybatis.xml","classpath:spring-servlet.xml"})
@WebAppConfiguration("src/main/webapp")
public class test extends AbstractJUnit4SpringContextTests {
    @Autowired
    MemberService memberService;
    @Autowired
    ArticleService articleService;
    @Autowired
    WordService wordService;
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void test1() throws CustomerException {

        System.out.println(articleService.getArticle("21d20b2d-0d4c-4009-8580-3d3c3a4b2f4a","8cefbedb-2f82-4ae7-9917-e888be7db1ca"));
    }
}

