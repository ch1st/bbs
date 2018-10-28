package com.bbs.test;


import com.bbs.dao.*;
import com.bbs.exception.CustomerException;
import com.bbs.pojo.*;
import com.bbs.service.*;
import com.bbs.utils.Bcrypt;
import com.bbs.utils.DateToLong;
import com.bbs.utils.GetTime;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ResetService resetService;

    @Autowired
    private MessageService messageService;
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void test1() throws CustomerException, ParseException {

        System.out.println(messageService.getCountMessageByUserId("8cefbedb-2f82-4ae7-9917-e888be7db1ca"));
    }

    }


