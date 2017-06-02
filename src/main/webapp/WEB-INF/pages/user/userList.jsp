<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
	<div id="listPanel" class="panel" style="padding:2px;">
		<div id="table-toolbar">
				<div class="btn-group">
					<button class="btn btn-success" type="button">
						<span class="glyphicon glyphicon-file"></span>查询
					</button>
				</div>
		</div>
	     <table id="teacher_table">
			 <thead >
				<tr>
					<th data-field="name">姓名</th>
					<th data-field="number">编码</th>
					<th data-field="createDate" data-type="datetime">创建时间</th>
					<th data-field="passWord" >密码</th>
					<th data-field="remark" >备注</th>
					<th data-field="defOrg_name" >组织名称</th>
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
	var editWin ={title:'用户信息',width:620,height:450};
	var listUI = $('#listPanel').listUI({tableEl:'#teacher_table',baseUrl:'base/users',editWin:editWin,toolbar:"#table-toolbar"});
	listUI.onLoad();
})
</script>
</html>