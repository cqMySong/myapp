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
<body style="padding: 5px;" class="panel">
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
				<table name="purchaseStockDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="applyMaterialInfo" data-width="100" data-type="f7" data-locked="true">申购单号</th>
						<th data-field="materialType" data-width="120" data-type="select" data-locked="true">材料类型</th>
						<th data-field="applyMaterialDetailInfo" data-type="f7" data-visible="false">申购明细</th>
						<th data-field="material" data-type="f7"
							data-editor="{mutil:true,uiWin:{title:'申购信息',height:580,width:880,url:'ec/purchase/applyMaterialDetailF7',uiParams:getParams}}">物料名称</th>
						<th data-field="specification" data-type="text" data-locked="true" data-width="120">规格</th>
						<th data-field="measureUnitName" data-type="text" data-locked="true" data-width="100">计量单位</th>
						<th data-field="origin" data-type="text" data-width="200">产地</th>
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
    var purchaseStockDetailInfosEntry;
    var purchaseStockDetailInfosEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function purchaseStockDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(purchaseStockDetailInfosEntry)) return;
        if(obj.field=='material'){
            if(!webUtil.isEmpty(purchaseStockDetailInfosEntry)){
                var applyMaterialArr = obj.rowData[obj.field];
                if(applyMaterialArr&&applyMaterialArr.length>0) {
                    var  applyMaterialFirst = null;
                    $.each(applyMaterialArr, function (i, applyMaterialInfo) {
                        if (i == 0) {
                            purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex, 'materialType',
                                applyMaterialInfo["bdi.materialType"]);
                            purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex, 'specification',
                                applyMaterialInfo["bdi.specification"]);
                            purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex, 'measureUnitName',
                                applyMaterialInfo["mui.name"]);
                            purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex, 'applyMaterialDetailInfo',
                                {id: applyMaterialInfo.id});
                            purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex, 'applyMaterialInfo',
                                {id:applyMaterialInfo["pr.id"],name:applyMaterialInfo["pr.number"]});
                            purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex, 'materialType',
                                applyMaterialInfo["bdi.materialType"]);
                            applyMaterialFirst = applyMaterialInfo;
                        }else{
                            var rowData = {materialType:applyMaterialInfo["bdi.materialType"],
                                specification:applyMaterialInfo["bdi.specification"],
                                applyMaterialDetailInfo:{id: applyMaterialInfo.id},
                                measureUnitName:applyMaterialInfo["mui.name"],
                                material:{id:applyMaterialInfo["ma.id"],name:applyMaterialInfo["bdi.materialName"]},
                                applyMaterialInfo:{id:applyMaterialInfo["pr.id"],name:applyMaterialInfo["pr.number"]}};
                            purchaseStockDetailInfosEntry.insertRow(obj.rowIndex+i,rowData);
                        }
                    });
                    purchaseStockDetailInfosEntry.setTableCellValue(obj.rowIndex, 'material',
                        {id:applyMaterialFirst["ma.id"],name:applyMaterialFirst["bdi.materialName"]});
                }

            }
		}
    }

    function getParams(){
        var pro = {};
        pro.projectId = $('input[name="project"]').myF7().getValue();
        pro.purchaseContract = true;
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
        var entryOption = "{type:'entry',height:"+(winHeight<270?270:winHeight)+",tableOpt:{editDataChanged:purchaseStockDetailInfos_dataChanged}"+
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
        //webUtil.initMainPanel('#editPanel');
    })
</script>
</html>