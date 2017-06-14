<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:60px;margin-bottom:5px;">
		<div class="btn-group">
			<button id="syncSysPermission" class="btn btn-success" type="button">
				<span class="glyphicon glyphicon-file"></span>同步系统权限项
			</button>
		</div>
	</div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel">
				<div class="" id="tblMain_toolbar">
					<div class="input-group" style="width:160px;">
		                <span class="input-group-addon" style="width:80px;">包含下级</span>
		                <input id="includeChild" class="input-item" type="checkbox"/>
	              	</div>
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead>
							<tr>
								<th data-field="longNumber" data-formatter ="treeNumber_formarter" data-align="left">编码</th>
								<th data-field="name">名称</th>
								<th data-field="url" data-align="left">请求地址</th>
								<th data-field="type" data-formatter="type_formarter">权限类型</th>
								<th data-field="parent_number" >上级编码</th>
								<th data-field="remark" >备注</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="../base/base_treelist.jsp"%>
<script type="text/javascript">
var thisOrgList ;
var includeChild;
function treeNumber_formarter(value, row, index){
	var txt = value;
	if(txt){
		while(txt.indexOf('!')>0){
			txt = txt.replace('!','.');
		}
	}
	return txt;
}
function type_formarter(value, row, index){
	var txt = value;
	if(value=='PAGE'){
		txt = '菜单';
	}else if(value=='FUNCTION'){
		txt = '功能';
	}else if(value=='PAGEADDFUNCTION'){
		txt = '菜单&功能';
	}
	return txt;
}
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		if(webUtil.isEmpty(params)){
			webUtil.mesg('请先选择的组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
	params.ex ={name:'参数代码'};
}
function includeChild_click(){
	var thisParams = {includeChild:includeChild.getData()};
	thisOrgList.listUI.executeQueryByParams(thisParams);
}
function sysnc_sysPermission(){
	var _url = "base/permissions/sync"
	webUtil.ajaxData({url:_url,async:false,success:function(data){
		var retData = data.data;
		var content = '本次权限同步共计:'+retData.total+"条记录。\r\n 更新:"+retData.update+'条，插入:'+retData.insert+'条。';
		var mesg = {};
		layer.alert(content, {icon: 6,end:function(index){
			thisOrgList.onLoad();
		}});  
	}});
}
$(document).ready(function() {
     var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
     includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
     includeChild.setData(true);
     
     var editWin ={title:'权限项信息',url:'base/permission/edit',width:620,height:430};
     var height = 680;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',baseUrl:'base/permissions',title:'权限信息',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
    	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false}});
     thisOrgList.onLoad();
     
     $('#syncSysPermission').click(function(){
    	 sysnc_sysPermission();
     });
});

</script>
</html>