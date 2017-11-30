<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>材料、设备出入库台账</title>
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
								<th data-field="mater.number" width="120">物料编码</th>
								<th data-field="bdi.materialName" width="120">物料名称</th>
								<th data-field="bdi.specification"  width="120">规格</th>
								<th data-field="bdi.quantity"  width="100">预算数量</th>
								<th data-field="pr.inStockDate" width="100" data-type="date">入库时间</th>
								<th data-field="count" width="100">入库数量</th>
								<th data-field="pr.number" width="100">入库单号</th>
								<th width="100">累计入库数量</th>
								<th  data-field="stockOutPr.outStockDate" width="100" data-type="date">出库时间</th>
								<th data-field="stockOut.count" width="100">出库数量</th>
								<th width="100">领用单位</th>
								<th data-field="pick.name" width="100">领用人</th>
								<th data-filed="stockOutPr.number" width="100">出库单号</th>
								<th width="100">归还情况</th>
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
				baseUrl:'ec/purchase/stockledger',title:'项目工程',height:(height+42),
                hasDefToolbar:false,treeContainer:"#tree_container",
				editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt,
				treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,
					sortStable:false}});
			thisOrgList.onLoad();

	});

</script>
</html>