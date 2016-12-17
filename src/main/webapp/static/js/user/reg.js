/**
 * Created by Mxia on 2016/12/15.
 */
(function () {

    //表单提交以ajax提交
    $("#regBtn").click(function(){
        $("#regForm").submit();
    });


    //设置表单验证及其规则
    $("#regForm").validate({
        errorElement: "span",
        errorClass: "text-error",
        //要制定的规则
        rules: {
            userName: {
                required: true,
                minlength: 3,
                remote:"/login/reg",//验证账户名是否重复

            },
            password: {
                required: true,
                rangelength: [3, 10],
            },

            repassword: {
                required: true,
                rangelength: [3, 10],
                equalTo:"#password"
            },
            email: {
                required: true,
                email: true,
                remote:"/login/email",
            },
            phone: {
                required: true,
                digits: true,
                rangelength: [11, 11]
            },


        },
        //触发规则后显示
        messages: {
            userName: {
                required: "请输入账户名",
                minlength: "至少设置三个字符",
                remote: "账户名已被占用",

            },
            password: {
                required: "请输入密码",
                rangelength: "密码长度3-10个字符",

            },
            repassword: {
                required: "请输入密码",
                rangelength: "密码长度3-10个字符",
                equalTo:"密码输入不一致",
            },
            email: {
                required: "请输入电子邮箱",
                email: "电子邮箱格式错误",
                remote:"邮箱已被占用",
            },
            phone: {
                required: "手机号不能为空",
                digits: "手机号格式错误",
                rangelength: "手机号格式错误",
            },
        },
        submitHandler:function(){
            $.ajax({
                url:"/reg",
                type:"post",
                data:$("#regForm").serialize(),
                beforeSend:function(){
                    $("#regBtn").text("注册中...").attr("disabled","disabled");
                },
                success:function(data){
                    if(data.state=="success"){
                        alert("注册成功！ 请查收邮件已激活账号");
                        //跳转到登录界面
                        window.location.href="/login";
                    }else{
                        alert(data.message);
                    }
                },
                error:function(){
                    alert("服务器错误");
                },
                complete:function(){
                    $("#regBtn").text("注册").removeAttr("disabled");
                }
            });
        }
    })
})();