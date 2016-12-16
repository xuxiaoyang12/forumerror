package com.mxiaixy.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/15.
 */
public abstract  class AbstractFilter implements Filter {
    //对Filter里面的方法进行空实现
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public abstract void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException ;

    @Override
    public void destroy() {

    }
}
