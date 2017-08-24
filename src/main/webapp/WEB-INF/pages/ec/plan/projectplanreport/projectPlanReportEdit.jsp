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
						<span class="input-group-addon lable">汇报编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-8">
					<div class="input-group">
						<span class="input-group-addon lable">汇报名称</span>
						<input name="name" class="input-item form-control"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">总进度计划编码</span> 
						<input name="planInfo" class="require input-item form-control read" 
							data-opt="{type:'f7'}" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">开始日期</span>
						<input name="begDate" class="form-control input-item" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">截止日期</span>
						<input name="endDate" class="form-control input-item" data-opt="{type:'date'}">
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
					<div class="input-group">
						<span class="input-group-addon lable">业务日期</span>
						 <input type="text" name="bizDate" class="form-control input-item" data-opt="{type:'date'}">
					</div>
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
					<table name="planReportItems" class="input-entry" data-opt="{type:'entry',height:430,toolbar:{title:'施工日志填报'}
							,tableOpt:{editDataChanged:planItems_dataChanged},addRow:tblAddRow}">
						<thead>
							<tr>
								<th data-field="proStructure" data-width="200" rowspan="2" data-type="f7" data-formatter="displayName" data-locked="true">项目工程结构</th>
								<th data-field="proSub" rowspan="2" data-width="100" data-type="f7" data-locked="true">项目分部工程</th>
								<th data-field="proSubItem" data-width="100" rowspan="2" data-type="f7" data-locked="true">项目分项结构</th>
								<th data-field="planContent" data-locked="true" rowspan="2" data-type="textarea">计划内容</th>
								<th colspan="2" data-width="210">工作日志</th>
								<th data-field="planItemId" data-visible="false" data-locked="true" rowspan="2">计划ID</th>
							</tr>
							<tr>
								<th data-field="begDate" class="_myMerge" data-type="date">开始日期</th>
								<th data-field="endDate" data-type="date">完成日期</th>
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
	function tblAddRow(table){
		
		var _win = {width:900,height:750,maxmin:false};
		_win.url = webUtil.toUrl('ec/plan/projectplanitems/show')+'?projectId='+$('input[name="project"]').myF7().getValue();
		_win.btns = ['确定','取消'];
		_win.title = '计划明细导入';
		_win.btnCallBack = function(index,layerIndex,layero){
			if(layero){
				var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
				var datas = iframe_win.getData();
				if(index==1&&datas&&datas.length>0){
					
					var hasDoRow = planItemsEntry.getData();
					var rmp = new Map(); 
					if(hasDoRow&&hasDoRow.length>0){
						for(var i=0;i<hasDoRow.length;i++){
							var sbobj = hasDoRow[i];
							if(sbobj&&sbobj.planItemId){
								rmp.put(sbobj.planItemId,sbobj.planItemId);
							}
						}
					}
					
					for(var i=0;i<datas.length;i++){
						var drow = datas[i];
						if(!rmp.containsKey(drow.id)){
							var newRow = {};
							newRow.proStructure = drow.proStructure;
							newRow.proSub = drow.proSub;
							newRow.proSubItem = drow.proSubItem;
							newRow.planContent = drow.content;
							newRow.planItemId = drow.id;
							planItemsEntry.addRow(newRow);
						}
					}
				}
			}
			return true;
		}
		webUtil.openWin(_win);
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
		//webUtil.initMainPanel('#editPanel');
		planItemsEntryObj = editUI.getEntryObj('planReportItems');
		if(!webUtil.isEmpty(planItemsEntryObj)){
			planItemsEntry = planItemsEntryObj.entry;
			var rightBtnGroup = planItemsEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
			rightBtnGroup.addBtn({entry:planItemsEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
		}
		var height = $(top.window).height()-110;
		$('#editPanel').height(height);
	})
</script>
</html>