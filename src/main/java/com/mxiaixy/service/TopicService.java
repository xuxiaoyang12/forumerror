package com.mxiaixy.service;

import com.mxiaixy.dao.NodeDao;
import com.mxiaixy.dao.TopicDao;
import com.mxiaixy.entity.Node;
import com.mxiaixy.entity.Topic;

import java.util.List;

/**
 * 发帖业务逻辑
 * Created by Mxia on 2016/12/20.
 */
public class TopicService {


    private TopicDao topicDao = new TopicDao();
    private NodeDao nodeDao = new NodeDao();
    private Node node = new Node();
    private Topic topic = new Topic();
    /**
     * 获取当前所有的标签
     */
    public List<Node> findAll() {
          return  nodeDao.findAll();

    }
}
