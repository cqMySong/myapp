<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>施工方案编写规划</title>
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
						<input name="number" class="require input-item form-control"/>
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="require input-item form-control"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">方案类别</span>
						<input name="schemeType" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'方案类别',height:700,width:900,url:'ec/basedata/schemeTypeF7'}}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">编制人</span> 
						<input name="compiler" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'人员',height:750,width:900,url:'base/userf7'}}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编制日期</span> 
						<input name="compileDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">最迟完成日期</span> 
						<input name="lastFinishDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span>
						<input name="project" class="require input-item form-control"
							   data-opt="{type:'f7',uiWin:{title:'工程项目',height:750,width:900,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">状态</span> 
						<select name="schemeState" data-opt="{type:'select',selected:'COMPANY',url:'base/common/combox?enum=com.myapp.core.enums.SchemeState'}"
		                	class="form-control input-item require">
		                </select>
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
			title : "施工方案编写规划",
			baseUrl : "ec/basedata/schemeedit",
			toolbar : "#table-toolbar",
			billModel: 2,
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>