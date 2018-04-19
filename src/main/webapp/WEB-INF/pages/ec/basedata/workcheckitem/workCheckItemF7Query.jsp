<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../../inc/webBase.inc"%>
<title>施工现场检查项目</title>
<style type="text/css">
.panel { width: 100%;padding: 0px 2px 2px 2px;}
.mainContrainer {width: 100%; overflow:hidden;  padding: 0px 2px 2px 2px;}
.leftContainer {width: 230px; float: left;}
.rightContainer { overflow:hidden;  padding-left: 5px;}
</style>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;overflow: hidden;" class="panel">
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container" style="border: 1px solid #259dab;">
			<div class="panel">
				<div class="" id="tblMain_toolbar">
					
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead >
							<tr>
								<th data-field="workCheckType" data-type="select">检查类别</th>
								<th data-field="number">项目编码</th>
								<th data-field="name">检查项目</th>
								<th data-field="checkRequire" data-width="400" data-type="textarea">检查要求</th>
							</tr>
						</thead>
					</table>
					<div id="selected">
						<div style="position: relative; margin: 0px; line-height: 35px; height: 35px; vertical-align: middle; padding: 0px 5px;"
							id="selToolBar">
							<div class="bs-bar pull-left">
								<i class="glyphicon glyphicon-list"></i>&nbsp;已选清单
							</div>
							<div class="pull-right">
								<div class="btn-group">
									<a id="_addRow" class="btn btn-sm  btn-success"><span
										class="fa fa-plus-square"></span>&nbsp;选择</a> <a id="_removeRow"
										class="btn btn-sm  btn-success"><span
										class="fa fa-minus-square"></span>&nbsp;删除</a>
								</div>
							</div>
						</div>
						<table id="tblSelMain">
							<thead >
								<tr>
									<th data-field="workCheckType" data-type="select">检查类别</th>
									<th data-field="number">项目编码</th>
									<th data-field="name">检查项目</th>
									<th data-field="checkRequire" data-width="400" data-type="textarea">检查要求</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var mutil = true;
var treeMain;
var treeViewer;
var tblMain;
var treeSetting = {
    view: {dblClickExpand: true, showLine: true,selectedMulti: false}
    ,data: {simpleData: {enable:false,idKey: "id"}}
    ,callback:{onClick:tree_clickChange}
};
function getData(){
	if(webUtil.isEmpty(tblSelMain)) return [];
	return tblSelMain.getData();
}
var curTreeNode = {id:''};
function tree_clickChange(event, treeId, treeNode){
	curTreeNode = treeNode;
	loadTableData();
}
function getMyQueryParams(){
	return webUtil.json2Str({type:curTreeNode.id,group:'${checkGroup}'});
}

function loadTableData(){
	if(!webUtil.isEmpty(tblMain)){
		tblMain.refreshData();
	}
}

function dbClick_toAddrow(row, $element, field){
	if(!webUtil.isEmpty(tblSelMain)
			&&!webUtil.isEmpty(tblMain)){
		addToSelTable(row);
	}
}
function dbClick_toRemoveRow(row, $element, field){
	if(!webUtil.isEmpty(tblSelMain)){
		tblSelMain.removeRow($element);
	}
}
function addRow(){
	if(!webUtil.isEmpty(tblSelMain)
			&&!webUtil.isEmpty(tblMain)){
		var selRowsData = tblMain.getSelections();
		if(!webUtil.isEmpty(selRowsData)&&selRowsData.length>0){
			for(var i=0;i<selRowsData.length;i++){
				var rowData =  $.extend(true,{},selRowsData[i]);
				addToSelTable(rowData);
			}
		}else{
			webUtil.mesg("请先选择行!");
		}
	}
}
function addToSelTable(rowData){
	if(webUtil.isEmpty(tblSelMain)) return;
	var ok = true;
	var datas = tblSelMain.getData();
	if(!webUtil.isEmpty(datas)&&datas.length>0){
		if(!mutil){
			ok = false;
			webUtil.mesg("不允许选择多个!");
		}else{
			for(var i=0;i<datas.length;i++){
				if(rowData.id==datas[i].id){
					ok = false;
					webUtil.mesg("不能重复选择数据!");
					return;
				}
			}
		}
	}
	if(ok){
		tblSelMain.addRow(rowData);
	}
}

function removeRow(){
	if(!webUtil.isEmpty(tblSelMain)){
		var rIdx = tblSelMain.getSelectRowIndex();
		if(rIdx>=0){
			tblSelMain.removeRow(rIdx);
		}else{
			webUtil.mesg("请先在已选区选中行!");
		}
	}
}
$(document).ready(function() {
	treeViewer = $('#tree_container').myTreeViewer(null);
	treeViewer.init({title:'施工现场检查分类',height:680});
	treeViewer.addTree(treeSetting,[]);
	treeMain = treeViewer.getTree();
    var treeUrl = "ec/basedata/workcheckitemquery/treeData";
    mutil = '${mutil}'=='true'?true:false;
    webUtil.ajaxData({url:treeUrl,async:false,success:function(data){
		var treeDatas = data.data;
		if (treeDatas.length>0) {
			treeMain.reLoadTree(treeDatas);
			treeMain.selectNodeByIndex(0);
		}
	}});
    var dataURL = "ec/basedata/workcheckitemquery/f7Data";
    var myQueryTalbOpt = {height:380,striped:true,sortStable:true,showRefresh:false,clicToSelect:true
			,cache:false,pageSize:20,showToggle:true,showColumns:true,idField:"id",mypagination:true
			,toolbar:"#tblMain_toolbar",url:dataURL,queryParams:getMyQueryParams,selectModel:2};
	myQueryTalbOpt.selectModel = 2;
	myQueryTalbOpt.onDblClickRow = dbClick_toAddrow;
	tblMain =  $('#tblMain').myDataTable(myQueryTalbOpt);
	tblMain.refreshData();
	
	var _defEntryTableOpt = {mypagination:false,height:200,sortStable:false};
	var thisTblOpt = $.extend(true,{}, _defEntryTableOpt,{updateRow2Body:false,cusTopToolBar:$('#selToolBar')});
	thisTblOpt.onDblClickRow = dbClick_toRemoveRow;
	tblSelMain = $('#tblSelMain').myDataTable(thisTblOpt);
	$('#_addRow').click(function(){
		addRow();
	});
	$('#_removeRow').click(function(){
		removeRow();
	});
});

</script>
</html>