<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目主要工作安排摘要</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
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
								<th data-field="workType" data-type="select">工作分类</th>
								<th data-field="jobContent" data-width="250" data-type="textarea">工作内容</th>
								<th data-field="startDate" data-type="date">开始时间</th>
								<th data-field="finishDate" data-type="select">完成时间</th>
								<th data-field="personLiable">责任人</th>
								<th data-field="takeOver" data-type="checkbox">是否交办</th>
								<th data-field="sendee" >交办接收人</th>
								<th data-field="workFollowUp" data-type="checkbox">工作跟进</th>
								<th data-field="finish" data-type="checkbox">是否完成</th>
								<th data-field="remark">备注</th>
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
//界面参数传递扩展方法
function openUIParams(operate,params){
	
}
$(document).ready(function() {
     var treeNode2QueryProp = ["id","name","number","longNumber","type"];
     var editWin ={title:'项目主要工作安排摘要',width:630,height:600};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     var height = top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',listModel:1,treeUrl:'ec/basedata/projects/projectTree'
    	 ,baseUrl:'ec/basedata/workschedules',height:height,treeContainer:"#tree_container",title:"工程项目"
    	 ,editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-50}});
     thisOrgList.onLoad();
});

</script>
</html>