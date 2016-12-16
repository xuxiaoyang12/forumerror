/**
 * Created by Mxia on 2016/12/16.
 */
(function(){

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

            })



        }


    })





})();
