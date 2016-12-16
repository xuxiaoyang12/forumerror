package com.mxiaixy.web.user;

import com.mxiaixy.exception.ServiceException;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/16.
 */
@WebServlet("/user/active")
public class Activeservlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          //获取token值激活账户
        String token = req.getParameter("_");
        System.out.print(token);
        //判断token是否为空
        if(StringUtils.isEmpty(token)){
           resp.sendError(404);
        }else {
            try {
                //激活账户
                UserService userService = new UserService();
                userService.activeAccount(token);
                //激活成功跳转到成功页面
                forward("/user/success.jsp",req,resp);
            }catch(ServiceException e){
                //激活失败
                req.setAttribute("message",e.getMessage());
                forward("/user/error.jsp",req,resp);

            }
        }







    }
}
