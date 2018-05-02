<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>施工现场安全用电检查表</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;padding-top: 1px;margin-bottom: 5px;"></div>
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
								<th data-field="project_name">工程项目</th>
								<th data-field="number">检查编码</th>
								<th data-field="name">检查单位</th>
								<th data-field="bizDate" data-type="date">检查日期</th>
								<th data-field="billState" data-type="select">业务状态</th>
								<th data-field="ecWorker_name">施工员</th>
								<th data-field="ecChecker_name">检查员</th>
								<th data-field="createUser_name">制单人</th>
								<th data-field="createDate" data-type="date">创建日期</th>
								<th data-field="auditor_name">审核人</th>
								<th data-field="auditDate" data-type="date">审核日期</th>
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
     var height =  top.getTopMainHeight()-50;
     var winHeight = height+150;
     var editWin ={title:'施工现场安全用电检查表',width:1100,height:winHeight>800?800:winHeight};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/usepower/prousepowerchecks',title:'工程项目',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-45,sortStable:false}});
     thisOrgList.onLoad();
});

</script>
</html>