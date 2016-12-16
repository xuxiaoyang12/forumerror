package com.mxiaixy.dao;

import com.mxiaixy.entity.Login;
import com.mxiaixy.util.Dbhelp;

/**
 * Created by Mxia on 2016/12/16.
 */
public class LoginDao {

    private Login login = new Login();


    /**
     * 保存用户日志
     * @param login
     */
    public void save(Login login) {
        String sql = "insert into t_login (ip,t_user_id) values(?,?) ";
        Dbhelp.update(sql,login.getIp(),login.getT_user_id());
    }
}
