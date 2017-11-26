<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>库存盘存</title>
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
					<span class="input-group-addon lable">盘存单号</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">盘存说明</span>
					<input name="name" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">工程项目</span>
					<input name="project" class="require input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">盘存人</span>
					<input name="operator" class="require input-item form-control require"
						   data-opt="{type:'f7',uiWin:{title:'人员选择',height:600,width:800,url:'base/userf7'}}" />
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">盘存日期</span>
					<input type="text" name="inventoryDate" class="form-control input-item require" data-opt="{type:'date'}">
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
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">备注</span>
					<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="stockInventoryDetailInfos" class="input-entry" >
					<thead>
						<tr>
							<th data-field="stockInfo" data-type="f7" data-width="100" data-locked="true">入库单号</th>
							<th data-field="materialType" data-width="100" data-type="select" data-locked="true">材料类型</th>
							<th data-field="material" data-type="f7"  data-width="150" data-locked="true">物料名称</th>
							<th data-field="specification" data-type="text" data-locked="true" data-width="100">规格</th>
							<th data-field="measureUnit" data-type="text" data-locked="true" data-width="100">计量单位</th>
							<th data-field="stockCount" data-type="number" data-locked="true" data-width="100">库存数量</th>
							<th data-field="inventoryCount" data-type="number" data-width="100">盘存数量</th>
							<th data-field="remark" data-type="text" data-width="100">备注</th>
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
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
    var editUI;
    var stockInventoryDetailInfosEntry;
    var stockInventoryDetailInfosEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function stockInventoryDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(stockInventoryDetailInfosEntry)) return;
        if(obj.field=='material'){
            if(!webUtil.isEmpty(stockInventoryDetailInfosEntry)){
                var selectPlanRow = obj.rowData[obj.field];
                if(!selectPlanRow){
                    return false;
				}
                var materialType = {key:selectPlanRow.material_materialType_id,val:selectPlanRow.material_materialType};
                stockInventoryDetailInfosEntry.setTableCellValue(obj.rowIndex,'specification',selectPlanRow.specification);
                stockInventoryDetailInfosEntry.setTableCellValue(obj.rowIndex,'materialType',materialType);
                stockInventoryDetailInfosEntry.setTableCellValue(obj.rowIndex,'measureUnit',selectPlanRow.measureUnit);
                stockInventoryDetailInfosEntry.setTableCellValue(obj.rowIndex,'stockOutDetailInfo',
					{id:selectPlanRow.stockOutDetailInfo_id});
                stockInventoryDetailInfosEntry.setTableCellValue(obj.rowIndex,'stockOutNumber',selectPlanRow.parent_number);
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
        console.log(opt);

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
        var height = window.outerHeight-460;
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:stockInventoryDetailInfos_dataChanged}"+
            ",toolbar:{title:'盘存物料列表'}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "库存盘存",billModel:2,
            baseUrl : "ec/stock/inventory",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        stockInventoryDetailInfosEntryObj = editUI.getEntryObj('stockInventoryDetailInfos');
        if(!webUtil.isEmpty(stockInventoryDetailInfosEntryObj)){
            stockInventoryDetailInfosEntry = stockInventoryDetailInfosEntryObj.entry;
            //var rightBtnGroup = stockInventoryDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            //rightBtnGroup.addBtn({entry:stockInventoryDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            stockInventoryDetailInfosEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>