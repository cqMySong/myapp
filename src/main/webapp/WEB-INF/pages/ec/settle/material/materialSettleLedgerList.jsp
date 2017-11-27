<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>材料、设备结算一览表</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:60px;margin-bottom:5px;"></div>
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
								<th data-field="contract.expenseType" data-type="select">合同类型</th>
								<th data-field="contract.number">合同单号</th>
								<th data-field="contract.name" >合同名称</th>
								<th data-field="contract.supplyCompany">合同单位</th>
								<th data-field="">使用部位</th>
								<th data-field="pr.settleDate" data-type="date">时间段</th>
								<th data-field="settleAmount">金额(元)</th>
								<th data-field="">累计金额(元)</th>
								<th data-field="contract.amount">合同金额(元)</th>
								<th data-field="op.name">经办人</th>
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
	function beforeAction(opt){
		return true;
	}
	//界面参数传递扩展方法
	function openUIParams(operate,params){

	}
	$(document).ready(function() {
			var treeNode2QueryProp = ["id","name","number","longNumber","type"];
			var treeOpt = {
					setting:{
						data: {
							simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
						}
					}};
			var height = window.outerHeight-325;
			thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',
				baseUrl:'ec/settle/materialsettleledgers',title:'项目工程',height:(height+42),
                hasDefToolbar:false,treeContainer:"#tree_container",toolbar:"#table-toolbar",
				searchParams:{includeChild:true},treeOpt:treeOpt,treeNode2QueryProp:treeNode2QueryProp,
				extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false}});
			thisOrgList.onLoad();

	});

</script>
</html>