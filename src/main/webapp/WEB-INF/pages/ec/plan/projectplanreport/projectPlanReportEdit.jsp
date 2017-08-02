<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目施工日志</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;">
		<div id="editPanel" class="myMainContent panel">
		<div id="table-toolbar"></div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">总计划编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">总计划名称</span>
						<input name="name" class="input-item form-control"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">业务日期</span>
						 <input type="text" name="bizDate" class="form-control input-item" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">业务状态</span>
						<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}" 
		                	class="form-control input-item require read">
		                </select>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">&nbsp;</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12 " style="border: 1px solid #ddd;">
					<table name="planReportItems" class="input-entry" data-opt="{type:'entry',height:430,defRowData:importData,toolbar:{title:'施工日志填报',beforeClick:btnBefClick}
							,tableOpt:{editDataChanged:planItems_dataChanged}}">
						<thead>
							<tr>
								<th data-field="proStructure" rowspan="2" data-type="f7" data-formatter="displayName" data-locked="true"
										data-editor="{uiWin:{title:'项目结构',height:600,width:300,url:'ec/basedata/proStructureF7',uiParams:getParams}}">项目工程结构</th>
								<th data-field="proSub" rowspan="2"  data-type="f7" 
										data-editor="{uiWin:{title:'项目分部工程',height:550,width:680,url:'ec/basedata/proSubF7',uiParams:getParams}}">项目分部工程</th>
								<th data-field="proSubItem" rowspan="2" data-type="f7"
										data-editor="{uiWin:{title:'项目分项工程',height:550,width:680,url:'ec/basedata/proSubItemF7',uiParams:getParams}}">项目分项结构</th>
								<th data-field="planContent" data-locked="true" rowspan="2" data-type="textarea">计划内容</th>
								
								<th colspan="3">工作日志</th>
								<th data-field="filler" rowspan="2" data-type="f7"
										data-editor="{uiWin:{title:'责任人',height:550,width:680,url:'base/userf7',uiParams:getParams}}">责任人</th>
								<th data-field="remark" rowspan="2" width="500" data-type="textarea">备注</th>
								<th data-field="planItemId" data-locked="true" rowspan="2">计划ID</th>
								
							</tr>
							<tr>
								<th data-field="fillDate" class="_myMerge" data-type="date">填报日期</th>
								<th data-field="progress" data-type="number">完成进度</th>
								<th data-field="finishContent" data-type="textarea">完成内容</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">创建人</span> 
						<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">最后修改人</span>
						<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">审核人</span>
						 <input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">创建日期</span> 
						<input name="createDate" class="input-item form-control read" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">最后修改日期</span>
						<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">审核日期</span>
						<input name="auditDate" type="text" class="form-control input-item read" data-opt="{type:'date'}"/>
					</div>
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
	function btnBefClick(btnName){
		var toGo = true;
		if('addRow'==btnName){
			//导入计划分录信息
			
		}
		return toGo;
	}
	
	function importData(){
		return [{remark:'1'},{remark:'2'},{remark:'3'}];
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
		if('proSubItem'==obj.field){
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
		pro.projectId = $('input[name="project"]').myF7().getValue();
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
				$('input[name="project"]').myF7().setData(uiCtx.tree);
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
			title : "项目施工日志",billModel:2,
			baseUrl : "ec/plan/projectplanreport",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		webUtil.initMainPanel('#editPanel');
		planItemsEntryObj = editUI.getEntryObj('planReportItems');
		if(!webUtil.isEmpty(planItemsEntryObj)){
			planItemsEntry = planItemsEntryObj.entry;
			var rightBtnGroup = planItemsEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
			rightBtnGroup.addBtn({entry:planItemsEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
		}
		
	})
</script>
</html>