<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>材料申购(申购人员修改)</title>
	<style type="text/css">
		.mainContrainer {
			width: 100%;
			height: 100%;
			overflow:hidden;
		}
		.rightContainer {
			height: 100%;
			overflow:hidden;
		}
		.panel {
			width: 100%;
			height: 100%;
		}
	</style>
</head>
<script type="text/javascript">
</script>
<body >
<div class="panel" style="margin-bottom: 0px;padding-bottom: 0px;">
	<div class="mainContrainer">
		<div class="rightContainer" id="main_container">
			<table id="deptManagerGrid">
				<thead >
				<tr>
					<th data-field="materialNumber" rowspan="2" data-locked="true">材料编号</th>
					<th data-field="materialName" rowspan="2" data-locked="true">材料名称</th>
					<th data-field="specification" rowspan="2" data-locked="true">规格</th>
					<th data-field="materialUnit" rowspan="2" data-locked="true">单位</th>
					<th data-field="quantity" rowspan="2" data-locked="true">预算数量</th>
					<th data-field="budgetaryPrice" rowspan="2" data-locked="true">预算单价</th>
					<th data-field="stockCount" rowspan="2" data-locked="true">库存量</th>
					<th data-field="purchasePlan" colspan="4" data-locked="true">材料申购计划</th>
					<th data-field="submitDate" rowspan="2" data-type="datetime" data-locked="true">提交时间</th>
				</tr>
				<tr>
					<th data-field="applyNumber" data-locked="true">申购单号</th>
					<th data-field="purchaseNum" data-type="number">申购数量</th>
					<th data-field="arrivalTime" data-type="date">计划到场时间</th>
					<th data-field="cumulativePurchaseNum" data-locked="true">累计申购数量</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
    var deptManagerGrid;
    function initTable(){
        var height = top.getTopMainHeight();
        var businessKey = "${id}";
        var table_options = {type:'entry',height:height-295,striped:true,sortStable:false,showRefresh:false,selectModel:1
            ,cache:false,showToggle:false,search:false,toolbar:null,showColumns:false,idField:"id"};
        deptManagerGrid = $('#deptManagerGrid').myDataTable(table_options);
        deptManagerGrid.refreshData("ec/purchase/applymaterial/audit/query/data?type=material&businessKey="+webUtil.uuIdReplaceID(businessKey));
        deptManagerGrid.setEnabled(true);
        deptManagerGrid.initTableColumnEditor('click-cell',{editDataChanged:saveEditVal});
    }
    function saveEditVal($cell,obj){
        webUtil.ajaxData({url:"ec/purchase/applymaterial/audit/edit/data",
            data:{purchasePrice:obj.rowData.purchasePrice?obj.rowData.purchasePrice:"",
                purchaseArrivalTime:obj.rowData.purchaseArrivalTime?obj.rowData.purchaseArrivalTime:"",
                purchaseNum:obj.rowData.purchaseNum,
                arrivalTime:obj.rowData.arrivalTime?obj.rowData.arrivalTime:"",
                id:obj.rowData.id},
            async:false,
            success:function(data){
                if(data.statusCode==0){
                    return false;
                }
                var msgObj = {title:".::系统提示::.",type:"info"};//==1 提示
                msgObj.content = data.statusMesg;
                if(data.statusCode<0){//异常 错误
                    msgObj.title = ".::系统操作"+(data.statusCode==-100?"异常":"错误")+"::.";
                    msgObj.type = "error";
                }else if(data.statusCode==100){//警告
                    msgObj.title = ".::系统操作警告::.";
                    msgObj.type = "warning";
                }
                webUtil.showMesg(msgObj);
            }});
    }
    $(function(){
        var height = top.getTopMainHeight();
        $(".mainContrainer").height(height-290);
        initTable();
    });
</script>
</html>