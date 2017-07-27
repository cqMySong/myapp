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
	<div class="ui-layout-north">
		<div id="table-toolbar">
				<div class="btn-group">
					<a id="resetEncrypt" class="btn btn-success">
						<span class="fa fa-retweet"></span>密码重置
					</a>
				</div>
		</div>
	</div>
	<div class="ui-layout-west" id="tree_container" style="margin: 0px;padding: 0px;">
		
	</div>
	<div id="listPanel" class="ui-layout-center">
		<div class="" id="tblMain_toolbar">
			<div class="input-group" style="width:160px;">
                <span class="input-group-addon" style="width:80px;">包含下级</span>
                <input id="includeChild" class="input-item" type="checkbox"/>
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

<%@include file="../base/base_treelist.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
var thisBaseUrl = 'base/users';
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
function reSetUserEncrypt(){
	var _selRows = thisOrgList.listUI.getSelectRow();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
		var _thisRowData = _selRows[0];
		var thisId = _thisRowData.id;
		var _thisURL = thisBaseUrl+'/resetEncrypt'
		webUtil.ajaxData({url:_thisURL,async:false,data:{id:thisId},success:function(data){
			thisOrgList.listUI.executeQuery();
		}});
	}
}
function includeChild_click(){
	var thisParams = {includeChild:includeChild.getData()};
	thisOrgList.listUI.executeQueryByParams(thisParams);
}
$(document).ready(function() {
	
	var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
    includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
    includeChild.setData(true);
    
    var editWin ={title:'用户信息',width:620,height:450};
    var height = 700;
    thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'base/orgs/tree',baseUrl:thisBaseUrl,title:'组织信息',height:height,
   	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
   	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height}});
    thisOrgList.onLoad();
    var myLayout = $('body').layout({ applyDefaultStyles: true,west__size:300});
    myLayout.sizePane('west',280);
    myLayout.sizePane('center',800);
	$('#resetEncrypt').click(function(){
		reSetUserEncrypt();
	});
})
</script>
</html>