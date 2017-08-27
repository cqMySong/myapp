<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>数据字典</title>
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
					<th data-field="pinyinCode">拼音码</th>
					<th data-field="dataDicType" data-type="select">数据类型</th>
					<th data-field="enabled" data-type="checkbox">启用</th>
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
	var editWin ={title:'数据字典',width:620,height:360};
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/basedata/datadics',
		editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:window.outerHeight-295}});
	listUI.onLoad();
})
</script>
</html>