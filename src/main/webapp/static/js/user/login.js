/**
 * Created by Mxia on 2016/12/16.
 */
(function(){

    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }
    $("#loginBtn").click(function(){
        $("#loginForm").submit();
    })
    //设置表单验证规则
    $("#loginForm").validate({
        //错误是显示元素
        errorElement:"span",
        errorClass:"text-error",

        //规则
        rules:{
            userName:{
                required:true,
                minlength:3,
            },
            password:{
                required:true,
                rangelength:[3,10],
            }
        },
        messages:{
            userName:{
                required:"请输入账户名",
                minlength:"账户名格式错误",
            },
            password:{
                required:"请输入密码",
                rangelength:"密码长度3-10个字符",
            }

        },
        //提交表单时  Ajax请求
        submitHandler:function(){
            $.ajax({
                url:"/login",
                type:"post",
                data:$("#loginForm").serialize(),

                beforeSend:function(){
                    $("#loginBtn").text("登录中...").attr("disable","disable");
                },
                success:function(data){
                    if(data.state=="success"){
                        alert("登陆成功");
                        //跳转到主页面并显示用户界面
                        var url = getParameterByName("redirect");
                        if(url){
                            window.location.href=url;
                        }else{
                            window.location.href="/home";
                        }

                    }else{
                        alert(data.message);
                    }

                },
                error:function(){
                    alert("服务器升级呢 请稍后再试@！")
                },
                complete:function(){
                    $("#loginBtn").text("登录").removeAttr("disable");
                },

            })



        }


    })





})();