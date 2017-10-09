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
<body style="padding: 5px;" >
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;">
		<div class="btn-group">
			<a id="resetEncrypt" class="btn btn-success">
				<span class="fa fa-retweet"></span>密码重置
			</a>
		</div>
	</div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel">
				<div class="row">
					<div class="col-md-8" style="padding: 2px;">
						<div class="" id="tblMain_toolbar">
							<div class="input-group" style="width:160px;">
				                <span class="input-group-addon" style="width:80px;">包含下级</span>
				                <input id="includeChild" class="input-item" type="checkbox"/>
			              	</div>
						</div>
						<div class="panel-body" style="padding: 0px">
							<table id="tblMain">
								<thead >
									<tr>
										<th data-field="defOrg_name" >所属组织</th>
										<th data-field="name">姓名</th>
										<th data-field="number">编码</th>
										<th data-field="linkers" >联系方式</th>
										<th data-field="userState" data-type="select">状态</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div class="col-md-4" style="padding: 2px;">
						<div class="" id="tblMain2_toolbar">
							<div class="btn-group">
				                <button class="btn btn-success" id="toAssignPermission"><span class="fa fa-tag"></span>&nbsp;分配权限项</button>
				                <button class="btn btn-success" id="unAssignPermission"><span class="fa fa-trash"></span>&nbsp;清除权限项</button>
				                <button class="btn btn-success" id="importPositionPermission"><span class="fa fa-tags"></span>&nbsp;导入岗位权限</button>
			              	</div>
						</div>
						<table id="tblMain2" style="width: 100%;border: 1px solid #bdc3d1;">
							 <thead >
								<tr>
									<th data-field="pln" data-width="40%" data-align="left">权限编码</th>
									<th data-field="pdn" data-width="55%" data-align="left">权限名称</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
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
var showAssignUrl = webUtil.toUrl('base/permissionAssign/show');
var toAssignUrl = 'base/permissionAssign/assign';
var assignWin = {url:showAssignUrl,height:580,width:710,title:'权限分配',maxmin:false,uiParams:{leaf:'T'}
	,btns:['分配','取消'],btnCallBack:function(index,layerIndex,layero){
		if(layero){
			if(index==1){
				var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
				var datas = iframe_win.getData();
				if(!webUtil.isEmpty(datas)&&datas.length>0){
					var permissionIds = getAllChildrenNodes(datas[0],'');
					var pams = {targetId:curSourceId,permissionIds:permissionIds};
					webUtil.ajaxData({url:toAssignUrl,data:pams,async:true,success:function(data){
						load_tblMain2Data();
					}});
					return true;
				}else{
					webUtil.mesg("未选择任何数据!");
					return false;
				}
			}
		}
		return true;
}};
function getAllChildrenNodes(treeNode,result){
    if (treeNode.isParent) {
      var childrenNodes = treeNode.children;
      if (childrenNodes) {
          for (var i = 0; i < childrenNodes.length; i++) {
        	  var curChdren = childrenNodes[i];
        	  if(curChdren.isParent){
        		  result = getAllChildrenNodes(curChdren, result);
        	  }else{
        		  if(!webUtil.isEmpty(result)){
        			  result +=',';
        		  }
        		  result += childrenNodes[i].id;
        	  }
          }
      }
  }
  return result;
}
function getSelectedId(){
	var _selRows = thisOrgList.listUI.getSelectRow();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
		return _selRows[0].id;
	}
	return null;
}
var curSourceId ;
function toAssignPermission(){
	curSourceId = getSelectedId();
	if(!webUtil.isEmpty(curSourceId)){
		assignWin.url = showAssignUrl+'?targetId='+webUtil.uuIdReplaceID(curSourceId);
		webUtil.openWin(assignWin);
	}else{
		webUtil.mesg('请先选择对应的工作职责信息!');
	}
}

var hasAssignUrl = 'base/permissionAssign/hasAssign';
var tblMain2 ;
function tblMain_selectChange(ridx,row,rowDom){
	load_tblMain2Data();
}

function load_tblMain2Data(){
	var sourceId = getSelectedId();
	if(!webUtil.isEmpty(sourceId)){
		var url = hasAssignUrl+'?targetId='+webUtil.uuIdReplaceID(sourceId);
		webUtil.ajaxData({url:url,async:true,success:function(data){
			var tbDatas = data.data;
			tblMain2.loadData(tbDatas);
		}});
	}else{
		tblMain2.loadData([]);
	}
}
var unAssignURL = 'base/permissionAssign/unassign';
function unAssignPermission(){
	var selRows = tblMain2.getSelections();
	if(!webUtil.isEmpty(selRows)&&selRows.length>0){
		var permissionIds = '';
		for(var i=0;i<selRows.length;i++){
			if(!webUtil.isEmpty(permissionIds)) permissionIds+=',';
			permissionIds+=selRows[i].id;
		}
		webUtil.ajaxData({url:unAssignURL,data:{permissionIds:permissionIds},success:function(data){
			load_tblMain2Data();
		}});
	}else{
		webUtil.mesg('请先选择对应的工作职责权限信息!');
	}
}
//分配岗位权限
var assignPositionUrl = 'base/permissionAssign/assignPosition';
function assignPositionPermission(){
	var sourceId = getSelectedId();
	if(!webUtil.isEmpty(sourceId)){
		var url = assignPositionUrl+'?targetId='+webUtil.uuIdReplaceID(sourceId);
		webUtil.ajaxData({url:url,async:true,success:function(data){
			load_tblMain2Data();
		}});
	}else{
		tblMain2.loadData([]);
	}
}

$(document).ready(function() {
	
	var _checkOpt = {event:{name:'click',callBack:includeChild_click}};
    includeChild = $('#includeChild').myComponet('checkbox',{method:'init',opt:_checkOpt});
    includeChild.setData(true);
    
    var height = top.getTopMainHeight()-45;
    
    var itemTable_opt = {height:height,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
 			,cache:false,showToggle:true,search:false,toolbar:'#tblMain2_toolbar'
 			,showColumns:true,idField:"id",mypagination:false,selectModel:2};
	tblMain2 = $('#tblMain2').myDataTable(itemTable_opt);
	$('#toAssignPermission').click(function(){
		toAssignPermission();
	});
	$('#unAssignPermission').click(function(){
		unAssignPermission();
	});
	$('#importPositionPermission').click(function(){
		assignPositionPermission();
	});
	var editWin ={title:'用户信息',width:620,height:575};
    thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'base/orgs/tree',baseUrl:thisBaseUrl,title:'组织信息',height:height,
   	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true}
   	 ,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,selectChanaged:tblMain_selectChange}});
    thisOrgList.onLoad();
	$('#resetEncrypt').click(function(){
		reSetUserEncrypt();
	});
})
</script>
</html>