<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>工程预结一览表</title>
<style type="text/css">
.mainContrainer {
	width: 100%;
	height: 100%;
	overflow:hidden;
	padding: 0px 2px 2px 2px;
}
.leftContainer {
	width: 260px;
	height: 100%;
	float: left;
}
.rightContainer {
	height: 100%;
	overflow:hidden;
	padding-left: 5px;
}
.panel {
	width: 100%;
	height: 100%;
	padding: 0px 2px 2px 2px;
}
</style>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
<div class="panel">
	<div class="mainContrainer">
		<div class="leftContainer" style="border-right: 2px solid #d8dce3" id="left_container">
		</div>
		<div class="rightContainer" id="main_container">
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<thead>
						<col/><col/><col/><col/><col/><col/><col/><col/><col/><col/>
					</thead>
					<tbody id="content">

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
    var orgTree;
    var curSelOrg;
    function treeClick(event, treeId, treeNode){
        if(webUtil.isEmpty(curSelOrg)) curSelOrg = {id:'xyz'};
        if(webUtil.isEmpty(curSelOrg.id)) curSelOrg.id = 'xyz';
        if(curSelOrg.id!=treeNode.id){
            curSelOrg = treeNode;
            queryLedger();
        }
    }
    function initOrgTree(){
        var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
            ,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
            ,callback:{onClick:treeClick}
        };
        var treeViewer = $('#left_container').myTreeViewer(null);
        treeViewer.init({theme:"panel-success",title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
        treeViewer.addTree(treeOpt,[]);
        orgTree = treeViewer.getTree();
        treeViewer.addRefreshBtn({clickFun:function(btn){
            loadTreeData();
        }});
        loadTreeData();
    }
    function loadTreeData(){
        webUtil.ajaxData({url:'ec/basedata/projects/projectTree',async:false,success:function(data){
            var treeDatas = data.data;
            if (treeDatas.length>0&&!webUtil.isEmpty(orgTree)) {
                orgTree.reLoadTree(treeDatas)
                orgTree.selectNodeByIndex(0);
            }
        }});
    }
    $(function(){
        var height = top.getTopMainHeight();
        $(".mainContrainer").height(height);
        initOrgTree();
	});
    function queryLedger(){
        webUtil.ajaxData({url:'ec/engineering/proprietorledgers/query',
			data:{projectId:curSelOrg.id},
			async:false,
			success:function(data){
				var ledgerData = data.data;
				var strContent = "";
				if(ledgerData['budgeting']&&ledgerData['budgeting'].length>0){
                    strContent+="<tr><th rowspan='"+(ledgerData['budgeting'].length+2)+"' " +
						"style='text-align: center;vertical-align: middle;'>工程预算</th>" +
						"<th>名称</th>" +
						"<th colspan='4'>计价范围</th><th colspan='2'>金额（元）</th>" +
						"<th colspan='2'>计价依据</th>" +
						"</tr>";
                    var totalBudgetAmount = 0;
                    $.each(ledgerData['budgeting'],function(i,v){
                        strContent+="<tr>" +
                            "<td>"+v["name"]+"</td>" +
							"<td colspan='4'>"+v["rangeValuation"]+"</td>" +
							"<td colspan='2'>"+v["amount"]+"</td>" +
                            "<td colspan='2'>"+v["basisValuation"]+"</td>" +
							"</tr>";
                        totalBudgetAmount+=parseFloat(v["amount"]);
					});
                    strContent+="<tr>" +
                        "<th>合计</th><th colspan='4'></th><th colspan='2'>"+totalBudgetAmount+"</th>" +
                        "<th colspan='2'></th>" +
                        "</tr>";
				}
                if(ledgerData['payment']&&ledgerData['payment'].length>0){
                    strContent+="<tr><th rowspan='"+(ledgerData['payment'].length+2)+"' " +
						"style='text-align: center;vertical-align: middle;'>工程进度款</th>" +
                        "<th>名称</th><th colspan='2'>报送时间</th><th colspan='2'>报送金额（元）</th>" +
                        "<th>核定金额（元）</th><th>实际付款金额（元）</th>" +
                        "<th>实际支付比例</th><th>约定支付比例</th>" +
                        "</tr>";
                    var totalDeliveryAmount = 0;
                    var totalApprovedAmount = 0;
                    var totalPaymentAmount = 0;
                    var deliveryDate = null;
                    $.each(ledgerData['payment'],function(i,v){
                        deliveryDate = new Date();
                        deliveryDate.setTime(v["deliveryDate"]);
                        strContent+="<tr>" +
                            "<td>"+v["name"]+"</td>" +
                            "<td colspan='2'>"+deliveryDate.format("yyyy-MM-dd")+"</td>" +
                            "<td colspan='2'>"+v["deliveryAmount"]+"</td>" +
                            "<td>"+v["approvedAmount"]+"</td>" +
                            "<td>"+v["paymentAmount"]+"</td>" +
                            "<td>"+v["actualRatio"]+"</td>" +
                            "<td>"+v["paymentRatio"]+"</td>" +
                            "</tr>";
                        totalDeliveryAmount+=parseFloat(v["deliveryAmount"]);
                        totalApprovedAmount+=parseFloat(v["approvedAmount"]);
                        totalPaymentAmount+=parseFloat(v["paymentAmount"]);
                    });
                    strContent+="<tr>" +
                        "<th>合计</th><th colspan='2'></th><th colspan='2'>"+totalDeliveryAmount+"</th>" +
                        "<th>"+totalApprovedAmount+"</th><th>"+totalPaymentAmount+"</th><th></th><th></th>" +
                        "</tr>";
                }
                if(ledgerData['settle']&&ledgerData['settle'].length>0){
                    strContent+="<tr><th rowspan='"+(ledgerData['settle'].length+2)+"' " +
						"style='text-align: center;vertical-align: middle;'>工程结算</th>" +
                        "<th>名称</th><th colspan='4'>计价范围</th><th colspan='2'>金额（元）</th>" +
                        "<th colspan='2'>计价依据</th>" +
                        "</tr>";
                    var totalBudgetAmount = 0;
                    $.each(ledgerData['settle'],function(i,v){
                        strContent+="<tr>" +
                            "<td>"+v["name"]+"</td>" +
                            "<td colspan='4'>"+v["rangeValuation"]+"</td>" +
                            "<td colspan='2'>"+v["amount"]+"</td>" +
                            "<td colspan='2'>"+v["basisValuation"]+"</td>" +
                            "</tr>";
                        totalBudgetAmount+=parseFloat(v["amount"]);
                    });
                    strContent+="<tr>" +
                        "<th>合计</th><th colspan='4'></th><th colspan='2'>"+totalBudgetAmount+"</th>" +
                        "<th colspan='2'></th>" +
                        "</tr>";
                }
				$("#content").html(strContent);
        	}
        });
	}
</script>
</html>