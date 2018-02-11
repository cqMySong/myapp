<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>周转材料、设备使用（租用）一览表</title>
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
				<span class="input-group-addon lable">出租开始时间</span>
				<input name="startDate"  autocomplete="off" type="text" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">出租结束时间</span>
				<input name="endDate"  autocomplete="off" type="text" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">出租单位</span>
				<input type="text" class="input-item form-control" name="leaseUnit">
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
					<th data-field="materialNumber" rowspan="2">编号</th>
					<th data-field="materialName" rowspan="2">材料、设备名称</th>
					<th data-field="materialType" rowspan="2" data-formatter="materialTypeFormatter">类型</th>
					<th data-field="materialUnit" rowspan="2">单位</th>
					<th data-field="leaseUnit" rowspan="2">出租单位</th>
					<th colspan="2">租用</th>
					<th colspan="2">归还</th>
					<th colspan="2">现场损耗</th>
				</tr>
				<tr>
					<th data-field="leaseDate" data-type="date">时间</th>
					<th data-field="leaseCount">数量</th>
					<th data-field="backDate" data-type="date">时间</th>
					<th data-field="backCount">数量</th>
					<th data-field="diffCount">数量</th>
					<th data-field="diffRatio">比值（%）</th>
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
    function materialTypeFormatter(value, row, index){
        var txt = value;
        if(value=='STRUCTURE'){
            txt = '材料';
        }else if(value=='APPARATUS'){
            txt = '机械';
        }
        return txt;
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
            ,showColumns:false,idField:"id",mypagination:true,url:'ec/stock/leaseledger/query'};
        tblMain = $('#tblMain').myDataTable(table_options);
    }
    function searchPrams(){
        var params = {};
        params.projectId = curSelOrg.id;
        params.startDate = $("input[name='startDate']").val();
        params.endDate = $("input[name='endDate']").val();
        params.leaseUnit = $("input[name='leaseUnit']").val();
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
    function changeBgColor(row, index) {
        var color = "";
        if((row.materialType=='STRUCTURE'&&row.diffRatio>2)||(row.materialType=='APPARATUS'&&row.diffRatio>0)){
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