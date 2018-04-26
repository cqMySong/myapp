<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目分部分项工程</title>
<style type="text/css">
.mainContrainer {
  width: 100%;
  height: 100%;
  overflow:hidden;
  padding: 0px 2px 2px 2px;
}
.leftContainer {
  width: 230px;
  height: 100%;
  float: left;
}
.rightContainer {
  height: 100%;
  overflow:hidden;
  padding-left: 5px;
}
.fixed-table-pagination .pagination-detail{
	width: 220px;
}
</style>

</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;overflow: hidden;" class="panel">
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container">
		
		</div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel" style="margin-bottom: 5px;">
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead>
							<tr>
								<th data-field="proStruct_name" data-align="left" data-visible="false">(子)单位工程</th>
								<th data-field="proStruct_displayName">(子)单位工程</th>
								<th data-field="number" data-align="left">编码</th>
								<th data-field="name" data-visible="false" data-align="left">名称</th>
								<th data-field="displayName">全名</th>
								<th data-field="wbsType">结构类别</th>
								<th data-field="parent_number" data-align="left">上级编码</th>
								<th data-field="displayName" data-align="left">全名</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
var includeChild;
var _height = 520;
function initHeadStyle(){
	
}
var curTreeNode = {id:'',longNumber:'',type:''};
var orgTree ;
function treeOnClick(event, treeId, treeNode){
	var selNode = treeNode;
	if(curTreeNode.id!=selNode.id){
		curTreeNode.id = selNode.id;
		curTreeNode.longNumber = selNode.longNumber;
		curTreeNode.type = selNode.type;
		loadTableData();
	}
	return true;
}
var tblMain;
function tableParams(){
	return JSON.stringify({tree:curTreeNode,wbsType:"${wbsType}"});
}
var def_table_options = {height:_height-80,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
		,cache:false,pageSize:20,showToggle:true,search:false,queryParams:tableParams
		,showColumns:false,idField:"id",mypagination:true,url:'ec/basedata/proWbsF7/f7Data'};
function loadTableData(){
	tblMain.refreshData();
}
function getData(){
	var selRows = tblMain.getSelections();
	if(selRows&&selRows.length>0){
		return selRows[0];
	}
	return {};
}
function initTree(){
	var treeViewer = $('#tree_container').myTreeViewer(null);
	var treeViewer_opt = {theme:"panel-success",title:'(子)单位工程结构',height:_height,search:true};
	treeViewer.init(treeViewer_opt);
	
	var treeOpt = {
		height:500,callback:{onClick:treeOnClick}
		,data: {
     		simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
	 	}
	};
	var tree_url = 'ec/basedata/proWbsF7/treeData';
	var _data = {projectId:proId};
	webUtil.ajaxData({url:tree_url,async:false,data:_data,success:function(data){
		var treeDatas = data.data;
		if (!webUtil.isEmpty(treeDatas)) {
			treeViewer.addTree(treeOpt,treeDatas);
			orgTree = treeViewer.getTree();
			orgTree.selectNodeByIndex(0);
		}
	}});
}
function initTable(){
	tblMain = $('#tblMain').myDataTable(def_table_options);
	loadTableData();
}
var proId ;
$(document).ready(function() {
	proId = '${projectId}';
	if(!webUtil.isEmpty(proId)){
		proId = webUtil.uuIdReplaceID(proId) ;
	}
	initHeadStyle();
    initTree();
    initTable();
    
});

</script>
</html>