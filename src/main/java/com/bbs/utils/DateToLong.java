package com.bbs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToLong {
    public static  long fromDateStringToLong(String inVal) { //此方法计算时间毫秒
         Date date = null; //定义时间类型       
         SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:ss");
         try {
         date = inputFormat.parse(inVal); //将字符型转换成日期型
         } catch (Exception e) {
             e.printStackTrace();
         }
         return date.getTime();//返回毫秒数
}

}
