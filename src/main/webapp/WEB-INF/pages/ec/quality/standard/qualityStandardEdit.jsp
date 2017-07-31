<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>质量交底</title>
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
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:750,width:900,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">施工单位</span> 
						<input name="unit" class="require input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'施工单位',height:750,width:900,url:'ec/basedata/ecunitF7'}}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">分部分项名称</span>
						<input name="proSubItem" class="require input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'项目分项工程',height:750,width:900,url:'ec/basedata/proSubItemF7'}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">施工班组</span> 
						<input name="constructionClass" class="input-item form-control" >
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">交底部位</span> 
						<input name="techUser" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'人员',height:750,width:900,url:'base/userf7'}}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">计划完成日期</span> 
						<input name="finishDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">交底日期</span> 
						<input name="qualityDate" class="input-item form-control" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">主要参加人员</span> 
						<input name="participants" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">技术负责人</span> 
						<input name="techUser" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'人员',height:750,width:900,url:'base/userf7'}}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">交底人</span> 
						<input name="qualityUser" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'人员',height:750,width:900,url:'base/userf7'}}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">接收人</span> 
						<input name="accepter" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'人员',height:750,width:900,url:'base/userf7'}}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" class="input-item form-control" rows="2"></textarea>
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
				alert(uiCtx.tree.name);
				$('input[name="project"]').myF7().setData(uiCtx.tree);
			}
		}
	}

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "质量交底",
			baseUrl : "ec/quality/standard/qualityStandardEdit",
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