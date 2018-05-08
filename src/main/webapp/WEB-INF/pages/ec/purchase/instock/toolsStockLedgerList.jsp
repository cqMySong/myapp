<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>工、器具出入库台帐</title>
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
				<span class="input-group-addon lable">材料名称</span>
				<input type="text" class="input-item form-control" name="materialName">
			</div>
		</div>
		<div class="btn-group">
			<button class="btn btn-success" type="button" id="queryStockLedger">
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
					<th data-field="materialNumber" width="120">编号</th>
					<th data-field="materialName" width="120">材料名称</th>
					<th data-field="specification"  width="120">规格</th>
					<th data-field="budgetCount"  width="100">预算数量</th>
					<th data-field="inStockDate" width="100" data-type="datetime">入库时间</th>
					<th data-field="inStockCount" width="100">入库数量</th>
					<th data-field="inStockNo" width="100" data-formatter="showAttach">入库单号及附件</th>
					<th data-field="totalInStockCount" width="100">累计入库数量</th>
					<th data-field="outStockDate" width="100" data-type="datetime">出库时间</th>
					<th data-field="outStockCount" width="100">出库数量</th>
					<th data-field="outStockNo" width="100" data-formatter="showAttachOut">出库单号及附件</th>
					<th data-field="totalOutStockCount" width="100">累计出库数量</th>
					<th data-field="revertCount" width="100">归还情况</th>
					<th data-field = "stockCount" width="100" data-formatter="showStockCount">库存量</th>
					<th data-field = "remark" width="100" data-type="textarea">备注</th>

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
    function showAttach(value, row, index) {
        if(!value){
            return '-';
        }
        return "<a href=\"javascript:webUtil.showAttach('"+row.inStockId+"','"+value+"');\">"+value+"</a>"
    }
    function showAttachOut(value, row, index) {
        if(!value){
            return '-';
        }
        return "<a href=\"javascript:webUtil.showAttach('"+row.outStockId+"','"+value+"');\">"+value+"</a>"
    }
    function showStockCount(value, row, index) {
        return Number(row.totalInStockCount - row.totalOutStockCount).toFixed(2);
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
        var height = top.getTopMainHeight()-50;
        var table_options = {height:height,striped:true,sortStable:false,showRefresh:false,selectModel:1
            ,cache:false,showToggle:false,search:false,queryParams:searchPrams,toolbar:false
            ,showColumns:false,idField:"id",mypagination:true,url:'ec/purchase/toolsstockledger/query'};
        tblMain = $('#tblMain').myDataTable(table_options);
    }
    function searchPrams(){
        var params = {};
        params.projectId = curSelOrg.id;
        params.materialName = $("input[name='materialName']").val();
        return webUtil.json2Str(params);
    }
    $(function(){
        var height = top.getTopMainHeight()-40;
        $(".mainContrainer").height(height);
        initOrgTree(height);
        initTable();
        $("#queryStockLedger").on('click',function(){
            tblMain.refreshData();
        });
    });

</script>
</html>