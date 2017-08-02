<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>施工方案</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
    <div id="table-toolbar" class="panel" style="height:60px;margin-bottom:5px;"></div>
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
								<th data-field="compiler">方案编制人</th>
								<th data-field="compileDate" data-type="date">编制日期</th>
								<th data-field="lastFinishDate" data-type="date">最迟完成日期</th>
								<th data-field="schemeType_name">方案类别</th>
								<th data-field="billState" >状态</th>
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
/**
 * 一切操作前的接口函数
 */
var listUI;
var thisOrgList ;
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

function enableClick(btn){
	alert(btn.text);
}
$(document).ready(function() {
    var treeNode2QueryProp = ["id","name","number","longNumber","type"];
    var editWin ={title:'施工方案',width:980,height:730,openType:'MAINTAB',id:"projecttotalplans_tab"};
    var treeOpt = {setting:{data: {
        	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
   	 }}};
    var height = 700;
    thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/basedata/schemelist',title:'工程项目',height:height,
   	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
   	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false}});
    thisOrgList.onLoad();
});
</script>
</html>