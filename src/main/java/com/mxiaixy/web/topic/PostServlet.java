package com.mxiaixy.web.topic;

import com.mxiaixy.entity.Topic;
import com.mxiaixy.entity.User;
import com.mxiaixy.service.TopicService;
import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mxia on 2016/12/18.
 */
@WebServlet("/post")
public class PostServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收传过来的id值
        String topicId = req.getParameter("id");
        HttpSession session =req.getSession();
        User user = (User) session.getAttribute("curr_user");
        //判断id是否为当前用户的新帖id
        TopicService topicService =new TopicService();

        Topic topic = topicService.findById(Integer.valueOf(topicId),user);
        if(topic!=null) {
            req.setAttribute("topic", topic);

            forward("topic/post.jsp", req, resp);
        }else{
            resp.sendError(404,"帖子不存在！");
        }
    }
}
