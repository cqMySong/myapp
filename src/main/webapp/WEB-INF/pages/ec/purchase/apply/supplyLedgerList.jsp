<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>材料申购、供应台账</title>
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
								<th data-field="mater.number"  rowspan="2" width="120">物料编码</th>
								<th data-field="bdi.materialName"  rowspan="2" width="120">物料名称</th>
								<th data-field="bdi.specification"  rowspan="2" width="120">规格</th>
								<th data-field="mui.name"  rowspan="2" width="60">计量单位</th>
								<th data-field="bdi.quantity"  rowspan="2" width="100">预算数量</th>
								<th colspan="4"  width="400">材料申购计划</th>
								<th colspan="3"  width="300">材料实际供应</th>
							</tr>
							<tr>
								<th data-field="pr.number" class="_myMerge" width="100">申购单号</th>
								<th data-field="purchaseNum" width="100">申购数量</th>
								<th data-field="arrivalTime" width="100" data-type="date">计划到场时间</th>
								<th data-field="cumulativePurchaseNum" width="100">累计申购数量</th>
								<th data-field="psdi.count" class="_myMerge" width="100">供应数量</th>
								<th data-field="psdipr.inStockDate" width="100" data-type="date">到场时间</th>
								<th data-field="psdi.cumulativeCount" width="100">累计供应数量</th>
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
			var editWin ={title:'采购入库',width:(window.outerWidth-50),height:(window.outerHeight-100)};
			var treeOpt = {
					setting:{
						data: {
							simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
						}
					}};
			var height = window.outerHeight-325;
			thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',
				baseUrl:'ec/purchase/supplyledger',title:'项目工程',height:(height+42),
                hasDefToolbar:false,treeContainer:"#tree_container",
				editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt,
				treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,
					sortStable:false}});
			thisOrgList.onLoad();

	});

</script>
</html>