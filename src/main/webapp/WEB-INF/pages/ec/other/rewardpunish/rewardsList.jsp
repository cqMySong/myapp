<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>奖励通知单</title>
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
					<th data-field="orgUnit">受奖单位</th>
					<th data-field="rewardPunishTypeInfo_name">奖惩类别</th>
					<th data-field="grandDate" data-type="date">授予日期</th>
					<th data-field="item">奖励事项</th>
					<th data-field="measures">奖励办法</th>
					<th data-field="grantOrgUnit">授予单位</th>
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
	var editWin ={title:'奖励通知单',width:620,height:400};
	var height = top.getTopMainHeight()-45;
	listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/other/rewards'
		,editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height-40}});
	listUI.onLoad();
})
</script>
</html>