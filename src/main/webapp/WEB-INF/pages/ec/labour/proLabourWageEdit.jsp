<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目民工工资表</title>
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
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item require" name="number">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="input-item form-control require"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">业务日期</span>
						<input type="text" name="bizDate" class="form-control input-item require" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">劳务班组</span>
						<input name="labourGroup" class="input-item form-control require" data-rule="notEmpty"/>
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
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" style="height:60px;" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-12 " style="border: 1px solid #ddd;">
					<table name="labourwageItem" class="input-entry" data-opt="{type:'entry',height:430
							,toolbar:{title:'民工工资明细'},addRow:labourwageItem_addRow}">
						<thead>
							<tr>
								<th data-field="labour" data-width="200" data-type="f7" data-locked="true">姓名</th>
								<th data-field="idCard" data-locked="true">身份证号</th>
								<th data-field="bankNo" >银行卡号</th>
								<th data-field="workDays" data-type="number">用工时间(天)</th>
								<th data-field="wage" data-type="number">应得工资</th>
								<th data-field="signDate" data-type=date>签收时间</th>
							</tr>
						</thead>
					</table>
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
						<input name="createDate" class="input-item form-control read" data-opt="{type:'datetime'}">
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
	var wageItemEntry;
	var wageItemEntryObj;
	
	/**
	 * 一切操作前的接口函数
	 */
	function beforeAction(opt) {
		return true;
	}
	var impUrl = webUtil.toUrl("ec/basedata/proLabourF7/f7show?mutil=true");
	var curProjectId = '';
	var uiWin_impData = {
		url:impUrl,title:"<i class='fa fa-windows'></i>&nbsp;劳务人员选择",btns:['确定','取消']
		,maxmin:false,width:900,height:780
		,btnCallBack :function(index,layerIndex,layero){
			if(layero){
				var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
				var datas = iframe_win.getData();
				if(index==1&&!webUtil.isEmpty(datas)){
					if($.isArray(datas)){
						for(var i=0;i<datas.length;i++){
							addRowData(datas[i]);
						}
					}
				}
			}
			return true;
		}
	};
	
	function addRowData(item){
		if(!webUtil.isEmpty(item)){
			var rowData = {};
			rowData.labour = {id:item.id,name:item.name};
			rowData.idCard = item.idCard;
			rowData.bankNo = item.bankNo;
			wageItemEntry.addRow(rowData);
		}
	}
	function labourwageItem_addRow(entry){
		if(!webUtil.isEmpty(curProjectId)){
			var uiParams = {projectId:curProjectId};
			var selIds = '';
			var items = wageItemEntry.getData();
			for(var i=0;i<items.length;i++){
				var labour = items[i].labour;
				if(!webUtil.isEmpty(labour)&&$.isPlainObject(labour)){
					var labourId = labour.id;
					if(!webUtil.isEmpty(labourId)){
						if(!webUtil.isEmpty(selIds)) selIds+=',';
						selIds+=labourId;
					}
				}
			}
			uiParams.selIds = webUtil.uuIdReplaceID(selIds);
			uiWin_impData.uiParams = uiParams;
			webUtil.openWin(uiWin_impData);
		}else{
			webUtil.mesg("请先选择对应的工程项目!");
		}
	}
	function afterAction(_opt){
		if(_opt==OperateType.addnew){
			var uiCtx = getUICtx();
			if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
					&&!webUtil.isEmpty(uiCtx.tree)){
				$('input[name="project"]').myF7().setData(uiCtx.tree);
			}
		}
		curProjectId = $('input[name="project"]').myF7().getValue();
	}
	$(document).ready(function() {
		editUI = $('#editPanel').editUI({
			title : "项目民工工资表",billModel:2,
			baseUrl : "ec/labour/prolabourwage",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		
		wageItemEntryObj = editUI.getEntryObj('labourwageItem');
		if(!webUtil.isEmpty(wageItemEntryObj)){
			wageItemEntry = wageItemEntryObj.entry;
			wageItemEntry.resetView();
		}
		var height = top.getTopMainHeight();
		$('#editPanel').height(height+30);
	})
</script>
</html>