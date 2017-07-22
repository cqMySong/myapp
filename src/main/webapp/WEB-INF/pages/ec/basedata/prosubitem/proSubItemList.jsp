<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目分部分项</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div class="ui-layout-north">
		<div id="table-toolbar" class="panel" style="margin-bottom:5px;"></div>
	</div>
	<div class="ui-layout-west" id="tree_container">
	</div>
	<div class="ui-layout-center" id="mainContainer">
		<div class="ui-layout-north">
			<div class="" id="tblMain2_toolbar">
				项目分部工程明细
			</div>
			<div class="panel-body" style="padding: 0px 2px 2px 2px;">
				<table id="tblMain2">
					 <thead >
						<tr>
							<th data-field="proStruct_name">工程结构</th>
							<th data-field="number">编码</th>
							<th data-field="name">名称</th>
							<th data-field="enabled" data-type="checkbox">启用</th>
							<th data-field="remark" >备注</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div class="ui-layout-center">
			<div class="" id="tblMain_toolbar">
				分项工程明细
			</div>
			<div class="panel-body" style="padding: 0px 2px 2px 2px;">
				<table id="tblMain">
					 <thead >
						<tr>
							<th data-field="proStruct_name">工程结构</th>
							<th data-field="proSub_name">分部工程</th>
							<th data-field="number">编码</th>
							<th data-field="name">名称</th>
							<th data-field="enabled" data-type="checkbox">启用</th>
							<th data-field="remark" >备注</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
<%@include file="../../../base/base_treelist.jsp"%>
<script type="text/javascript">

var thisOrgList ;
var includeChild;
var treeNode2QueryProp = ["id","name","number","longNumber","type"];
var tblMain2;
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		var tree = thisOrgList.tree;
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&('project'==params.tree.type||'proStructure'==params.tree.type)){
		}else{
			webUtil.mesg('请先选择的工程项目组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
	if(operate=='addnew'){
		var proSubRow = tblMain2.getSelections();
		if(!webUtil.isEmpty(proSubRow)){
			proSubRow = proSubRow[0];
			var proSubObj = {};
			proSubObj.id = proSubRow.id;
			proSubObj.name = proSubRow.name;
			params.proSub = proSubObj;
		}
	}
}

function packeTreeNode(treeNode){
	var to_treeNode = {};
	if(!webUtil.isEmpty(treeNode)){
		for(var i=0;i<treeNode2QueryProp.length;i++){
			var prop = treeNode2QueryProp[i];
			to_treeNode[prop] = treeNode[prop];
		}
	}
	return to_treeNode;
}
var curSelTree = {};
function treeSelectChange(e, treeId, treeNode){
	curSelTree = {tree:packeTreeNode(treeNode)};
	tblMain2.myQueryParams = webUtil.json2Str(curSelTree);
	tblMain2.refreshData();
	
}
function tb2SelectChange(ridx,row,rowDom){
	curSelTree.proSubId = row.id;
	refreshDataTable();
}
function refreshDataTable(){
	thisOrgList.listUI.executeQueryByParams(curSelTree);
}
$(document).ready(function() {
    
     var editWin ={title:'项目分部分项',width:620,height:450};
     var treeOpt = {
    		 selectChange:treeSelectChange,
    		 setting:{data: {
         		simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     var height = 700;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',listModel:1,treeUrl:'ec/basedata/prostructures/proStructureTree'
    	 ,baseUrl:'ec/basedata/prosubitems',title:'工程项目',height:height,treeSectedChange2Query:false
    	 ,treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:320,sortStable:false}});
     thisOrgList.onLoad();
     thisOrgList.toRefreshTree = false;
     
     var myQueryTalbOpt = {height:250,striped:true,sortStable:true,showRefresh:false,clicToSelect:true
 			,cache:false,pageSize:20,showToggle:true,showColumns:true,idField:"id",mypagination:true
 			,toolbar:"#tblMain2_toolbar",selectModel:1,url:'ec/basedata/prosubs/query',selectChanaged:tb2SelectChange};
     tblMain2 = $('#tblMain2').myDataTable(myQueryTalbOpt);
     
     $('.fixed-table-toolbar').find('div').css({"margin-top":"0px"});
     $('body').layout({ applyDefaultStyles: true,west__size:300});
     $('#mainContainer').layout({applyDefaultStyles: true});
     //$('#tree_container').css({"padding":'2px'});
});

</script>
</html>