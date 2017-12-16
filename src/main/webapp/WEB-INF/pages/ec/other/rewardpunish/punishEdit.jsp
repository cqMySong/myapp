<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>惩罚通知单</title>
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
						<span class="input-group-addon lable">受惩罚单位</span> 
						<input class="require input-item" name="orgUnit">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">奖励类别</span>
						<input name="rewardPunishTypeInfo" class=" input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'奖励类别',height:400,width:500,url:'ec/other/punishtypeF7'}}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">惩罚日期</span>
						<input class="input-item form-control require" name="grandDate" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">检查单位</span>
						<input class="input-item require" name="grantOrgUnit">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">截止日期</span>
						<input class="input-item form-control require" name="expirationDate" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">惩罚事项</span> 
						<textarea name="item" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">惩罚办法</span>
						<textarea name="measures" class="input-item form-control" rows="2"></textarea>
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
			title : "惩罚通知单",
			baseUrl : "ec/other/punish",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>