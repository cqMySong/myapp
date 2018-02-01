<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>物料信息列表</title>
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
					<th data-field="number">编码</th>
					<th data-field="name">名称</th>
					<th data-field="specification">规格</th>
					<th data-field="unit_name">单位</th>
					<th data-field="materialType" data-type="select">类型</th>
					<th data-field="enabled" data-type="checkbox">启用</th>
					<th data-field="remark" >备注</th>
				</tr>
			</thead>
		</table>
	</div>
</body>

<%@include file="../../base/base_list.jsp"%>
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
	var editWin ={title:'物料信息',width:620,height:410};
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'base/materials',
		editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:window.outerHeight-295}});
	listUI.onLoad();
})
</script>
</html>