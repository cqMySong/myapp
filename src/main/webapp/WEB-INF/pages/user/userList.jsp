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
<body style="padding: 5px;margin: 0px;" >
	<div id="listPanel" class="panel" style="padding:2px;">
		<div id="table-toolbar">
				<div class="btn-group">
					<a id="resetEncrypt" class="btn btn-success">
						<span class="fa fa-retweet"></span>密码重置
					</a>
				</div>
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="name">姓名</th>
					<th data-field="number">编码</th>
					<th data-field="defOrg_name" >所属组织</th>
					<th data-field="linkers" >联系方式</th>
					<th data-field="userState" data-type="select">状态</th>
					<th data-field="createDate" data-type="datetime">创建时间</th>
					<th data-field="passWord" data-type="password">密码</th>
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
var thisListUI ;
var thisBaseUrl = 'base/users';
function beforeAction(opt){
	return true;
}
function userState_formarter(value, row, index){
	var txt = value;
	if(value=='ENABLE'){
		txt = '正常';
	}else if(value=='DISABLE'){
		txt = '失效';
	}else if(value=='FREEZE'){
		txt = '冻结';
	}
	return txt;
}
function reSetUserEncrypt(){
	var _selRows = thisListUI.getSelectRow();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
		var _thisRowData = _selRows[0];
		var thisId = _thisRowData.id;
		var _thisURL = thisBaseUrl+'/resetEncrypt'
		webUtil.ajaxData({url:_thisURL,async:false,data:{id:thisId},success:function(data){
			thisListUI.executeQuery();
		}});
	}
}

$(document).ready(function() {
	var editWin ={title:'用户信息',width:620,height:450};
	thisListUI = $('#listPanel').listUI({tableEl:'#tblMain',height:680,baseUrl:thisBaseUrl,editWin:editWin,toolbar:"#table-toolbar"});
	thisListUI.onLoad();
	
	$('#resetEncrypt').click(function(){
		reSetUserEncrypt();
	});
})
</script>
</html>