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
			<div class="col-md-10 col-lg-9 profile-right">
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
								<div class="row panel-quick-page">
							<% 
								String curOrgLn = webCtx.getCurOrg()!=null?webCtx.getCurOrg().getLongNumber():null;
								List<Map<String, Object>> positions = webCtx.getPositions();
								if(positions!=null&&positions.size()>0){
									for(Map<String,Object> pm:positions){
										Object obj = pm.get("orgLn");
										if(!(curOrgLn!=null&&obj!=null&&obj.toString().startsWith(curOrgLn))) continue;
										obj = pm.get("id");
										String pid = obj!=null?obj.toString():"";
										obj = pm.get("name");
										String name = obj!=null?obj.toString():"";
										obj = pm.get("isMain");
										String theme = "panel-primary-full";
										boolean isMain = true;
										if(obj!=null&&obj instanceof Boolean){
											if(!(Boolean)obj){
												theme = "panel-warning-full";
												isMain = false;
											}
										}
										obj = pm.get("org");
										String orgName = obj==null?"":obj.toString();
										
										obj = pm.get("respible");//负责人职位
										boolean isResp = false;
										if(obj!=null&&obj instanceof Boolean){
											isResp = (Boolean)obj;
										}
										String backtheem = isMain?"page-reports" :"page-statistics";
							%>
							
								<div class="col-md-12 col-lg-6 <%=backtheem%>">
									<div class="panel" style="margin-bottom: 0px;padding-bottom: 0px;">
										<div class="panel-heading" style="padding: 15px 20px;">
											<ul class="panel-options">
								              <li><a><i class="fa fa-refresh"></i></a></li>
								              <li><a class="panel-minimize"><i class="fa fa-chevron-down"></i></a></li>
								              <li><a class="panel-remove"><i class="fa fa-remove"></i></a></li>
								            </ul>
											<h3 class="panel-title">
												<%if(isResp){ %><i class="fa fa-star"></i><%}%>&nbsp;
													<%=orgName %>_<%=name %>
											 </h3>
										</div>
										<div class="panel-body" style="padding: 2px;">
											<div class="table-responsive" >
								                <table class="table table-bordered table-success table-striped nomargin">
								                  <thead>
								                    <tr>
								                      <th style="width: 30px;"> №</th>
								                      <th class="text-center" style="width: 100px;">职责名称</th>
								                      <th class="text-center" style="width: 130px;">菜单</th>
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
						</div>
						
						<div class="tab-pane" id="toDo">
								<div>
									<i class="fa" style="color: red;">
										&nbsp;&#xf008;
									</i>
									<i class="fa" style="color: red;">
										&nbsp;&#xf009;
									</i>
									<i class="glyphicon" style="color: red;">
										&nbsp;&#xe010;
									</i>
								</div>
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
			<div class="col-md-2 col-lg-3 profile-sidebar"  style="padding-left:5px;">
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
	<br>
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
    var height = top.getTopMainHeight()-60;
    $('.tab-pane').height(height);
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