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
</style>
<script type="text/javascript">
</script>
<body style="padding: 2px 0px 0px 5px;">
	<div class="panel">
		<div id="table-toolbar" style="height:40px;">
			<div class="btn-group">
				<button class="btn btn-success" type="button">
					<span class="fa fa-file-o"></span>&nbsp;新增</button>
				<button class="btn btn-success" type="button">
					<span class="fa fa-file-text-o"></span>&nbsp;查看</button>
				<button class="btn btn-success" type="button">
					<span class="fa fa-edit"></span>&nbsp;修改</button>
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
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/gantt/css/style.css?v=121"/>
<script src="<%=appRoot%>/assets/lib/gantt/js/jquery.fn.gantt.js?v=1254" charset ="GB2312"></script>

<script type="text/javascript">
var orgTree ;
var curSelOrg = {};
function initOrgTree(){
	var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
		,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
		,callback:{onClick:treeClick}
		};
	var treeViewer = $('#left_container').myTreeViewer(null);
	treeViewer.init({theme:"panel-success",title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
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
	if(curSelOrg.id!=treeNode.id){
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
		          {text:'单位工程',name:'dwgc',algin:'left',width:150},
		          {text:'分解结构',name:'wbs',algin:'left',width:150},
		          {text:'具体工作内容',name:'content',type:'textarea',algin:'center',width:150},
		          {text:'生产情况',name:'item',algin:'center',width:60},
		          {text:'开始时间',name:'bd',width:70},
		          {text:'截止时间',name:'ed',width:70},
		          {text:'进度完成(%)',name:'progress',width:80},
		          {text:'工程量',name:'proqty',width:50},
		          {text:'施工人员',name:'sgry',width:80},
		          {text:'持续天数',name:'days',width:60}
		  ],
		leftColClick:function(data){
			//alert('你点击了第:'+data.rowIdx+'行');
		}
	});
}
var itemUrl = "ec/plan/projectplans/planRpt";
function loadPlanItemData(){
	var dataPrams = {projectId:curSelOrg.id};
	webUtil.ajaxData({url:itemUrl,data:dataPrams,success:function(data){
		var items = data.data;
		$(".gantt").gantt({
			navigate: "scroll"
			,leftPanelWidth:600
			,itemsPerPage:20
			,source:items});
	}});
}

$(document).ready(function() {
	initHeadStyle();
	loadTreeData();
})
</script>
</html>