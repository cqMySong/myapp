<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目工程分部分项</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel">
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">工程结构</span>
						<input name="proStruct" class="input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程结构',height:550,width:700}}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">分部工程</span>
						<input name="proSub" class="input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'分部工程',height:550,width:700,url:'ec/basedata/proSubF7',uiParams:proSub_data},dataChange:proSub_dataChange}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">启用</span> 
						<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
	/**
	 * 一切操作前的接口函数
	 */
	function beforeAction(opt) {
		return true;
	}
	
	function afterAction(_opt){
		if(_opt==OperateType.addnew){
			var uiCtx = getUICtx();
			
		}
	}
	function proSub_data(){
		var pro = {};
		pro.projectId = $('input[name="project"]').myF7().getValue();
		pro.proStructId = $('input[name="proStruct"]').myF7().getValue();
		return pro;
	}
	
	function proSub_dataChange(oldData,newData){
		var proStruct = {};
		proStruct.id = newData.proStruct_id;
		proStruct.name = newData.proStruct_name;
		$('input[name="proStruct"]').myF7().setData(proStruct);
	}

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "项目工程分部分项",
			baseUrl : "ec/basedata/prosubitem",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		$('input[name="project"]').myF7().setEnabled(false);
	})
</script>
</html>