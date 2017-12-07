<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>资料项目录</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;overflow:hidden;" class="panel">
	<div id="editPanel" class="panel">
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div style="display: none;">
				<input class="input-item" name="skillType" value="SM">
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="require input-item form-control">
					</div>
				</div>
			</div>
			<div class="row mt20">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">类别</span> 
						<select name="resourceType" id="rtype" data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.enums.ec.ResourceType'}" 
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">启用</span> 
						<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
			</div>
			<div class="row mt20">
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
			if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
					&&!webUtil.isEmpty(uiCtx.tree)){
				$('select[name="resourceType"]').myComponet('select',{method:'setdata',opt:uiCtx.tree.id});
			}
		}
	}

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "资料项目录",
			baseUrl : "ec/basedata/resourceitem",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>