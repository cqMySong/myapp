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
<body class="panel" style="padding: 5px;padding-bottom: 0px;" >
	<div id="transactPanel" class="panel" >
			<div id="table-toolbar">
				<div class="btn-group">
					<button class="btn btn-success" type="button" id="subAudit">
						<span class="glyphicon glyphicon-file"></span>提交审批
					</button>
					<button class="btn btn-success" type="button" id="showBackLogPic">
						<span class="glyphicon glyphicon-file"></span>查看流程图
					</button>
				</div>
			</div>
		   <div class="row no-padding">
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
							<th data-field="taskName" data-width="180px">执行环节</th>
							<th data-field="assigneeName" data-width="80px">执行人</th>
							<th data-field="pass" data-width="50px" data-formatter="formatter_pass">审批结果</th>
							<th data-field="reason">审批意见</th>
							<th data-field="startTime" data-type="datetime" data-width="160px">开始时间</th>
							<th data-field="endTime" data-type="datetime" data-width="160px">结束时间</th>
						</tr>
					</thead>
				</table>
				<div class="row mt5">
					<div class="col-sm-12 col-lg-12">
						<div class="input-group">
							<span class="input-group-addon lable">审查事项</span>
							<textarea name="needAttention" class="input-item form-control" rows="2">${needAttention}</textarea>
						</div>
					</div>
				</div>
				<div class="row mt5" >
					<div class="col-sm-2 col-lg-2">
						<div class="input-group">
							<span class="input-group-addon lable" style="height: 52px;">同意</span>
							<input name="pass" class="input-item" value="1" data-opt="{type:'radio',height:52,checked:true}" type="radio"/>
						</div>
					</div>
					<div class="col-sm-2 col-lg-2">
						<div class="input-group">
							<span class="input-group-addon lable" style="height: 52px;">不同意</span>
							<input name="pass" class="input-item" value="0" data-opt="{type:'radio',height:52}" type="radio"/>
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
function formatter_pass(value, row, index){
	var txt = value;
	if(value=="1"){
		txt = '同意';
	}else if(value=='0'){
		txt = '不同意';
	}
	return txt;
}
$(document).ready(function() {
	var editUI = $('#transactPanel').editUI({title:"审核信息",baseUrl:"base/backlog",toolbar:"#table-toolbar",
			form:{el:"#transactForm"},hasDefToolbar:false,operate:OperateType.audit});
	editUI.onLoad();
    var height = top.getTopMainHeight()+100;
	$('#billInfo').height(height-390);
	$('#transactForm').css({"margin-top":"10px","height":"260px"});
	var auditDetailTable = $('#auditDetail').myDataTable({height:170,mypagination:false,striped:true,sortStable:false,
		showRefresh:false,selectModel:1,cache:false,showToggle:false,search:false});
	auditDetailTable.refreshData('base/backlogs/histoic/flow/${processInstanceId}');
	$('#showBackLogPic').on('click',function(){
		var url = app.root+"/base/backlogs/photo/${processDefinitionId}/${executionId}";
		webUtil.openWin({title:'流程进度',btns:null,operate:OperateType.audit,width:1100,
				height:460,url:url});
	});
	//提交审核
	$('#subAudit').on('click',function(){
		var thisEditUI = editUI;
		if(thisEditUI.actionBefore(OperateType.save)){
				var this_editData = thisEditUI.storeData();
				var _toData = {};
				_toData.editData = JSON.stringify(this_editData);
				if(operateParams&&!webUtil.isEmpty(operateParams)&&$.isFunction(operateParams)){
					operateParams(OperateType.save,_toData);
				}
				var _thisUrl = thisEditUI.options.baseUrl+"/audit";
				webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
				}});
		}
	});
});
</script>
</html>