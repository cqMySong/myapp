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
							<a class="pull-right" style="cursor:pointer;"> <i class="fa fa-lock tooltips" title="锁定"></i></a>
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
					<li class="tooltips" data-toggle="tooltip" title="主菜单">
						<a data-toggle="tab" data-target="#mainmenu">
							<i class=" fa fa-home"></i>
						</a>
					</li>
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
					<li class="tooltips" data-toggle="tooltip" title="系统管理">
						<a data-toggle="tab" data-target="#settings">
							<i class="fa fa-cog"></i>
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
					
					<!-- ################# MAIN MENU ################### -->
					<div class="tab-pane" id="mainmenu">
						<div class="input-group" style="padding: 0px 5px 5px 5px;">
							<input type="text" class="form-control" placeholder="菜单搜索...">
							<span class="input-group-btn">
	            				<button class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
	          				</span>
						</div>
						<div id="userMenus"></div>
					</div>
					<!-- tab-pane -->

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
					<!-- tab-pane -->
					<div class="tab-pane" id="settings">
						<div id="sysMenus">
            			</div>
					</div>
					<!-- tab-pane -->
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
	return $(top.document).height()-($('#headPanel').innerHeight()+$('#mainTab>ul.nav-tabs').height())-10;
}
$(document).ready(function() {
	var userMenus = {menus:[
							{title:'项目管理首页',icon:'fa fa-home'},
							{title:'基础资料',icon:'fa fa-cogs',child:[
                                {title:'单位',icon:'fa fa-cogs',url:'ec/basedata/ecunitList/list'}
                               ,{title:'施工方案类别',icon:'fa fa-cogs',url:'ec/basedata/schemeTypeList/list'}
                               ,{title:'技术分类',icon:'fa fa-cogs',url:'ec/basedata/skillclasss/list'}
                               ,{title:'施工技术交底',icon:'fa fa-cogs',url:'ec/basedata/qmskillitems/list'}
                               ,{title:'安全技术交底',icon:'fa fa-cogs',url:'ec/basedata/smskillitems/list'}
                               ,{title:'检验批划分',icon:'fa fa-cogs',url:'ec/basedata/batchtests/list'}
                               ,{title:'施工现场检查项目',icon:'fa fa-cogs',url:'ec/basedata/workcheckitems/list'}
                               ,{title:'施工安全用电标准',icon:'fa fa-cogs',url:'ec/basedata/safeusepowers/list'}
                               ,{title:'安全资料分类',icon:'fa fa-cogs',url:'base/datagroups/list?code=safedata'}
							    ]},
							{title:'项目基础数据',icon:'fa fa-home',child:[
								{title:'工程项目',icon:'fa fa-building-o',url:'ec/basedata/projects/list'}
								,{title:'项目结构类型',icon:'fa fa-cogs',url:'ec/basedata/structTypes/list'}
								,{title:'施工方案',icon:'fa fa-cogs',url:'ec/basedata/schemelist/list'}
								,{title:'项目单位工程',icon:'fa fa-cogs',url:'ec/basedata/prostructures/list'}
								,{title:'项目分部工程',icon:'fa fa-cogs',url:'ec/basedata/prosubs/list'}
								,{title:'项目分项工程',icon:'fa fa-cogs',url:'ec/basedata/prosubitems/list'}
								,{title:'安保人员值班记录',icon:'fa fa-cogs',url:'ec/basedata/ondutyrecords/list'}
								,{title:'项目安保预案',icon:'fa fa-cogs',url:'ec/basedata/prosecuritycases/list'}
								,{title:'到访人员登记',icon:'fa fa-cogs',url:'ec/basedata/personvisitrecords/list'}
								,{title:'到访车辆登记',icon:'fa fa-cogs',url:'ec/basedata/carvisitrecords/list'}
								,{title:'安保监督检查',icon:'fa fa-cogs',url:'ec/basedata/safecheckrecords/list'}
								,{title:'安保巡查记录',icon:'fa fa-cogs',url:'ec/basedata/safepatrolrecords/list'}
								,{title:'设计变更(洽商)',icon:'fa fa-cogs',url:'ec/other/designchanges/list'}
                                ,{title:'会议纪要',icon:'fa fa-cogs',url:'ec/basedata/meetingsummaries/list'}
							  ]},
							{title:'计划管理',icon:'fa fa-star',child:[
								{title:'项目总计划',icon:'fa fa-user',url:'ec/plan/projecttotalplans/list'},
								{title:'项目月计划',icon:'fa fa-user',url:'ec/plan/projectplans/monthlist'},
								{title:'项目周计划',icon:'fa fa-user',url:'ec/plan/projectplans/weeklist'},
								{title:'项目施工日志',icon:'fa fa-user',url:'ec/plan/worklogs/list'},
								{title:'项目计划汇报',icon:'fa fa-user',url:'ec/plan/projectplanreports/list'},
								{title:'项目总计划-old',icon:'fa fa-user',url:'ec/plan/projectplans/list'}
								]},
							{title:'质量管理',icon:'fa fa-home',child:[
								{title:'质量交底',icon:'fa fa-building-o',url:'ec/quality/standard/qualityStandardList/list'}]},
							{title:'安全管理',icon:'fa fa-home',child:[
								{title:'三级安全教育花名册',icon:'fa fa-building-o',url:'ec/safty/saftyedubooks/list'}]},
							{title:'图纸会审',icon:'fa fa-home',child:[
								{title:'图纸会审',icon:'fa fa-building-o',url:'ec/discussiondrawings/list'}]},
							{title:'预算编制',icon:'fa fa-home',child:[
								{title:'预算编制',icon:'fa fa-building-o',url:'ec/budget/budgetings/list'},
								{title:'预算询价',icon:'fa fa-building-o',url:'ec/budget/enquiryprices/list'},
                                {title:'材设准备一览表',icon:'fa fa-building-o',url:'ec/budget/enquirypricedetail/list'},
							]},
							{title:'采购管理',icon:'fa fa-home',child:[
								{title:'材料申购',icon:'fa fa-building-o',url:'ec/purchase/applymaterials/list'},
								{title:'采购合同',icon:'fa fa-building-o',url:'ec/purchase/purchasecontracts/list'},
                                {title:'采购入库',icon:'fa fa-building-o',url:'ec/purchase/purchasestocks/list'},
                                {title:'材料申购、供应台账',icon:'fa fa-building-o',url:'ec/purchase/supplyledger/list'},
							]},
							{title:'库存管理',icon:'fa fa-home',child:[
								{title:'领料出库',icon:'fa fa-building-o',url:'ec/stock/stockouts/list'},
                                {title:'物料归还',icon:'fa fa-building-o',url:'ec/stock/stockreverts/list'},
                                {title:'盘存',icon:'fa fa-building-o',url:'ec/stock/inventories/list'}]},
							{title:'结算管理',icon:'fa fa-home',child:[
								{title:'物料结算',icon:'fa fa-building-o',url:'ec/settle/materialsettles/list'}]},
							{title:'工程管理',icon:'fa fa-home',child:[
								{title:'工程合同',icon:'fa fa-building-o',url:'ec/engineering/contracts/list'},
                                {title:'工程进度款',icon:'fa fa-building-o',url:'ec/engineering/progressfunds/list'}]},
							]};

	$('#userMenus').myPillTreeMenu('init',userMenus);
	var sysMenusOpt = {menus:[{title:'首页',icon:'fa fa-home'}
	                          ,{title:'基础数据',icon:'fa fa-cogs',child:[
	                              {title:'计量单位',icon:'fa fa-cogs',url:'base/measureunits/list'}
            					  ,{title:'物料信息',icon:'fa fa-cogs',url:'base/materials/list'}
            					  ,{title:'Icon图标',icon:'fa fa-cogs',url:'/base/web/font'}
	                          ]}
	                         ,{title:'系统管理',icon:'fa fa-cogs',child:[
								{title:'组织管理',icon:'fa fa-cogs',url:'base/orgs/list'}
						   		,{title:'附件管理',icon:'fa fa-server',url:'base/ftps/list'}
							 ]}
	                        ,{title:'门户管理',icon:'fa fa-home',child:[
	                            {title:'菜单管理',icon:'fa fa-cogs',url:'base/home/menus/list'}
	                         ]}
							,{title:'安全管理',icon:'fa fa-star',child:[
								{title:'工作职责',icon:'fa fa-users',url:'base/jobdutys/list'},
								{title:'岗位管理',icon:'fa fa-users',url:'base/positions/list'},
								{title:'用户管理',icon:'fa fa-user',active:true,url:'base/users/list'},
								{title:'角色管理',icon:'fa fa-users',url:'base/roles/list'},
								{title:'权限管理',icon:'fa fa-tags',url:'base/permissions/list'}
							]}
							,{title:'流程管理',icon:'fa fa-star',child:[
								{title:'模型管理',icon:'fa fa-user',url:'base/actmodels/list'},
								{title:'流程管理',icon:'fa fa-tags',url:'base/actprocesses/list'},
								{title:'待办事项',icon:'fa fa-tags',url:'base/backlogs/list'}
							]}
						]};
	$('#sysMenus').myPillTreeMenu('init',sysMenusOpt);
	var initTabs = {items:[{id:'homeIdex',title:'主页',icon:'fa fa-home',enColse:false,url:'main/home'}]};
	mainTab = $('#mainTab').myTab('init',initTabs);
	$('#mainTab').find('ul.nav-tabs').css({"position":'fixed',"width":'100%'});
	$('#mainTab').find('div.tab-content').css({"padding-top":'45px'});

	$('#userSet').click(function(){
		openUserSetUI();
	});
	var menuUrl = "/main/menu";
	$('._menus').each(function(){
		var _fln = $(this).data('fln');
		var target = $(this).data('target');
		if(!webUtil.isEmpty(_fln)&&!webUtil.isEmpty(target)){
			var params ={fln:_fln};
			webUtil.ajaxData({url:menuUrl,data:params,success:function(data){
				var menusData = data.data;
				if(!webUtil.isEmpty(menusData)){
					$(target).html('');
					$(target).myPillTreeMenu('init',menusData);
				}
			}});
		}
	});
})
</script>

</html>