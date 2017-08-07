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
					<input name="type" class="input-item" type="hidden"/>
					<input name="belongId" class="require input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:400,url:'ec/discussiondrawing'}}" />
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
	function getParams(){
		var pro = {};
		pro.belongId = $('input[name="belongId"]').myF7().getValue();
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
				$('input[name="type"]').val(uiCtx.tree.type);
			}
		}else{
			var uiCtx = getUICtx();
			console.log(uiCtx);
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