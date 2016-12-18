package com.mxiaixy.web.user;

import com.google.common.collect.Maps;
import com.mxiaixy.entity.User;
import com.mxiaixy.exception.ServiceException;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Mxia on 2016/12/17.
 */
@WebServlet("/found/active")
public class resetPasswordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受传过来的token值
        String token = req.getParameter("_");
        //判断token是否正确
        UserService userService = new UserService();
        Map<String ,Object> map = Maps.newHashMap();


            try {
                User user = userService.foundPasswordUserByToken(token);
                req.setAttribute("user", user);
                req.setAttribute("token", token);
                forward("/user/resetPassword.jsp", req, resp);
            } catch (ServiceException e) {
                req.setAttribute("message", e.getMessage());
                forward("user/error.jsp", req, resp);
            }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取传回来的新密码 id,token
        String id = req.getParameter("id");
        String token = req.getParameter("token");
        String password = req.getParameter("password");

        UserService userService = new UserService();
        Map<String,Object> map = Maps.newHashMap();
        //判断用户是否登陆
        HttpSession session = req.getSession();
        User curr_user = (User)session.getAttribute("curr_user");
        if(curr_user==null) {
        try {
            userService.resetPassword(id, token, password);
            map.put("state","success");
        }catch(ServiceException e){
            map.put("state","error");
            map.put("message",e.getMessage());
        }

        }else{
            try {
                userService.resetPassword(password, curr_user);
                session.invalidate();
                map.put("state","success");
            }catch (ServiceException e){
                map.put("state","error");
                map.put("message",e.getMessage());

            }
        }
        renderJson(map,resp);
    }
}
