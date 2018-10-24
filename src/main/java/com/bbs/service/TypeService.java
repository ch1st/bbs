package com.bbs.service;

import com.bbs.dao.TypeDao;
import com.bbs.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TypeService")
public class TypeService {
    @Autowired
    private TypeDao typeDao;

    public List<Type> getTypes(){
        return typeDao.getTypes();
    }
}
