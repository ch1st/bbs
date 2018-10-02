package com.bbs.exception;

import com.bbs.utils.ReturnJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GlobalExceptionHandler implements HandlerExceptionResolver {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        ReturnJson r =null;
        ObjectMapper mapper = new ObjectMapper();
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); //设置状态码500
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);//设置返回格式
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        if(ex instanceof CustomerException){
             r = new ReturnJson(1,((CustomerException)ex).getMsg(),0,"");
        }else{
            System.out.println("/n未知异常如下:"+ ex.getMessage());
             r = new ReturnJson(1,"未知错误",0,"");
        }

        try {
            response.getWriter().write(mapper.writeValueAsString(r));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }
}
