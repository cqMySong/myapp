<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>施工现场检查项目</title>
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
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">项目编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">检查类别</span>
						<select id="wct" name="workCheckType" class="require input-item form-control" 
							data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.enums.ec.WorkCheckType'}" >
						</select>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">检查项目</span>
						<input name="name" class="require input-item form-control">
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
						<span class="input-group-addon lable">检查要求</span>
						<textarea name="checkRequire" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" class="input-item form-control" rows="1"></textarea>
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
				$('#wct').myComponet(DataType.select,{method:'setdata',opt:uiCtx.tree.id});
			}
		}
	}

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "施工现场检查项目",
			baseUrl : "ec/basedata/workcheckitem",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>