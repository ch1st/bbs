package com.bbs.utils;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class Email {
    public static  boolean send(String to,String subject,String msg){
        Properties props= new Properties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.host","smtp.163.com");
        //收件人
        props.put("mail.from","xhack00@163.com");

        Session session =Session.getDefaultInstance(props);
        session.setDebug(true);

        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", "true");
            //第二步：获取邮件传输对象
            Transport ts= session.getTransport();
            //连接邮件服务器
            ts.connect("*****", "*******");
            //第三步：创建邮件消息体
            MimeMessage message = new MimeMessage(session);
            //设置邮件的内容
            message.setSubject(subject);
            //设置邮件的内容
            message.setContent(msg,"text/html;charset=utf-8");
            //第四步：设置发送昵称
            String nick="";
            nick = javax.mail.internet.MimeUtility.encodeText("重置密码");
            message.setFrom(new InternetAddress(nick+"'<****@163.com>'"));
            //第五步：设置接收人信息
            ts.sendMessage(message, InternetAddress.parse(to));

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

}
