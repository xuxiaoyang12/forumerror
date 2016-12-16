package com.mxiaixy.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据池
 * Created by Mxia on 2016/12/13.
 */
public class ConnectionManager {
    //声明数据库基本数据
    private static String DRIVER;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static BasicDataSource dataSource = new BasicDataSource();//

    static{
        //加载并读取config.properties文件
        //程序运行时只执行一次  实现加载数据库驱动并且获取数据库连接

//        Properties prop = new Properties();
//
//        try {
//            prop.load(ConnectionManager.class.getClassLoader().getResourceAsStream("config.properties"));

        DRIVER =Config.get("jdbc.driver");
        URL=Config.get("jdbc.url");
        USERNAME = Config.get("jdbc.username");
        PASSWORD = Config.get("jdbc.password");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        dataSource.setInitialSize(5);//设置初始化的快数
        dataSource.setMinIdle(5);//设置最小闲置个数
        dataSource.setMaxIdle(20);//设置最大闲置个数
        dataSource.setMaxWaitMillis(5000000);//设置最大等待毫秒数
    }
    /**
     * 获取数据库连接池
     */
    public static DataSource getDataSource(){
        return dataSource;
    }
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}


