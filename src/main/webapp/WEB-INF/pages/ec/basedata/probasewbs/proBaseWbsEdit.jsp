<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>分部分项工程标准</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel" >
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程编码</span> 
						<input class="require input-item" name="number" type="text">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">工程名称</span> 
						<input name="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">上级工程</span> 
						<input name="parent" class="input-item form-control read" data-opt="{type:'f7'}" />
						
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">结构类型</span> 
						<select name="wbsType" data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.enums.ec.ProWbsType'}" 
		                	class="form-control input-item require"></select>
					</div>
				</div>
			</div>
			<div class="row mt10">
               	<div class="col-sm-6">
                  <div class="input-group">
	              	 <span class="input-group-addon lable">启用</span>
	                 <input name="enabled" class="input-item" data-opt="{type:'checkbox'}" type="checkbox"/>
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
						<span class="input-group-addon lable">备注信息</span>
						<textarea name="remark" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
function beforeAction(opt){
	return true;
}
function afterAction(_opt){
	if(_opt==OperateType.addnew){
		var uiCtx = getUICtx();
		if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
				&&!webUtil.isEmpty(uiCtx.tree)){
			var parent = uiCtx.tree;
			$('input[name="parent"]').myF7().setData(parent);
			$('input[name="number"]').val(parent.number+'.');
		}
	}
}
$(document).ready(function() {
	var editUI = $('#editPanel').editUI({title:"分部分项工程标准",baseUrl:"ec/basedata/probasewbs",toolbar:"#table-toolbar",form:{el:"#editForm"}});
	editUI.onLoad();
})
</script>
</html>