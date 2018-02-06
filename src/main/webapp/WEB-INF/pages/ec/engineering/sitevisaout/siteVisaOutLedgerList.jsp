<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>现场签证(支出台帐)</title>
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
					<th data-field="fVisaDate" data-type="date" rowspan="2">时间</th>
					<th data-field="fVisaUnit" rowspan="2">签证单位</th>
					<th data-field="fWorkPart" rowspan="2">工作部位</th>
					<th data-field="fJobContent" rowspan="2">工作内容</th>
					<th data-field="fAmount" rowspan="2">金额(元)</th>
					<th colspan="8">按实发生费用</th>
					<th data-field="fChargingBasis" rowspan="2" data-formatter="chargingBasis">计费依据</th>
					<th data-field="fcreateDate" data-type="datetime" rowspan="2">录入时间</th>
					<th data-field="fChargingContent" rowspan="2">备注</th>
				</tr>
				<tr>
					<th data-field="fTypeOfWork" data-formatter="typeOfWork">工种</th>
					<th data-field="fWorkStartTime" data-type="datetime">开始时间</th>
					<th data-field="fWorkEndTime" data-type="datetime">结束时间</th>
					<th data-field="fMechanicalName">机械名称/型号</th>
					<th data-field="fMechanicalStartTime" data-type="datetime">开始时间</th>
					<th data-field="fMechanicalEndTime" data-type="datetime">结束时间</th>
					<th data-field="fMaterialName">材料名称</th>
					<th data-field="fUseCount">数量</th>
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
        var txt = value;
        if(value=='GENERAL'){
            txt = '普工';
        }else if(value=='MECHANIC'){
            txt = '技工';
        }
        return txt;
    }
    function chargingBasis(value, row, index) {
        var txt = value;
        if(value=='INCREASE_CONTRACT'){
            txt = '合同外增加';
        }else if(value=='EMERGENCY_INSPECTION'){
            txt = '应急检查';
        }else if(value=='OWNER_ORDER'){
            txt = '业主指令';
        }else if(value=='CONTRACT_STIPULATION'){
            txt = '合同约定';
        }else if(value=='INCREASE_QUANTITY'){
            txt = '工程量增加';
        }else if(value=='OTHER'){
            txt = '其他';
        }
        return txt;
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
            ,cache:false,showToggle:false,search:false,queryParams:searchPrams,toolbar:false,rowStyle:changeBgColor
            ,showColumns:false,idField:"id",mypagination:true,url:'ec/engineering/sitevisaoutledegers/query'};
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
        initOrgTree();
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