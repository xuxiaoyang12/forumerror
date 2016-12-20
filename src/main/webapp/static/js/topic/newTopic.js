/**
 * Created by Mxia on 2016/12/20.
 */
(function(){
    $("#topicBtn").click(function(){
        $("#topicForm").submit();
    })
    $("#topicForm").validate({
        errorElement:"span",
        errorClass:"text-error",

        rules:{
            title:{
                required:true
            },
            nodeId:{
                required:true
            }

        },
        messages:{
            title:{
                required:"请输入主题内容"
            },
            nodeId:{
                required:"请选择节点"
            }
        },
        submitHandler:function(){
           $.ajax({
               url:"/newTopic",
               type:"post",
               data:$("#topicForm").serialize(),
               beforeSend:function(){
                   $("#topicBtn").text("发布中...").attr("disabled","disabled");
               },
               success:function(data){

                   if(data.state=="success"){
                       alert(data.topicId);
                       window.location.href="/post";
                   }else{
                       aler(data.message)
                   }

               },
               error:function(){
                   alert("发布失败！请稍后重试");
               },
               complete:function(){
                   $("#topicBtn").text("发布主题").removeAttr("disabled");

               },
           })



        }
    })




})();