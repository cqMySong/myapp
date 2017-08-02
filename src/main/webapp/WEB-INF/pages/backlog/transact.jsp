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
	<div id="transactPanel" class="panel" >
			<div id="table-toolbar">
				<div class="btn-group">
					<button class="btn btn-success" type="button" id="subAudit">
						<span class="glyphicon glyphicon-file"></span>提交审批
					</button>
					<button class="btn btn-success" type="button" id="showBackLogPic">
						<span class="glyphicon glyphicon-file"></span>查看流程图
					</button>
					<button class="btn btn-success" type="button">
						<span class="glyphicon glyphicon-file"></span>查看附件
					</button>
				</div>
			</div>
		   <div class="row">
			   <iframe src="${appRoot}/${formKey}?id=${businessKey}" id="billInfo"  class="col-lg-12 col-sm-12" style="border: 0px;"></iframe>
		   </div>
            <form id="transactForm">
				<input type="hidden" class="input-item" name="taskId" id="taskId" value="${taskId}"/>
				<input type="hidden" class="input-item" name="taskDefinitionKey"  id="taskDefinitionKey" value="${taskDefinitionKey}"/>
				<input type="hidden" class="input-item" name="processInstanceId" id="processInstanceId" value="${processInstanceId}"/>
				<input type="hidden" class="input-item" name="processDefinitionId" id="processDefinitionId" value="${processDefinitionId}"/>
				<input type="hidden" class="input-item" name="status" id="status" value="${status}"/>
				<table id="auditDetail">
					<thead>
						<tr>
							<th data-field="name" data-width="120px">执行环节</th>
							<th data-field="key" data-width="50px">审批结果</th>
							<th data-field="version">审批意见</th>
							<th data-field="createTime" data-type="datetime" data-width="120px">开始时间</th>
							<th data-field="lastUpdateTime" data-type="datetime" data-width="120px">结束时间</th>
						</tr>
					</thead>
				</table>
				<div class="row" >
					<div class="col-sm-2 col-lg-2">
						<div class="input-group">
							<span class="input-group-addon lable" style="height: 52px;">同意</span>
							<input name="pass" class="input-item" value="1" data-opt="{type:'radio',height:52}" type="radio"/>
						</div>
					</div>
					<div class="col-sm-2 col-lg-2">
						<div class="input-group">
							<span class="input-group-addon lable" style="height: 52px;">不同意</span>
							<input name="pass" class="input-item" value="2" data-opt="{type:'radio',height:52}"/>
						</div>
					</div>
					<div class="col-sm-8 col-lg-8">
						<div class="input-group">
							<span class="input-group-addon lable">审核意见</span>
							<textarea name="reason" class="input-item form-control" rows="2"></textarea>
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
	var editUI = $('#editPanel').editUI({title:"审核信息",baseUrl:"base/backlog",toolbar:"#table-toolbar",
			form:{el:"#transactForm"},hasDefToolbar:false,operate:OperateType.audit});
	editUI.onLoad();
	$('#billInfo').height(window.outerHeight-500);
	$('#transactForm').height("260");
	$('#auditDetail').myDataTable({height:200});
	$('#showBackLogPic').on('click',function(){

	});
	//提交审核
	$('#subAudit').on('click',function(){
		var thisEditUI = editUI;
		if(thisEditUI.editForm.verifyInputRequire()
				&&thisEditUI.actionBefore(OperateType.save)){
				var this_editData = thisEditUI.storeData();
				var _toData = {};
				_toData.editData = JSON.stringify(this_editData);
				if(operateParams&&!webUtil.isEmpty(operateParams)&&$.isFunction(operateParams)){
					operateParams(OperateType.save,_toData);
				}
				console.log(_toData);
				var _thisUrl = thisEditUI.options.baseUrl+"/audit";
				webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){

				}});
		}
	});
});
</script>
</html>