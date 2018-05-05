<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>采购合同</title>
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
					<span class="input-group-addon lable">合同编号</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">合同名称</span>
					<input name="name" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">合同金额</span>
					<input name="amount"  type="number" class="form-control input-item require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">费用类型</span>
					<select name="expenseType" data-opt="{type:'select',selected:'MATERIAL',url:'base/common/combox?enum=com.myapp.core.enums.PurchaseExpenseType'}"
							class="form-control input-item require">
					</select>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">付款方式</span>
					<select name="paymentMethod" data-opt="{type:'select',selected:'ADVANCE',url:'base/common/combox?enum=com.myapp.core.enums.PaymentMethod'}"
							class="form-control input-item require">
					</select>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">合同单位</span>
					<input name="supplyCompany" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">负责人</span>
					<input name="operator" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">联系电话</span>
					<input name="contactTel" class="form-control input-item require"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
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
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">备注</span>
					<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="purchaseContractDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="materialType" data-width="120" data-type="select" data-locked="true"
						data-editor="{type:'select',url:'base/common/combox?enum=com.myapp.enums.materialType'}">材料类型</th>
						<th data-field="applyMaterialDetailInfo" data-type="f7" data-visible="false">申购单id</th>
						<th data-field="materialName" data-type="text" data-visible="false">材料名称</th>
						<th data-field="material" data-type="f7"
							data-editor="{mutil:true,uiWin:{title:'材料申购单',height:580,width:880,url:'ec/purchase/applyMaterialDetailF7',uiParams:getParams}}">物料名称</th>
						<th data-field="specification" data-type="text" data-locked="true" data-width="150">规格</th>
						<th data-field="measureUnitName" data-type="text" data-locked="true" data-width="100">计量单位</th>
						<th data-field="origin"  data-width="200" data-type="text">产地</th>
						<th data-field="quantity" data-type="number" data-width="100">采购数量</th>
						<th data-field="purchasePrice" data-width="100"  data-type="number">采购单价</th>
						<th data-field="totalPrice" data-width="100"  data-type="text" data-locked="true">总计</th>
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
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
    var editUI;
    var purchaseContractDetailInfosEntry;
    var purchaseContractDetailInfosEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function purchaseContractDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(purchaseContractDetailInfosEntry)) return;
        if(obj.field=='material'&&!webUtil.isEmpty(purchaseContractDetailInfosEntry)){
			var applyMaterialArr = obj.rowData[obj.field];
			if(applyMaterialArr&&applyMaterialArr.length>0) {
				var  applyMaterialFirst = null;
				$.each(applyMaterialArr, function (i, applyMaterialInfo) {
					if (i == 0) {
						purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex, 'materialType',
							applyMaterialInfo["bdi.materialType"]);
						purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex, 'specification',
                            applyMaterialInfo["bdi.specification"]);
						purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex, 'measureUnitName',
                            applyMaterialInfo["mui.name"]);
						purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex, 'materialName',
                            applyMaterialInfo["bdi.materialName"]);
						purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex, 'applyMaterialDetailInfo',
							{id: applyMaterialInfo.id});
						purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex, 'quantity',
							applyMaterialInfo.purchaseNum);
                        applyMaterialFirst = applyMaterialInfo;
					}else{
                        var rowData = {materialType:applyMaterialInfo["bdi.materialType"],
                            specification:applyMaterialInfo["bdi.specification"],
                            quantity:applyMaterialInfo.purchaseNum,
                            materialName:applyMaterialInfo["bdi.materialName"],
                            applyMaterialDetailInfo:{id: applyMaterialInfo.id},
                            measureUnitName:applyMaterialInfo["mui.name"],
                            material:{id:applyMaterialInfo["ma.id"],name:applyMaterialInfo["bdi.materialName"]}};
                        purchaseContractDetailInfosEntry.insertRow(obj.rowIndex+i,rowData);
					}
				});
                purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex, 'material',
                    {id:applyMaterialFirst["ma.id"],name:applyMaterialFirst["bdi.materialName"]});
            }
		}
        if(obj.rowData["quantity"]&&obj.rowData["purchasePrice"]){
            if(!webUtil.isEmpty(purchaseContractDetailInfosEntry)){
                purchaseContractDetailInfosEntry.setTableCellValue(obj.rowIndex,'totalPrice',
                    Number(obj.rowData["quantity"]*obj.rowData["purchasePrice"]).toFixed(2));
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
        var height = top.getTopMainHeight()+100;
        height = height>760?760:height;
        var winHeight = height-480;
        var entryOption = "{type:'entry',height:"+(winHeight<220?220:winHeight)+",tableOpt:{editDataChanged:purchaseContractDetailInfos_dataChanged}"+
            ",toolbar:{title:'采购合同清单'}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "采购合同",billModel:2,
            baseUrl : "ec/purchase/purchasecontract",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        purchaseContractDetailInfosEntryObj = editUI.getEntryObj('purchaseContractDetailInfos');
        if(!webUtil.isEmpty(purchaseContractDetailInfosEntryObj)){
            purchaseContractDetailInfosEntry = purchaseContractDetailInfosEntryObj.entry;
            var rightBtnGroup = purchaseContractDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:purchaseContractDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            purchaseContractDetailInfosEntry.resetView();
        }
        //webUtil.initMainPanel('#editPanel');
    })
</script>
</html>