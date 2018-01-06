<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>工程项目分解结构</title>
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
						<span class="input-group-addon lable">分解编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">分解名称</span>
						<input name="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" data-opt="{type:'f7'}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">单位工程</span>
						<input name="proStruct" class="input-item form-control require read" data-opt="{type:'f7'}" />
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">结构类型</span> 
						<select name="wbsType" data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.enums.ec.ProWbsType'}" 
		                	class="form-control input-item require read"></select>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">上级分解结构</span>
						<input name="parent" class="input-item form-control" 
							data-opt="{type:'f7',dataChange:parent_dataChange,uiWin:{title:'项目单位工程',height:580,width:800,url:'ec/basedata/proWbsF7',uiParams:proWbs_params}}" />
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">启用</span> 
						<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						
					</div>
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
		}
	}
	function proWbs_params(){
		var pro = {};
		pro.projectId = $('input[name="project"]').myF7().getValue();
		pro.structId = $('input[name="proStruct"]').myF7().getValue();
		return pro;
	}
	
	function parent_dataChange(oldVal,newVal){
		var wbsType = 'FBGC';
		var number = '01';
		if(!webUtil.isEmpty(newVal)){
			number = newVal.number||'01';
			var nt = newVal.wbsType.key||'OTHER';
			if('FBGC' == nt){
				wbsType = 'ZFBGC';
			}else if('ZFBGC' == nt){
				wbsType = 'FXGC';
			}else{
				wbsType = 'OTHER';
			}
		}
		$('select[name="wbsType"]').myComponet(DataType.select,{method:'setData',opt:wbsType});
		$('input[name="number"]').val(number+'.');
	}

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "工程项目分解结构",
			baseUrl : "ec/basedata/prowbs",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>