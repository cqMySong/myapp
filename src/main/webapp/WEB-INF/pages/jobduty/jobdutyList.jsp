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
								<th data-field="number" >编码</th>
								<th data-field="name" >名称</th>
								<th data-field="shortCutMenu_displayName" data-width="200">快捷菜单</th>
								<th data-field="enabled" data-type="checkbox" data-width="80">启用</th>
								<th data-field="remark" data-type="textarea" data-width="400">职责描述</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="col-md-5" style="padding: 2px;">
					<div class="" id="tblMain2_toolbar">
						<div class="btn-group">
			                <button class="btn btn-success" type="button" id="toAssignPermission"><span class="fa fa-tag"></span>&nbsp;分配权限项</button>
			                <button class="btn btn-success" type="button" id="unAssignPermission"><span class="fa fa-trash"></span>&nbsp;清除权限项</button>
		              	</div>
					</div>
					<table id="tblMain2" style="width: 100%;border: 1px solid #bdc3d1;">
						 <thead >
							<tr>
								<th data-field="pln" data-width="40%">权限编码</th>
								<th data-field="pdn"  data-width="55%">权限名称</th>
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

function beforeAction(opt){
	return true;
}

function afterAction(opt){
	if(OperateType.refesh==opt){
		load_tblMain2Data();
	}
}


function getSelectedId(){
	var _selRows = listUI.getSelectRow();
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
$(document).ready(function() {
	var editWin ={title:'工作职责',width:620,height:360};
	var height = top.getTopMainHeight()-100;
	
	var itemTable_opt = {height:height+40,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
	 			,cache:false,showToggle:true,search:false,toolbar:'#tblMain2_toolbar'
	 			,showColumns:true,idField:"id",mypagination:false,selectModel:2};
	tblMain2 = $('#tblMain2').myDataTable(itemTable_opt);
	$('#toAssignPermission').click(function(){
		toAssignPermission();
	});
	listUI = $('body').listUI({tableEl:'#tblMain',height:height,listModel:1,baseUrl:'base/jobdutys'
		,editWin:editWin,toolbar:"#table-toolbar"
		,extendTableOptions:{height:height,selectChanaged:tblMain_selectChange}})
	listUI.onLoad();
	$('#unAssignPermission').click(function(){
		unAssignPermission();
	});
})
</script>
</html>