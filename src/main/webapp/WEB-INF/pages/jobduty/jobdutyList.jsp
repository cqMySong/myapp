<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>工作职责</title>
</head>

<script type="text/javascript">
</script>
<body style="padding: 5px;" >
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;"></div>
	<div class="mainContrainer">
		<div class="container panel">
			<div class="row">
				<div class="col-md-7" style="padding: 2px;">
					<table id="tblMain" style="width: 100%;border: 1px solid #bdc3d1;">
						 <thead >
							<tr>
								<th data-field="number" data-width="150">编码</th>
								<th data-field="name" data-width="250">名称</th>
								<th data-field="enabled" data-type="checkbox" data-width="100">启用</th>
								<th data-field="remark" data-type="textarea" data-width="350">职责描述</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="col-md-5" style="padding: 2px;">
					<div class="" id="tblMain2_toolbar">
						<div class="btn-group">
			                <button class="btn btn-success" type="button"><span class="fa fa-tag"></span>&nbsp;分配权限项</button>
		              	</div>
					</div>
					<table id="tblMain2" style="width: 100%;border: 1px solid #bdc3d1;">
						 <thead >
							<tr>
								<th data-field="number" >权限编码</th>
								<th data-field="name" >权限名称</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>

<%@include file="../base/base_list.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
var listUI;
function beforeAction(opt){
	return true;
}

function enableClick(btn){
	alert(btn.text);
}

$(document).ready(function() {
	var editWin ={title:'工作职责',width:620,height:360};
	var height = top.getTopMainHeight()-100;
	listUI = $('body').listUI({tableEl:'#tblMain',height:height,listModel:1,baseUrl:'base/jobdutys',editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height}})
	listUI.onLoad();
	  var itemTable_opt = {height:height,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
	 			,cache:false,pageSize:10,showToggle:true,search:true,toolbar:'#tblMain2_toolbar'
	 			,showColumns:true,idField:"id",mypagination:true,url:''};
	  $('#tblMain2').myDataTable(itemTable_opt);
	  
})
</script>
</html>