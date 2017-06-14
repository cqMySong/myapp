<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>基础组织信息</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel" >
		<div id="table-toolbar">
			<div class="btn-group">
				<button class="btn btn-success" type="button">
					<span class="glyphicon glyphicon-file"></span>其他
				</button>
			</div>
		</div>
		<form id="editForm">
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
						<input name="name" class="input-item require">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">上级</span> 
						<input name="parent" class="require input-item form-control" 
							data-opt="{type:'f7',dataChange:parent_dataChange,uiWin:{title:'组织查询',height:550,width:800,url:'base/orgf7',params:'key=124&val=1344'}}" />
						
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
		                <span class="input-group-addon lable">启用</span>
		                <input name="enabled" class="input-item require" data-opt="{type:'checkbox'}" type="checkbox"/>
		              </div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">地址</span> 
							<input name="url" class="input-item require">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
		                <span class="input-group-addon lable">类型</span>
		                <select name="type" class="input-item require form-control" 
		                	data-opt="{type:'select',selected:'FUNCTION',url:'base/common/combox?enum=com.myapp.core.enums.PermissionTypeEnum'}">
		                	</select>
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
<%@include file="../base/base_edit.jsp"%>
<script type="text/javascript">
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
	}
}
function parent_dataChange(oldData,newData){
	//alert('触发了值改变事件');
}

function isAdmin_click(data){
	webUtil.mesg(data);
}
$(document).ready(function() {
	var editUI = $('#editPanel').editUI({title:"权限信息",baseUrl:"base/permission",toolbar:"#table-toolbar",form:{el:"#editForm"}});
	editUI.onLoad();
	
	$('#parent1').myF7().setEnabled(false);
})
</script>
</html>