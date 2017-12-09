<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>当期图算用量</title>
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
					<span class="input-group-addon lable">图算单号</span>
					<input class="require form-control input-item" name="number">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">图算名称</span>
					<input name="name" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">盘存名称</span>
					<input name="stockInventoryInfo" class="require input-item form-control"
						   data-opt="{type:'f7',dataChange:selectInventory,uiWin:{title:'盘存信息',height:600,width:800,url:'ec/stock/inventoryF7',uiParams:getParams}}" />
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">开始日期</span>
					<input type="text" name="startDate" class="form-control input-item require read"
						   data-opt="{type:'date'}">
				</div>
			</div>

		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">结束日期</span>
					<input type="text" name="endDate" class="form-control input-item require read"
						   data-opt="{type:'date'}">
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
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">备注</span>
					<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="stockCalculationDetailInfos" class="input-entry" >
					<thead>
						<tr>
							<th data-field="stockInventoryDetailInfo" data-type="f7" data-visible="false">盘存明细id</th>
							<th data-field="materialType" data-width="100" data-type="select" data-locked="true">材料类型</th>
							<th data-field="number"  data-width="150" data-locked="true">物料编码</th>
							<th data-field="material" data-type="f7"  data-width="150" data-locked="true">物料名称</th>
							<th data-field="specification" data-type="text" data-locked="true" data-width="100">规格</th>
							<th data-field="measureUnit" data-type="text" data-locked="true" data-width="100">单位</th>
							<th data-field="stockCount" data-type="number" data-locked="true" data-width="100">库存数量</th>
							<th data-field="inventoryCount" data-type="number" data-locked="true" data-width="100">盘存数量</th>
							<th data-field="calculationCount" data-type="number" data-width="100">图算用量</th>
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
    var stockCalculationDetailInfosEntry;
    var stockCalculationDetailInfosEntryObj;
    function selectInventory(oldData,newData){
        $("input[name='startDate']").myComponet(DataType.date,{method:'setdata',opt:newData.startDate});
        $("input[name='endDate']").myComponet(DataType.date,{method:'setdata',opt:newData.endDate});
        //获取物料清单信息
        webUtil.ajaxData({url:"ec/stock/inventory/detail",data:{inventoryId:newData.id},async:false,success:function(data){
          if(data.statusCode==0){
               //清空表格
              stockCalculationDetailInfosEntry.loadData(data.data);
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
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{}"+
            ",toolbar:{title:'图算用量物料列表'}}";
		$("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "图算用量",billModel:2,
            baseUrl : "ec/stock/calculation",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        stockCalculationDetailInfosEntryObj = editUI.getEntryObj('stockCalculationDetailInfos');
        if(!webUtil.isEmpty(stockCalculationDetailInfosEntryObj)){
            stockCalculationDetailInfosEntry = stockCalculationDetailInfosEntryObj.entry;
            stockCalculationDetailInfosEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>