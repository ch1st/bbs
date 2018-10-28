package com.bbs.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Bcrypt {

    /**
     * 加密密码
     * @param pwd
     * @return
     */
    public static String bryptPwd(String pwd){
        return BCrypt.hashpw(pwd,BCrypt.gensalt());
    }

    /**
     * 验证密码
     * @param pwd
     * @param hashed
     * @return
     */
    public static boolean validPwd(String pwd,String hashed){
        try{
        return BCrypt.checkpw(pwd, hashed);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
