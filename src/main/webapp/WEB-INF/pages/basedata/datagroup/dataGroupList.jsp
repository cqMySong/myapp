<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;"></div>
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
								<th data-field="number" data-align="left">编码</th>
								<th data-field="name">名称</th>
								<th data-field="parent_number" data-align="left">上级编码</th>
								<th data-field="enabled" data-type="checkbox">启用</th>
								<th data-field="remark" data-width="400">备注</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="../../base/base_treelist.jsp"%>
<script type="text/javascript">

var thisOrgList;
var includeChild;
var title = '${title}';
var code = '${code}';
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
	if(operate=='addnew'){
		params.code = code;
	}
}
function getTreeQueryParams(){
	return {code:code};
}
function includeChild_click(){
	var thisParams = {includeChild:includeChild.getData(),code:code};
	thisOrgList.listUI.executeQueryByParams(thisParams);
}
$(document).ready(function() {
     var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
     includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
     includeChild.setData(true);
     var editWin ={title:title,width:620,height:320};
     var height = top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',listModel:1,treeUrl:'base/datagroups/tree'
    	 ,baseUrl:'base/datagroups',title:title,height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true,code:code}
    	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false}});
     thisOrgList.onLoad();
});
</script>
</html>