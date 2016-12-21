package com.mxiaixy.web.topic;

import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 加入收藏
 * Created by Mxia on 2016/12/21.
 */
@WebServlet("/fav")
public class FavServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
