<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>整改通知单</title>
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
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input class="require input-item" name="name">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">受检单位</span>
						<input class="require input-item" name="orgUnit">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">受检人</span>
						<input class="require input-item" name="orgUnitPerson">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">检查单位</span>
						<input name="checkOrgUnit" class="input-item form-control">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">检查人</span>
						<input name="checkOrgPerson" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">检查日期</span>
						<input name="checkDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">所属工程</span>
						<input name="project" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">类别</span>
						<input class="require input-item" name="type">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">截至时间</span>
						<input name="endDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">存在的问题</span>
						<textarea name="problem" class="input-item form-control" rows="4"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">整改要求</span>
						<textarea name="requires" class="input-item form-control" rows="4"></textarea>
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
    function afterAction(_opt){
        if(_opt==OperateType.addnew){
            var uiCtx = getUICtx();
            if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
                &&!webUtil.isEmpty(uiCtx.tree)){
                $('input[name="project"]').myF7().setData(uiCtx.tree);
            }
        }
    }
	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "整改通知单",
			baseUrl : "ec/other/rectifyNotice",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>