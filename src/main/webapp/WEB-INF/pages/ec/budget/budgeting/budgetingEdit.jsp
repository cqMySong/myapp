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
					<input class="require input-item" name="number" id="demoNumer">
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
				<table name="budgetingDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="materialType" data-width="150" data-type="select" data-locked="true"
						data-editor="{type:'select',url:'base/common/combox?enum=com.myapp.enums.MaterialType}">类型</th>
						<th data-field="material" data-type="f7"
							data-editor="{uiWin:{title:'物料信息',height:550,width:680,url:'base/materialF7',uiParams:getParams}}">物料名称</th>
						<th data-field="specification" data-type="text" data-width="140" data-locked="true">规格</th>
						<th data-field="measureUnitInfo" data-width="80"  data-type="f7" data-locked="true">单位</th>
						<th data-field="quantity" data-type="number" data-width="120">数量</th>
						<th data-field="budgetaryPrice" data-width="120"  data-type="number">预算价格</th>
						<th data-field="totalPrice"  data-width="120"  data-locked="true" >总价</th>
						<th data-field="remark"  data-width="150" data-type="textarea">备注</th>
						<th data-field="materialName" data-type="text" data-visible="false">物料名称</th>
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
    var budgetingDetailInfosEntry;
    var budgetingDetailInfosEntryObj;
    var importData = {};
    function proSubItem_dataChange(oldData,newData){

    }
    function budgetingDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(budgetingDetailInfosEntry)) return;
        if(obj.field=='material'){
            if(!webUtil.isEmpty(budgetingDetailInfosEntry)){
                var materialObj = obj.rowData[obj.field];
                var materialTypeVal = materialObj.materialType;
                if(materialTypeVal){
                    budgetingDetailInfosEntry.setTableCellValue(obj.rowIndex,'materialType',materialTypeVal);
                    budgetingDetailInfosEntry.setTableCellValue(obj.rowIndex,'specification',materialObj.specification);
                    var measureUnitInfo = {id:materialObj.unit_id,name:materialObj.unit_name};
                    budgetingDetailInfosEntry.setTableCellValue(obj.rowIndex,'measureUnitInfo',measureUnitInfo);
                    budgetingDetailInfosEntry.setTableCellValue(obj.rowIndex,'materialName',materialObj.name);
				}
            }
		}
		if(obj.rowData["quantity"]&&obj.rowData["budgetaryPrice"]){
            if(!webUtil.isEmpty(budgetingDetailInfosEntry)){
                budgetingDetailInfosEntry.setTableCellValue(obj.rowIndex,'totalPrice',
					Number(obj.rowData["quantity"]*obj.rowData["budgetaryPrice"]).toFixed(2));
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
    function showImportWin(btn){
        var forwardUrl = webUtil.toUrl('ec/budget/budgeting/import/view');
        webUtil.openWin({maxmin:false,width:500,height:400,title:'导入预算信息',url:forwardUrl,
            btnCallBack:function(btnIndex,index,layero){
            	if(layero){
                    var result = $(layero).find("iframe")[0].contentWindow._importResult;
                    if(result){
                        $.each(result,function(i,data){
                            btn.entry.insertRow(i,data);
						})
					}
				}
				return true;
			}});
	}
    $(document).ready(function() {
        var height = window.outerHeight-470
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:budgetingDetailInfos_dataChanged}"+
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

        budgetingDetailInfosEntryObj = editUI.getEntryObj('budgetingDetailInfos');
        if(!webUtil.isEmpty(budgetingDetailInfosEntryObj)){
            budgetingDetailInfosEntry = budgetingDetailInfosEntryObj.entry;
            var rightBtnGroup = budgetingDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:budgetingDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            rightBtnGroup.addBtn({entry:budgetingDetailInfosEntry,css:'btn-sm',text:'导入清单',icon:"fa fa-file-excel-o",clickFun:showImportWin});
            budgetingDetailInfosEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>