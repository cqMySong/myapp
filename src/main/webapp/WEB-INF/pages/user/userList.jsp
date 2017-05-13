<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<%@include file="../inc/webBase.inc"%>
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
					<th data-field="name" data-sortable="true">姓名</th>
					<th data-field="number" data-sortable="true">编码</th>
					<th data-field="cdate" data-sortable="true">创建时间</th>
					<th data-field="pwd" >密码</th>
				</tr>
			</thead>
		</table>
	</div>
</body>

<%@include file="../base/baselist.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
function beforeAction(opt){
	return true;
}

$(document).ready(function() {
	var listUI = $('#listPanel').listUI({el:'#teacher_table',dataUrl:'user/list',toolbar:"#table-toolbar"});
	listUI.onLoad();
})
</script>
</html>