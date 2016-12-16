package com.mxiaixy.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当用户注册成功后 向用户邮箱发送激活账号邮件
 * Created by Mxia on 2016/12/16.
 */
public class EmailUtil {


    //安全日志
    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
    //发送邮件
    public static void sendHtmlEmail( String toAddress,String title,String content){
        HtmlEmail htmlEmail = new HtmlEmail();//获取邮件对象

        htmlEmail.setHostName(Config.get("HostName"));
        htmlEmail.setAuthentication(Config.get("sendEmailName"),Config.get("sendEmailpwd"));
        htmlEmail.setCharset(Config.get("setCharset"));
        htmlEmail.setStartTLSEnabled(true);


        try {
            htmlEmail.setFrom(Config.get("setForm"));
            htmlEmail.setSubject(title);
            htmlEmail.setHtmlMsg(content);
            htmlEmail.addTo(toAddress);

            htmlEmail.send();
        }catch(EmailException e){
            e.printStackTrace();
            throw new RuntimeException("邮件发送异常");

        }






    }


}
