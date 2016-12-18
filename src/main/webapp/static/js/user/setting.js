/**
 * Created by Mxia on 2016/12/17.
 */
(function(){
    $("#basicBtn").click(function(){
       $("#basicForm").submit();
    });

    //验证规则
    $("#basicForm").validate({
        errorElement:"span",
        errorClass:"text-error",

        rules:{
            email:{
                email:true,
            }

        },
        messages:{
            email:{
                email:"邮箱格式错误！",
            }

        },
        submitHandler:function(){
            $.ajax({
                url:"/basicSetting",
                type:"post",
                data:$("#basicForm").serialize(),
                beforeSend:function(){
                    $("#basicBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function(data){
                    if(data.state=="success"){
                        alert("保存成功")
                    }else{}
                    alert(data.message)
                },
                error:function(){
                    alert("服务器错误")
                },
                complete:function(){
                    $("#basicBtn").text("保存").removeAttr("disabled");
                },
            })

        },

    })



})();