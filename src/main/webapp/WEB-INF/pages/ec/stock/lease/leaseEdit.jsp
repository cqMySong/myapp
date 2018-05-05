<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>周转材料、设备使用(出租)</title>
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
					<span class="input-group-addon lable">出租单号</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">出租名称</span>
					<input name="name" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">出租单位</span>
					<input class="require input-item" name="leaseUnit">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">出租时间</span>
					<input name="leaseDate" class="input-item form-control require" data-opt="{type:'date'}"/>
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
				<table name="materialLeaseDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="materialType" data-width="120" data-type="select" data-locked="true"
							data-editor="{type:'select',url:'base/common/combox?enum=com.myapp.enums.materialType'}">材料类型</th>
						<th data-field="materialInfo" data-type="f7"
							data-editor="{uiWin:{title:'物料信息',height:550,width:750,url:'base/materialF7',uiParams:getParams}}">物料名称</th>
						<th data-field="specification" data-type="text" data-locked="true" data-width="100">规格</th>
						<th data-field="measureUnitInfo" data-type="f7" data-locked="true" data-width="100">计量单位</th>
						<th data-field="leaseCount" data-type="number" data-width="100">出租数量</th>
						<th data-field="remark" data-width="150" data-type="textarea" >备注</th>
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
    var materialLeaseDetailInfosEntry;
    var materialLeaseDetailInfosEntryObj;
    function proSubItem_dataChange(oldData,newData){

    }
    function materialLeaseDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(materialLeaseDetailInfosEntry)) return;
        if(obj.field=='materialInfo'&&!webUtil.isEmpty(materialLeaseDetailInfosEntry)){
            var materialInfoObj = obj.rowData[obj.field];
            materialLeaseDetailInfosEntry.setTableCellValue(obj.rowIndex, 'specification',
                materialInfoObj.specification);
            materialLeaseDetailInfosEntry.setTableCellValue(obj.rowIndex, 'materialType',
                materialInfoObj.materialType);
            materialLeaseDetailInfosEntry.setTableCellValue(obj.rowIndex, 'measureUnitInfo',
				{id:materialInfoObj.unit_id,name:materialInfoObj.unit_name});
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
        var entryOption = "{type:'entry',height:250,tableOpt:{editDataChanged:materialLeaseDetailInfos_dataChanged}"+
            ",toolbar:{title:'材设出租清单'}}";
        $("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "周转材料、设备使用(出租)",billModel:2,
            baseUrl : "ec/stock/lease",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        materialLeaseDetailInfosEntryObj = editUI.getEntryObj('materialLeaseDetailInfos');
        if(!webUtil.isEmpty(materialLeaseDetailInfosEntryObj)){
            materialLeaseDetailInfosEntry = materialLeaseDetailInfosEntryObj.entry;
            var rightBtnGroup = materialLeaseDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:materialLeaseDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            materialLeaseDetailInfosEntry.resetView();
        }
        //webUtil.initMainPanel('#editPanel');
    })
</script>
</html>