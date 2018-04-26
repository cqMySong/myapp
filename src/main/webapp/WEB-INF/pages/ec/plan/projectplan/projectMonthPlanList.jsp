<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目总进度计划-月计划分解</title>
</head>
<script type="text/javascript">
</script>
<style type="text/css">
.mainContrainer {
  width: 100%;
  height: 100%;
  overflow:hidden;
  padding: 0px 2px 2px 2px;
}
.leftContainer {
  width: 260px;
  height: 100%;
  float: left;
}
.rightContainer {
  height: 100%;
  overflow:hidden;
  padding-left: 5px;
}
</style>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;">
		<button type="button" id="refresh" class="btn btn-success">
			<span class="fa fa-refresh"></span>&nbsp;刷新
		</button>
	</div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel">
				<div class="" id="tblMain_toolbar">
					<div class="input-group">
						<div class="input-group-addon" id="preMonth" style="width: 50px;min-width: 50px;cursor: pointer;">
							上月<span class="glyphicon glyphicon-step-backward"></span>
						</div>
						<input type="text" class="form-control" id="bizDate" readonly="readonly" style="width: 120px;text-align: center;">
						<div class="input-group-addon" id="nextMonth" style="width: 50px;min-width: 50px;cursor: pointer;">
							<span class="glyphicon glyphicon-step-forward"></span>下月
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						  <thead >
							<tr>
								<th data-field="proStructureName" rowspan="2" width="150">单位(子单位)工程</th>
								<th data-field="proWbsName" rowspan="2" width="250">项目分部分项工程</th>
								
								<th data-field="proSubName" data-visible="false" rowspan="2" width="100">项目分部工程</th>
								<th data-field="proSubItemName" data-visible="false" rowspan="2" width="100">项目分项工程</th>
								<th colspan="3">计划</th>
								<th data-field="progress" rowspan="2" width="60" data-type="number">当前进度</th>
								<th data-field="content" rowspan="2" width="200" >工作内容</th>
								<th data-field="proQty" rowspan="2" width="60">工程量</th>
								<th data-field="dutyers" data-type="f7" rowspan="2" width="120">施工人员</th>
								<th data-field="remark" rowspan="2">备注</th>
							</tr>
							<tr>
								<th data-field="bd" class="_myMerge" width="100" data-type="date">开始日期</th>
								<th data-field="ed" width="100" data-type="date">截止日期</th>
								<th data-field="days" width="60" data-locked="true">持续天数</th>
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
var orgTree ;
var curYM ;
var curPeriod = new Date();
var tblMain;
var curSelOrg = {};
function setCurPeriod(val,query){
	$('#bizDate').datetimepicker('update',val);
	curPeriod = val;
	var newYm = val.format('yyyyMM');
	if(newYm!=curYM){
		curYM = newYm;
		if(query){
			toQuery();
		}
	}
}
function toQuery(){
	if(orgTree&&!webUtil.isEmpty(curSelOrg.id)){
		tblMain.refreshData();
	}
}
function loadTreeData(){
	webUtil.ajaxData({url:'ec/basedata/projects/projectTree',async:false,success:function(data){
		var treeDatas = data.data;
		if (treeDatas.length>0&&!webUtil.isEmpty(orgTree)) {
			orgTree.reLoadTree(treeDatas)
			orgTree.selectNodeByIndex(0);
		}
	}});
}
function treeClick(event, treeId, treeNode){
	if(webUtil.isEmpty(curSelOrg)) curSelOrg = {id:'xyz'};
	if(webUtil.isEmpty(curSelOrg.id)) curSelOrg.id = 'xyz';
	if(curSelOrg.id!=treeNode.id){
		curSelOrg = treeNode;
		toQuery();
	}
}
function initOrgTree(){
	var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
		,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
		,callback:{onClick:treeClick}
		};
	var treeViewer = $('#tree_container').myTreeViewer(null);
	var height =  top.getTopMainHeight()-45;
	treeViewer.init({theme:"panel-success",height:height,title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
	treeViewer.addTree(treeOpt,[]);
	orgTree = treeViewer.getTree();
	treeViewer.addRefreshBtn({clickFun:function(btn){
		loadTreeData();
	}});
}
function serachPrams(){
	var params = {};
	params.curPeriod = curYM;
	params.type = 'month';
	if(webUtil.isEmpty(curSelOrg)) curSelOrg = {id:'xyz'};
	if(webUtil.isEmpty(curSelOrg.id)) curSelOrg.id = 'xyz';
	params.treeId = curSelOrg.id;
	return webUtil.json2Str(params);
}
function initHeadStyle(){
	$('#bizDate').datetimepicker({
		format: 'yyyymm',weekStart: 1
		,autoclose: true,startView: 3
		,minView: 3,forceParse: false
		,pickerPosition: "bottom-left"
		,language: 'zh-CN'}); 
	$('#bizDate').on('changeDate',function(e){
		setCurPeriod($('#bizDate').datetimepicker('getDate'),true);
	});
}
function initEvent(){
	$('#preMonth').click(function(){
		setCurPeriod(curPeriod.addMonths(-1),true);
	});
	$('#nextMonth').click(function(){
		setCurPeriod(curPeriod.addMonths(1),true);
	});
	$('#refresh').click(function(){
		toQuery();
	});
}
function initData(){
	loadTreeData();
	setCurPeriod(curPeriod,false);
}

function initTable(){
	var height =  top.getTopMainHeight()-100;
	var table_options = {height:height,striped:true,sortStable:false,showRefresh:false,selectModel:1
			,cache:false,showToggle:true,search:true,queryParams:serachPrams,toolbar:"#tblMain_toolbar"
			,showColumns:true,idField:"id",mypagination:true,url:'ec/plan/projectplans/month'};
	tblMain = $('#tblMain').myDataTable(table_options);
}
function _onLoad(){
	initHeadStyle();
	initOrgTree();
	initTable();
	initEvent();
	initData();
	toQuery();
}
function _onShow(){
	
}
$(document).ready(function() {
	_onLoad();
	_onShow();
});

</script>
</html>