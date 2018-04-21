<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>劳务人员登记备案台帐</title>
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
					<th data-field="name">姓名</th>
					<th data-field="sex" data-type="select">性别</th>
					<th data-field="age">年龄</th>
					<th data-field="workType">工种</th>
					<th data-field="homeAddress">家庭地址</th>
					<th data-field="companyDate" data-type="date">进单位日期</th>
					<th data-field="idCardNo">身份证号</th>
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
	var height = top.getTopMainHeight()-45;
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/safty/saftyedubooks'
		,editWin:undefined,toolbar:"#table-toolbar",hasDefToolbar:false,extendTableOptions:{height:height-40}});
	listUI.onLoad();
})
</script>
</html>