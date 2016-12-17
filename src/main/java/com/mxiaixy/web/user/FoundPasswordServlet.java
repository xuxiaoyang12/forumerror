package com.mxiaixy.web.user;

import com.google.common.collect.Maps;
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
 * Created by Mxia on 2016/12/16.
 */
@WebServlet("/foundPassword")
public class FoundPasswordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/foundPassword.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取服务端请求数据
        String type = req.getParameter("type");
        String value = req.getParameter("value");
        System.out.print(type);

        //获取当前用户id  以供判断或限制 用户连续提交表单
        HttpSession session = req.getSession();
        String id = session.getId();

        //查询邮件
        UserService userService = new UserService();
        Map<String ,Object> map = Maps.newHashMap();//生成map对象
        try {
            userService.foundPassword(type, value, id);
            map.put("state","success");
        }catch (ServiceException e){
            map.put("state","error");
            map.put("message",e.getMessage());
        }

        //输出json
        renderJson(map,resp);
    }
}
