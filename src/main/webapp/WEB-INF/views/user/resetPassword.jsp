<%--
  Created by IntelliJ IDEA.
  User: Mxia
  Date: 2016/12/17
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/headBar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i>找回密码</span>
        </div>

        <form action="" id="resetForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">新密码</label>
                <div class="controls">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="hidden" name="token" value="${token}">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">确认密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>

            <div class="form-actions">
                <button id="resetBtn" type="button" class="btn btn-primary">保存</button>
                <a class="pull-right" href="/reg">注册账号</a>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-3.1.1.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/resetPassword.js"></script>
</body>
</html>

