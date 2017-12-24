<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>物料结算</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">

</script>
<body style="padding: 5px;">
<div id="editPanel" class="myMainContent panel">
	<div id="table-toolbar" style="height: 20px;"></div>
	<form id="editForm">
		<div class="row">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">结算单号</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">结算名称</span>
					<input name="name" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">合同名称</span>
					<input class="require input-item form-control" name="purchaseContractInfo"
						   data-opt="{type:'f7',dataChange:selectPurchaseContract,uiWin:{title:'采购合同',height:600,width:800,url:'ec/purchase/purchaseContractF7',uiParams:getParams}}" />
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">采购单位</span>
					<input name="supplyCompany" class="require input-item form-control require"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">开始时间</span>
					<input type="text" name="startDate" class="form-control input-item require" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">结束时间</span>
					<input type="text" name="endDate" class="form-control input-item require" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">结算金额</span>
					<input name="settleAmount" class="input-item form-control require" type="number"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">经办人</span>
					<input name="operator" class="require input-item form-control require"
						   data-opt="{type:'f7',uiWin:{title:'人员选择',height:600,width:800,url:'base/userf7'}}" />
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon lable">工作内容</span>
					<textarea name="jobContent" style="height:38px;" class="input-item form-control"></textarea>
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
					<textarea name="remark" style="height:38px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="materialSettleDetailInfos" class="input-entry" >
					<thead>
						<tr>
							<th data-field="materialType" data-width="100" data-type="select" data-locked="true">物料类型</th>
							<th data-field="materialInfo" data-type="f7" data-visible="false">物料id</th>
							<th data-field="purchaseContractDetailInfo" data-type="f7" data-visible="false">采购明细id</th>
							<th data-field="materialName" data-locked="true" data-width="100">物料名称</th>
							<th data-field="specification" data-type="text" data-locked="true" data-width="100">规格</th>
							<th data-field="measureUnitName" data-type="text" data-locked="true" data-width="100">单位</th>
							<th data-field="price" data-type="number" data-locked="true" data-width="100">采购单价</th>
							<th data-field="settleCount" data-type="number" data-width="100">结算数量</th>
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
    var materialSettleDetailInfosEntry;
    var materialSettleDetailInfosEntryObj;
    //采购合同信息
    function selectPurchaseContract(oldData,newData){
        $("input[name='supplyCompany']").val(newData.supplyCompany);
		//获取采购物料清单
        webUtil.ajaxData({url:"ec/purchase/purchasecontract/detail",data:{purchaseContractId:newData.id},async:false,success:function(data){
            if(data.statusCode==0){
                //清空表格
                materialSettleDetailInfosEntry.loadData(data.data);
            }
        }});
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
        //materialSettleDetailInfosEntry.endEdit();
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
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{}"+
            ",toolbar:{title:'结算物料清单',addBtn:null,removeBtn:null}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "物料结算",billModel:2,
            baseUrl : "ec/settle/materialsettle",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        materialSettleDetailInfosEntryObj = editUI.getEntryObj('materialSettleDetailInfos');
        if(!webUtil.isEmpty(materialSettleDetailInfosEntryObj)){
            materialSettleDetailInfosEntry = materialSettleDetailInfosEntryObj.entry;
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>