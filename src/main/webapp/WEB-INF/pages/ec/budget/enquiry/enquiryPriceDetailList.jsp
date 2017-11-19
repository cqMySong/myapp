<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>材料、设备采购准备一览表</title>
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
								<th data-field="mat.number"  rowspan="2" width="120">物料编码</th>
								<th data-field="bdi.materialName"  rowspan="2" width="120">物料名称</th>
								<th data-field="bdi.specification"  rowspan="2" width="120">规格</th>
								<th data-field="bdi.measureUnitInfo" data-type="f7" rowspan="2" width="60">计量单位</th>
								<th data-field="bdi.quantity"  rowspan="2" width="100">数量</th>
								<th data-field="bdi.budgetaryPrice"  rowspan="2" width="100">预算价</th>
								<th data-field="intentionPrice" rowspan="2" width="40">市场采购意向价格</th>
								<th data-field="paymentMethod"  rowspan="2" width="100">付款方式</th>
								<th data-field="origin"  rowspan="2" width="100">产地</th>
								<th data-field="supplyCycle"  rowspan="2" width="100">供货周期</th>
								<th colspan="3" width="400">备选单位（不少于三家）</th>
								<th data-field="p.createDate" data-type="date" rowspan="2" width="100">填报时间</th>
								<th data-field="contractSignMethod" data-type="select" rowspan="2" width="100">预计合同订立方式</th>
							</tr>
							<tr>
								<th data-field="supplyCompany" class="_myMerge" width="100">单位</th>
								<th data-field="contactMan" width="100">联系人</th>
								<th data-field="contactTel" width="100">电话</th>
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
			var editWin ={title:'预算询价',width:(window.outerWidth-50),height:(window.outerHeight-100)};
			var treeOpt = {
					setting:{
						data: {
							simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
						}
					}};
			var height = window.outerHeight-325;
			thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/budget/enquirypricedetail',title:'项目工程',height:(height+42),
							treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
							,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false}});
			thisOrgList.onLoad();

	});

</script>
</html>