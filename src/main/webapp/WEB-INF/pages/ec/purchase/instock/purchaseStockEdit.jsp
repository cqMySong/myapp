<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>采购入库</title>
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
					<span class="input-group-addon lable">入库单号</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">入库名称</span>
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
					<span class="input-group-addon lable">总金额</span>
					<input name="totalPrice"  type="number" class="form-control input-item require"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">收货人</span>
					<input name="consignee" class="require input-item form-control require"
				   data-opt="{type:'f7',uiWin:{title:'人员选择',height:600,width:800,url:'base/userf7'}}" />
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">收货日期</span>
					<input type="text" name="inStockDate" class="form-control input-item require" data-opt="{type:'date'}">
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
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">备注</span>
					<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="purchaseStockDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="purchaseContractInfo" data-width="100" data-type="f7" data-locked="true">合同名称</th>
						<th data-field="materialType" data-width="100" data-type="select" data-locked="true">材料类型</th>
						<th data-field="purchaseContractDetailInfo" data-type="f7" data-visible="false">合同明细</th>
						<th data-field="material" data-type="f7"  data-width="150"
							data-editor="{uiWin:{title:'合同明细',height:580,width:880,url:'ec/purchase/purchaseContractDetailF7',uiParams:getParams}}">物料名称</th>
						<th data-field="specification" data-type="text" data-locked="true" data-width="100">规格</th>
						<th data-field="measureUnitName" data-type="text" data-locked="true" data-width="100">计量单位</th>
						<th data-field="purchasePrice" data-width="100"  data-type="number">采购单价</th>
						<th data-field="quantity" data-type="number" data-width="100">采购数量</th>
						<th data-field="count" data-type="number" data-width="100">入库数量</th>
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
    var purchaseStockDetailInfosEntry;
    var purchaseStockDetailInfosEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function purchaseStockDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(purchaseStockDetailInfosEntry)) return;
        if(obj.field=='material'){
            if(!webUtil.isEmpty(purchaseStockDetailInfosEntry)){
                var selectPlanRow = obj.rowData[obj.field];
                if(!selectPlanRow){
                    return false;
				}
                var purchaseContractInfo = {id:selectPlanRow.parent_id,
                    name:selectPlanRow.parent_name};
                purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex,'purchaseContractInfo',purchaseContractInfo);
                purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex,'purchaseContractDetailInfo',{id:selectPlanRow.id});
                purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex,'specification',
                    selectPlanRow.specification);
                purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex,'materialType',
                    selectPlanRow.materialType);
                purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex,'measureUnitName',
                    selectPlanRow.measureUnitName);
                purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex,'purchasePrice',selectPlanRow.purchasePrice);
                purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex,'quantity',selectPlanRow.quantity);

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
        var height = window.outerHeight-460;
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:purchaseStockDetailInfos_dataChanged}"+
            ",toolbar:{title:'采购入库清单'}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "采购入库",billModel:2,
            baseUrl : "ec/purchase/purchasestock",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        purchaseStockDetailInfosEntryObj = editUI.getEntryObj('purchaseStockDetailInfos');
        if(!webUtil.isEmpty(purchaseStockDetailInfosEntryObj)){
            purchaseStockDetailInfosEntry = purchaseStockDetailInfosEntryObj.entry;
            var rightBtnGroup = purchaseStockDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:purchaseStockDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            purchaseStockDetailInfosEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>