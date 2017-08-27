<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>预算编制</title>
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
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">预算编码</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">预算名称</span>
					<input name="name" class="input-item form-control"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">工程项目</span>
					<input name="project" class="require input-item form-control read"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
				</div>
			</div>
			<div class="col-sm-3">
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
				<table name="planItems" class="input-entry" >
					<thead>
					<tr>
						<th data-field="dataDicType" data-width="150" data-locked="true" >材料类型</th>
						<th data-field="dataDic"   data-type="f7"
							data-editor="{uiWin:{title:'物料名称',height:550,width:680,url:'ec/basedata/dataDicF7',uiParams:getParams}}">物料名称</th>
						<th data-field="quantity" data-type="number" data-width="120">数量</th>
						<th data-field="unitPrice" data-width="120"  data-type="number">单价</th>
						<th data-field="totalPrice"  data-width="120"  data-locked="true" >总价</th>
						<th data-field="remark"  data-width="250" data-type="textarea">备注</th>
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
    var editUI;
    var planItemsEntry;
    var planItemsEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function planItems_dataChanged($cell,obj){
        if(webUtil.isEmpty(planItemsEntry)) return;
        if(obj.field=='dataDic'){
            if(!webUtil.isEmpty(planItemsEntry)){
                var dataDicTypeVal = obj.rowData[obj.field].dataDicType;
                if(dataDicTypeVal){
                    planItemsEntry.setTableCellValue(obj.rowIndex,'dataDicType',dataDicTypeVal.val);
				}
            }
		}
		if(obj.rowData["quantity"]&&obj.rowData["unitPrice"]){
            if(!webUtil.isEmpty(planItemsEntry)){
                planItemsEntry.setTableCellValue(obj.rowIndex,'totalPrice',
					Number(obj.rowData["quantity"]*obj.rowData["unitPrice"]).toFixed(2));
            }
		}
    }

    function getParams(){
        var pro = {};
        pro.projectId = $('input[name="project"]').myF7().getValue();
        return pro;
    }
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
    function btnCopyInsertRow(btn){
        var entry = btn.entry;
        if(!webUtil.isEmpty(entry)){
            var selRowsData = entry.getSelections();
            var rowIdx = entry.getSelectRowIndex();
            if(!webUtil.isEmpty(selRowsData)&&selRowsData.length>0
                &&rowIdx>=0){
                var rowData =  $.extend(true,{},selRowsData[0]);
                rowData.id = '';
                entry.insertRow(rowIdx+1,rowData);
            }else{
                webUtil.mesg("请先选中行");
            }
        }
    }
    $(document).ready(function() {
        var height = window.outerHeight-470
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:planItems_dataChanged}"+
            ",toolbar:{title:'预算清单'}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "预算编制",billModel:2,
            baseUrl : "ec/budget/budgeting",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        planItemsEntryObj = editUI.getEntryObj('planItems');
        if(!webUtil.isEmpty(planItemsEntryObj)){
            planItemsEntry = planItemsEntryObj.entry;
            var rightBtnGroup = planItemsEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:planItemsEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            planItemsEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>