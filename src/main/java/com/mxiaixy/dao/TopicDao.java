package com.mxiaixy.dao;

import com.mxiaixy.entity.Topic;
import com.mxiaixy.util.Dbhelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Mxia on 2016/12/20.
 */
public class TopicDao {
    /**
     * 保存新帖
     * @param topic
     */
    public Integer save(Topic topic) {
        String sql = "insert into t_topic (title,content,nodeId,userId) values(?,?,?,?)";
        return Dbhelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNodeId(),topic.getUserId());


    }

    /**
     * 通过id查询数据
     * @param topicId
     * @return
     */
    public Topic findById(Integer topicId) {
        String sql = "select* from t_topic where id = ?";
        return Dbhelp.query(sql,new BeanHandler<Topic>(Topic.class),topicId);
    }
}
