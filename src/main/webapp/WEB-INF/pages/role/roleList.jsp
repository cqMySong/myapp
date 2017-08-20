<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>角色信息</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
	<div id="listPanel" class="panel" style="padding:2px;">
		<div id="table-toolbar">
			<div class="btn-group">
				<button class="btn btn-success" type="button"><span class="fa fa-paperclip"></span>&nbsp;分配权限</button>
			</div>
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="number">编码</th>
					<th data-field="name">名称</th>
					<th data-field="enabled" data-type="checkbox">启用</th>
					<th data-field="remark" >备注</th>
				</tr>
			</thead>
		</table>
	</div>
</body>

<%@include file="../base/base_list.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
function beforeAction(opt){
	return true;
}

$(document).ready(function() {
	var editWin ={title:'角色信息',width:620,height:330};
	var listUI = $('#listPanel').listUI({tableEl:'#tblMain',baseUrl:'base/roles',editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:700}});
	listUI.onLoad();
})
</script>
</html>