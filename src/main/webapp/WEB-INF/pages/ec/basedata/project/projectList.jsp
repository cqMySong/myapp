<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:60px;margin-bottom:5px;"></div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel">
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
								<th data-field="number" data-align="left">项目编码</th>
								<th data-field="name">项目名称</th>
								<th data-field="industryType" data-type="select">工程分类</th>
								<th data-field="proState" data-type="select">项目状态</th>
								<th data-field="org_name">所属组织</th>
								<th data-field="address">项目地址</th>
								<th data-field="scale" >规模</th>
								<th data-field="eavesHeight" >建筑高度(m)</th>
								<th data-field="floorHeight" >层高(m)</th>
								<th data-field="structTypes" data-type='f7'>结构类型</th>
								<th data-field="area" >占地面积</th>
								<th data-field="aseismicLevel" >抗震等级</th>
								<th data-field="remark" >备注</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="../../../base/base_treelist.jsp"%>
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
$(document).ready(function() {
     var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
     includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
     includeChild.setData(true);
     
     var editWin ={title:'工程项目',width:620,height:580};
     var height = 700;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'base/orgs/tree',baseUrl:'ec/basedata/projects',title:'工程项目',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
    	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false}});
     thisOrgList.onLoad();
});
function getTreeQueryParams(){
	return {orgType:"COMPANYORG,PROJECTORG",includeProOrg:true};
}
</script>
</html>