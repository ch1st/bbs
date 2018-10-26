package com.bbs.test;


import com.bbs.dao.MemberDao;
import com.bbs.dao.StarDao;
import com.bbs.dao.WordDao;
import com.bbs.exception.CustomerException;
import com.bbs.pojo.Article;
import com.bbs.pojo.Member;
import com.bbs.pojo.Star;
import com.bbs.pojo.Word;
import com.bbs.service.ArticleService;
import com.bbs.service.MemberService;
import com.bbs.service.WordService;
import com.github.pagehelper.Page;
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
import java.util.*;


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
    @Autowired
    private WordDao wordDao;
    @Autowired
    private StarDao starDao;
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void test1() throws CustomerException {
        System.out.println(articleService.updateArticleStatusByUserId(0,"a43f4818-c174-4a5a-b346-7e6a92bd13a3","8cefbedb-2f82-4ae7-9917-e888be7db1ca"));
    }
}

