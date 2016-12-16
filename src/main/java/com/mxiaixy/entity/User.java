package com.mxiaixy.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Mxia on 2016/12/15.
 */
public class User implements Serializable {


    /**
     * 新用户默认头像key
     */
    public static final String DEFAULT_AVATAR_NAME = "default-avatar.jpg";
    /**
     * 用户状态:未激活
     */
    public static final Integer USERSTATE_UNACTIVE = 0;
    /**
     * 用户状态:已激活（正常）
     */
    public static final Integer USERSTATE_ACTIVE = 1;
    /**
     * 用户状态:禁用
     */
    public static final Integer USERSTATE_DISABLED = 2;

    private Integer id;
    private String userName;
    private String password;
    private String email;
    private Integer state;
    private String phone;
    private Timestamp currentTime;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
    }
}
