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
<body style="padding: 2px 5px;" >
	<div id="listPanel" class="panel">
		<div id="table-toolbar">
			<div class="btn-group">
				<a id="resetEncrypt" class="btn btn-success">
					<span class="fa fa-retweet"></span>密码重置
				</a>
			</div>
		</div>
		
		
		<div class="row">
			<div class="col-md-7" style="padding: 2px;">
				<div class="panel-body" style="padding: 0px">
					<table id="tblMain">
						<thead>
							<tr>
								<th data-field="name">姓名</th>
								<th data-field="number">编码</th>
								<th data-field="linkers" >联系方式</th>
								<th data-field="userState" data-type="select">状态</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div class="col-md-5" style="padding: 2px;">
				<div class="" id="tblMain2_toolbar">
					<div class="btn-group">
		                <button class="btn btn-success" id="toAssignPermission" title="分配权限项"><span class="fa fa-tag"></span>&nbsp;分配</button>
		                <button class="btn btn-success" id="unAssignPermission" title="清除权限项"><span class="fa fa-trash"></span>&nbsp;清除</button>
		                <button class="btn btn-success" id="importPositionPermission" title="导入权限项"><span class="fa fa-tags"></span>&nbsp;导入</button>
					</div>
					<div class="btn-group">
						<div class="input-group" style="width: 250px;">
							<input class="form-control" id="queryPermissionVal" type="text" placeholder="快速查询(名称)">
							<span class="input-group-btn">
								<button class="btn btn-success" id="quickQueryPermission" type="button"><i class="fa fa-search"></i></button>
							</span>
						</div>
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
</body>

<%@include file="../base/base_list.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
var thisBaseUrl = 'base/users';
var thisOrgList ;
function reSetUserEncrypt(){
	var _selRows = thisOrgList.getSelectRow();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
		var _thisRowData = _selRows[0];
		var thisId = _thisRowData.id;
		var _thisURL = thisBaseUrl+'/resetEncrypt'
		webUtil.ajaxData({url:_thisURL,async:false,data:{id:thisId},success:function(data){
			thisOrgList.executeQuery();
		}});
	}
}
var showAssignUrl = webUtil.toUrl('base/permissionAssign/show');
var toAssignUrl = 'base/permissionAssign/assign';
var assignWin = {url:showAssignUrl,height:580,width:710,title:'权限分配',maxmin:false,uiParams:{leaf:'T'}
	,btns:['分配','取消'],btnCallBack:function(index,layerIndex,layero){
		if(layero){
			if(index==1){
				var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
				var datas = iframe_win.getData();
				var orgId = iframe_win.getSelOrg();
				if(!webUtil.isEmpty(orgId)&&!webUtil.isEmpty(datas)&&datas.length>0){
					var permissionIds = getAllChildrenNodes(datas[0],'');
					var pams = {targetId:curSourceId,permissionIds:permissionIds,orgId:orgId};
					webUtil.ajaxData({url:toAssignUrl,data:pams,async:true,success:function(data){
						load_tblMain2Data();
					}});
					return true;
				}else{
					webUtil.mesg("未选择任何权限数据或者为选择对应的权限组织!");
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
	var _selRows = thisOrgList.getSelectRow();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
		return _selRows[0].id;
	}
	return null;
}
function getSelectedRow(){
	var _selRows = thisOrgList.getSelectRow();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
		return _selRows[0];
	}
	return null;
}
var curSourceId ;
function toAssignPermission(){
	var selRow = getSelectedRow();
	if(!webUtil.isEmpty(selRow)){
		curSourceId = selRow.id;
		assignWin.url = showAssignUrl+'?targetId='+webUtil.uuIdReplaceID(curSourceId)+"&target=User";//权限到用户
		assignWin.title = '权限分部-'+selRow.name;
		webUtil.openWin(assignWin);
	}else{
		curSourceId = '';
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
		var _queryName = $('#queryPermissionVal').val();
		var url = hasAssignUrl+'?targetId='+webUtil.uuIdReplaceID(sourceId);
		if(!webUtil.isEmpty(_queryName)){
			url+='&permName='+_queryName;
		}
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
	thisOrgList = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:thisBaseUrl
		,editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height-40,selectChanaged:tblMain_selectChange}});
	thisOrgList.onLoad();
	
	$('#resetEncrypt').click(function(){
		reSetUserEncrypt();
	});
	
	$('#quickQueryPermission').click(function(){
		load_tblMain2Data();
	});
	$('#queryPermissionVal').keydown(function(e){
		if(e.which == "13"){//回车事件
			$("#quickQueryPermission").trigger('click');
		} 
	});
})
</script>
</html>