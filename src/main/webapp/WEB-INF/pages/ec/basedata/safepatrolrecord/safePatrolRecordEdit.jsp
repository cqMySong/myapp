<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>安保巡查记录</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;" class="panel">
	<div id="editPanel" class="panel">
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">巡查时间</span> 
						<input name="patrolDate" class="input-item form-control require" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">范围及线路</span> 
						<input name="range" class="input-item form-control require">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">注意事项</span> 
						<textarea name="attention" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">异常</span>
						<input name="hasQuestion" class="input-item" data-opt="{type:'checkbox'}" type="checkbox"/> 
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">处理办法</span>
						<select name="doWay" data-opt="{type:'select',opt:{multiple:true},url:'base/common/combox?enum=com.myapp.enums.ec.DoWayType'}" 
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">制单人</span> 
						<input name="creator" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">制单时间</span> 
						<input name="createDate" class="input-item form-control read" data-opt="{type:'datetime'}">
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
			title : "安保巡查记录",
			baseUrl : "ec/basedata/safepatrolrecord",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>