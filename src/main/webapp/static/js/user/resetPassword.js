/**
 * Created by Mxia on 2016/12/17.
 */
(function(){

    $("#resetBtn").click(function(){
        $("#resetForm").submit();
    });

    //表单验证规则
    $("#resetForm").validate({
        errorElement:"span",
        errorClass:"text-error",

        rules:{
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
               url:"/found/active",
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


})();
