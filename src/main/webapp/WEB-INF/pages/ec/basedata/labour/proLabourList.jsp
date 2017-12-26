<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目劳务人员备案</title>
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
								<th data-field="project_name">工程项目</th>
								<th data-field="number">工号</th>
								<th data-field="name">姓名</th>
								<th data-field="sex" data-type="select">性别</th>
								<th data-field="age">年龄</th>
								<th data-field="workType_name">工种</th>
								<th data-field="addr" data-width="300">地址</th>
								<th data-field="joinDate" data-type="date">进场时间</th>
								<th data-field="idCard">身份证号</th>
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
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&!webUtil.isEmpty(params.tree.id)
				&&'project'==params.tree.type){
		}else{
			webUtil.mesg('请先选择具体的工程项目，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
	
}
function getTreeQueryParams(){
	return {orgType:"COMPANYORG,PROJECTORG"};
}
$(document).ready(function() {
	var treeNode2QueryProp = ["id","name","number","longNumber","type"];
    var editWin ={title:'项目劳务人员备案',width:620,height:500};
    var treeOpt = {setting:{data: {
        	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
   	 }}};
    var height = top.getTopMainHeight()-45;
    thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/labour/prolabours',title:'工程项目',height:height,
   	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
   	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false}});
    thisOrgList.onLoad();
});

</script>
</html>