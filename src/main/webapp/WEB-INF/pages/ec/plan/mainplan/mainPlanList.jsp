<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目主要工作安排摘要</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
    <div id="listPanel" class="panel">
		<div id="table-toolbar">
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="workType" data-type="select">工作分类</th>
					<th data-field="content">工作内容</th>
					<th data-field="beginDate" data-type="date">开始时间</th>
					<th data-field="endDate" data-type="date">完成时间</th>
					<th data-field="dutyPerson">责任人</th>
					<th data-field="isDelivery" data-type="checkbox">是否交办</th>
					<th data-field="deliveryPerson">交办接收人</th>
					<th data-field="workFollow" data-type="select">工作跟进</th>
					<th data-field="isDone" data-type="checkbox">是否完成</th>
					<th data-field="remark" >备注</th>
				</tr>
			</thead>
		</table>
	</div>
</body>

<%@include file="../../../base/base_list.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
var listUI;
function beforeAction(opt){
	return true;
}

function enableClick(btn){
	
}

$(document).ready(function() {
	var editWin ={title:'项目主要工作安排摘要',width:620,height:650};
	var height = top.getTopMainHeight()-45;
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/plan/mainplans'
		,editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height-40}});
	listUI.onLoad();
})
</script>
</html>