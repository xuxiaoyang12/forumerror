package com.mxiaixy.web.user;

import com.mxiaixy.dao.UserDao;
import com.mxiaixy.entity.User;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;
import org.apache.commons.lang3.StringUtils;

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
        //账户登录状态下更改邮箱 判定邮箱是否被占用 并且不包含当前登录用户邮箱 传入一个参数来做判断
        String type = req.getParameter("type");
        System.out.print(type);
        if(StringUtils.isNotEmpty(type)&&"1".equals(type)){
            //如果是登录状态更改则 判定邮箱是否与当前用户邮箱一致
            //从session中获取当前邮箱
            String oldEmail = ((User)req.getSession().getAttribute("curr_user")).getEmail();
            if(oldEmail.equals(email)){
                renderJson("true",resp);
                return;//如果邮箱一致则直接跳转
            }
        }

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
