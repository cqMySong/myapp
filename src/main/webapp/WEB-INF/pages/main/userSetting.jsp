<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myapp.core.model.MyWebContext" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>账户设置</title>
</head>
<style type="text/css">
</style>
<%@include file="../inc/webBase.inc"%>
<link rel="stylesheet" href="<%=appRoot%>/assets/css/main.css"/>
<% MyWebContext webCtx = (MyWebContext)request.getSession().getAttribute("webCtx"); %>
<script type="text/javascript">
</script>
<body style="overflow: hidden;">
	<div id="editPanel" class="panel" >
		<div class="media leftpanel-profile">
			<div class="media-left">
				<a href="#">
					<img src="<%=appRoot%>/assets/images/photos/loggeduser.png" alt="" class="media-object img-circle">
				</a>
			</div>
			<div class="media-body">
				<h4 class="media-heading" style="cursor:pointer;" data-toggle="collapse" 
					data-target="#loguserinfo" ><span class="userName"><%=webCtx.getUserName()%></span> 
					<a class="pull-right"> <i class="fa fa-angle-down"></i></a>
				</h4>
				<h5 class="media-heading"><%=webCtx.getMainPositionStr()%></h5>
			</div>
		</div>
		<div class="leftpanel-userinfo collapse" id="loguserinfo" style="padding-top: 0px;">
			<ul class="list-group">
				<li class="list-group-item">
					<label class="pull-left">联系方式：</label>
					<span class="pull-right"><%=webCtx.getLinker() %></span>
				</li>
			</ul>
		</div>
		<ul class="nav nav-tabs nav-line mt10">
           <li class="active"><a href="#passwordSet" data-toggle="tab"><strong>密码设置</strong></a></li>
           <li><a href="#userNoSet" data-toggle="tab"><strong>登录名设置</strong></a></li>
           <li><a href="#otherSet" data-toggle="tab"><strong>其他设置</strong></a></li>
        </ul>
        <div class="tab-content" style="height: 365px;padding: 0px;margin: 0px;">
          <div class="tab-pane active" id="passwordSet" style="padding: 5px 10px;">
	          	<div id="reSetPassword" >
					<div class="input-group">
						<span class="input-group-addon lable">登录账号</span>
						<input name="userNumber" value="<%=webCtx.getUserNumber()%>" disabled="disabled" class="input-item form-control read">
					</div>
					<div class="input-group mt10">
						<span class="input-group-addon lable">新密码</span>
						<input name="newPassword" placeholder="请输入新密码" type="password" class="input-item form-control">
					</div>
					<div class="input-group mt10">
						<span class="input-group-addon lable">重复确认</span>
						<input name="newPassword2" placeholder="请重复输入新密码"  type="password" class="input-item form-control">
					</div>
					
					<div class="pull-right mt10">
						<div class="btn-group">
							<button class="btn btn-success" id="saveEdit" type="button">
							<span class="fa fa-save"></span>&nbsp;确定</button>
							<button class="btn btn-success" id="reset" type="button">
							<span class="fa fa-mail-reply"></span>&nbsp;重置</button>
						</div>
					</div>
				</div>
			</div>
          <div class="tab-pane" id="userNoSet" style="padding: 5px 10px;">
          	用户登录名设置
          </div>
          <div class="tab-pane" id="otherSet" style="padding: 5px 10px;">
          	其他设置
          </div>
        </div>
	</div>
</body>
<script type="text/javascript">
var reSetUrl = "main/resetPwd";
function resetPWD(){
	$('input[name="newPassword"]').val('');
	$('input[name="newPassword2"]').val('');
}
$(document).ready(function() {
	$('input[name="newPassword"]').focus();
	$('#reset').click(function(){
		resetPWD();
	});
	$('input[name="newPassword"]').keydown(function(e){
		if(e.which == "13"){//回车事件
			$('input[name="newPassword2"]').focus();
		} 
	});
	$('input[name="newPassword2"]').keydown(function(e){
		if(e.which == "13"){//回车事件
			$("#saveEdit").trigger('click');
		} 
	});
	$('#saveEdit').click(function(){
		var pd = $('input[name="newPassword"]').val();
		if(webUtil.isEmpty(pd)){
			webUtil.mesg('请先输入新的密码!');
		}else{
			var pd2 = $('input[name="newPassword2"]').val();
			if(pd===pd2){
				var un = $('input[name="userNumber"]').val();
				var toData = {};
				toData.un = un;
				toData.pd = pd;
				webUtil.ajaxData({url:reSetUrl,data:toData,success:function(data){
					resetPWD();
				}});
			}else{
				webUtil.mesg('两次密码输入不一致!');
			}
		}
	});
})
</script>
</html>