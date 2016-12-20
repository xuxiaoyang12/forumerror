package com.mxiaixy.web.topic;

import com.mxiaixy.entity.Node;
import com.mxiaixy.exception.ServiceException;
import com.mxiaixy.service.TopicService;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mxia on 2016/12/18.
 */
@WebServlet("/newTopic")
public class TopicServlet extends BaseServlet {

    private TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取标签的值
        try {
            List<Node> nodeList = topicService.findAll();
            req.setAttribute("nodeList",nodeList);
            forward("user/newTopic.jsp",req,resp);
        }catch(Exception e){
            resp.sendError(404);
        }



    }
}
