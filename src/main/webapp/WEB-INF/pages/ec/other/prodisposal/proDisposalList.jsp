<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目主要工作问题处理</title>
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
					<th data-field="bizDate" data-type="date">开始时间</th>
					<th data-field="workType" data-type="select">问题分类</th>
					<th data-field="proDescription">存在的问题</th>
					<th data-field="disposal">解决办法</th>
					<th data-field="workFollow" data-type="select">工作跟进</th>
					<th data-field="lastestDate" data-type="date">最迟解决时间</th>
					<th data-field="isDone" data-type="checkbox">是否解决</th>
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
	var editWin ={title:'项目主要工作问题处理',width:620,height:650};
	var height = top.getTopMainHeight()-45;
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/other/prodisposals'
		,editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height-40}});
	listUI.onLoad();
})
</script>
</html>