package com.bbs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
    public static String getNow(){
        Date now=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(now);
        return time;
    }
}
