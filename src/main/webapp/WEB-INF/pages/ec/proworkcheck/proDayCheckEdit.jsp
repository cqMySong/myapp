<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>施工现场实体安全隐患日检记录</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;" class="panel">
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
						<span class="input-group-addon lable">检查名称</span>
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
						<span class="input-group-addon lable">专职安全员</span>
						<input name="proSafer" class="input-item form-control" data-opt="{type:'f7',uiWin:{title:'用户信息',height:600,width:800,url:'base/userf7'}}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">监理检查员</span>
						<input name="partBChecker" class="input-item form-control" data-rule="notEmpty">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">业务状态</span>
						<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}"
								class="form-control input-item require read">
						</select>
					</div>
				</div>
				<div class="col-sm-8">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-12 " style="border: 1px solid #ddd;">
					<table name="dayCheckItems" class="input-entry" data-opt="{type:'entry',height:230,tableOpt:{editDataChanged:dayCheckItems_dataChanged}
							,toolbar:{title:'检查记录清单'}}">
						<thead>
							<tr>
								<th data-field="workCheckItem" data-visible="false" data-width="100" data-type="f7">检验项目</th>
								<th data-field="workCheckType" data-width="100" data-type="select"
									 data-editor="{url:'base/common/combox?enum=com.myapp.enums.ec.WorkCheckType'}">检验分类</th>
								<th data-field="checkItem" data-width="160">检查项目</th>
								<th data-field="checkRequire" data-width="200">基本要求</th>
								<th data-field="safePeril" data-width="100" data-type="select" 
									data-editor="{url:'base/common/combox?enum=com.myapp.enums.ec.YesNoEnum'}">安全隐患</th>
								<th data-field="perilContent" data-width="200"  data-type="textarea">隐患情况</th>
								<th data-field="removePeril" data-width="100" data-type="select"
									data-editor="{url:'base/common/combox?enum=com.myapp.enums.ec.YesNoEnum'}">整改结果</th>
								<th data-field="remark" data-type="textarea">备注</th>
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

function safePeril_formatter(value, row, index){
	if(webUtil.isEmpty(value)) return '';
	if($.isPlainObject(value)){
		var txt = value.val;
		if('YES'==value.key){
			txt = '有';
		}else if('NO'==value.key){
			txt = '无';
		}
		return txt;
	}else{
		return value;
	}
}
function removePeril_formatter(value, row, index){
	if(webUtil.isEmpty(value)) return '';
	if($.isPlainObject(value)){
		var txt = value.val;
		if('YES'==value.key){
			txt = '已排除';
		}else if('NO'==value.key){
			txt = '未排除';
		}
		return txt;
	}else{
		return value;
	}
}
	var editUI;
	var dayCheckItems;
	var dayCheckItemsObj;
	
	function dayCheckItems_dataChanged($cell,obj){
		/* var obj= {};
		obj.oldVal = oldVal;
		obj.value = val;
		obj.rowData = rowData;
		obj.field = field;
		obj.column = thisColumn;
		obj.rowIndex = rowIdx;
		obj.colIndex = colIdx; */
		if(webUtil.isEmpty(dayCheckItems)) return;
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
			var _win = {width:950,height:750,maxmin:false
					,url:webUtil.toUrl("ec/basedata/workcheckitemquery/f7show?mutil=true&checkgroup=DAY")
					,title:'检查项目导入',btns:['确定','取消']
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
		if(!webUtil.isEmpty(dayCheckItems)&&!webUtil.isEmpty(rowData)){
			var row = {};
			row.safePeril = {key:'BLANK',val:' '};
			row.removePeril = {key:'BLANK',val:' '};
			row.workCheckItem = {id:rowData.id,name:rowData.name};
			row.workCheckType = rowData.workCheckType;
			row.checkItem = rowData.name;
			row.checkRequire = rowData.checkRequire;
			row.perilContent = '';
			dayCheckItems.addRow(row);
		}
	}
	$(document).ready(function() {
		editUI = $('#editPanel').editUI({
			title : "施工现场实体安全隐患日检记录",billModel:2,
			baseUrl : "ec/proworkcheck/prodaycheck",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		
		dayCheckItemsObj = editUI.getEntryObj('dayCheckItems');
		if(!webUtil.isEmpty(dayCheckItemsObj)){
			dayCheckItems =dayCheckItemsObj.entry;
			var rightBtnGroup = dayCheckItemsObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
			rightBtnGroup.addBtn({entry:dayCheckItems,css:'btn-sm',text:'检查标准导入',icon:"fa fa-edit",clickFun:btnImpData});
			dayCheckItems.resetView();
		}
		//var height = top.getTopMainHeight();
		//$('#editPanel').height(height+15);
	})
</script>
</html>