<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>安全样板工作要点</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="listPanel" class="panel">
		<div id="table-toolbar">
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="number">编码</th>
					<th data-field="name">名称</th>
					<th data-field="branchBaseWbs_name">分部</th>
					<th data-field="subentry_name">分项</th>
					<th data-field="enabled" data-type="checkbox">启用</th>
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
	alert(btn.text);
}

$(document).ready(function() {
    var height = top.getTopMainHeight()-45;
	var editWin ={title:'安全样板工作要点',width:1000,height:height+90};
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/basedata/safetemplates'
		,editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height-40}});
	listUI.onLoad();
})
</script>
</html>