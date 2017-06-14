<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myapp.core.model.MyWebContent" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>账户设置</title>
</head>
<style type="text/css">
</style>
<%@include file="../inc/webBase.inc"%>
<link rel="stylesheet" href="<%=appRoot%>/assets/css/main.css"/>
<% MyWebContent webCtx = (MyWebContent)request.getSession().getAttribute("webCtx"); %>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
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
				<h5 class="media-heading">@软件工程师</h5>
			</div>
		</div>
		<div class="leftpanel-userinfo collapse" id="loguserinfo">
			<h5 class="sidebar-title">地址：</h5>
			<address>重庆江北</address>
			<h5 class="sidebar-title">联系方式：</h5>
			<ul class="list-group">
				<li class="list-group-item">
					<label class="pull-left">Email</label>
					<span class="pull-right">me@themepixels.com</span>
				</li>
				<li class="list-group-item">
					<label class="pull-left">Home</label>
					<span class="pull-right">(032) 1234 567</span>
				</li>
				<li class="list-group-item">
					<label class="pull-left">Mobile</label>
					<span class="pull-right">+63012 3456 789</span>
				</li>
				<li class="list-group-item">
					<label class="pull-left">Social</label>
					<div class="social-icons pull-right">
						<a href="#"><i class="fa fa-facebook-official"></i></a>
						<a href="#"><i class="fa fa-twitter"></i></a>
						<a href="#"><i class="fa fa-pinterest"></i></a>
					</div>
				</li>
			</ul>
		</div>
		<ul class="nav nav-tabs nav-line mt10">
           <li class="active"><a href="#passwordSet" data-toggle="tab"><strong>密码设置</strong></a></li>
           <li><a href="#userNoSet" data-toggle="tab"><strong>登录名设置</strong></a></li>
           <li><a href="#otherSet" data-toggle="tab"><strong>其他设置</strong></a></li>
        </ul>
        <div class="tab-content" style="height: 362px;padding: 0px;margin: 0px;">
          <div class="tab-pane active" id="passwordSet" style="padding: 2px;">
          	重置密码
          </div>
          <div class="tab-pane" id="userNoSet" style="padding: 2px;">
          	用户登录名设置
          </div>
          <div class="tab-pane" id="otherSet" style="padding: 2px;">
          	其他设置
          </div>
        </div>
	</div>
</body>
<script type="text/javascript">

$(document).ready(function() {
	
})
</script>
</html>