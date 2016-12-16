package com.mxiaixy.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Mxia on 2016/12/15.
 */
public class Login  implements Serializable{

    private Integer id;
    private String ip;
    private Timestamp createTime;
    private Integer t_user_id;

    public Integer getT_user_id() {
        return t_user_id;
    }

    public void setT_user_id(Integer t_user_id) {
        this.t_user_id = t_user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
