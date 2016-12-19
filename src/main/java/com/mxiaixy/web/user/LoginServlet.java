package com.mxiaixy.web.user;

import com.google.common.collect.Maps;
import com.mxiaixy.entity.User;
import com.mxiaixy.exception.ServiceException;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Mxia on 2016/12/15.
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果用户登录状态则强制退出
        req.getSession().removeAttribute("curr_user");
        forward("user/login.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取表单数据
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        //查询账号和密码是否匹配
        UserService userService = new UserService();
        //获取用户ip
        String ip = req.getRemoteAddr();

        Map<String , Object> map = Maps.newHashMap();
        try {
            User user = userService.findByNameAndPwd(userName, password, ip);
            //将用户放入session
            HttpSession session = req.getSession();
            session.setAttribute("curr_user",user);

            map.put("state","success");
        }catch(ServiceException e){

            map.put("state","error");
            map.put("message",e.getMessage());
            System.out.println(e.getMessage());
        }
        renderJson(map,resp);

    }
}
