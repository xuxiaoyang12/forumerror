package com.mxiaixy.entity;

import java.sql.Timestamp;

/**
 * 发帖数据
 *
 * Created by Mxia on 2016/12/20.
 */
public class Topic {

    private Integer id;
    private String title;//发帖主题
    private String content;//发帖内容
    private Timestamp createTime;//发帖时间
    private Integer thanksNum;//感谢人数
    private Integer favNum;//收藏人数
    private Integer click;//点击次数
    private Integer userId;//发帖id
    private Integer nodeId;//标签id
    private Integer replyNum;//回复次数
    private Timestamp lastreplyTime;//最后回复时间


    private User user;

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Timestamp getLastreplyTime() {
        return lastreplyTime;
    }

    public void setLastreplyTime(Timestamp lastreplyTime) {
        this.lastreplyTime = lastreplyTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getThanksNum() {
        return thanksNum;
    }

    public void setThanksNum(Integer thanksNum) {
        this.thanksNum = thanksNum;
    }

    public Integer getFavNum() {
        return favNum;
    }

    public void setFavNum(Integer favNum) {
        this.favNum = favNum;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}




