<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>质量样板工作要点</title>
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
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="input-item form-control require">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">分部</span>
						<input name="branchBaseWbs" class="input-item form-control"
							   data-opt="{type:'f7',uiWin:{title:'分部',height:560,width:800,url:'ec/basedata/proBaseWbsF7'}}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">分项</span>
						<input name="subentry" class="input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'分项',height:560,width:800,url:'ec/basedata/proBaseWbsF7'}}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工作岗位</span>
						<input name="position" class="require input-item form-control"
							   data-opt="{type:'f7',uiWin:{title:'工作岗位',height:600,width:800,url:'base/positionf7'}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">启用</span>
						<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">工作要求<br/><br/>(<span style="color: red">回车区分要点</span>)</span>
						<textarea name="jobRequirement" class="input-item form-control" rows="10"></textarea>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" class="input-item form-control" rows="1"></textarea>
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
			title : "质量样板工作要点",
			baseUrl : "ec/basedata/qualitytemplate",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>