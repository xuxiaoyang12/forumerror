<%--
  Created by IntelliJ IDEA.
  User: Mxia
  Date: 2016/12/16
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%--引入外部jsp--%>
<%@include file="../include/headBar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title">找回密码</span>
        </div>


        <form action="" id="foundForm" class="form-horizontal">

            <div class="control-group">
                <label class="control-label">选择找回方式</label>
                <div class="controls">
                    <select name="type" id="type">
                        <option value="email">通过电子邮件找回</option>
                        <option value="phone">通过手机号码找回</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" id="typename">电子邮件</label>
                <div class="controls">
                    <input type="text" name="value">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="foundBtn" class="btn btn-primary">提交</button>
            </div>

        </form>

    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-3.1.1.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/foundPassword.js"></script>
</body>
</html>
