<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>岗位信息</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 4px;">
	<div id="editPanel" class="panel">
		<div id="table-toolbar"></div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">所属组织</span> 
						<input name="org" class="input-item form-control read" data-opt="{type:'f7',enabled:false}" />

					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">上级岗位</span> 
						<input name="parent" class="input-item form-control" 
							data-opt="{type:'f7',uiWin:{title:'工作岗位',height:600,width:800,url:'base/positionf7',uiParams:getPostionParams}}" />
							
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">负责人岗</span> 
						<input class="require input-item" name="respible" data-opt="{type:'checkbox'}" type="checkbox">

					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">启用</span> 
						<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">岗位描述</span>
						<textarea name="remark" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12 " style="border: 1px solid #ddd;">
					<table name="jobDutyItems" class="input-entry" data-opt="{type:'entry',height:170
						,toolbar:{title:'岗位职责'},addRow:jobduty_addRow}">
						<thead>
							<tr>
								<th data-field="jobDuty" data-type="f7" data-locked="true"
									data-editor="{uiWin:{title:'工作职责',height:600,width:800,url:'base/jobDutyf7'}}">
									工作职责</th>
								<th data-field="remark" data-width="250" data-type="textarea">备注</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../base/base_edit.jsp"%>
<script type="text/javascript">
	/**
	 * 一切操作前的接口函数
	 */
	 
	var jobdutyEntry ;
	function beforeAction(opt) {
		return true;
	}
	
	function afterAction(_opt,data){
		if(_opt==OperateType.addnew){
			var uiCtx = getUICtx();
			if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
					&&!webUtil.isEmpty(uiCtx.tree)){
				$('input[name="org"]').myF7().setData(uiCtx.tree);
			}
		}
	}
	
	function getPostionParams(){
		var orgId = $('input[name="org"]').myF7().getValue();
		return {orgId:orgId};
	}
	var addRowUrl = webUtil.toUrl('base/jobdutys/treeShow'); 
	var addRowTitle = '<i class="fa fa-windows"></i>&nbsp;工作职责选择';
	function jobduty_addRow(tbl){
		webUtil.openWin({title:addRowTitle,url:addRowUrl,width:900,height:750
			,btns:['确定','取消'],maxmin:false,btnCallBack:function(index,layerIndex,layero){
				if(layero){
					var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
					var data = iframe_win.getData();
					if(data){
						if($.isArray(data)){
							for(var i=0;i<data.length;i++){
								var thisData = data[i];
								var rowData = {};
								var _jobduty = {id:thisData.id,name:thisData.name};
								rowData.jobDuty = _jobduty;
								tbl.addRow(rowData);
							}
						}else if($.isPlainObject(data)){
							var rowData = {};
							var _jobduty = {id:data.id,name:data.name};
							rowData.jobDuty = _jobduty;
							tbl.addRow(rowData);
						}
					}
				}
				return true;
			}});
	}
	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "组织岗位",
			baseUrl : "base/position",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		var jobdutyEntryObj = editUI.getEntryObj('jobDutyItems');
		if (!webUtil.isEmpty(jobdutyEntryObj)) {
			jobdutyEntry = jobdutyEntryObj.entry;
		}
	})
</script>
</html>