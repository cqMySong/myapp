<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>三级安全教育花名册</title>
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
						<span class="input-group-addon lable">姓名</span> 
						<input class="require input-item" name="name">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">性别</span> 
						<select name="sex" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.Sex'}"  
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">年龄</span> 
						<input class="require input-item" name="age">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">工种</span> 
						<input name="workType" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">家庭住址</span> 
						<input name="homeAddress" class="input-item form-control">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">进公司日期</span> 
						<input name="companyDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">身份证号</span> 
						<input name="idCardNo" class="input-item form-control">
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
		baseUrl : "ec/safty/saftyedubook",
		toolbar : "#table-toolbar",
		form : {
			el : "#editForm"
		}
	});
	editUI.onLoad();
})
</script>
</html>