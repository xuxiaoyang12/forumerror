package com.mxiaixy.service;

import com.mxiaixy.dao.NodeDao;
import com.mxiaixy.dao.TopicDao;
import com.mxiaixy.entity.Node;
import com.mxiaixy.entity.Topic;
import com.mxiaixy.entity.User;
import com.mxiaixy.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 保存新帖
     * @param title
     * @param content
     * @param nodeId
     */
    public Integer saveTopic(String title, String content, String nodeId,Integer userId) {
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeId(Integer.valueOf(nodeId));
        topic.setUserId(userId);
        Integer topicId =   topicDao.save(topic);
        return topicId;
    }


    /**
     * 判断id是否为新帖id
     * @param topicId
     */
    public Topic findById(Integer topicId, User user) {

        if(StringUtils.isNotEmpty(topicId.toString())) {
            Topic topic = topicDao.findById(topicId);
            if (topic!=null&&user.getId().equals(topic.getUserId())) {

                //通过id获取user对象和node对象
                Node node = NodeDao.findById(topic.getNodeId());
                topic.setNode(node);
                topic.setUser(user);
                return topic;

            }
            return null;
        }else{
            throw new ServiceException("帖子不存在");
        }
    }
}
