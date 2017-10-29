<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${title }</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px; overflow: hidden;" class="panel">
	<div id="editPanel" class="panel" >
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div style="display: none;">
				<input name="code" class="input-item"/>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number" type="text">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span> 
						<input name="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">上级分类</span> 
						<input name="parent" class="input-item form-control read" data-opt="{type:'f7'}" />
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
						<span class="input-group-addon lable">备注信息</span>
						<textarea name="remark" class="input-item " rows="2"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../base/base_edit.jsp"%>
<script type="text/javascript">
var title = '${title}';
var code = '${code}';
function beforeAction(opt){
	return true;
}
function afterAction(_opt){
	if(_opt==OperateType.addnew){
		var uiCtx = getUICtx();
		if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
				&&!webUtil.isEmpty(uiCtx.tree)){
			$('input[name="parent"]').myF7().setData(uiCtx.tree);
		}
		$('input[name="code"]').val(code);
	}
}

$(document).ready(function() {
	var editUI = $('#editPanel').editUI({title:title,baseUrl:"base/datagroup",toolbar:"#table-toolbar",form:{el:"#editForm"}});
	editUI.onLoad();
})
</script>
</html>