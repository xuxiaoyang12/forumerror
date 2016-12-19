/**
 * Created by Mxia on 2016/12/17.
 */
(function(){
    //用户更改电子邮件 js 验证规则
    $("#basicBtn").click(function(){
       $("#basicForm").submit();
    });

    $("#basicForm").validate({
        errorElement:"span",
        errorClass:"text-error",

        rules:{
            email:{
                required:true,
                email:true,
                remote:"/login/email?type=1"
            }
        },
        messages:{
            email:{
                required:"请输入邮箱！",
                email:"邮箱格式错误！",
                remote:"邮箱已被占用！"
            }

        },
        submitHandler:function(){
            $.ajax({
                url:"/setting?active=email",
                type:"post",
                data:$("#basicForm").serialize(),
                beforeSend:function(){
                    $("#basicBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function(data){
                    if(data.state=="success"){
                        alert("修改成功")
                    }else{
                        alert(data.message)
                    }

                },
                error:function(){
                    alert("服务器错误")
                },
                complete:function(){
                    $("#basicBtn").text("保存").removeAttr("disabled");
                }
            })
        }
    })

    //重置密码  js    验证规则
    $("#resetBtn").click(function(){
        $("#resetForm").submit();
    });
    $("#resetForm").validate({
        errorElement:"span",
        errorClass:"text-error",

        rules:{
            oldPassword:{
                required:true,
                //remote:"/validate/password",
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

        },
        messages:{
            oldPassword:{
                required:"请输入旧密码",
                //remote:"原始密码错误",
            },
            password:{
                required:"请输入新密码",
                rangelength:"密码长度3-10个字符",

            },
            repassword:{
                required:"请输入新密码",
                rangelength:"密码长度3-10个字符",
                equalsTo:"两次密码不一致",
            },
        },
        submitHandler:function(){
            $.ajax({
                url:"/setting?active=password",
                type:"post",
                data:$("#resetForm").serialize(),
                beforeSend :function(){
                    $("#resetBtn").text("保存中..").attr("disabled","disabled");
                },
                success:function(data){
                    if(data.state=="success"){
                        alert("密码修改成功 请登录");
                        window.location.href="/login";
                    }else{
                        alert(data.message);
                    }

                },
                error:function(){
                    alert("服务器异常")
                },
                complete:function(){
                    $("#resetBtn").text("保存").removeAttr("disabled");
                },

            });


        }

    })

    //头像上传js 规则验证
    $("#")



})();