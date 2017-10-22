<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>方案类型</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
	<div id="listPanel" class="panel">
		<div id="table-toolbar" style="height:40px;margin-bottom:5px;">
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="number">编码</th>
					<th data-field="name">名称</th>
					<th data-field="skillType" data-type="select">技术类别</th>
					<th data-field="enabled" data-type="checkbox">启用</th>
					<th data-field="remark" data-width="450">备注</th>
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

$(document).ready(function() {
	var height = top.getTopMainHeight()-90;
	var editWin ={title:'方案类型',width:620,height:360};
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1
		,baseUrl:'ec/basedata/skillclasss',editWin:editWin,toolbar:"#table-toolbar"
		,extendTableOptions:{height:height}});
	listUI.onLoad();
})
</script>
</html>