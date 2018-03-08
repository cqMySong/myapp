<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>基础资料</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;overflow: hidden;" class="panel">
	<div id="editPanel" >
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">分组</span> 
						<input class="form-control input-item" name="group"
						 data-opt="{type:'f7',displayName:'displayName',uiWin:{title:'职责分组',height:570,width:300,url:'base/jobDutyGroupQuery'}}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="require input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">快捷菜单</span> 
						 <input name="shortCutMenu" class="input-item form-control require" 
							data-opt="{type:'f7',displayName:'displayName',uiWin:{title:'权限查询',height:570,width:800,url:'base/permissionf7',uiParams:getShortCutMenuParams}}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">启用</span> 
						<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">职责描述</span>
						<textarea name="remark" class="input-item form-control" rows="3"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../base/base_edit.jsp"%>
<script type="text/javascript">
	function getShortCutMenuParams(){
		return {type:'PAGE',leaf:'T'};
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
				$('input[name="group"]').myF7().setData(uiCtx.tree);
				console.log(uiCtx.tree);
			}
		}
	}

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "工作职责",
			baseUrl : "base/jobduty",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>