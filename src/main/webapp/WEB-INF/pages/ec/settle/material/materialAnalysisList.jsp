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
	<div id="table-toolbar" style="height:40px;">
		<div class="btn-group">
			<button class="btn btn-success" type="button">
				<span class="fa fa-refresh"></span>&nbsp;刷新</button>
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
						<th data-field="content"width="200" >当期预算数量</th>
						<th data-field="purchaseCount"  width="60">当期入库数量</th>
						<th data-field="dutyers"  width="120">当期结算数量</th>
						<th data-field="fCalculationCount"  width="120">当期图算用量</th>
						<th data-field="fActualUseCount"  width="120">当期实际用量</th>
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
    function initOrgTree(){
        var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
            ,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
            ,callback:{onClick:treeClick}
        };
        var treeViewer = $('#left_container').myTreeViewer(null);
        treeViewer.init({theme:"panel-success",title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
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
            ,cache:false,showToggle:true,search:true,queryParams:searchPrams,toolbar:"#tblMain_toolbar"
            ,showColumns:true,idField:"id",mypagination:true,url:'ec/settle/materianalysis/query'};
        tblMain = $('#tblMain').myDataTable(table_options);
    }
    function searchPrams(){
        var params = {};
        params.treeId = curSelOrg.id;
        return webUtil.json2Str(params);
    }
    $(function(){
        var height = top.getTopMainHeight()-40;
        $(".mainContrainer").height(height);
        initOrgTree();
        initTable();
	});
    function queryData() {
        webUtil.ajaxData({url:'ec/settle/materianalysis/query',data:{},success:function(data) {
            tblMain.loadData(data.data);
        }});
    }
</script>
</html>