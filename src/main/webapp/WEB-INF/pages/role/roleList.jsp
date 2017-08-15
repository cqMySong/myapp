<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>组织岗位</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:60px;margin-bottom:5px;"></div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel" style="margin-bottom: 5px;">
				<div class="" id="tblMain_toolbar">
					<div class="input-group" style="width:160px;">
		                <span class="input-group-addon" style="width:80px;">包含下级</span>
		                <input id="includeChild" class="input-item" type="checkbox"/>
	              	</div>
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead>
							<tr>
								<th data-field="org_name">组织</th>
								<th data-field="number">编码</th>
								<th data-field="name">名称</th>
								<th data-field="remark" >备注</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div class="container panel">
				<div class="row">
					<div class="col-md-6" style="padding: 2px;">
						<div class="" id="userTable_toolbar">
							<div class="btn-group">
				                <button class="btn btn-success" type="button"><span class="fa fa-tags"></span>&nbsp;用户分配</button>
			              	</div>
						</div>
						<table id="userTable" style="width: 100%;border: 1px solid #bdc3d1;">
							 <thead>
								<tr>
									<th data-field="number">编码</th>
									<th data-field="name">名称</th>
									<th data-field="remark" >描述</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="col-md-6" style="padding: 2px;">
						<div class="" id="permisTable_toolbar">
							<div class="btn-group">
				                <button class="btn btn-success" type="button"><span class="fa fa-tag"></span>&nbsp;岗位职责分配</button>
			              	</div>
						</div>
						<table id="permisTable" style="width: 100%;border: 1px solid #bdc3d1;">
							 <thead>
								<tr>
									<th data-field="number">编码</th>
									<th data-field="name">名称</th>
									<th data-field="remark" >描述</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="../base/base_treelist.jsp"%>
<script type="text/javascript">

var thisOrgList ;
var includeChild;
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		var tree = thisOrgList.tree;
		if(webUtil.isEmpty(params)&&tree&&tree.getNodes().length>0){
			webUtil.mesg('请先选择的组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
	//params.ex ={name:'参数代码'};
}
function includeChild_click(){
	var thisParams = {includeChild:includeChild.getData()};
	thisOrgList.listUI.executeQueryByParams(thisParams);
}
function queryUserParams(){
	
}
function queryPermissionParams(){
	
}
$(document).ready(function() {
     var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
     includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
     includeChild.setData(true);
     
     var editWin ={title:'组织岗位',width:620,height:360};
     var height = 700;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'base/orgs/tree',baseUrl:'base/roles',title:'组织信息',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
    	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:290,sortStable:false}});
     thisOrgList.onLoad();
     
     var userTable_opt = {height:280,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
 			,cache:false,pageSize:10,showToggle:true,search:true,queryParams:queryUserParams
 			,showColumns:true,idField:"id",mypagination:true,url:''};
     
     userTable_opt.toolbar = '#userTable_toolbar';
     $('#userTable').myDataTable(userTable_opt);
     
     userTable_opt.toolbar = '#permisTable_toolbar';
     $('#permisTable').myDataTable(userTable_opt);
     
});

</script>
</html>