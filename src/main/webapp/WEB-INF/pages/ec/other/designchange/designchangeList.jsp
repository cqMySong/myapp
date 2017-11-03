<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>设计变更(洽商)</title>
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
					<th data-field="changeType" data-type="select">变更类型</th>
					<th data-field="number">编号</th>
					<th data-field="changePlace">变更部位</th>
					<th data-field="changeContent">变更内容</th>
					<th data-field="changeDate" data-type="date">变更时间</th>
					<th data-field="disclosureType" data-type="select">交底情况</th>
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
	var editWin ={title:'设计变更(洽商)',width:620,height:450};
	var height = top.getTopMainHeight()-45;
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/other/designchanges'
		,editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height-40}});
	listUI.onLoad();
})
</script>
</html>