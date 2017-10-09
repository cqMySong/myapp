<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myapp.core.model.MyWebContext" %>
<%@ page import="java.util.List,java.util.ArrayList" %>
<%@ page import="java.util.Map,java.util.HashMap" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<%@include file="../inc/webBase.inc"%>
<style type="text/css">
.table > tbody > tr > td{
	vertical-align:middle;
}
.table > thead > tr > th{
	padding-bottom: 10px;
	padding-top: 10px;
}
.lineThrough{
	text-decoration:line-through;
}

</style>

<% MyWebContext webCtx = (MyWebContext)request.getSession().getAttribute("webCtx"); %>
<body>
	<div class="contentpanel" style="padding:5px;">
		<div class="row profile-wrapper" >
			<div class="col-xs-12 col-md-3 col-lg-2 profile-left" id="leftpanel">
				<div class="profile-left-heading">
					<ul class="panel-options">
						<li><a><i class="glyphicon glyphicon-option-vertical"></i></a></li>
					</ul>
					<a href="" class="profile-photo">
						<img class="img-circle img-responsive" src="<%=appRoot%>/assets/images/photos/profilepic.png" alt=""></a>
					<h2 class="profile-name"><%=webCtx.getUserName()%></h2>
					<h4 class="profile-designation"><%=webCtx.getMainPositionStr()%></h4>

					<ul class="list-group">
						<li class="list-group-item">登陆地址: <a href="#">127.0.0.1</a></li>
						<li class="list-group-item">上次登录时间： <a href="#">2017-08-31</a></li>
					</ul>
				</div>
				<div class="profile-left-body">
					<h4 class="panel-title">联系方式</h4>
					<p><i class="glyphicon glyphicon-phone mr5"></i><%=webCtx.getLinker() %></p>
					<hr class="fadeout">
					<h4 class="panel-title">天气情况</h4>
					<p><i class="glyphicon glyphicon-briefcase mr5"></i> 晴....</p>
					<hr class="fadeout">
					<h4 class="panel-title">其他快捷入口</h4>
					<ul class="list-inline profile-social">
						<li><a href=""><i class="fa fa-facebook-official"></i></a></li>
						<li><a href=""><i class="fa fa-twitter"></i></a></li>
						<li><a href=""><i class="fa fa-dribbble"></i></a></li>
						<li><a href=""><i class="fa fa-linkedin"></i></a></li>
					</ul>
					
				</div>
			</div>
			<div class="col-md-6 col-lg-8 profile-right">
				<div class="profile-right-body">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs nav-justified nav-line">
						<li class="active">
							<a href="#workJobDuty" data-toggle="tab"><strong><i class="fa fa-user"></i>工作职责</strong></a>
						</li>
						<li class="">
							<a href="#toDo" data-toggle="tab"><strong><i class="fa fa-user"></i>待办工作</strong></a>
						</li>
						<li>
							<a href="#sysData" data-toggle="tab"><strong><i class="fa fa-user"></i>资料库</strong></a>
						</li>
						<li>
							<a href="#other" data-toggle="tab"><strong><i class="fa fa-user"></i>.其他.</strong></a>
						</li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div class="tab-pane active" id="workJobDuty">
							<div class="row">
							
							<% List<Map<String, Object>> positions = webCtx.getPositions();
								if(positions!=null&&positions.size()>0){
									for(Map<String,Object> pm:positions){
										Object obj = pm.get("id");
										String pid = obj!=null?obj.toString():"";
										obj = pm.get("name");
										String name = obj!=null?obj.toString():"";
										obj = pm.get("isMain");
										String theme = "panel-primary-full";
										if(obj!=null&&obj instanceof Boolean){
											if(!(Boolean)obj){
												theme = "panel-warning-full";
											}
										}
										obj = pm.get("respible");//负责人职位
										boolean isResp = false;
										if(obj!=null&&obj instanceof Boolean){
											if((Boolean)obj){
												isResp = true;
											}
										}
							%>
							<div class="col-md-12">
									<div class="panel <%=theme %>" style="padding: 10px 10px 0px 10px;margin-bottom: 10px;">
										<div class="panel-heading">
											<h3 class="panel-title">
												<%if(isResp){ %><i class="fa fa-star"></i><%}%>&nbsp;
													<%=name %>
											 </h3>
										</div>
										<div class="panel-body">
											<div class="table-responsive">
								                <table class="table table-bordered table-success table-striped nomargin">
								                  <thead>
								                    <tr>
								                      <th style="width: 30px;"> №</th>
								                      <th class="text-center" style="width: 150px;">职责名称</th>
								                      <th class="text-center" style="width: 200px;">菜单</th>
								                      <th class="text-center">职责描述</th>
								                    </tr>
								                  </thead>
								                  <tbody>
								                  <%
								                  	obj = pm.get("jobItems");
								                 	Map<String,Map<String,String>> perms = webCtx.getPermission();
								                 	
								                  	if(obj!=null&&obj instanceof List&&((List)obj).size()>0){
								                 		List<Map<String, String>> jobItems = (List<Map<String, String>>)obj;
								                 		int idx = 1;
								                 	 	for(Map<String, String> item:jobItems){
								                 	 		
								                  %>
								                  	<tr jobId ='<=%item.get("id")%>'>
								                  	  <td><%=idx %></td>
								                      <td><%=item.get("name")%></td>
								                      <td>
								                      	<a class='toTab 
								                      		<% if(perms!=null&&!perms.containsKey(item.get("menuUrl"))){%>
								                      	 		lineThrough
								                      	 	<%} %>'
								                      	menuId='<%=item.get("menuId")%>' menuUrl='<%=item.get("menuUrl")%>' href="#"
								                      	 style='color: #d9534f;'>
								                      	<i class="fa fa-send"></i>
								                      	<%=item.get("menuName")%></a></td>
								                      <td> 	
														<pre class="tablepre"><%=item.get("remark") %></pre>
													  </td>
								                  	</tr>
								                  		<%
								                  		idx = idx+1;
								                  		} %>
								                  <%}else{ %>
								                  	<tr>
								                  		<td colspan="4">无对应的工作职责!</td>
								                  	</tr>
								                  <%} %>
								                  </tbody>
								               </table>
								            </div>
										</div>
									</div>
								</div>
							
								<%} 
							}%>
							
							</div>
						</div>
						<div class="tab-pane" id="toDo">
							待办工作....
						</div>
						<div class="tab-pane" id="sysData">
							资料库...
						</div>
						<div class="tab-pane" id="other">
							资料库...
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-lg-2 profile-sidebar"  style="padding-left:5px;">
				<div class="panel panel-warning list-announcement">
                <div class="panel-heading">
                  <h4 class="panel-title">系统通知</h4>
                </div>
                <div class="panel-body" id="right">
                  <ul class="list-unstyled mb20">
                    <li>
                      <a href="">Testing Credit Card Payments on...</a>
                      <small>June 30, 2015 <a href="">7 shares</a></small>
                    </li>
                    <li>
                      <a href="">A Shopping Cart for New and...</a>
                      <small>June 15, 2015 &nbsp; <a href="">11 shares</a></small>
                    </li>
                    <li>
                      <a href="">A Shopping Cart for New and...</a>
                      <small>June 15, 2015 &nbsp; <a href="">2 shares</a></small>
                    </li>
                  </ul>
                </div>
                <div class="panel-footer" style="padding: 10px;">
                  <button class="btn btn-warning btn-block ">更多 <i class="fa fa-arrow-right"></i></button>
                </div>
              </div>
			</div>
		</div>
		<!-- row -->

	</div>
</body>
<script type="text/javascript">
function toSimpleUid(billId){
	if(billId){
		var r, re;                 
		re = /\+/g;
		r = billId.replace(re, "");
		re = /\=/g;
		r = r.replace(re, "");
		re = /\//g;
		r = r.replace(re, "");
		return(r);
	}
    return billId;
}
$(document).ready(function() {
    var height = top.getTopMainHeight()+25;
    $('#leftpanel').height(height);
    $('.toTab').click(function(){
    	if($(this).hasClass('lineThrough')){
    		webUtil.mesg('未分配此权限,不能操作!');
    	}else{
    		var menuId = toSimpleUid($(this).attr('menuId'));
        	var menuUrl = $(this).attr('menuUrl');
        	var win = {};
        	win.id = menuId;
        	win.url = menuUrl;
        	win.title= $(this).html();
        	parent.addMainTab(win)
    	}
    });
})

</script>
</html>