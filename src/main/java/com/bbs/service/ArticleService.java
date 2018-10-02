package com.bbs.service;

import com.bbs.dao.ArticleDao;
import com.bbs.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ArticleService")
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    public List<Type> getTypes(){
        return articleDao.getTypes();
    }
}
