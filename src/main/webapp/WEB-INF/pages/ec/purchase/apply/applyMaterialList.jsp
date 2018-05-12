<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>材料申购</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;padding-top: 1px;margin-bottom: 5px;">
		<button class="btn btn-success" type="button" id="opinion">
			<span class="fa fa-edit"></span>&nbsp;查看意见
		</button>
		<button class="btn btn-success" type="button" id="schedule">
			<span class="fa fa-edit"></span>&nbsp;流程进度
		</button>
	</div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel" style="margin-bottom: 0px;">
				<div class="" id="tblMain_toolbar">
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead >
							<tr>
								<th data-field="name">申购名称</th>
								<th data-field="number">申购单号</th>
								<th data-field="project_name">工程项目</th>
								<th data-field="billState" data-type="select">业务状态</th>
								<th data-field="auditState" data-type="select">流程状态</th>
								<th data-field="createUser_name">申购人</th>
								<th data-field="createDate" data-type="date">申购时间</th>
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
	var thisBudgetList ;
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

	}

	$(document).ready(function() {
			var treeNode2QueryProp = ["id","name","number","longNumber","type"];
			var height = top.getTopMainHeight()-50;
			var winHeight = height+150;
			var editWin ={title:'材料申购',width:1200,height:winHeight>800?800:winHeight};
			var treeOpt = {
					setting:{
						data: {
							simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
						}
					}};
			thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/purchase/applymaterials',title:'项目工程',height:height,
							treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
							,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-45,sortStable:false}});
			thisOrgList.onLoad();
			//查看流程进度
			$('#schedule').on('click',function(){
                var _selRows = thisOrgList.listUI.getSelectRow();
				if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
					var backLogData = _selRows[0];
					var url = app.root+"/base/backlogs/photo/"+backLogData.processInstanceId;
					webUtil.openWin({title:'流程进度',btns:null,operate:OperateType.audit,width:1200,height:winHeight>800?800:winHeight,url:url});
				}else{
					webUtil.mesg('请先选中对应的数据行，方可进行查看!');
				}
			});
			//查看审核意见
			$('#opinion').on('click',function(){
                var _selRows = thisOrgList.listUI.getSelectRow();
				if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
					var backLogData = _selRows[0];
					var url = app.root+"/base/backlogs/histoic/flow/show/"+backLogData.processInstanceId;
					webUtil.openWin({title:'审核意见',btns:null,operate:OperateType.audit,width:1200,height:winHeight>800?800:winHeight,url:url});
				}else{
					webUtil.mesg('请先选中对应的数据行，方可进行查看审核意见!');
				}
			});

	});

</script>
</html>