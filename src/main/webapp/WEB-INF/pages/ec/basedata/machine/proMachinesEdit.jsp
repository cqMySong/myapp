<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>现场施工机械一览表</title>
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
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">机械编码</span> 
						<input class="require input-item" name="number" data-rule="notEmpty" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">机械名称</span>
						<input name="name" class="input-item form-control require" data-rule="notEmpty"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">操作人员</span> 
						<input name="operator" class="input-item form-control" data-rule="notEmpty"/>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">维修人员</span> 
						<input name="maintenancer" class="input-item form-control" data-rule="notEmpty"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">验收情况</span>
						<textarea name="checkContent" style="height:60px;" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
var editUI;
function afterAction(_opt){
	if(_opt==OperateType.addnew){
		var uiCtx = getUICtx();
		if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
				&&!webUtil.isEmpty(uiCtx.tree)){
			$('input[name="project"]').myF7().setData(uiCtx.tree);
		}
	}
}
$(document).ready(function() {
	editUI = $('#editPanel').editUI({
		title : "现场施工机械一览表",
		baseUrl : "ec/machine/promachines",
		toolbar : "#table-toolbar",
		form : {
			el : "#editForm"
		}
	});
	editUI.onLoad();
})
</script>
</html>