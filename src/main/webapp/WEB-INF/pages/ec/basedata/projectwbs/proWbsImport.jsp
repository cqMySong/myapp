<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目分部分项工程标准导入</title>
</head>
<style type="text/css">
.mainContrainer {
  width: 100%;
  height: 100%;
  overflow:hidden;
  padding: 0px 2px 1px 2px;
}
.leftContainer {
  width: 390px;
  height: 100%;
  float: left;
}
.centerContainer {
  width: 63px;
  height: 100%;
  float: left;
  padding: 3px;
}
.rightContainer {
  height: 100%;
  overflow:hidden;
  padding-left: 5px;
}

</style>
<script type="text/javascript">
</script>
<body style="padding: 2px;" style="width: 700px;">
	<div class="mainContrainer" style="height: 470px;">
		<div class="leftContainer">
			<div id="allTreeItems"></div>
		</div>
		<div class="centerContainer">
			<div class="middleContainner" style="padding-top: 130px;">
				<button class="btn btn-success" type="button" style="width:62px;" id="toAllAssign"><span class="fa fa-server" ></span>&nbsp;全分</button>
				<button class="btn btn-success" type="button" style="width:62px;margin-top: 2px;" id="toRight"><span class="fa fa-tasks"></span>&nbsp;分配</button>
				<button class="btn btn-success" type="button" style="width:62px;margin-top: 2px;" id="toRemove"><span class="fa fa-trash-o"></span>&nbsp;取消</button>
				<button class="btn btn-success" type="button" style="width:62px;margin-top: 2px;" id="toRemoveAll"><span class="fa fa-trash"></span>&nbsp;全取</button>
			</div>
		</div>
		<div class=" rightContainer" >
			<div>
				<div class="panel panel-success">
					<div class="panel-heading" style="padding: 13px;">
						<h3 class="panel-title">选择分解结构标准</h3>
						<ul class="panel-options">
							<i style="cursor: pointer; font-size: 12px; color: rgb(255, 255, 255);"
								class="fa fa-search">&nbsp;</i>
						</ul>
					</div>
					<div class="panel-body" style="padding: 0px 2px 2px 2px;">
						<ul class="ztree nomargin" id="toAssignTreeItems"
							style="width: 99%; height: 430px; overflow: auto; -moz-user-select: none;"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
	var setting = {
		check: {enable: true},
		data: {simpleData: {enable: true,idKey: "id",pIdKey: "parent_id",rootPId: ""}}
	};
	var setting2 = {
			check: {enable: false},
			data: {simpleData: {enable: true,idKey: "id",pIdKey: "parent_id",rootPId: ""}}
		};
	var leftTreeViewer;
	var leftTree;
	var rightTreeViewer;
	var rightTree;
	var targetId = '${targetId}';
	var treeUrl = 'ec/basedata/probasewbss/tree';
	$(document).ready(function(){
		leftTreeViewer = $('#allTreeItems').myTreeViewer(null);
		leftTreeViewer.init({title:'项目分部分项工程标准',height:470});
		leftTreeViewer.addRefreshBtn({clickFun:function(){
			loadLeftTreeData();
		}});
		leftTreeViewer.addTree(setting,[]);
		leftTree = leftTreeViewer.getTree();
		
		rightTree = $.fn.zTree.init($('#toAssignTreeItems'), setting2, []);
		loadLeftTreeData();
	});
	var treeDatas ;
	function loadLeftTreeData(){
		var url =treeUrl;
		if(!webUtil.isEmpty(targetId)){
			url = treeUrl+'?targetId='+webUtil.uuIdReplaceID(targetId);
		}
		webUtil.ajaxData({url:url,async:false,success:function(data){
			treeDatas = data.data;
			if (treeDatas.length>0&&!webUtil.isEmpty(leftTree)) {
				leftTree.reLoadTree(treeDatas)
			}
		}});
	}
</script>
<script type="text/javascript">
function getData(){
	return rightTree.getNodes();
}

function toAddTree(treeNodes){
	rightTree.destroy();
	var addTreeNode = [];
	if(!webUtil.isEmpty(treeNodes)){
		for(var i=0;i<treeNodes.length;i++){
			var node = treeNodes[i];
			var curNode = {};
			curNode.id = node.id;
			curNode.name = node.name;
			curNode.open = node.open;
			curNode.parent_id = node.parent_id;
			addTreeNode.push(curNode);
		}
	}
	if(addTreeNode.length<=0){
		webUtil.mesg("未选中数据!");
	}
	rightTree = $.fn.zTree.init($('#toAssignTreeItems'), setting2, addTreeNode)
}

function toAssign(all){
	var treeNodes = [];
	if(!all){
		treeNodes = leftTree.getTree().getCheckedNodes(true);
	}else{
		treeNodes = treeDatas;
	}
	toAddTree(treeNodes);
}
function unAssign(all){
	if(!all){
		var nodes = rightTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			rightTree.removeChildNodes(nodes[0]);
			rightTree.removeNode(nodes[0]);
		}
	}else{
		rightTree.destroy();
		rightTree = $.fn.zTree.init($('#toAssignTreeItems'), setting2, [])
	}
}
$(document).ready(function() {
	$('#toRight').click(function(){
		toAssign(false);
	});
	$('#toAllAssign').click(function(){
		toAssign(true);
	});
	$('#toRemoveAll').click(function(){
		unAssign(true);
	});
	$('#toRemove').click(function(){
		unAssign(false);
	});
})
</script>
</html>