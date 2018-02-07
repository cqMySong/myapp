<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>材料设备对比分析表</title>
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
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
<div class="panel">
	<div id="table-toolbar" style="height:40px;padding-top: 2px;">
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">填报开始时间</span>
				<input name="startDate"  autocomplete="off" type="text" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">填报结束时间</span>
				<input name="endDate"  autocomplete="off" type="text" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">材料名称</span>
				<input type="text" class="input-item form-control" name="materialName">
			</div>
		</div>
		<div class="btn-group">
			<button class="btn btn-success" type="button" id="queryAnalysis">
				<span class="fa fa-search"></span>&nbsp;查询</button>
		</div>
	</div>
	<hr style="margin: 2px 0px;">
	<div class="mainContrainer">
		<div class="leftContainer" style="border-right: 2px solid #d8dce3" id="left_container">
		</div>
		<div class="rightContainer" id="main_container">
			<table id="tblMain">
				<thead >
					<tr>
						<th data-field="fnumber"  rowspan="2" width="120">物料编码</th>
						<th data-field="fMaterialName"  rowspan="2" width="120">物料名称</th>
						<th data-field="fspecification"  rowspan="2" width="120">规格</th>
						<th data-field="unitName" rowspan="2" width="60">单位</th>
						<th data-field="fQuantity"  rowspan="2" width="100">数量</th>
						<th data-field="fBudgetaryPrice"  rowspan="2" width="100">预算价</th>
						<th data-field="fIntentionPrice" rowspan="2" width="40">市场采购意向价格</th>
						<th data-field="fPaymentMethod"  rowspan="2" width="100">付款方式</th>
						<th data-field="fOrigin"  rowspan="2" width="100">产地</th>
						<th data-field="fSupplyCycle"  rowspan="2" width="100">供货周期</th>
						<th colspan="3" width="400">备选单位（不少于三家）</th>
						<th data-field="fcreateDate" data-type="date" rowspan="2" width="100">填报时间</th>
						<th data-field="fContractSignMethod" rowspan="2" width="100">预计合同订立方式</th>
					</tr>
					<tr>
						<th data-field="fSupplyCompany" class="_myMerge" width="100">单位</th>
						<th data-field="fContactMan" width="100">联系人</th>
						<th data-field="fContactTel" width="100">电话</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
    var orgTree;
    var curSelOrg;
    var tblMain;
    function treeClick(event, treeId, treeNode){
        if(webUtil.isEmpty(curSelOrg)) curSelOrg = {id:'xyz'};
        if(webUtil.isEmpty(curSelOrg.id)) curSelOrg.id = 'xyz';
        if(curSelOrg.id!=treeNode.id){
            curSelOrg = treeNode;
            tblMain.refreshData();
        }
    }
    function initOrgTree(height){
        var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
            ,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
            ,callback:{onClick:treeClick}
        };
        var treeViewer = $('#left_container').myTreeViewer(null);
        treeViewer.init({height:height-10,theme:"panel-success",title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
        treeViewer.addTree(treeOpt,[]);
        orgTree = treeViewer.getTree();
        treeViewer.addRefreshBtn({clickFun:function(btn){
            loadTreeData();
        }});
        loadTreeData();
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
    function initTable(){
        var height = top.getTopMainHeight()-105;
        var table_options = {height:height,striped:true,sortStable:false,showRefresh:false,selectModel:1
            ,cache:false,showToggle:false,search:false,queryParams:searchPrams,toolbar:false
            ,showColumns:false,idField:"id",mypagination:true,url:'ec/budget/enquirypricedetail/query'};
        tblMain = $('#tblMain').myDataTable(table_options);
    }
    function searchPrams(){
        var params = {};
        params.projectId = curSelOrg.id;
        params.startDate = $("input[name='startDate']").val();
        params.endDate = $("input[name='endDate']").val();
        params.materialName = $("input[name='materialName']").val();
        return webUtil.json2Str(params);
    }
    $(function(){
        var height = top.getTopMainHeight()-40;
        $(".mainContrainer").height(height);
        initOrgTree(height);
        initTable();
        $("input[name='startDate']").myComponet(DataType.date,{method:"init",opt:{}});
        $("input[name='endDate']").myComponet(DataType.date,{method:"init",opt:{}});
        $("#queryAnalysis").on('click',function(){
            tblMain.refreshData();
        });
    });
</script>
</html>