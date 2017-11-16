<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;"></div>
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
								<th data-field="parent_number" data-align="left">上级编码</th>
								<th data-field="number" data-align="left">编码</th>
								<th data-field="name">名称</th>
								<th data-field="icon" data-formatter="fromterIcon">图标</th>
								<th data-field="url"  data-align="left" data-formatter="fromterUrl">地址</th>
								<th data-field="sysMenu" data-type="checkbox">系统菜单</th>
								<th data-field="onShow" data-type="checkbox">显示</th>
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
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		var tree = thisOrgList.tree;
		if(webUtil.isEmpty(params)&&tree&&tree.getNodes().length>0){
			webUtil.mesg('请先选择的组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
function fromterIcon(value, row, index){
	if(!webUtil.isEmpty(value)){
		var iconHtml = '<i class= "';
		if(!webUtil.isEmpty(row.iconType)){
			iconHtml += row.iconType.key;
		}
		var iconCodeType = row.iconCodeType;
		if(!webUtil.isEmpty(iconCodeType)){
			if('CLASS'==iconCodeType.key){
				iconHtml+=' '+value;
			}
			iconHtml+='">';
			if('UNICODE'==iconCodeType.key){
				iconHtml+='&#x'+value+';';
			}
		}else{
			iconHtml+='">';
		}
		iconHtml+='</i>';
		return iconHtml;
	}
	return '';
}
function fromterUrl(value, row, index){
	var params = row.params;
	var url = value;
	if(!webUtil.isEmpty(params)){
		if(url.indexOf('?')>0) url +='?';
		url +=params;
	}
	return url;
}
function includeChild_click(){
	var thisParams = {includeChild:includeChild.getData()};
	thisOrgList.listUI.executeQueryByParams(thisParams);
}
$(document).ready(function() {
     var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
     includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
     includeChild.setData(true);
     
     var editWin ={title:'系统菜单配置',width:650,height:450};
     var height = top.getTopMainHeight()-48;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',baseUrl:'base/home/menus',title:'系统菜单',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
    	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-50}});
     thisOrgList.onLoad();
});

</script>
</html>