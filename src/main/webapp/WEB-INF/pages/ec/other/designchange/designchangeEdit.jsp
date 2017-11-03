<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>设计变更(洽商)</title>
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
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编号</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">变更类型</span> 
						<select name="changeType" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.enums.ec.ChangeType'}"  
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">变更部位</span> 
						<input class="require input-item" name="changePlace">
					</div>
				</div>
				
			</div>
			<div class="row mt10">
				<div class="col-sm-12 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">变更内容</span> 
						<input name="changeContent" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">变更时间</span> 
						<input name="changeDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">交底情况</span> 
						<select name="disclosureType" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.enums.ec.DisclosureType'}" 
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
			</div>
			<div class="row">
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
		title : "设计变更(洽商)",
		baseUrl : "ec/other/designchange",
		toolbar : "#table-toolbar",
		form : {
			el : "#editForm"
		}
	});
	editUI.onLoad();
})
</script>
</html>