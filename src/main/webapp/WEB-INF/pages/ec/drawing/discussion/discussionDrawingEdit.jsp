<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>图纸会审编辑</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;">
		<div id="editPanel" class="panel">
		<div id="table-toolbar"></div>
		<form id="editForm" class="row">
			<div class="col-lg-4 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">图纸名称</span>
					<input class="require input-item" name="name">
				</div>
			</div>
			<div class="col-sm-6 col-lg-4 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">图纸编号</span>
					<input name="number" class="require input-item form-control"/>
				</div>
			</div>
			<div class="col-lg-4 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">图纸所属</span>
					<input name="belongId" class="require input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
				</div>
			</div>
			<div class="col-lg-4 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable" style="height: 38px;">完成签章</span>
					<input name="completeSignature" class="input-item form-control"
						   data-opt="{type:'checkbox',height:38}" type="checkbox"/>
				</div>
			</div>
			<div class="col-lg-4 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">主持人</span>
					<input name="moderator" class="require input-item form-control" type="text"/>
				</div>
			</div>
			<div class="col-lg-4 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">会审时间</span>
				    <input type="text" name="conferenceDate" class="require form-control input-item" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-lg-8 col-sm-12 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">会审地点</span>
					<input name="conferencePlace" class="require input-item form-control" type="text"/>
				</div>
			</div>
			<div class="col-sm-12 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">参会人员</span>
					<input name="participants" class="require input-item form-control" type="text"/>
				</div>
			</div>
			<div class="col-sm-12 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">参会单位</span>
					<input name="participantUnits" class="require input-item form-control" type="text"/>
				</div>
			</div>
			<div class="col-lg-12 col-sm-12 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">会议主题</span>
					<textarea name="subject" style="height:40px;" class="require input-item form-control"></textarea>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">创建人</span>
					<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">创建日期</span>
					<input name="createDate" class="input-item form-control read" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">最后修改人</span>
					<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">最后修改日期</span>
					<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">审核人</span>
					 <input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 mb5">
				<div class="input-group">
					<span class="input-group-addon lable">审核日期</span>
					<input name="auditDate" type="text" class="form-control input-item read" data-opt="{type:'date'}"/>
				</div>
			</div>
			</form>
		</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
	var editUI;
	var planItemsEntry;
	var planItemsEntryObj;
	function proSubItem_dataChange(oldData,newData){
		var proStructure = {};
		proStructure.id = newData.proStruct_id;
		proStructure.name = newData.proStruct_name;
		proStructure.displayName = newData.proStruct_displayName;
		var proSub = {};
		proSub.id = newData.proSub_id;
		proSub.name = newData.proSub_name;
	}
	function planItems_dataChanged($cell,obj){
		/* var obj= {};
		obj.oldVal = oldVal;
		obj.value = val;
		obj.rowData = rowData;
		obj.field = field;
		obj.column = thisColumn;
		obj.rowIndex = rowIdx;
		obj.colIndex = colIdx; */
		if(webUtil.isEmpty(planItemsEntry)) return;
		if('planBegDate'==obj.field||"planEndDate"==obj.field){
			var btimes = obj.rowData['planBegDate'];
			var etimes = obj.rowData['planEndDate'];
			var days = webUtil.betweenDateDays(btimes,etimes);
			if(webUtil.isEmpty(days)) days = null;
			if(!webUtil.isEmpty(planItemsEntry)){
				planItemsEntry.setTableCellValue(obj.rowIndex,'planDays',days);
			}
		}else if('proSubItem'==obj.field){
			var newData = obj.rowData[obj.field];
			/* var proStructure = {};
			proStructure.id = newData.proStruct_id;
			proStructure.displayName = newData.proStruct_displayName; */
			var proSub = {};
			proSub.id = newData.proSub_id;
			proSub.name = newData.proSub_name;
			proSub.proStruct_id = newData.proStruct_id;
			proSub.proStruct_displayName = newData.proStruct_displayName;
			//planItemsEntry.setTableCellValue(obj.rowIndex,'proStructure',proStructure);
			planItemsEntry.setTableCellValue(obj.rowIndex,'proSub',proSub);
		}else if('proSub'==obj.field){
			var newData = obj.rowData[obj.field];
			var proStructure = {};
			proStructure.id = newData.proStruct_id;
			proStructure.displayName = newData.proStruct_displayName;
			planItemsEntry.setTableCellValue(obj.rowIndex,'proStructure',proStructure);
		}
	}
	
	function getParams(){
		var pro = {};
		pro.projectId = $('input[name="belongId"]').myF7().getValue();
		return pro;
	}
	/**
	 * 一切操作前的接口函数
	 */
	function beforeAction(opt) {
		return true;
	}
	
	
	function afterAction(_opt){
		if(_opt==OperateType.addnew){
			var uiCtx = getUICtx();
			if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
					&&!webUtil.isEmpty(uiCtx.tree)){
				$('input[name="belongId"]').myF7().setData(uiCtx.tree);
			}
		}
	}
	function btnCopyInsertRow(btn){
		var entry = btn.entry;
		if(!webUtil.isEmpty(entry)){
			var selRowsData = entry.getSelections();
			var rowIdx = entry.getSelectRowIndex();
			if(!webUtil.isEmpty(selRowsData)&&selRowsData.length>0
					&&rowIdx>=0){
				var rowData =  $.extend(true,{},selRowsData[0]);
				rowData.id = '';
				entry.insertRow(rowIdx+1,rowData);
			}else{
				webUtil.mesg("请先选中行");
			}
		}
	}
	$(document).ready(function() {
		editUI = $('#editPanel').editUI({
			title : "图纸会审",billModel:2,
			baseUrl : "ec/discussiondrawing",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	});
</script>
</html>