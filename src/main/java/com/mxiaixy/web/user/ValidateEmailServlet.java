package com.mxiaixy.web.user;

import com.mxiaixy.dao.UserDao;
import com.mxiaixy.entity.User;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/15.
 */
@WebServlet("/login/email")
public class ValidateEmailServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        //查找email是否存在
        UserService userService = new UserService();
        User user = userService.findByEmail(email);
        System.out.println(user);
        //判断是否为空
        if(user==null){
            renderText("true",resp);
        }else{
            renderText("false",resp);
        }
    }
}
