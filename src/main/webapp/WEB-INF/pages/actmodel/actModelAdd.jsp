<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel" >
		<div id="table-toolbar">
			<div class="btn-group">
				<button class="btn btn-success" id="saveActModel" type="button">
					<span class="glyphicon glyphicon-file"></span>保存
				</button>
			</div>
		</div>
		<form id="editForm">
			  <div class="row">
				  <div class="col-sm-6 mb15">
					  <div class="input-group">
						  <span class="input-group-addon lable">模型名称</span>
						  <input name="name" class="input-item form-control">
					  </div>
				  </div>
				<div class="col-sm-6">
				  <div class="input-group">
					<span class="input-group-addon lable">模型编号</span>
					<input class="require input-item" name="number">
				  </div>
				</div>
			  </div>
			   <div class="row">
				<div class="col-sm-6 col-sm-6 mb15">
				  <div class="input-group">
					<span class="input-group-addon lable">流程分类</span>
					  <select name="category" data-opt="{type:'select',url:'subsystem/combox/BIZBILL',
					  key:'fentityObjectType',val:'fentityName'}"  class="form-control input-item require">
					  </select>
				  </div>
				</div>
			   </div>
			  <div class="row mt10" >
				<div class="col-sm-12">
				  <div class="input-group">
					<span class="input-group-addon lable">模型描述</span>
					<textarea name="description" class="input-item form-control" rows="2"></textarea>
				  </div>
				</div>
			  </div>
		  </form>
	</div>
</body>
<%@include file="../base/base_edit.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
function beforeAction(opt){
	return true;
}

$(document).ready(function() {
	var editUI = $('#editPanel').editUI({title:"流程信息",baseUrl:"base/actmodel",toolbar:"#table-toolbar",form:{el:"#editForm"},hasDefToolbar:false});
	editUI.onLoad();
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	$('#saveActModel').on('click',function(){
			var thisEditUI = editUI;
			if(thisEditUI.editForm.verifyInputRequire()
					&&thisEditUI.actionBefore(OperateType.save)){
					var this_editData = thisEditUI.storeData();
					var _toData = {};
					_toData.editData = JSON.stringify(this_editData);
					if(operateParams&&!webUtil.isEmpty(operateParams)&&$.isFunction(operateParams)){
						operateParams(OperateType.save,_toData);
					}
					var _thisUrl = thisEditUI.options.baseUrl+"/save";
					webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
							var _editData = data.data;
							window.location.href=app.root+"/act/process-editor/modeler.jsp?modelId="+_editData.id;
							parent.layer.full(index);
					}});
			}
	});
})
</script>
</html>