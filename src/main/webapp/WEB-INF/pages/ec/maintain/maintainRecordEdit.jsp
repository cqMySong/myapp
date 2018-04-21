<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>维修记录表</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;" class="panel">
	<div id="editPanel" class="myMainContent">
	<div id="table-toolbar"></div>
	<form id="editForm">
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">维修单号</span> 
					<input class="require input-item" name="number" data-rule="notEmpty" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">维修名称</span>
					<input name="name" class="input-item form-control" data-rule="notEmpty"/>
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
					<span class="input-group-addon lable">维修日期</span>
					 <input type="text" name="bizDate" class="form-control input-item" data-rule="notEmpty" data-opt="{type:'date'}">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">维修员</span> 
				<input name="maintainer" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'用户信息',height:600,width:800,url:'base/userf7'}}">
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon lable">维修内容</span>
					<textarea name="content" style="height:200px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon lable">备注</span>
					<input name="remark" class="input-item form-control" style="width:100%;"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">创建人</span> 
					<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">创建日期</span> 
					<input name="createDate" class="input-item form-control read" data-opt="{type:'datetime'}">
				</div>
			</div>
		</div>
	</form>
  </div>
</body>
<%@include file="../../base/base_edit.jsp"%>
<script type="text/javascript">
	var editUI;
	function beforeAction(opt) {
		return true;
	}
	
	function btnImpData(){
		
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
	
	$(document).ready(function() {
		editUI = $('#editPanel').editUI({
			title : "施工现场安全用电检查表",
			baseUrl : "ec/maintain/maintainrecord",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>