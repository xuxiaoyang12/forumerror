package com.mxiaixy.web.user;

import com.google.common.collect.Maps;
import com.mxiaixy.entity.User;
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
 * Created by Mxia on 2016/12/17.
 */
@WebServlet("/basicSetting")
public class BasicSettingServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        //获取当前登录对象
        HttpSession session = req.getSession();
        User user =(User) session.getAttribute("curr_user");

        UserService userService = new UserService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            userService.updateEmail(email, user);
            map.put("state","success");
        }catch (Exception e){
            map.put("state","error");
            map.put("message","邮件更改失败");
        }
        renderJson(map,resp);
    }
}
