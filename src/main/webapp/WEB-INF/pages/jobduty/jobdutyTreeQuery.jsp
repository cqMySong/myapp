<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>工作职责选择</title>
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
								<th data-field="group_displayName" data-width="150">分组</th>
								<th data-field="number" >编码</th>
								<th data-field="name" >名称</th>
								<th data-field="enabled" data-type="checkbox" data-width="80">启用</th>
								<th data-field="remark" data-type="textarea" data-width="300">职责描述</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div class="panel" style="margin-bottom: 5px;">
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblData">
						 <thead>
							<tr>
								<th data-field="group_displayName" data-width="150">分组</th>
								<th data-field="number" >编码</th>
								<th data-field="name" >名称</th>
								<th data-field="enabled" data-type="checkbox" data-width="80">启用</th>
								<th data-field="remark" data-type="textarea" data-width="300">职责描述</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="../inc/webBase.inc"%>
<script type="text/javascript">
var includeChild;
var _height = 650;
function initHeadStyle(){
	
}
var curTreeNode = {id:'',longNumber:''};
var orgTree ;
function treeOnClick(event, treeId, treeNode){
	var selNode = treeNode;
	if(curTreeNode.id!=selNode.id){
		curTreeNode.id = selNode.id;
		curTreeNode.longNumber = selNode.longNumber;
		loadTableData();
	}
	return true;
}
var tblMain;
function tableParams(){
	return JSON.stringify({tree:curTreeNode});
}
var def_table_options = {height:_height-300,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
		,cache:false,pageSize:20,showToggle:false,search:false,queryParams:tableParams
		,showColumns:false,idField:"id",mypagination:true,url:'base/jobdutys/query'};
		
var def_datatable_options = {height:225,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
		,cache:false,pageSize:20,showToggle:false,search:false
		,showColumns:false,idField:"id",mypagination:false};
		
function loadTableData(){
	tblMain.refreshData();
}
function getData(){
	if(tblData){
		return tblData.getData();
	}else{
		return [];
	}
}
function initTree(){
	var treeViewer = $('#tree_container').myTreeViewer(null);
	var treeViewer_opt = {theme:"panel-success",title:'工作职责分组',height:_height,search:true};
	treeViewer.init(treeViewer_opt);
	
	var treeOpt = {
		height:_height,callback:{onClick:treeOnClick}
	};
	var tree_url = 'base/jobdutygroups/tree';
	var _data = {};
	webUtil.ajaxData({url:tree_url,async:false,data:_data,success:function(data){
		var treeDatas = data.data;
		if (!webUtil.isEmpty(treeDatas)) {
			treeViewer.addTree(treeOpt,treeDatas);
			orgTree = treeViewer.getTree();
			orgTree.selectNodeByIndex(0);
		}
	}});
}
var tblData ;
function initTable(){
	tblMain = $('#tblMain').myDataTable(def_table_options);
	loadTableData();
	tblData = $('#tblData').myDataTable(def_datatable_options);
	
	
}
function hasSel(key){
	var hasSel = false;
	if(tblData){
		var datas = tblData.getData();
		if(datas&&$.isArray(datas)){
			for(var i=0;i<datas.length;i++){
				var data = datas[i];
				if(data.id === key){
					hasSel = true;
					break;
				}
			}
		}
	}
	return hasSel;
}
function __addRow(row, $element, field){
	 if(tblData){
     	var id = row.id;
     	if(!webUtil.isEmpty(id)&&hasSel(id)){
     		webUtil.mesg('此数据['+row.name+']已经选择了，不允许重复选择!');
     	}else{
     		tblData.addRow(row);
     	}
     }
}
function __removeRow(row, $element, field){
	 if(tblData){
		 tblData.removeRow( $element);
     }
}
$(document).ready(function() {
	initHeadStyle();
    initTree();
    initTable();
    $('#tblMain').on('dbl-click-row.bs.table', function (e, row, $element, field) {
    	__addRow(row, $element, field);
    });
    $('#tblData').on('dbl-click-row.bs.table', function (e, row, $element, field) {
    	__removeRow(row, $element, field);
    });
});

</script>
</html>