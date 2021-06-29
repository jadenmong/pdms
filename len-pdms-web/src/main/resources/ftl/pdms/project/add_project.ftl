<#--Created by IntelliJ IDEA.
User: Administrator
Date: 2017/12/7
Time: 12:40
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>用户管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
</head>

<body>
<div class="x-body">
  <form  class="layui-form layui-form-pane" style="margin-left: 20px;">
<#--    隐藏域：tenantid = tanantid；-->
    <input type="hidden" name="tenantId" id="tenantId" value="${tenantId}">
    <input type="hidden" name="icon" id="icon" value="xes68e;">
    <div style="width:100%;height:400px;overflow: auto;">
      <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
          <legend style="font-size:16px;">创建项目</legend>
        </fieldset>
      </div>
    <div class="layui-form-item">
      <label for="uname" class="layui-form-label">
        <span class="x-red">*</span>项目名
      </label>
      <div class="layui-input-inline">

        <input type="text"  id="projectName" name="projectName"<#--        lay-verify="username"-->
               autocomplete="off" class="layui-input">
      </div>
      <div id="ms" class="layui-form-mid layui-word-aux">
        <span class="x-red">*</span><span id="ums">项目名不能为空</span>
      </div>
    </div>
      <div style="height: 60px"></div>
      <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
          <legend style="font-size:16px;">项目图标</legend>
        </fieldset>
      </div>
        <div class="layui-form-item" >
          <div tag="icon" value="xe68e;" class="icon-box" style="float: left;width: 80px;height: 80px;border: 1px solid #dddddd">
            <i class="layui-icon" style="color: #007DDB;font-size: 50px;padding: 0 5px;margin: auto;">&#xe68e;</i>
          </div>
          <div tag="icon" value="xe702;" class="icon-box" style="float: left;width: 80px;height: 80px;border:1px solid #dddddd">
            <i class="layui-icon" style="color: #007DDB;font-size: 50px;padding: 0 10px;margin: auto;">&#xe702;</i>
          </div>
          <div tag="icon" value="xe653;" class="icon-box" style="float: left;width: 80px;height: 80px;border: 1px solid #dddddd">
            <i class="layui-icon" style="color: #007DDB;font-size: 50px;padding: 0 5px;margin: auto;">&#xe653;</i>
          </div>
      </div>
    </div>
  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
      <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit >
        创建
      </button>
      <button  class="layui-btn layui-btn-primary" id="close">
        取消
      </button>
    </div>
  </div>
  </form>
</div>
<script>
  var flag,msg;
  console.info(flag);
  $(function(){
      $('#uname').on("blur",function(){
        var uname=$('#uname').val();
        if(uname.match(/[\u4e00-\u9fa5]/)){
          return;
        }
        if(!/(.+){3,12}$/.test(uname)){
          return;
        }
        if(uname!='') {
          $.ajax({
            url: 'checkUser?uname=' + uname, async: false, type: 'get', success: function (data) {
              console.info(!data.flag);
              flag = data.flag;
              $('#ms').find('span').remove();
              if (!data.flag) {
                msg = data.msg;
                $('#ms').append("<span style='color: red;'>"+data.msg+"</span>");
               // layer.msg(msg,{icon: 5,anim: 6});
              }else{
                flag=true;
                $('#ms').append("<span style='color: green;'>用户名可用</span>");
              }
            },beforeSend:function(){
              $('#ms').find('span').remove();
              $('#ms').append("<span>验证ing</span>");
            }
          });
        }
      });

  });
  layui.use(['form','layer','upload'], function(){
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer,
        upload = layui.upload;

    upload.render({
      elem: '#test10'
      ,url: 'upload'
      ,before: function(obj){
        //预读，不支持ie8
        obj.preview(function(index, file, result){
          $('#demo2').find('img').remove();
          $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" width="100px" height="100px" class="layui-upload-img layui-circle">');
        });
      },done: function(res){
       if(!res.flag){
         layer.msg(res.msg,{icon: 5,anim: 6});
       }else{
         $("#photo").val(res.msg);
         console.info($('#photo').val());
       }
      }
    });

    //自定义验证规则
    form.verify({
      username: function(value){
        if(value.trim()==""){
          return "项目名不能为空";
        }
        if(value.match(/[\u4e00-\u9fa5]/)){
          return "项目名不能为中文";
        }
        if(!/(.+){3,12}$/.test(value)){
          return "项目名必须3到12位";
        }
        if(typeof(flag)=='undefined'){
          return "项目名验证ing";
        }
        if(!flag){
          return msg;
        }
      }
    });

   $('#close').click(function(){
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   });
    //监听提交  当前页面input的数据 tenantid username
    form.on('submit(add)', function(data){
       var index = parent.layer.getFrameIndex(window.name);
       $.post('addProject',data.field,function (data) {
          parent.layer.msg(data.msg);
          if(data.flag){
            parent.layer.close(index);
          }
       },'json');
      return false;
    });

    // 图标点击高亮
    $("[tag='icon']").click(function () {
      $("[tag='icon']").css("border","1px solid #dddddd")
      $(this).css("border","1px solid blue");
      // 图标传值
      var vals = $(this).attr("value");
      $("#icon").val(vals)
    });

    form.render();
  });
</script>
</body>

</html>
