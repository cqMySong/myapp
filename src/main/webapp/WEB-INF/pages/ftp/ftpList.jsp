<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>FTP服务器配置</title>
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
						<span class="glyphicon glyphicon-file"></span>连接测试
					</button>
				</div>
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="name">名称</th>
					<th data-field="number">编码</th>
					<th data-field="host">服务器地址</th>
					<th data-field="root">根目录</th>
					<th data-field="userName">用户名</th>
					<th data-field="password" data-type="password">密码</th>
					<th data-field="port">端口</th>
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
	var editWin ={title:'FTP服务器信息',width:620,height:480};
	var listUI = $('#listPanel').listUI({tableEl:'#tblMain',baseUrl:'base/ftps',editWin:editWin,toolbar:"#table-toolbar"});
	listUI.onLoad();
})
</script>
</html>