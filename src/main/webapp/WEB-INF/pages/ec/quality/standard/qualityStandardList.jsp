<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>质量交底</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
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
								<th data-field="unit_name">施工单位</th>
								<th data-field="proSubItem_name">分部分项名称</th>
								<th data-field="constructionClass">施工班组</th>
								<th data-field="proStructure_name">交底部位</th>
								<th data-field="finishDate" data-type="date">日期</th>
								<th data-field="qualityDate" data-type="date">日期</th>
								<th data-field="participants">主要参加人员</th>
								<th data-field="techUser_name" >技术负责人</th>
								<th data-field="qualityUser_name" >交底人</th>
								<th data-field="accepter_name" >接收人</th>
								<th data-field="remark" >接收人</th>
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

}
$(document).ready(function() {
    var treeNode2QueryProp = ["id","name","number","longNumber","type"];
    var editWin ={title:'质量交底',width:980,height:530};
    var treeOpt = {setting:{data: {
        	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
   	 }}};
    var height = top.getTopMainHeight()-50;
    thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/quality/standard/qualityStandardList',title:'工程项目',height:height,
   	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
   	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-45,sortStable:false}});
    thisOrgList.onLoad();
});
</script>
</html>