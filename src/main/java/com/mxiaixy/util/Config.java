package com.mxiaixy.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 为了使config.properies 配置文件需要时只调用一次 做一下配置
 * Created by Mxia on 2016/12/15.
 */
public class Config {

    //获取config.properies 配置文件的对象
    private static Properties properties = new Properties();

    /**
     * 程序初始化时运行一次
     */
    static {
        try {
            //读取配置文件
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件异常",e);
        }
    }

    //获取配置文件值   通过key值 获取valu值
    public static String get(String key){
        return properties.getProperty(key);
    }
}
