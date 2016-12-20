package com.mxiaixy.dao;

import com.mxiaixy.entity.Node;
import com.mxiaixy.util.Dbhelp;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * 标签的查询
 * Created by Mxia on 2016/12/20.
 */
public class NodeDao {


    /**
     * 查询所有的标签
     */
    public List<Node> findAll() {
        String sql  = "select * from t_node ";
        return Dbhelp.query(sql,new BeanListHandler<Node>(Node.class));
    }
}
