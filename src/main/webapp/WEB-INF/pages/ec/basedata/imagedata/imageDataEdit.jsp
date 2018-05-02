<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>影像资料</title>
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
						<input name="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">工程结构</span>
						<input name="proStructure" class="input-item form-control read"
							data-opt="{type:'f7',uiWin:{title:'项目工程结构',height:580,width:300,url:'ec/basedata/proStructureF7',uiParams:proStructure_data}}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">分部分项</span>
						<input name="proBaseWbs" class="input-item form-control read"
							   data-opt="{type:'f7',uiWin:{title:'分项工程',height:560,width:800,url:'ec/basedata/proBaseWbsF7',uiParams:proBaseWbsParam}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">检验批</span>
						<input name="proBatchTest" class="input-item form-control"
							   data-opt="{type:'f7',dataChange:proBatchTestChange,uiWin:{title:'项目检验批',height:580,width:800,url:'ec/basedata/proBatchTestF7',uiParams:proBatchTestParam}}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">资料类型</span>
						<select name="imageDataType" data-opt="{type:'select',selected:'IMAGE',url:'base/common/combox?enum=com.myapp.core.enums.ImageDataType'}"
								class="form-control input-item require">
						</select>
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
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">创建人</span>
						<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">创建日期</span>
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
		}
	}
	function proStructure_data(){
		var pro = {};
		pro.projectId = $('input[name="project"]').myF7().getValue();
		return pro;
	}
	function proBatchTestChange(oldVal,newVal){
		if(newVal){
            $('input[name="proBaseWbs"]').myF7().setData({id:newVal.proBaseWbs_id,name:newVal.proBaseWbs_displayName});
		}
	}
	function proBaseWbsParam() {
        var pro = {};
        pro.projectId = $('input[name="project"]').myF7().getValue();
        return pro;
    }
	function proBatchTestParam() {
        var pro = {};
        pro.projectId = $('input[name="project"]').myF7().getValue();
        pro.proBaseWbs = $('input[name="proBaseWbs"]').myF7().getValue();
        return pro;
    }
	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "工程影像资料",
			baseUrl : "ec/basedata/imagedata",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	});
</script>
</html>