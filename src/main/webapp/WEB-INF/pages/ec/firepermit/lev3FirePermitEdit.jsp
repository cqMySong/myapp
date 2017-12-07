<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>三级动火许可</title>
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
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">申请编码</span> 
						<input class="require input-item" name="number" data-rule="notEmpty" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">单位名称</span>
						<input name="name" class="input-item form-control" data-rule="notEmpty"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">业务日期</span>
						<input type="text" name="bizDate" class="form-control input-item" data-rule="notEmpty" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class=" input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">业务状态</span>
						<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}" 
		                	class="form-control input-item require read">
		                </select>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">动火日期</span>
						<input type="text" name="bizDate" class="form-control input-item" data-rule="notEmpty" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">动火部位</span>
						<input name="firePart" class="input-item form-control" style="width: 100%;" data-rule="notEmpty"/>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">防火措施</span>
						<textarea name="remark" rows="5" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-3">
					<div class="input-group">
						<span class="input-group-addon lable">焊工</span> 
						<input class="require input-item" name="welder" />
					</div>
				</div>
				<div class="col-sm-3">
					<div class="input-group">
						<span class="input-group-addon lable">监护人</span> 
						<input class="require input-item" name="guarder" />
					</div>
				</div>
				<div class="col-sm-3">
					<div class="input-group">
						<span class="input-group-addon lable">申请动火人</span> 
						<input class="require input-item" name="proposer" />
					</div>
				</div>
				<div class="col-sm-3">
					<div class="input-group">
						<span class="input-group-addon lable">焊工班长</span> 
						<input class="require input-item" name="welderbz" />
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">批准人</span> 
						<input name="pzUser" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'用户信息',height:600,width:800,url:'base/userf7'}}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">技术科</span>
						<input name="jsUser" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'用户信息',height:600,width:800,url:'base/userf7'}}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">安全科</span>
						<input name="aqUser" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'用户信息',height:600,width:800,url:'base/userf7'}}">
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">创建人</span> 
						<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">最后修改人</span>
						<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">审核人</span>
						 <input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">创建日期</span> 
						<input name="createDate" class="input-item form-control read" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">最后修改日期</span>
						<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">审核日期</span>
						<input name="auditDate" type="text" class="form-control input-item read" data-opt="{type:'date'}"/>
					</div>
				</div>
			</div>
			</form>
		</div>
</body>
<%@include file="../../base/base_edit.jsp"%>
<script type="text/javascript">
	var editUI;
	
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
		editUI = $('#editPanel').editUI({
			title : "三级动火许可",billModel:2,
			baseUrl : "ec/firepermit/lev3firepermit",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		var height = top.getTopMainHeight();
		if($('#editPanel').height()<height+10)
			$('#editPanel').height(height);
	})
</script>
</html>