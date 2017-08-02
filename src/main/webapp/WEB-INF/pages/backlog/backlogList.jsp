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
<body style="padding: 5px;margin: 0px;" >
	<div id="listPanel" class="panel" style="padding:2px;">
		<div id="table-toolbar">
			<div class="btn-group">
				<button class="btn btn-success" type="button" id="transact">
					<span class="fa fa-file-o"></span>&nbsp;办理
				</button>
				<button class="btn btn-success" type="button" id="opinion">
					<span class="fa fa-edit"></span>&nbsp;查看意见
				</button>
				<button class="btn btn-success" type="button" id="schedule">
					<span class="fa fa-edit"></span>&nbsp;流程进度
				</button>
			</div>
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="title">标题</th>
					<th data-field="taskName">当前环节</th>
					<th data-field="name" >业务名称</th>
					<th data-field="createDate" data-type="datetime">创建时间</th>
					<th data-field="status" >状态</th>
				</tr>
			</thead>
		</table>
	</div>
</body>

<%@include file="../base/base_list.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
var thisListUI ;
var thisBaseUrl = 'base/backlogs';

$(document).ready(function() {
	thisListUI = $('#listPanel').listUI({tableEl:'#tblMain',height:680,baseUrl:thisBaseUrl,
			hasDefToolbar:false,toolbar:"#table-toolbar"});
	thisListUI.onLoad();
	//办理流程
	$('#transact').on('click',function(){
		var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
			var backLogData = _selRows[0];
			var url = app.root+"/"+thisBaseUrl+"/transact/"+backLogData.id+"/"+backLogData.taskDefinitionKey+
					"/"+backLogData.processInstanceId+"/"+backLogData.processDefinitionId+"/"+backLogData.status;
			webUtil.openWin({title:'办理流程',btns:null,operate:OperateType.audit,width:(window.outerWidth-20),height:(window.outerHeight-100),url:url});
		}else{
			webUtil.mesg('请先选中对应的数据行，方可进行办理操作!');
		}
	});
	//查看流程进度
	$('#schedule').on('click',function(){
		var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
			var backLogData = _selRows[0];
			var url = app.root+"/"+thisBaseUrl+"/photo/"+backLogData.processDefinitionId+"/"+backLogData.executionId;
			webUtil.openWin({title:'流程进度',btns:null,operate:OperateType.audit,width:(window.outerWidth-20),height:(window.outerHeight-100),url:url});
		}else{
			webUtil.mesg('请先选中对应的数据行，方可进行查看!');
		}
	});
});
</script>
</html>