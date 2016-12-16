package com.mxiaixy.web.filter;

import com.mxiaixy.util.Config;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/16.
 */
public class EncodingFilter extends  AbstractFilter {

    private String encoding ="UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String result = filterConfig.getInitParameter("encoding");
        if(StringUtils.isNotEmpty(result)){
            this.encoding=result;
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //设置字符编码
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        //通行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
