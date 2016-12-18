package com.mxiaixy.web.user;

import com.mxiaixy.entity.User;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/18.
 */
@WebServlet("/validate/password")
public class ValidatePasswordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受老密码
        String password = req.getParameter("oldPassword");

        //获取当前用户的
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        //判断用户输入密码是否正确
        UserService userService = new UserService();

        Boolean result = userService.validatePassword(password,user);

        if(result){
            renderText("true",resp);
        }else{
            renderText("false",resp);
        }


    }
}
