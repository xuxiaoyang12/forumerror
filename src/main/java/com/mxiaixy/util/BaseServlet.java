package com.mxiaixy.util;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Mxia on 2016/12/15.
 */

public class BaseServlet extends HttpServlet {

    //请求转发跳转 模板
    public void forward(String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/"+path).forward(req,resp);

    }

    //输出流模板
    public void renderText(String  result,HttpServletResponse resp) throws IOException {
        //设置输出MIMETYPE
        resp.setContentType("text/plain,charset=UTF-8");

        //输出流
        PrintWriter out = resp.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }
    public void renderJson(Object result,HttpServletResponse resp) throws IOException {
        //设置
        resp.setContentType("application/json,charset=UTF-8");

        PrintWriter out = resp.getWriter();

        //把集合转化为json用gson库
        out.print(new Gson().toJson(result));
        out.flush();
        out.close();
    }
}
