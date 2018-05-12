<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>办理流转已经列表</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;margin: 0px;" class="panel">
	<div id="listPanel" class="panel" style="padding:2px;">
		<div id="table-toolbar">
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="taskName">执行环节</th>
					<th data-field="assigneeName">执行人</th>
					<th data-field="pass" data-formatter="formatter_pass">审批结果</th>
					<th data-field="reason">审批意见</th>
					<th data-field="startTime" data-type="datetime">开始时间</th>
					<th data-field="endTime" data-type="datetime">结束时间</th>
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
var thisListUI ;
function beforeAction(opt){
	return true;
}
function formatter_pass(value, row, index){
		var txt = value;
		if(value=="1"){
			txt = '同意';
		}else if(value=="0"){
			txt = '不同意';
		}
		return txt;
}
$(document).ready(function() {
    var height = top.getTopMainHeight()-50;
    var winHeight = height+150;
	thisListUI = $('#listPanel').listUI({tableEl:'#tblMain',
			extendTableOptions:{url:'base/backlogs/histoic/flow/${procInsId}',height:winHeight>450?450:winHeight},hasDefToolbar:false});
	thisListUI.onLoad();
});
</script>
</html>