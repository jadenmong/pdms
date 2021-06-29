
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册结果</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/font.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>

</head>
<body class="login-bg">

<div class="login">
    <div class="message">用户名${at_username}注册成功</div>
    <div id="darkbannerwrap"></div>
        <hr class="hr15">
    <a href="/pdms/tenant/goLogin/${at_username}" type="button" style="width:100%;" class="layui-btn">立即登录</a>
<#--        <input value="注册" lay-submit lay-filter="login" style="width:100%;" type="submit">-->
        <hr class="hr20" >
</div>


<script>
  var layer;
    layer = layui.layer;
    var msg='${message}';
    if(msg.trim()!=""){
        layer.msg(msg, {icon: 5,anim:6,offset: 't'});
    }
      $("#code").click(function(){
          var url = "${re.contextPath}/getCode?"+new Date().getTime();
          this.src = url;
      }).click().show();

  if (window != top)
    top.location.href = location.href;
</script>


<!-- 底部结束 -->
</body>
</html>
