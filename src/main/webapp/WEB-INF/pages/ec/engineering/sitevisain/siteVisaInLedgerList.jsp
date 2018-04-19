<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>现场签证台账(支出)</title>
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
				<span class="input-group-addon lable">签证开始时间</span>
				<input name="startDate"  autocomplete="off" type="text" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">签证结束时间</span>
				<input name="endDate"  autocomplete="off" type="text" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">签证单位</span>
				<input type="text" class="input-item form-control" name="visaUnit">
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
					<th data-field="fVisaDate" data-type="date">时间</th>
					<th data-field="fVisaUnit" >签证单位</th>
					<th data-field="fWorkPart" >工作部位</th>
					<th data-field="fJobContent" >工作内容</th>
					<th data-field="fChargingBasis"  data-formatter="chargingBasis">计费依据</th>
					<th data-field="fAmount" >金额(元)</th>
					<th data-field="outAttachs" >签证计费附件</th>
					<th data-field="fChargingBasisIn" data-formatter="chargingBasis">向第三方办理签证的依据</th>
					<th data-field="fVisaUnitIn" >第三方单位名称</th>
					<th data-field="fHandleType" data-formatter="handleType">是否办理</th>
					<th data-field="fAmountIn" >金额(元)</th>
					<th data-field="outAttachsIn" >附件</th>
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
    function typeOfWork(value, row, index){
        return TypeOfWork[value];
    }
    function chargingBasis(value, row, index) {
        return ChargingBasis[value];
    }
    function handleType(value, row, index) {
        return HandleType[value];
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
            ,showColumns:false,idField:"id",mypagination:true,url:'ec/engineering/sitevisainledegers/query'};
        tblMain = $('#tblMain').myDataTable(table_options);
    }
    function searchPrams(){
        var params = {};
        params.projectId = curSelOrg.id;
        params.startDate = $("input[name='startDate']").val();
        params.endDate = $("input[name='endDate']").val();
        params.visaUnit = $("input[name='visaUnit']").val();
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
    function changeBgColor(row,index){
        var color = "";
        var days = webUtil.betweenDateDays(row.fVisaDate,row.fcreateDate);
        if(days>2&&days<10){
            color=EarlyWarning.warning;
        }else if(days>=10){
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