package com.mxiaixy.web.user;

import com.google.common.collect.Maps;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * Created by Mxia on 2016/12/15.
 */
@WebServlet("/reg")
public class RegServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/reg.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码  加过滤器

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        //不管成功还是失败 都返回给ajax json值
        Map<String ,Object> result = Maps.newHashMap();
        try {
            UserService userService = new UserService();
            userService.save(userName, password, email, phone);
            result.put("state", "success");
        }catch (Exception e){
            e.printStackTrace();
            result.put("state","error");
            result.put("message","注册失败请稍后再试");
        }
        renderJson(result,resp);


    }
}
