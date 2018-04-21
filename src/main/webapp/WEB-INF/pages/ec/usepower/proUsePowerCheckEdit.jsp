<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>施工现场安全用电检查表</title>
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
						<span class="input-group-addon lable">检查编码</span> 
						<input class="require input-item" name="number" data-rule="notEmpty" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">检查单位</span>
						<input name="name" class="input-item form-control" data-rule="notEmpty"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">检查日期</span>
						 <input type="text" name="bizDate" class="form-control input-item" data-rule="notEmpty" data-opt="{type:'date'}">
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
						<span class="input-group-addon lable">施工员</span> 
						<input name="ecWorker" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'用户信息',height:600,width:800,url:'base/userf7'}}">
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
						<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-12 " style="border: 1px solid #ddd;">
					<table name="checkItems" class="input-entry" data-opt="{type:'entry',height:430,tableOpt:{editDataChanged:checkItems_dataChanged}
							,toolbar:{title:'检查内容清单'}}">
						<thead>
							<tr>
							
								<th data-field="usePower" data-width="100" data-type="f7"
									 data-editor="{uiWin:{title:'安全用电标准查询',height:550,width:680
									 ,url:'ec/basedata/safeusepowerF7'}}">检查内容</th>
								<th data-field="standard" data-width="200">检查标准</th>
								<th data-field="checkResult" data-width="200" data-type="textarea">检查结论</th>
								<th data-field="remark" data-type="textarea">备注</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">检查员</span> 
						<input name="ecChecker" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'用户信息',height:600,width:800,url:'base/userf7'}}">
					</div>
				</div>
				<div class="col-sm-8">
					<div class="input-group">
						<span class="input-group-addon lable">整改意见</span>
						<input name="rectifyIdea" class="input-item form-control" style="width: 100%;" data-rule="notEmpty">
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
	var checkItems;
	var checkItemsObj;
	
	function checkItems_dataChanged($cell,obj){
		/* var obj= {};
		obj.oldVal = oldVal;
		obj.value = val;
		obj.rowData = rowData;
		obj.field = field;
		obj.column = thisColumn;
		obj.rowIndex = rowIdx;
		obj.colIndex = colIdx; */
		if(webUtil.isEmpty(checkItems)) return;
		if('usePower'==obj.field){
			var newData = obj.rowData[obj.field];
			if(!webUtil.isEmpty(newData)){
				checkItems.setTableCellValue(obj.rowIndex,'standard',newData.standard);
			}
		}
	}
	
	/**
	 * 一切操作前的接口函数
	 */
	function beforeAction(opt) {
		return true;
	}
	
	function btnImpData(){
		
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
	function btnImpData(btn){
		var entry = btn.entry;
		if(!webUtil.isEmpty(entry)){
			var _win = {width:950,height:780,maxmin:false
					,url:webUtil.toUrl("ec/basedata/safeusepowerF7/f7show?mutil=true")
					,title:'用电标准项导入',btns:['确定','取消']
				,btnCallBack:function(index,layerIndex,layero){
					if(layero){
						var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
						var datas = iframe_win.getData();
						if(index==1&&datas&&datas.length>0){
							for(var i=0;i<datas.length;i++){
								impAddRow(datas[i]);
							}
						}
					}
					return true;
				}};
			webUtil.openWin(_win);
		}
	}
	function impAddRow(rowData){
		if(!webUtil.isEmpty(checkItems)&&!webUtil.isEmpty(rowData)){
			var row = {};
			var _usePower = {id:rowData.id,name:rowData.name};
			row.usePower =_usePower;
			row.standard = rowData.standard;
			checkItems.addRow(row);
		}
	}
	$(document).ready(function() {
		editUI = $('#editPanel').editUI({
			title : "施工现场安全用电检查表",billModel:2,
			baseUrl : "ec/usepower/prousepowercheck",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		
		checkItemsObj = editUI.getEntryObj('checkItems');
		if(!webUtil.isEmpty(checkItemsObj)){
			checkItems =checkItemsObj.entry;
			var rightBtnGroup = checkItemsObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
			rightBtnGroup.addBtn({entry:checkItems,css:'btn-sm',text:'检查标准导入',icon:"fa fa-edit",clickFun:btnImpData});
			checkItems.resetView();
		}
		var height = top.getTopMainHeight();
		$('#editPanel').height(height+15);
	})
</script>
</html>