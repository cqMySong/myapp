<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>三级动火许可</title>
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
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead >
							<tr>
								<th data-field="number">申请编码</th>
								<th data-field="name">单位名称</th>
								<th data-field="project_name">工程项目</th>
								<th data-field="bizDate" data-type="date">申请日期</th>
								<th data-field="billState" data-type="select">业务状态</th>
								<th data-field="firePart">动火部位</th>
								<th data-field="fireDate" data-type="date">动火日期</th>
								<th data-field="welder">焊工</th>
								<th data-field="guarder">监护人</th>
								<th data-field="proposer">申请动火人</th>
								<th data-field="welderbz">焊工班长</th>
								<th data-field="pzUser_name" >批准人</th>
								<th data-field="jsUser_name" >技术科</th>
								<th data-field="aqUser_name" >安全科</th>
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
var thisOrgList ;
var includeChild;
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		var tree = thisOrgList.tree;
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&'project'==params.tree.type){
		}else{
			webUtil.mesg('请先选择的工程项目组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
}
$(document).ready(function() {
     var treeNode2QueryProp = ["id","name","number","longNumber","type"];
     var editWin ={title:'三级动火许可申请',openType:'MAINTAB',id:"lev3firepermit_tab"};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     var height =  top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/firepermit/lev3firepermits',title:'工程项目',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false}});
     thisOrgList.onLoad();
});

</script>
</html>