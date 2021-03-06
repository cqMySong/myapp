<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>材料申购</title>
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
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">申购单号</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">申购名称</span>
					<input name="name" class="input-item form-control"/>
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
				<table name="applyMaterialDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="materialType" data-width="100" data-type="select" data-locked="true">物料类型</th>
						<th data-field="materialInfo" data-type="f7"  data-visible="false">物料信息</th>
						<th data-field="budgetingDetailInfo" data-type="f7"
							data-editor="{mutil:true,uiWin:{title:'预算详细',height:580,width:880,url:'ec/budget/budgetingDetailF7',uiParams:getParams}}">物料名称</th>
						<th data-field="specification" data-type="text" data-locked="true" data-width="150">规格</th>
						<th data-field="measureUnit" data-type="f7" data-locked="true" data-width="80">计量单位</th>
						<th data-field="quantity" data-type="text" data-locked="true" data-width="80">预算数量</th>
						<th data-field="budgetaryPrice" data-width="100" data-type="text" data-locked="true">预算价</th>
						<th data-field="stockCount" data-width="100" data-type="number" data-locked="true">库存量</th>
						<th data-field="purchaseNum" data-width="100" data-type="number">申购数量</th>
						<th data-field="arrivalTime" data-width="120" data-type="date">计划到场时间</th>
						<th data-field="cumulativePurchaseNum" data-width="120" data-locked="true" data-type="number">累计申购数量</th>
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
					<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'datetime'}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">审核日期</span>
					<input name="auditDate" type="text" class="form-control input-item read" data-opt="{type:'datetime'}"/>
				</div>
			</div>
		</div>
	</form>
</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
    var editUI;
    var applyMaterialDetailInfosEntry;
    var applyMaterialDetailInfosEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function applyMaterialDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(applyMaterialDetailInfosEntry)) return;
        if(obj.field=='budgetingDetailInfo'){
            if(!webUtil.isEmpty(applyMaterialDetailInfosEntry)){
                console.log(obj);
                var budgetDetailArr = obj.rowData[obj.field];
                if(budgetDetailArr&&budgetDetailArr.length>0) {
                    var budgetDetailInfoFirst = null;
                    var materialIds = [];
                    $.each(budgetDetailArr, function (i, budgetDetailInfo) {
                        materialIds.push(budgetDetailInfo.material_id);
                    });
                    var materialStockCount = {};
                    webUtil.ajaxData({
                        url: 'ec/purchase/applymaterial/queryStockAndTotalApply',
						data: {projectId:$('input[name="project"]').myF7().getValue(),materialIds:materialIds.join(",")},
                        async: false, success: function (data) {
							if(data.statusCode=="0"){
                                materialStockCount = data.data;
							}
                        }
                    });
                    $.each(budgetDetailArr, function (i, budgetDetailInfo) {
                        if (i == 0) {
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'materialType', budgetDetailInfo.materialType);
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'specification', budgetDetailInfo.specification);
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'quantity', budgetDetailInfo.quantity);
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'budgetaryPrice', budgetDetailInfo.budgetaryPrice);
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'stockCount',
								materialStockCount[budgetDetailInfo.material_id]?materialStockCount[budgetDetailInfo.material_id].stockCount:0);
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'cumulativePurchaseNum',
								materialStockCount[budgetDetailInfo.material_id]?materialStockCount[budgetDetailInfo.material_id].purchaseNum:0);
                            var measureInfo = {
                                id: budgetDetailInfo.measureUnitInfo_id,
                                name: budgetDetailInfo.measureUnitInfo_name
                            };
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'measureUnit', measureInfo);
                            var materialInfo = {
                                id: budgetDetailInfo.material_id,
                                name: budgetDetailInfo.material_name
                            };
                            applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex, 'materialInfo', materialInfo);
                            budgetDetailInfoFirst = budgetDetailInfo;
                        } else {
                            var rowData = {materialType:budgetDetailInfo.materialType,
                                specification:budgetDetailInfo.specification,
                                quantity:budgetDetailInfo.quantity,
                                budgetaryPrice:budgetDetailInfo.budgetaryPrice,
                                budgetingDetailInfo:{id:budgetDetailInfo.id,name:budgetDetailInfo.material_name},
                                measureUnit:{id:budgetDetailInfo.measureUnitInfo_id,
                                    name:budgetDetailInfo.measureUnitInfo_name},
								materialInfo:{id: budgetDetailInfo.material_id,
                                    name: budgetDetailInfo.material_name},
								stockCount: materialStockCount[budgetDetailInfo.material_id]?
									materialStockCount[budgetDetailInfo.material_id].stockCount:0,
                                cumulativePurchaseNum: materialStockCount[budgetDetailInfo.material_id]?
									materialStockCount[budgetDetailInfo.material_id].purchaseNum:0
								};
                            applyMaterialDetailInfosEntry.insertRow(obj.rowIndex+i,rowData);
                        }
                    });
                    applyMaterialDetailInfosEntry.setTableCellValue(obj.rowIndex,'budgetingDetailInfo',
                      {id:budgetDetailInfoFirst.id,name:budgetDetailInfoFirst.material_name});
                }
            }
		}
    }

    function getParams(){
        var pro = {};
        pro.projectId = $('input[name="project"]').myF7().getValue();
        pro.enquiryPrice = true;
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
        var height = top.getTopMainHeight()+100;
        height = height>760?760:height;
        var winHeight = height-350;
        var entryOption = "{type:'entry',height:"+(winHeight<260?260:winHeight)+",tableOpt:{editDataChanged:applyMaterialDetailInfos_dataChanged}"+
            ",toolbar:{title:'材料申购明细'}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "材料申购",billModel:3,
            baseUrl : "ec/purchase/applymaterial",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        applyMaterialDetailInfosEntryObj = editUI.getEntryObj('applyMaterialDetailInfos');
        if(!webUtil.isEmpty(applyMaterialDetailInfosEntryObj)){
            applyMaterialDetailInfosEntry = applyMaterialDetailInfosEntryObj.entry;
            var rightBtnGroup = applyMaterialDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:applyMaterialDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            applyMaterialDetailInfosEntry.resetView();
        }
        //webUtil.initMainPanel('#editPanel');
    })
</script>
</html>