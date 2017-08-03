<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>

<%
String projectId = "";
Object proIdObj = request.getAttribute("projectId");
if(proIdObj!=null) projectId = proIdObj.toString();
%>
<script type="text/javascript">
	var mutil = true;
	var proId = '<%=projectId%>';
</script>
<body>
	<div id="listPanel" class="panel ui-layout-center">
		<div id="table-toolbar">
			<div class="btn-group" id="seachGBtn"></div>
		</div>
		<table id="tblMain">
			<thead>
				<tr>
					<th data-field="proStructure" rowspan="2" data-type="f7" data-formatter="displayName">项目工程结构</th>
					<th data-field="proSub" rowspan="2"  data-type="f7">项目分部工程</th>
					<th data-field="proSubItem" rowspan="2" data-type="f7">项目分项结构</th>
					<th colspan="3">计划</th>
					<th data-field="content" rowspan="2"   data-type="textarea">工作内容</th>
					<th data-field="proQty" rowspan="2" >工程量</th>
				</tr>
				<tr>
					<th data-field="planBegDate" class="_myMerge" data-type="date">开始日期</th>
					<th data-field="planEndDate"  data-type="date">截止日期</th>
					<th data-field="planDays"  data-locked="true">持续天数</th>
				</tr>
			</thead>
		</table>
		
		<div id="selected">
			<div style="position: relative;margin:0px;line-height: 35px;height: 35px;vertical-align: middle;padding: 0px 5px;" id="selToolBar">
				<div class="bs-bar pull-left">
					<i class="glyphicon glyphicon-list"></i>&nbsp;已选清单</div>
				<div class="pull-right">
					<div class="btn-group">
						<a id="_addRow" class="btn btn-sm  btn-success"><span class="fa fa-plus-square"></span>&nbsp;选择</a>
						<a id="_removeRow" class="btn btn-sm  btn-success"><span class="fa fa-minus-square"></span>&nbsp;删除</a>
					</div>
				</div>
			</div>
			<table id="tblSelMain">
				<thead>
					<tr>
						<th data-field="proStructure" rowspan="2" data-type="f7" data-formatter="displayName">项目工程结构</th>
						<th data-field="proSub" rowspan="2"  data-type="f7">项目分部工程</th>
						<th data-field="proSubItem" rowspan="2" data-type="f7">项目分项结构</th>
						<th colspan="3">计划</th>
						<th data-field="content" rowspan="2"   data-type="textarea">工作内容</th>
						<th data-field="proQty" rowspan="2" >工程量</th>
					</tr>
					<tr>
						<th data-field="planBegDate" class="_myMerge" data-type="date">开始日期</th>
						<th data-field="planEndDate"  data-type="date">截止日期</th>
						<th data-field="planDays"  data-locked="true">持续天数</th>
					</tr>
				</thead>
			</table>
		</div> 
	</div>
	 
	 
	
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
var tblMain;
var tblSelMain;
var thisQueryParams = {};
var _uiCtx = {};
function search_Query(_opt,item){
	if(!webUtil.isEmpty(item)){
		var _sarchParams = {};
		if('_blank'!=item.key){
			_sarchParams.key = item.key;
			_sarchParams.value = item.value;
			_sarchParams.type = item.type;
		}
		thisQueryParams = _sarchParams;
		tblMain.refreshData();
	}else{
		webUtil.mesg('请选择对应的列!');
		//layui-layer-btn layui-layer-btn-
	}
}
function getMyQueryParams(){
	var params = thisQueryParams;
	if(!webUtil.isEmpty(_uiCtx)){
		params.uiCtx = _uiCtx;
	}
	return JSON.stringify(params);
}

function getData(){
	if(mutil&&!webUtil.isEmpty(tblSelMain)){
		return tblSelMain.getData();
	} else{
		var selRows = tblMain.getSelections();
		if(selRows&&selRows.length>0){
			return selRows[0];
		}
		return {};
	}
}

function addRow(){
	if(!webUtil.isEmpty(tblSelMain)
			&&!webUtil.isEmpty(tblMain)){
		var selRowsData = tblMain.getSelections();
		if(!webUtil.isEmpty(selRowsData)&&selRowsData.length>0){
			for(var i=0;i<selRowsData.length;i++){
				var rowData =  $.extend(true,{},selRowsData[i]);
				addToSelTable(rowData);
				
			}
		}else{
			webUtil.mesg("请先选中行!");
		}
	}
}

function addToSelTable(rowData){
	if(webUtil.isEmpty(tblSelMain)) return;
	var ok = true;
	var datas = tblSelMain.getData();
	if(!webUtil.isEmpty(datas)&&datas.length>0){
		for(var i=0;i<datas.length;i++){
			if(rowData.id==datas[i].id){
				ok = false;
				webUtil.mesg("不能重复选择数据!");
				return;
			}
		}
	}
	if(ok){
		tblSelMain.addRow(rowData);
	}
}

function removeRow(){
	if(!webUtil.isEmpty(tblSelMain)){
		var rIdx = tblSelMain.getSelectRowIndex();
		if(rIdx>=0){
			tblSelMain.removeRow(rIdx);
		}else{
			webUtil.mesg("请先在已选区选中行!");
		}
	}
}
function dbClick_toAddrow(row, $element, field){
	if(!webUtil.isEmpty(tblSelMain)
			&&!webUtil.isEmpty(tblMain)){
		addToSelTable(row);
	}
}
function dbClick_toRemoveRow(row, $element, field){
	if(!webUtil.isEmpty(tblSelMain)){
		tblSelMain.removeRow($element);
	}
}
var dataURL = 'ec/plan/projectplanitems/datas?projectId='+proId;
$(document).ready(function() {
	
	var sear_btn_gp = $('#seachGBtn').myBtnGroup();
	//sear_btn_gp.addSearch({items:queryCols,dataChange:search_Query});
	
	var myQueryTalbOpt = {height:400,striped:true,sortStable:true,showRefresh:false,clicToSelect:true
			,cache:false,pageSize:20,showToggle:true,showColumns:true,idField:"id",mypagination:true
			,toolbar:"#table-toolbar",url:dataURL,queryParams:getMyQueryParams,selectModel:1};
	if(mutil){
		myQueryTalbOpt.selectModel = 2;
		myQueryTalbOpt.onDblClickRow = dbClick_toAddrow;
	}
	
	tblMain =  $('#tblMain').myDataTable(myQueryTalbOpt);
	tblMain.refreshData();
	
	$('body').layout({applyDefaultStyles: true});
	
	if(mutil){
		var _defEntryTableOpt = {mypagination:false,height:200,sortStable:false};
		var thisTblOpt = $.extend(true,{}, _defEntryTableOpt,{updateRow2Body:false,cusTopToolBar:$('#selToolBar')});
		thisTblOpt.onDblClickRow = dbClick_toRemoveRow;
		tblSelMain = $('#tblSelMain').myDataTable(thisTblOpt);
		$('#selected').show();
		$('#_addRow').click(function(){
			addRow();
		});
		$('#_removeRow').click(function(){
			removeRow();
		});
		
	}else{
		$('#selected').hide();
	}
});
</script>
</html>