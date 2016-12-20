package com.mxiaixy.entity;

/**
 * 发帖标签
 * Created by Mxia on 2016/12/20.
 */
public class Node {
    private Integer id;
    private String nodeName;
    private Integer topicNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(Integer topicNum) {
        this.topicNum = topicNum;
    }
}
