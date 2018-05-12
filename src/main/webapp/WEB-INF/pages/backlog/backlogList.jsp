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
<body style="padding: 5px;margin: 0px;" class="panel">
	<div id="listPanel" class="panel" style="height:40px;padding-top: 1px;margin-bottom: 5px;">
		<div id="table-toolbar">
			<div class="btn-group">
				<button class="btn btn-success" type="button" id="transact">
					<span class="fa fa-file-o"></span>&nbsp;办理
				</button>
				<button class="btn btn-success" type="button" id="claim">
					<span class="fa fa-file-o"></span>&nbsp;签收
				</button>
				<button class="btn btn-success" type="button" id="opinion">
					<span class="fa fa-edit"></span>&nbsp;查看意见
				</button>
				<button class="btn btn-success" type="button" id="schedule">
					<span class="fa fa-edit"></span>&nbsp;流程进度
				</button>
			</div>
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="title">标题</th>
					<th data-field="taskName">当前环节</th>
					<th data-field="name" >业务名称</th>
					<th data-field="createDate" data-type="datetime">创建时间</th>
					<th data-field="status" data-formatter="statusFormatter">状态</th>
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
var thisBaseUrl = 'base/backlogs';
function statusFormatter(value, row, index){
	if(value=='todo'){
	    return "办理";
	}
	return "签收";
}
$(document).ready(function() {
    var height = top.getTopMainHeight()-50;
	thisListUI = $('#listPanel').listUI({tableEl:'#tblMain',height:height,baseUrl:thisBaseUrl,
			hasDefToolbar:false,toolbar:"#table-toolbar",extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-45,sortStable:false}});
	thisListUI.onLoad();
	var winHeight = height+150;
	//办理
	$('#transact').on('click',function(){
		var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
			var backLogData = _selRows[0];
            if(backLogData.status!='todo'){
                webUtil.mesg(backLogData.title+'未指定办理人,请先签收!');
                return false;
            }
			var url = app.root+"/"+thisBaseUrl+"/transact/"+backLogData.id+"/"+backLogData.taskDefinitionKey+
					"/"+backLogData.processInstanceId+"/"+backLogData.processDefinitionId
					+"/"+backLogData.status+"/"+backLogData.executionId;
			webUtil.openWin({title:'办理流程',btns:null,operate:OperateType.audit,width:(window.outerWidth-80),
					height:winHeight>800?800:winHeight,url:url,colseCallBack:function(){
						thisListUI.executeQuery();
					}});
		}else{
			webUtil.mesg('请先选中对应的数据行，方可进行办理操作!');
		}
	});
	//认领
    $('#claim').on('click',function(){
        var _selRows = thisListUI.tblMain.getSelections();
        if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
            var backLogData = _selRows[0];
            if(backLogData.status=='todo'){
                webUtil.mesg(backLogData.title+'已指定办理人,不需签收!');
                return false;
			}
            webUtil.showConfirm({title:"签收任务",content:"你将签收["+backLogData.title+"]信息，是否继续?",
                callBack:function(ok){
                    if(ok){
                        var seleIds = _selRows[0].deploymentId;
                        var _thisURL = '/base/backlog/claim';
                        var _data = {};
                        _data.taskId = backLogData.id;
                        //签收操作
                        webUtil.ajaxData({url:_thisURL,async:false,data:_data,success:function(data){
                            thisListUI.executeQuery();
                        }});
                    }
                }});
        }else{
            webUtil.mesg('请先选中对应的数据行，方可进行办理操作!');
        }
    });
	//查看流程进度
	$('#schedule').on('click',function(){
		var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
			var backLogData = _selRows[0];
			var url = app.root+"/"+thisBaseUrl+"/photo/"+backLogData.processDefinitionId+"/"+backLogData.executionId;
			webUtil.openWin({title:'流程进度',btns:null,operate:OperateType.audit,width:1200,height:winHeight>800?800:winHeight,url:url});
		}else{
			webUtil.mesg('请先选中对应的数据行，方可进行查看!');
		}
	});
	//查看审核意见
	$('#opinion').on('click',function(){
		var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var backLogData = _selRows[0];
				var url = app.root+"/"+thisBaseUrl+"/histoic/flow/show/"+backLogData.processInstanceId;
				webUtil.openWin({title:'审核意见',btns:null,operate:OperateType.audit,width:1200,height:winHeight>800?800:winHeight,url:url});
		}else{
				webUtil.mesg('请先选中对应的数据行，方可进行查看审核意见!');
		}
	});
});
</script>
</html>