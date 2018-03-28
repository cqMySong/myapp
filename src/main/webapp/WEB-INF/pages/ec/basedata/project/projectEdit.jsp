<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>基础组织信息</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel" >
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">项目编码</span> 
						<input class="require input-item" name="number" type="text">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">项目名称</span> 
						<input name="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">项目状态</span> 
						<select name="proState" data-opt="{type:'select',selected:'SGZB',url:'base/common/combox?enum=com.myapp.enums.ProjectState'}" 
		                	class="form-control input-item require">
		                </select>
						
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">所属组织</span> 
						<input name="org"  class="input-item form-control read" 
							data-opt="{type:'f7',dataChange:parent_dataChange,enabled:false,uiWin:{title:'组织查询',height:550,width:800,url:'base/orgf7'}}" />

					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">建筑高度(m)</span> 
						<input name="eavesHeight" data-rule="number" class="require input-item">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">层高(m)</span> 
						<input name="floorHeight" data-rule="number" class="input-item require">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">结构类型</span> 
						<input name="structTypes" class="input-item form-control require" 
							data-opt="{mutil:true,type:'f7',dataChange:parent_dataChange,uiWin:{title:'结构类型',height:750,width:900,url:'ec/basedata/structTypeF7'}}" />
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">工程分类</span> 
						<select name="industryType" data-opt="{type:'select',selected:'FWJZ',url:'base/common/combox?enum=com.myapp.enums.IndustryType'}" 
		                	class="form-control input-item require">
		                </select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">占地面积</span> 
						<input name="area" data-opt="{type:'number',precision:2}" data-rule="number" class="require input-item">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">规模</span> 
						<input name="scale" class="input-item require">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 mb15">
					<div class="input-group ">
						<span class="input-group-addon lable">抗震等级</span> 
						<input name="aseismicLevel" class="input-item require">
					</div>
				</div>
				<div class="col-sm-6 mb15">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">地址</span> 
						<input name="address" class="require input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注信息</span>
						<textarea name="remark" class="input-item " rows="2"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
function beforeAction(opt){
	return true;
}
function afterAction(_opt){
	if(_opt==OperateType.addnew){
		var uiCtx = getUICtx();
		if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
				&&!webUtil.isEmpty(uiCtx.tree)){
			$('input[name="org"]').myF7().setData(uiCtx.tree);
		}
	}
}
function parent_dataChange(oldData,newData){
	//alert('触发了值改变事件');
}

function isAdmin_click(data){
	webUtil.mesg(data);
}
$(document).ready(function() {
	var editUI = $('#editPanel').editUI({title:"工程项目",baseUrl:"ec/basedata/project",toolbar:"#table-toolbar",form:{el:"#editForm"}});
	editUI.onLoad();
})
</script>
</html>