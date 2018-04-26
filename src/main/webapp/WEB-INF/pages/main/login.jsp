<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  	<meta content="text/html;charset=utf-8">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="description" content="个人应用框架开发实战">
	<meta name="author" content="mySong">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" >
	<%
	 String appRoot = request.getContextPath();
	%>
	<link rel="shortcut icon" href="<%=appRoot%>/assets/images/favicon.png" type="image/png">
 	 <title>${appName}◈登录</title>
	<link rel="stylesheet" href="<%=appRoot%>/assets/lib/fontawesome/css/font-awesome.css"/>
	<link rel="stylesheet" href="<%=appRoot%>/assets/css/quirk.css"/>
	<script src="<%=appRoot%>/assets/lib/jquery/jquery.js"></script>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="<%=appRoot%>/assets/lib/html5shiv/html5shiv.js"></script>
		<script src="<%=appRoot%>/assets/lib/respond/respond.src.js"></script>
	 <![endif]-->
</head>

<body class="signwrapper">
  <div class="sign-overlay"></div>
  <div class="signpanel"></div>
  <div class="panel signin">
    <div class="panel-heading" style="padding:0px;">
      <h1>${appName}</h1>
      <h4 class="panel-title">
      	<i class="fa fa-coffee" style="color:#54c224 !important;"></i>应用管理系统后台
      	</h4>
    </div>
    <div class="or">登录</div>
    <div class="panel-body">
    	<div id="error" class="alert alert-warning" style="display: none;">
        </div>
      <form action="">
        <div class="form-group mb10">
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
            <input name="un" type="text" class="form-control" placeholder="用户名">
          </div>
        </div>
        <div class="form-group nomargin">
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
            <input name="pd" type="password" class="form-control" placeholder="密码">
          </div>
        </div>
      </form>
    </div>
    <div class="or">◈◈</div>
    <div class="row">
     	 	<div class="col-sm-6" style="margin-top:8px;">
     	 		<label class="ckbox ckbox-success">
                  <input type="checkbox" checked><span>记住我</span>
               </label>
       	 </div>
       	 <div class="col-sm-6">
       	 	<button id="login" class="btn btn-success btn-block">登录</button>
       	 </div>
     </div>
  </div>
</body>
<script type="text/javascript">
    var app = {};
    app.root = '<%=appRoot%>';
</script>
<!--layer 3.0-->
<script type="text/javascript">
function showMesg(mesg){
	$('#error').show().html(mesg);
}
$(document).ready(function() {
	$('input[name="un"]').keydown(function(e){
		if(e.which == "13"){//回车事件
			$('input[name="pd"]').focus();
		} 
	});
	$('input[name="pd"]').keydown(function(e){
		if(e.which == "13"){//回车事件
			$("#login").trigger('click');
		} 
	});
	$("#login").click(function(){
		var un = $('input[name="un"]').val();
		if(un&&un.length>0){
			var pd = $('input[name="pd"]').val();
			var logUrl = app.root+'/main/tologin';
			var logData = {};
			logData.un = un;
			logData.pd = pd;
			$.ajax({type:"POST",url:logUrl,async:false,data:logData,dataType:'json',success:function(data){
				var code = data.statusCode;
				if(code==0){
					showMesg("登录成功!用户数据初始化中...");
					var retData = data.data;
					window.location.href = retData.indexUrl; 
				}else{
					var mesg = data.statusMesg;
					if(mesg){
						showMesg(mesg);
					}
				}
			  },complete:function(){
				  
				}
			});
		}else{
			showMesg('请输入用户名!');
		}
	});
})
</script>
</html>