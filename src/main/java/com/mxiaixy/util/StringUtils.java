package com.mxiaixy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by Mxia on 2016/12/15.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    public static  String isoToUtf8(String userName){


        try {
            return new String(userName.getBytes("ISO8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("转换{}异常",userName);
           throw new  RuntimeException("字符转换异常",e);
        }
    }


}
