package com.mxiaixy.web.user;


import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;
import com.mxiaixy.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * validate 验证跳转servlet
 * Created by Mxia on 2016/12/15.
 */
@WebServlet("/login/reg")
public class ValidateUserServlet extends BaseServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        //转化字符格式编码   StingUtils是写的字符转转换工具类
        userName = StringUtils.isoToUtf8(userName);

        //通过账户名去数据库里面查
        UserService userService = new UserService();
        Boolean result= userService.findByUserName(userName);
        //把输出流写到BaseServlet 中  方便调用
        if(result){
            renderText("true",resp);
        }else{
            renderText("false",resp);
        }

    }
}
