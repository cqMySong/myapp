<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目主要工作安排摘要</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel">
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工作分类</span> 
						<select name="workType" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.enums.ec.WorkType'}"  
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">工作内容</span> 
						<textarea name="content" style="height:40px;" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">开始时间</span> 
						<input class="input-item form-control require" name="beginDate" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">完成时间</span> 
						<input class="input-item form-control require" name="endDate" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">责任人</span> 
						<input name="dutyPerson" class="input-item form-control">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">是否交办</span> 
						<input name="isDelivery" class="input-item form-control" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">交办接收人</span> 
						<input name="deliveryPerson" class="input-item form-control">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">工作跟进</span> 
						<select name="workFollow" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.enums.ec.WorkFollow'}" 
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">是否完成</span> 
						<input name="isDone" class="input-item form-control" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span> 
						<input name="remark" class="input-item form-control">
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

$(document).ready(function() {
	var editUI = $('#editPanel').editUI({
		title : "项目主要工作安排摘要",
		baseUrl : "ec/plan/mainplan",
		toolbar : "#table-toolbar",
		form : {
			el : "#editForm"
		}
	});
	editUI.onLoad();
})
</script>
</html>