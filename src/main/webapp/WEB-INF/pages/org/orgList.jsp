<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar"></div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel">
				<div class="" id="tblMain_toolbar">
					<div class="input-group" style="width:120px;">
		                <span class="input-group-addon">包含下级</span>
		                <input id="includeChild" class="input-item" type="checkbox"/>
	              	</div>
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead>
							<tr>
								<th data-field="number">编码</th>
								<th data-field="name">姓名</th>
								<th data-field="shortCode" >简码</th>
								<th data-field="parent_number" >上级编码</th>
								<th data-field="parent_name" >上级名称</th>
								<th data-field="remark" >备注</th>
							</tr>
						</thead>
					</table>
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
		if(webUtil.isEmpty(params)){
			webUtil.mesg('请先选择的组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
	params.ex ={name:'参数代码'};
}
function includeChild_click(){
	var thisParams = {includeChild:includeChild.getData()};
	thisOrgList.listUI.executeQueryByParams(thisParams);
}
$(document).ready(function() {
     var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
     includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
     includeChild.setData(true);
     
     var editWin ={title:'组织信息',url:'base/orgs/edit',width:620,height:400};
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',baseUrl:'base/orgs',title:'组织信息',
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
    	 ,extendTableOptions:{toolbar:'#tblMain_toolbar'}});
     thisOrgList.onLoad();
});

</script>
</html>