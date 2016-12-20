package com.mxiaixy.util;

import com.mxiaixy.exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库查询
 * Created by Mxia on 2016/12/9.
 */
public class Dbhelp {
        private static QueryRunner queryRunner = new QueryRunner();
        private static Logger logger = LoggerFactory.getLogger(Dbhelp.class);


        //查询数据
        public static <T>T query(String sql, ResultSetHandler<T> handler,Object...params){
                //获取数据库连接
                //Connection con  = ConnectionManager.getConnection();
                Connection con = ConnectionManager.getConnection();
                T t =null;
                //查询数据 通过Dbutil包
                try {
                      t = queryRunner.query(con,sql,handler,params);
                     logger.info("执行{}",sql);
                } catch (SQLException e) {
                        e.printStackTrace();
                }finally{
                        close(con);
                }
                return t;

        }
        //添加更改删除
        public static  void update(String sql,Object...params){
                //Connection con = ConnectionManager.getConnection();
                Connection con = ConnectionManager.getConnection();

                try {
                        queryRunner.update(con,sql,params);
                        logger.info("Dbhelp执行{}",sql);
                } catch (SQLException e) {
                        e.printStackTrace();
                }finally{
                        close(con);
                }


        }
        public static Integer insert(String sql, Object...params){
                //Connection con = ConnectionManager.getConnection();
                Connection con = ConnectionManager.getConnection();

                try {
                        logger.info("  topic  Dbhelp执行{}",sql);
                      return   queryRunner.insert(con,sql,new ScalarHandler<Long>(),params).intValue();

                } catch (SQLException e) {
                       throw new DataAccessException("执行"+sql+"异常");
                }finally{
                        close(con);
                }

        }
        //关闭数据库
        public static void close(Connection conn){
                try {
                        if(conn!=null&conn.isClosed()){
                                conn.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

        }




}
