<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目施工技术交底</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;" class="panel">
		<div id="editPanel" class="myMainContent ">
		<div id="table-toolbar"></div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">技术分类</span> 
						<input name="skillClass" class="require input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'施工技术分类',height:600,width:800,url:'ec/basedata/skillclassf7',uiParams:{type:'QM'}}}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
						
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">交底编码</span> 
						<input class="require input-item" name="number" data-rule="notEmpty" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">交底名称</span>
						<input name="name" class="input-item form-control" data-rule="notEmpty"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">技术类别</span> 
						<select name="skillType" data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.enums.ec.SkillType'}" class="form-control input-item require read">
		                </select>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">交底人</span> 
						<input name="disclosurer" class="require input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'交底人',height:550,width:800,url:'base/userf7'}}" />
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../base/base_edit.jsp"%>
<script type="text/javascript">
	var editUI;
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
		editUI = $('#editPanel').editUI({
			title : "项目施工技术交底",
			baseUrl : "ec/skilldisclosure/proqmskill",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>