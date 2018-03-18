<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myapp.core.model.MyWebContext" %>
<%@ page import="java.util.List,java.util.ArrayList" %>
<%@ page import="java.util.Map,java.util.HashMap" %>
<html lang="en">
	<head>
		<title>${appName }</title>
	</head>
	<%@include file="../inc/webBase.inc"%>
	<% MyWebContext webCtx = (MyWebContext)request.getSession().getAttribute("webCtx"); %>
	<link rel="stylesheet" href="<%=appRoot%>/assets/css/main.css"/>
	<body>
		<div id="headPanel" class="headerpanel">
			<div class="logopanel">
				<h2><a href="#">${appName } - ${version }</a></h2>
			</div>
			<!-- logopanel -->
			<div class="headerbar">
				<a id="menuToggle" class="menutoggle"><i class="fa fa-bars"></i></a>
				<div class="searchpanel">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Search for.">
						<span class="input-group-btn">
            				<button class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
          				</span>
					</div>
					<!-- input-group -->
				</div>
				<div class="header-right">
					<ul class="headermenu">
						<li>
							<div id="noticePanel" class="btn-group">
								<button class="btn btn-notice alert-notice" data-toggle="dropdown">
									<i class="fa fa-globe"></i>
								</button>
								<div id="noticeDropdown" class="dropdown-menu dm-notice pull-right">
									<div role="tabpanel">
										<!-- Nav tabs -->
										<ul class="nav nav-tabs nav-justified" role="tablist">
											<li class="active"><a data-target="#notification" data-toggle="tab">站内信箱(2)</a></li>
											<li><a data-target="#reminders" data-toggle="tab">及时消息 (4)</a></li>
										</ul>

										<!-- Tab panes -->
										<div class="tab-content">
											<div role="tabpanel" class="tab-pane active" id="notification">
												<ul class="list-group notice-list">
													<li class="list-group-item unread">
														<div class="row">
															<div class="col-xs-2">
																<i class="fa fa-envelope"></i>
															</div>
															<div class="col-xs-10">
																<h5><a href="">New message from Weno Carasbong</a></h5>
																<small>June 20, 2015</small>
																<span>Soluta nobis est eligendi optio cumque...</span>
															</div>
														</div>
													</li>

												</ul>
												<a class="btn-more" href="">查看更多... <i class="fa fa-long-arrow-right"></i></a>
											</div>
											<!-- tab-pane -->
											<div role="tabpanel" class="tab-pane" id="reminders">
												<a class="btn-more" href="">查看更多... <i class="fa fa-long-arrow-right"></i></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="btn-group">
								<button type="button" class="btn btn-logged" data-toggle="dropdown">
									<img src="<%=appRoot%>/assets/images/photos/loggeduser.png" alt="" />
									<span class="userName"><%=webCtx.getUserName()%></span>
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="userSet"><i class="glyphicon glyphicon-cog"></i> 账户设置</a></li>
									<li><a href="#"><i class="glyphicon glyphicon-question-sign"></i> 帮助？</a></li>
									<li><a href="<%=appRoot%>/main/logout" id="_logout"><i class="glyphicon glyphicon-log-out"></i> 退出</a></li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
				<!-- header-right -->
			</div>
			<!-- headerbar -->
		</div>
		<!-- header-->

		<div class="leftpanel">
			<div class="leftpanelinner">
				<div class="media leftpanel-profile">
					<div class="media-left">
						<a href="#">
							<img src="<%=appRoot%>/assets/images/photos/loggeduser.png" alt="" class="media-object img-circle">
						</a>
					</div>
					<div class="media-body">
						<h4 class="media-heading" style="cursor:pointer;overflow: hidden;" data-toggle="collapse"
							data-target="#loguserinfo" ><span class="userName"><%=webCtx.getUserName()%></span>
							<a class="pull-right"> <i class="fa fa-angle-down"></i></a>
						</h4>
						<h5 class="media-heading"><%=webCtx.getMainPosition()%>
							<a id="_lockUser" class="pull-right" style="cursor:pointer;"> <i class="fa fa-lock tooltips" title="锁定"></i></a>
						</h5>
					</div>
				</div>
				<!-- leftpanel-profile -->

				<div class="leftpanel-userinfo collapse" id="loguserinfo">
					<h5 class="sidebar-title">行政部门：</h5>
					<address><%=webCtx.getOrgName()%></address>
					<h5 class="sidebar-title">联系方式：</h5>
					<ul class="list-group">
						<li class="list-group-item">
							<label class="pull-left">Tel</label>
							<span class="pull-right"><%=webCtx.getLinker()%></span>
						</li>
					</ul>
				</div>
				<!-- leftpanel-userinfo -->
				<ul class="nav nav-tabs nav-justified nav-sidebar">
					
					<% 
						if(webCtx!=null){
							List<Map<String, Object>> menus = webCtx.getMainMenu();
							for(int i=0;i<menus.size();i++){
								Map<String, Object> menu = menus.get(i);
					%>
								<li class="tooltips <%if(i==0){ %> active <%} %>" data-toggle="tooltip" title="<%=(String)menu.get("name")%>">
									<a data-toggle="tab" class="_menus" data-fln="<%=(String)menu.get("fln")%>" data-target="#_menu_<%=(i+1)%>">
										<i class="<%=(String)menu.get("iconType")%> <%if("CLASS".equals(menu.get("iconCodeType"))){ %> <%=(String)menu.get("icon") %> <%} %>">
											<%if("UNICODE".equals(menu.get("iconCodeType"))){ %> &#x<%=(String)menu.get("icon") %>; <%}%>
										</i>
									</a>
								</li>
					<%		
							}
						}
					%>
					<li class="tooltips" data-toggle="tooltip" title="个人邮箱">
						<a data-toggle="tab" data-target="#emailmenu">
							<i class="fa fa-envelope"></i>
							<span class="badge badge-warning" style="background-color: #259dab;top: 1px;right:1px;float: left;position: absolute;">10</span>
						</a>
					</li>
					<li class="tooltips" data-toggle="tooltip" title="通讯录">
						<a data-toggle="tab" data-target="#contactmenu">
							<i class="fa fa-user"></i>
						</a>
					</li>
				</ul>

				<div class="tab-content">
				<% 
						if(webCtx!=null){
							List<Map<String, Object>> menus = webCtx.getMainMenu();
							for(int i=0;i<menus.size();i++){
								Map<String, Object> menu = menus.get(i);
					%>
								<div class="tab-pane <%if(i==0){ %> active <%} %>" id="_menu_<%=(i+1)%>">
								   <%=(String)menu.get("name")%> 数据加载中...
								</div>
					<%		
							}
						}
					%>
					

					<!-- ######################## EMAIL MENU ##################### -->

					<div class="tab-pane" id="emailmenu">
						<div class="sidebar-btn-wrapper">
							<a href="compose.html" class="btn btn-primary"><i class="fa fa-pencil"></i><span style="margin-left: 5px;;">写信</span></a>
						</div>

						<h5 class="sidebar-title">邮箱</h5>
						<ul class="nav nav-pills nav-stacked nav-quirk nav-mail">
							<li><a href="email.html"><i class="fa fa-pencil"></i> <span>发件箱 (3)</span></a></li>
							<li><a href="email.html"><i class="fa fa-inbox"></i> <span>收件箱 (2)</span></a></li>
							<li><a href="email.html"><i class="fa fa-paper-plane"></i> <span>草稿</span></a></li>
						</ul>

						<h5 class="sidebar-title">标签</h5>
						<ul class="nav nav-pills nav-stacked nav-quirk nav-label">
							<li><a href="#"><i class="fa fa-tags primary"></i> <span>重要</span></a></li>
							<li><a href="#"><i class="fa fa-tags success"></i> <span>紧急</span></a></li>
							<li><a href="#"><i class="fa fa-tags warning"></i> <span>关键</span></a></li>
							<li><a href="#"><i class="fa fa-tags danger"></i> <span>普通</span></a></li>
						</ul>
					</div>
					<!-- tab-pane -->

					<!-- ################### CONTACT LIST ################### -->

					<div class="tab-pane" id="contactmenu">
						<div class="input-group" style="padding: 0px 5px 5px 5px;">
							<input type="text" class="form-control" placeholder="人员搜索...">
							<span class="input-group-btn">
	            				<button class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
	          				</span>
						</div>
						<h5 class="sidebar-title"></h5>
						<div role="tabpanel " class="dm-notice" style="padding: 0px 5px;">
							<!-- Nav tabs -->
							<ul class="nav nav-tabs nav-line" role="tablist">
								<li class="active">
									<a data-target="#allContacts" style="cursor:pointer;" data-toggle="tab">通讯录</a>
								</li>
								<li>
									<a data-target="#personToContacts" style="cursor:pointer;" data-toggle="tab">个人收藏</a>
								</li>
							</ul>
									<!-- Tab panes -->
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane active" id="allContacts">
									<ul class="media-list user-list">
										<li class="media">
											<a href="#">
												<div class="media-left">
													<img class="media-object img-circle" src="<%=appRoot%>/assets/images/photos/user1.png" alt="">
												</div>
												<div class="media-body">
													<h4 class="media-heading">张三</h4>
													<span><i class="fa fa-phone"></i> 386-752-1860</span>
												</div>
											</a>
										</li>
										<li class="media">
											<a href="#">
												<div class="media-left">
													<img class="media-object img-circle" src="<%=appRoot%>/assets/images/photos/user2.png" alt="">
												</div>
												<div class="media-body">
													<h4 class="media-heading">李四</h4>
													<span><i class="fa fa-mobile"></i> +1614-650-8281</span>
												</div>
											</a>
										</li>
									</ul>
								</div>
								<!-- tab-pane -->
								<div role="tabpanel" class="tab-pane" id="personToContacts">
									<ul class="media-list user-list">
										<li class="media">
											<a href="#">
												<div class="media-left">
													<img class="media-object img-circle" src="<%=appRoot%>/assets/images/photos/user1.png" alt="">
												</div>
												<div class="media-body">
													<h4 class="media-heading">张三</h4>
													<span><i class="fa fa-phone"></i> 386-752-1860</span>
												</div>
											</a>
										</li>
										<li class="media">
											<a href="#">
												<div class="media-left">
													<img class="media-object img-circle" src="<%=appRoot%>/assets/images/photos/user2.png" alt="">
												</div>
												<div class="media-body">
													<h4 class="media-heading">王五</h4>
													<span><i class="fa fa-mobile"></i> +1614-650-8281</span>
												</div>
											</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- tab-content -->
			</div>
			<!-- leftpanelinner -->
		</div>
		<!-- leftpanel -->

		<div class="mainpanel" >
			<div class="contentpanel my-nav-tabs" style="padding: 0px;margin: 0px;" id="mainTab" >
			</div>
		</div>
	</body>
<div id="lockDiv" style="display: none;width: 330px;background-color:#3b4354;overflow: hidden; padding:15px;border-radius：4px;">
	<div class="media" style="background-color: #3b4354;padding: 0px;">
		<div class="media-left">
			<a href="#">
				<img src="<%=appRoot%>/assets/images/photos/loggeduser.png" 
					class="media-object img-circle"
					style="width:100px;padding:3px;border: 2px solid #657390;">
			</a>
		</div>
		<div class="media-body">
			<div class="media-heading" style="cursor:pointer;overflow: hidden;">
				<div class="leftpanel-userinfo" style="left: 120px;right: 10px;
					font-size: 12px;border-radius：4px;height: 115px;padding-top: 0px;">
					<ul class="list-group">
						<li class="list-group-item" id="toUser" userNumber= "<%=webCtx.getUserNumber()%>">
							<label class="pull-left">用户:</label>
							<span class="pull-right"><%=webCtx.getUserName()%></span>
						</li>
						<li class="list-group-item">
							<label class="pull-left">职位:</label>
							<span class="pull-right"><%=webCtx.getMainPosition()%></span>
						</li>
						<li class="list-group-item" style="padding: 0px;">
							<div class="form-group">
								<div class="input-group">
									<input id="reLogName" type="password" style="background-color:#dddddd; " class="form-control" placeholder="登录密码">
						            <span id="btnLogin" class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
						        </div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

function openUserSetUI(){
	var win = {title:'<i class="fa fa-user"></i>&nbsp;用户信息设置',maxmin:false,height:500,width:350,btns:null,url:app.root+'/main/toUserSet'};
	webUtil.openWin(win);
}
var mainTab;
function addMainTab(item){
	mainTab.addTab(item);
}
function getTopMainHeight(){
	$('#mainTab').find('div.tab-content').css({"padding-top":$('#mainTab>ul.nav-tabs').height()+'px'});
	return $(top.document).height()-($('#headPanel').innerHeight()+$('#mainTab>ul.nav-tabs').height())-10;
}
var lockWinIndex  ;
function _lockUser() {
	$('#reLogName').val('');
	lockWinIndex = layer.open({
		type : 1,
		title : false,
		closeBtn : 0,
		shadeClose : false,
		shade:0.9,
		content :$('#lockDiv')
	});
	$('#layui-layer-shade'+lockWinIndex).css({"background-color":'#262b36'});
	$('#layui-layer'+lockWinIndex+' .layui-layer-content').css({"overflow":'hidden'});
	$('#layui-layer-shade'+lockWinIndex).click(function(){
		$('#reLogName').focus();
	});
}
function closeLockWin(){
	layer.close(lockWinIndex);
}

$(document).ready(function() {
	var initTabs = {
		items : [ {
			id : 'homeIdex',
			title : '主页',
			icon : 'fa fa-home',
			enColse : false,
			url : 'main/home'
		} ]
	};
	mainTab = $('#mainTab').myTab('init', initTabs);
	var mainTabW = $(document).width() - 280;
	$('#mainTab').find('ul.nav-tabs').css({
		"position" : 'fixed',
		"width" : mainTabW + 'px'
	});
	$('#mainTab').find('div.tab-content').css({
		"padding-top" : '45px'
	});
	$('#userSet').click(function() {
		openUserSetUI();
	});
	var menuUrl = "/main/menu";
	$('._menus').each(function() {
		var _fln = $(this).data('fln');
		var target = $(this).data('target');
		if (!webUtil.isEmpty(_fln) && !webUtil.isEmpty(target)) {
			var params = {
				fln : _fln
			};
			webUtil.ajaxData({
				url : menuUrl,
				data : params,
				success : function(data) {
					var menusData = data.data;
					if (!webUtil.isEmpty(menusData)) {
						$(target).html('');
						$(target).myPillTreeMenu('init', menusData);
					}
				}
			});
		}
	});
	$('#_lockUser').click(function() {
		_lockUser();
	});
	$('#reLogName').keydown(function(e){
		if(e.which == "13"){//回车事件
			$("#btnLogin").trigger('click');
		} 
	});
	$('#btnLogin').click(function() {
		var un = $('#toUser').attr('userNumber');
		if(un&&un.length>0){
			var pd = $('#reLogName').val();
			if(!webUtil.isEmpty(pd)){
				var logUrl = app.root+'/main/tologin';
				var logData = {};
				logData.un = un;
				logData.pd = pd;
				$.ajax({type:"POST",url:logUrl,async:false,data:logData,dataType:'json',success:function(data){
					var code = data.statusCode;
					if(code==0){
						webUtil.mesg("解锁成功!");
						closeLockWin();
					}else{
						var mesg = data.statusMesg;
						if(!webUtil.isEmpty(mesg)){
							webUtil.mesg(mesg);
						}
					}
				  },complete:function(){
					  
					}
				});
			}else{
				//
			}
		}
	});
})
</script>

</html>