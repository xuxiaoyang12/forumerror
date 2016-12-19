package com.mxiaixy.web;

import com.google.common.collect.Maps;
import com.mxiaixy.entity.User;
import com.mxiaixy.exception.ServiceException;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;
import com.mxiaixy.util.Config;
import com.qiniu.util.Auth;

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
@WebServlet("/setting")
public class SettingServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取七牛云的token值
        Auth auth =  Auth.create(Config.get("qiniu_ak"),Config.get("qiniu_sk"));
        String token = auth.uploadToken(Config.get("qiniu_bucketNum"));

        req.setAttribute("token",token);
        forward("setting.jsp",req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //判断当前请求的是 更改电子邮件/密码/头像  -->通过url中传回的值
        String active = req.getParameter("active");
        if ("email".equals(active)) {
            //修改电子邮件
            updateEmail(req,resp);
        } else if ("password".equals(active)) {
           //重置密码
        }else if("avatar".equals(active)){
            //修改头像
            uploadAvatar(req,resp);
        }
    }

    /**
     * 更改邮箱
     * @param req
     * @param resp
     */
    private void updateEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");//获取新邮箱

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

    private void uploadAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //修改头像
        String filekey = req.getParameter("filekey");//获取新头像的名字

        //获取当前的用户
        HttpSession session = req.getSession();
        User user=(User) session.getAttribute("curr_user");
        UserService userService = new UserService();
        Map<String,Object> map = Maps.newHashMap();

        try {
            userService.uploadAvatar(user, filekey);
            map.put("state","success");
        }catch(ServiceException e){
            map.put("state","error");
            map.put("message","上传头像失败请稍后再试");
        }
        renderJson(map,resp);


    }


}
