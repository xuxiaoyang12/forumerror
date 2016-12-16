package com.mxiaixy.dao;

import com.mxiaixy.entity.User;
import com.mxiaixy.util.Dbhelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mxia on 2016/12/15.
 */
public class UserDao {


    /**
     *添加日志
     */
    private static Logger logger = LoggerFactory.getLogger(UserDao.class);
    /**
     * 添加新注册用户
     * @param user
     */
    public void save(User user){
        String sql = "insert into t_user(userName,password,email,state,phone,avatar) values(?,?,?,?,?,?)";
        Dbhelp.update(sql,user.getUserName(),user.getPassword(),
                user.getEmail(),user.getState(),user.getPhone(),user.getAvatar());

    }

    /**
     * 通过账户名查找
     * @param userName
     * @return
     */
    public User findByUserName(String userName) {
        String sql = "select * from t_user where userName=?";
        logger.info("已执行{}",sql);
        return Dbhelp.query(sql,new BeanHandler<User>(User.class),userName);
//        if(user==null){
//            return true;
//        }else{
//            return false;
//        }
    }

    /**
     * 通过email查询用户数据
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        String sql = "select * from t_user where email=?";
        logger.info("已执行{}",sql);
        return Dbhelp.query(sql,new BeanHandler<User>(User.class),email);

    }

    public void update(User user) {
        String sql = "update t_user set password=?, email=? ,phone=? ,avatar=? ,state=? where id=?";
        Dbhelp.update(sql,user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar(),user.getState(),user.getId());
        logger.info("已执行{}语句",sql);
    }
}
