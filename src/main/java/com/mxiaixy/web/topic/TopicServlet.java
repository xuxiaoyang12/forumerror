package com.mxiaixy.web.topic;

import com.google.common.collect.Maps;
import com.mxiaixy.entity.Node;
import com.mxiaixy.entity.User;
import com.mxiaixy.exception.ServiceException;
import com.mxiaixy.service.TopicService;
import com.mxiaixy.service.UserService;
import com.mxiaixy.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Mxia on 2016/12/18.
 */
@WebServlet("/newTopic")
public class TopicServlet extends BaseServlet {

    private TopicService topicService = new TopicService();
    private Map<String,Object> map = Maps.newHashMap();

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title  = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeId = req.getParameter("nodeId");
        //获取session中当前用户的id
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");
        Integer userId = user.getId();

        try {
            //保存新帖 并获取新帖id
            Integer topicId = topicService.saveTopic(title, content, nodeId, userId);
            map.put("state","success");
            map.put("topicId",topicId);

        }catch(ServiceException e){
            map.put("state","error");
            map.put("message","发布失败！");
        }
        renderJson(map,resp);
    }
}
