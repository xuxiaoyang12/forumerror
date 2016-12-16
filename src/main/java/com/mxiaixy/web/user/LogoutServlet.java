package com.mxiaixy.web.user;

import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/16.
 */
@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //登出账户
        //删除session中的值
        HttpSession session = req.getSession();
        session.removeAttribute("curr_user");

        forward("index.jsp",req,resp);
    }
}
