package com.mxiaixy.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mxia on 2016/12/19.
 */
public class LoginFilter extends AbstractFilter {
    private List<String > urlList = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取配置文件中的需要过滤的字符串
        String validateUrl = filterConfig.getInitParameter("validateUrl");
                //获取值
        urlList = Arrays.asList(validateUrl.split(","));


    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取请求和相应对象
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp= (HttpServletResponse) servletResponse;

        //获取用户要访问的URL
        String requsetURL = req.getRequestURI();
        //如果用户未登录请求setting/....
        if(urlList !=null&&urlList.contains(requsetURL)){
            if(req.getSession().getAttribute("curr_user")==null){
                //去登录页面
                resp.sendRedirect("/login?redirect="+requsetURL);

            }else{
                filterChain.doFilter(req,resp);

            }
        }else{
            filterChain.doFilter(req,resp);

        }

    }
}
