<%--
  Created by IntelliJ IDEA.
  User: Mxia
  Date: 2016/12/17
  Time: 15:58
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
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/headBar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form action="" id="basicForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" readonly name="userName" value="${curr_user.userName}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="text" name="email" value="${curr_user.email}">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="basicBtn" class="btn btn-primary">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form action=""  id="resetForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">原始密码</label>
                <div class="controls">
                    <input type="password" name="oldPassword" >
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="resetBtn" class="btn btn-primary">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" id="imgForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img id="newImg" src="http://oi2ngv1it.bkt.clouddn.com/${curr_user.avatar}?imageView2/1/w/40/h/40" class="img-circle" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <div id="picker">上传新头像</div>
            </div>
        </form>

    </div>
    <!--box end-->

</div>
<!--container end-->
<script src="/static/js/jquery-3.1.1.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/setting.js"></script>
<script src="/static/js/uploader/webuploader.min.js"></script>
<%--用户上传新头像--%>
<script>
    (function(){
        //上传组件
        var webUpload = WebUploader.create({
            swf:"/static/js/uploader/Uploader.swf",//设置如果浏览器不支持html时默认flash
            server:"http://up-z1.qiniu.com",//设置上传的服务端
            pick:"#picker",//设置选择文件按钮
            auto:true ,//设置是否自动上传
            fileVal:"file",//设置文件的名称
            formData:{"token":"${requestScope.token}"}//获取服务端的token值
            /*accept: {
             title: 'Images',
             extensions: 'gif,jpg,jpeg,bmp,png',
             mimeTypes: 'image/!*'
             }*/
        });
        webUpload.on("uploadSuccess",function(file,data){
            //alert(data.key);//data.key 获取上传到七牛云的文件名称
            var filekey =data.key;//头像的名称
            //去数据库中修改头像数据
            $.post("/setting?active=avatar",{"filekey":filekey})
                .done(function(data) {
                    if(data.state=="success"){
                        //更换新头像
                        var url = "http://oi2ngv1it.bkt.clouddn.com/"+filekey;
                        $("#newImg").attr("src",url+"?imageView2/1/w/40/h/40");//更换设置中的头像
                        $("#headBarImg").attr("src",url+"?imageView2/1/w/35/h/35");//更换headBar中的头像
                        alert("头像上传成功");
                    }else{
                        alert(data.message);
                    }
                }).error(function(){
                    alert("头像上传失败");
            });

        });
        webUpload.on("uploadError",function(){
            alert("上传出现错误！");
        });


    })();



</script>
</body>
</html>
