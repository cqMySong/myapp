<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>图纸会审</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:60px;margin-bottom:5px;"></div>
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
								<th data-field="name">图纸名称</th>
								<th data-field="number">图纸编号</th>
								<th data-field="conferenceDate" data-type="date">会审时间</th>
								<th data-field="conferencePlace">会审地点</th>
								<th data-field="participantUnits">参会单位</th>
								<th data-field="participants">参会人员</th>
								<th data-field="moderator">主持人</th>
								<th data-field="subject">会议议题</th>
								<th data-field="completeSignature" data-formatter="formatter_complete">盖章手续</th>
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
	function formatter_complete(value, row, index){
		return (value==true)?'完成':'<span style="color: red;">未完成</span>';
	}
	var thisOrgList ;
	var includeChild;
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
			var editWin ={title:'图纸会审',width:680,height:580};
			var treeOpt = {setting:{
							data: {
								simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
							}
							}};
			var height = 700;
			thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/discussiondrawings',title:'项目工程',height:height,
							treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
							,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false}});
			thisOrgList.onLoad();
	});

</script>
</html>