<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>预算询价详细</title>
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
					<span class="input-group-addon lable">询价编码</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">询价名称</span>
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
				<table name="enquiryPriceDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="materialType" data-width="80" data-type="select" data-locked="true">物料类型</th>
						<th data-field="budgetingDetailInfo" data-type="f7"  data-width="150"
							data-editor="{mutil:true,uiWin:{title:'预算详细',height:580,width:880,url:'ec/budget/budgetingDetailF7',uiParams:getParams}}">物料名称</th>
						<th data-field="specification" data-type="text" data-locked="true" data-width="100">规格</th>
						<th data-field="measureUnit" data-type="f7" data-locked="true" data-width="80">计量单位</th>
						<th data-field="quantity" data-type="text" data-locked="true" data-width="80">数量</th>
						<th data-field="budgetaryPrice" data-width="100" data-type="text" data-locked="true">预算价</th>
						<th data-field="intentionPrice"  data-width="100" data-type="number">采购意向价</th>
						<th data-field="paymentMethod"  data-width="100"  data-type="text">付款方式</th>
						<th data-field="origin"  data-width="100" data-type="text">产地</th>
						<th data-field="supplyCycle"  data-width="100" data-type="text">供货周期</th>
						<th data-field="supplyCompany"  data-type="text">单位</th>
						<th data-field="contactMan"  data-width="100" data-type="text">联系人</th>
						<th data-field="contactTel"  data-width="100" data-type="text">联系电话</th>
						<th data-field="contractSignMethod"  data-width="100" data-type="select"
						data-editor="{type:'select',selected:'CURRENT',url:'base/common/combox?enum=com.myapp.core.enums.ContractSignMethod'}">合同签订方式</th>
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
    var enquiryPriceDetailInfosEntry;
    var enquiryPriceDetailInfosEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function enquiryPriceDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(enquiryPriceDetailInfosEntry)) return;
        if(obj.field=='budgetingDetailInfo'){
            if(!webUtil.isEmpty(enquiryPriceDetailInfosEntry)){
                var budgetDetailArr = obj.rowData[obj.field];
				if(budgetDetailArr&&budgetDetailArr.length>0){
                    var budgetDetailInfoFirst = null;
				    $.each(budgetDetailArr,function(i,budgetDetailInfo){
						if(i==0){
                            enquiryPriceDetailInfosEntry.setTableCellValue(obj.rowIndex,'materialType',
								budgetDetailInfo.materialType);
                            enquiryPriceDetailInfosEntry.setTableCellValue(obj.rowIndex,'specification',
								budgetDetailInfo.specification);
                            enquiryPriceDetailInfosEntry.setTableCellValue(obj.rowIndex,'quantity',
								budgetDetailInfo.quantity);
                            enquiryPriceDetailInfosEntry.setTableCellValue(obj.rowIndex,'budgetaryPrice',
								budgetDetailInfo.budgetaryPrice);
                            var budgetingDetailInfo = {id:budgetDetailInfo.id,name:budgetDetailInfo.material_name};
                            enquiryPriceDetailInfosEntry.setTableCellValue(obj.rowIndex,'budgetingDetailInfo',
								budgetingDetailInfo);
                            var measureInfo = {id:budgetDetailInfo.measureUnitInfo_id,name:budgetDetailInfo.measureUnitInfo_name};
                            enquiryPriceDetailInfosEntry.setTableCellValue(obj.rowIndex,'measureUnit',measureInfo);
                            budgetDetailInfoFirst = budgetDetailInfo;
						}else{
						    var rowData = {materialType:budgetDetailInfo.materialType,
								specification:budgetDetailInfo.specification,
                                quantity:budgetDetailInfo.quantity,
                                budgetaryPrice:budgetDetailInfo.budgetaryPrice,
                                budgetingDetailInfo:{id:budgetDetailInfo.id,name:budgetDetailInfo.material_name},
                                measureUnit:{id:budgetDetailInfo.measureUnitInfo_id,
									name:budgetDetailInfo.measureUnitInfo_name}};
                            enquiryPriceDetailInfosEntry.insertRow(obj.rowIndex+i,rowData);
						}
					});
                    enquiryPriceDetailInfosEntry.setTableCellValue(obj.rowIndex,'budgetingDetailInfo',
                        {id:budgetDetailInfoFirst.id,name:budgetDetailInfoFirst.material_name});
				}

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
                console.log(rowData);
                entry.insertRow(rowIdx+1,rowData);
            }else{
                webUtil.mesg("请先选中行");
            }
        }
    }
    $(document).ready(function() {
        var height = window.outerHeight-470
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:enquiryPriceDetailInfos_dataChanged}"+
            ",toolbar:{title:'预算询价明细'}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "预算询价",billModel:2,
            baseUrl : "ec/budget/enquiryprice",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        enquiryPriceDetailInfosEntryObj = editUI.getEntryObj('enquiryPriceDetailInfos');
        if(!webUtil.isEmpty(enquiryPriceDetailInfosEntryObj)){
            enquiryPriceDetailInfosEntry = enquiryPriceDetailInfosEntryObj.entry;
            var rightBtnGroup = enquiryPriceDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:enquiryPriceDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            enquiryPriceDetailInfosEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>