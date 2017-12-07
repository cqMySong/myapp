<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>工程基础分解结构</title>
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
								<th data-field="parent_name" data-align="left">上级结构</th>
								<th data-field="number" data-align="left">结构编码</th>
								<th data-field="name" data-align="left">结构名称</th>
								<th data-field="wbsType" data-type="select" data-align="left">结构类型</th>
								<th data-field="enabled" data-type="checkbox">启用</th>
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
function includeChild_click(){
	var thisParams = {includeChild:includeChild.getData()};
	thisOrgList.listUI.executeQueryByParams(thisParams);
}
$(document).ready(function() {
     var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
     includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
     includeChild.setData(true);
     
     var editWin ={title:'工程基础分解结构',width:620,height:400};
     var height = top.getTopMainHeight()-48;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',baseUrl:'ec/basedata/probasewbss',title:'组织信息',height:height,
    	 listModel:1,treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
    	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-50}});
     thisOrgList.onLoad();
});

</script>
</html>