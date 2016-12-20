package com.mxiaixy.dao;

import com.mxiaixy.entity.Topic;
import com.mxiaixy.util.Dbhelp;

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
}
