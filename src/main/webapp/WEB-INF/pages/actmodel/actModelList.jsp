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
				<button class="btn btn-success" type="button" id="actAdd">
					<span class="fa fa-file-o"></span>&nbsp;新增
				</button>
				<button class="btn btn-success" type="button" id="actEdit">
					<span class="fa fa-edit"></span>&nbsp;修改
				</button>
				<button class="btn btn-success" type="button" id="actDel">
					<span class="fa fa-trash"></span>&nbsp;删除
				</button>
				<button class="btn btn-success" type="button" id="actDeploy">
					<span class="fa fa-file-o"></span>&nbsp;部署
				</button>
				<button class="btn btn-success" type="button" id="actExport">
					<span class="fa fa-file-o"></span>&nbsp;导出
				</button>
			</div>
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="name">名称</th>
					<th data-field="key">编码</th>
					<th data-field="category" data-formatter="category_formatter">类型</th>
					<th data-field="version">版本号</th>
					<th data-field="createTime" data-type="datetime">创建时间</th>
					<th data-field="lastUpdateTime" data-type="datetime">最后更新时间</th>
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
var thisBaseUrl = 'base/actmodels';
function beforeAction(opt){
	return true;
}
function category_formatter(value, row, index){
	var txt = value;
	if(value=='BUDGET'){
			txt = '预算审核';
	}else if(value=='DRAWING'){
			txt = '图纸审核';
	}
	return txt;
}
$(document).ready(function() {
	var editWin ={title:'模型信息',width:620,height:380};
	thisListUI = $('#listPanel').listUI({tableEl:'#tblMain',height:680,baseUrl:thisBaseUrl,editWin:editWin,hasDefToolbar:false,toolbar:"#table-toolbar"});
	thisListUI.onLoad();
	//添加模型
	$('#actAdd').on('click',function(){
			var actModel = thisListUI.tblMain.getSelections();
			var url = app.root+"/"+thisBaseUrl+"/addnew";
			webUtil.openWin({title:'模型信息-新增',btns:null,width:620,height:340,url:url});
	});
	//修改模型
	$('#actEdit').on('click',function(){
       var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
			var url = app.root+"/act/process-editor/modeler.jsp?modelId="+_selRows[0].id;
			webUtil.openWin({title:'模型信息',width:(window.outerWidth-20),height:(window.outerHeight-100),url:url});
		}else{
			webUtil.mesg('请先选中对应的数据行，方可进行编辑操作!');
		}
	});
	//删除模型
	$('#actDel').on('click',function(){
			var _selRows = thisListUI.tblMain.getSelections();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				webUtil.showConfirm({title:"删除提醒",content:"你将删除["+_selRows.length+"]条记录信息，是否继续?",
						callBack:function(ok){
					if(ok){
							var seleIds = _selRows[0].id;
							var _thisURL = thisBaseUrl+'/remove';
							var _data = {};
							_data.id = seleIds;
							//同步删除操作
							webUtil.ajaxData({url:_thisURL,async:false,data:_data,success:function(data){
									thisListUI.executeQuery();
									}});
							}
				}});
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行删除操作!');
			}
	});
	//部署模型
	$('#actDeploy').on('click',function(){
			var _selRows = thisListUI.tblMain.getSelections();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
					webUtil.showConfirm({title:"部署提醒",content:"你将部署模型["+_selRows[0].name+"]，是否继续?",
							callBack:function(ok){
						if(ok){
								var seleIds = _selRows[0].id;
								var _thisURL = thisBaseUrl+'/deploy';
								var _data = {};
								_data.id = seleIds;
								//同步删除操作
								webUtil.ajaxData({url:_thisURL,async:false,data:_data,success:function(data){
										thisListUI.executeQuery();
										}});
								}
						}});
			}else{
					webUtil.mesg('请先选中对应的数据行，方可进行部署操作!');
			}
	});

	//导出模型
	$('#actExport').on('click',function(){
			var _selRows = thisListUI.tblMain.getSelections();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
					webUtil.showConfirm({title:"导出提醒",content:"你将导出模型["+_selRows[0].name+"]，是否继续?",
							callBack:function(ok){
									if(ok){
											var seleIds = _selRows[0].id;
											var _thisURL = app.root+"/"+thisBaseUrl+'/export/'+seleIds;
											window.location.href = _thisURL;
									}
							}});
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行导出操作!');
			}
	});
})
</script>
</html>