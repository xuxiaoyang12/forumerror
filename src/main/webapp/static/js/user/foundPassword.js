/**
 * Created by Mxia on 2016/12/16.
 */
(function(){

    $("#type").change(function(){
       var value = $(this).val();
       if("email"==value){
           $("#typename").text("电子邮件");
       }else{
           $("#typename").text("手机号码");
       }

    });


    $("#foundBtn").click(function(){
       $("#foundForm") .submit();
    });

    $("#foundForm").validate({
        errorElement:"span",
        errorClass:"text-error",

        rules:{
           value:{
                required:true,
            },

        },
        messages:{
            value:{
                required:"请输入电子邮件",
            },
        },
        submitHandler:function(){
            $.ajax({
                url:"/foundPassword",
                type:"post",
                data:$("#foundForm").serialize(),
                beforeSend:function(){
                    $("#foundBtn").text("提交中...").attr("disabled","disabled");
                },
                success:function(data){
                    if(data.state=="success"){
                        alert("邮件发送成功,请注意你的邮箱");
                        window.location.href="/login";
                    }else{
                        alert(data.messages);
                    }
                },
                error:function(data){
                    alert("服务器错误");
                },
                complete:function(){
                    $("#foundBtn").text("提交").removeAttr("disabled");
                },
            })
        }
    })





})();
