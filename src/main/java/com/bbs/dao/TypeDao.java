package com.bbs.dao;

import com.bbs.pojo.Type;

import java.util.List;

public interface TypeDao {
    /**
     * 获取文章分类
     */
    public List<Type> getTypes();

}
