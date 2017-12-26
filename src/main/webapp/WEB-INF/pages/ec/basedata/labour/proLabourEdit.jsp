<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目劳务人员备案</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;">
		<div id="editPanel" class="myMainContent panel">
		<div id="table-toolbar"></div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工号</span> 
						<input class="require input-item" name="number" data-rule="notEmpty" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">姓名</span>
						<input name="name" class="input-item form-control require" data-rule="notEmpty"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工种</span> 
						<input name="workType" class="require input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'施工工种查询',height:500,width:750,url:'ec/basedata/worktypeF7'}}" />
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
						<span class="input-group-addon lable">性别</span> 
						<select name="sex" data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.core.enums.Sex'}" 
	                		class="form-control input-item require">
	                	</select>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">年龄</span> 
						<input name="age" class="require input-item form-control" data-opt="{type:'number',precision:0}"
						 data-rule="['notEmpty','number',{name:'range',params:[16,60]}]"/>
						
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">加入时间</span> 
						<input name="joinDate" class="require form-control input-item" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">身份证号</span> 
						<input name="idCard" class=" require input-item form-control" data-rule="['notEmpty','idCard']"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">开户银行</span> 
						<input name="bank" class="form-control input-item" >
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">银行账号</span> 
						<input name="bankNo" class="input-item form-control"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">地址</span>
						<input name="addr" class="input-item form-control" style="width:100%;"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" style="height:75px;" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
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
		title : "项目劳务人员备案",
		baseUrl : "ec/labour/prolabour",
		toolbar : "#table-toolbar",
		form : {
			el : "#editForm"
		}
	});
	editUI.onLoad();
})
</script>
</html>