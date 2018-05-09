<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目进度计划监控</title>
</head>
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
.panel {
  width: 100%;
  height: 100%;
  padding: 0px 2px 2px 2px;
}
.my-progress-bar{
	width:60px !important;
	height: 30px !important;
	padding: 6px;
}

</style>
<script type="text/javascript">
</script>
<body style="padding: 2px 0px 0px 5px;">
	<div class="panel">
		<div id="table-toolbar" style="height:40px;">
			<div class="btn-group">
				<div class="input-group" style="width: 600px; margin-left: 10px;margin-top: 2px;">
					<input id="begDate" style="width: 200px;" class="form-control toDate" data-opt="{type:'date'}">
					<span style="vertical-align: middle;line-height: 35px;padding: 0px 5px;float: left;">监控期间</span>
					<input id="endDate" style="width: 200px;" class="form-control toDate" data-opt="{type:'date'}">
					<button type="button" id="toQuery" class="btn btn-success" style="float:left;margin-left: 5px;">
						<span class="fa fa-filter"></span>&nbsp;查询
					</button>
				</div>
			</div>	
			<div class="pull-right" style="padding:5px; ">
				<div class="progress-bar progress-bar-success my-progress-bar" ><span class="fn-label">提前完成</span></div>
				<div class="progress-bar progress-bar-warning my-progress-bar" ><span class="fn-label">如期完成</span></div>
				<div class="progress-bar progress-bar-danger my-progress-bar" ><span class="fn-label">延期完成</span></div>
			</div>	
		</div>
		<hr style="margin: 2px 0px;">
		<div class="mainContrainer">
			<div class="leftContainer" id="left_container">
			</div>
			<div class="rightContainer" id="main_container">
				<div class="gantt"></div>
			</div>
		</div>
	</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/gantt/css/style.css?v=12111"/>
<script src="<%=appRoot%>/assets/lib/gantt/js/jquery.fn.gantt.js?v=125114" charset ="GB2312"></script>

<script type="text/javascript">
var orgTree ;
var curSelOrg = {};
function initOrgTree(){
	var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
		,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
		,callback:{onClick:treeClick}
		};
	var height = top.getTopMainHeight()-45;;
	var treeViewer = $('#left_container').myTreeViewer(null);
	treeViewer.init({theme:"panel-success",height:height,title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
	treeViewer.addTree(treeOpt,[]);
	orgTree = treeViewer.getTree();
	treeViewer.addRefreshBtn({clickFun:function(btn){
		loadTreeData();
	}});
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
	if(curSelOrg.id!=treeNode.id&&'project'==treeNode.type){
		curSelOrg = treeNode;
		loadPlanItemData();
	}
}
function initHeadStyle(){
	var height = top.getTopMainHeight()-40;
	$(".mainContrainer").height(height);
	$(".gantt").height(height-5);
	initOrgTree();
	initGantView();
}
function initGantView(){
	$(".gantt").gantt({
		navigate: "scroll",
		leftPanelWidth:600,
		onItemClick: function(data) {
			//alert("data = "+data.label);
		},
		leftCols:[
		          //{text:'单位(子单位)工程',name:'dwgc',algin:'left',width:150},
		          //{text:'分部分项工程',name:'wbs',algin:'left',width:150},
		          {text:'工作内容',name:'content',type:'textarea',algin:'center',width:250},
		          {text:'生产情况',name:'item',algin:'center',width:60},
		          {text:'开始时间',name:'bd',width:70},
		          {text:'截止时间',name:'ed',width:70},
		          {text:'进度完成(%)',name:'progress',width:80},
		          {text:'工程量',name:'proqty',width:50},
		          {text:'施工人员',name:'sgry',width:80},
		          {text:'持续天数',name:'days',width:60},
		          {text:'延误性质',name:'dn',width:100},
		          {text:'延误原因',name:'dc',width:60},
		          {text:'专题会议纪要',name:'mc',width:100},
		          {text:'纪要执行情况',name:'mtd',width:100},
		          {text:'赶工情况',name:'td',width:80},
		          {text:'办理工期延误',name:'dd',width:60},
		          {text:'办理工期费用索赔',name:'dwp',width:60}
		  ],
		leftColClick:function(data){
			//alert('你点击了第:'+data.rowIdx+'行');
		}
	});
}
var itemUrl = "ec/plan/projectplans/planRpt";
function loadPlanItemData(){
	if('project'==curSelOrg.type){
		var bd = $('#begDate').val();
		var ed = $('#endDate').val();
		var dataPrams = {projectId:curSelOrg.id,begDate:bd,endDate:ed};
		webUtil.ajaxData({url:itemUrl,data:dataPrams,success:function(data){
			var items = data.data;
			$(".gantt").gantt({
				navigate: "scroll"
				,leftPanelWidth:600
				,itemsPerPage:20
				,source:items});
		}});
	}
	
}
function toQueryPlanItemData(){
	if(!webUtil.isEmpty(curSelOrg)&&'project'==curSelOrg.type){
		loadPlanItemData();
	}else{
		webUtil.mesg("请选择对应的工程项目!");
	}
}
var dpicker = {language: 'zh-CN',format:'yyyy-mm-dd',autoclose: true,todayBtn: 'linked',pickerPosition: "bottom-left"};
$(document).ready(function() {
	initHeadStyle();
	loadTreeData();
	$('.toDate').datepicker(dpicker);
	var curDate = new Date();
	var begDateS = curDate.format('yyyy-MM')+'-01';
	$('#begDate').datepicker('update',begDateS);
	curDate = curDate.format('yyyy-MM-dd');
	$('#endDate').datepicker('update',curDate);
	$('#toQuery').click(function(){
		toQueryPlanItemData();
		
	});
})
</script>
</html>