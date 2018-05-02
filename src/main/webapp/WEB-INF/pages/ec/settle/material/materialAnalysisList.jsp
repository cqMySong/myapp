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
				<span class="input-group-addon lable">开始时间</span>
				<input name="startDate"  autocomplete="off" type="text" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">结束时间</span>
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
						<th data-field="fnumber"  width="150">编码</th>
						<th data-field="fname" width="100">材料名称</th>
						<th data-field="fSpecification" width="100">规格</th>
						<th data-field="unitName" width="60">单位</th>
						<th data-field="quantity" width="100" >预算数量</th>
						<th data-field="quantityPrice" width="100" data-formatter="quantityPrice">平均预算单价</th>
						<th data-field="purchaseCount" width="100">当期入库数量</th>
						<th data-field="settleCount" width="100">当期结算数量</th>
						<th data-field="settlePrice" width="100" data-formatter="settlePrice" >平均结算单价</th>
						<th data-field="fCalculationCount"  width="100">当期图算用量</th>
						<th data-field="fActualUseCount"  width="100">当期实际用量</th>
						<th data-field="fstartdate"  data-type="date" width="120">开始时间</th>
						<th data-field="fenddate" data-type="date" width="120">结束时间</th>
						<th data-field="remark" width="200">预警</th>
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
            ,cache:false,showToggle:false,search:false,queryParams:searchPrams,toolbar:false,rowStyle:changeBgColor
            ,showColumns:false,idField:"id",mypagination:true,url:'ec/settle/materianalysis/query'};
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
    function quantityPrice(value, row, index) {
		if(row.quantity>0){
		    return Number(row.quantityPrice/row.quantity).toFixed(2);
		}
    }
    function settlePrice(value, row, index) {
        if(row.settleCount>0){
            return Number(row.settlePrice/row.settleCount).toFixed(2);
        }
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
    function changeBgColor(row, index) {
        var color = "";
        if(row.fActualUseCount>row.fCalculationCount){
            color=EarlyWarning.danger;
        }
        if(!color){
            return false;
        }
        var style={css:{'background-color':color}};
        return style;
    }
</script>
</html>