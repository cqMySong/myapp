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
	<div id="listPanel" class="panel">
		<div id="table-toolbar">
			<div class="btn-group">
				<button class="btn btn-success" type="button" id="actProcessSuspend">
					<span class="fa fa-file-o"></span>&nbsp;挂起/激活
				</button>
				<button class="btn btn-success" type="button" id="actProcessDel">
					<span class="fa fa-trash"></span>&nbsp;删除
				</button>
				<button class="btn btn-success" type="button" id="actDeploy">
					<span class="fa fa-file-o"></span>&nbsp;转为模型
				</button>
				<button class="btn btn-success" type="button" id="actProcessXml">
					<span class="fa fa-file-o"></span>&nbsp;查看XML
				</button>
				<button class="btn btn-success" type="button" id="actProcessPicture">
					<span class="fa fa-file-o"></span>&nbsp;查看图片
				</button>
			</div>
		</div>
	     <table id="tblMain">
			 <thead >
				<tr>
					<th data-field="category" data-formatter="category_formatter">类型</th>
					<th data-field="number">编码</th>
					<th data-field="name">名称</th>
					<th data-field="version">流程版本</th>
					<th data-field="deployDate" data-type="datetime">部署时间</th>
					<th data-field="suspended" data-formatter="suspended_formatter">是否挂起</th>
					<th data-field="xmlResourcesName">流程XML</th>
					<th data-field="pictureResourcesName">流程图片</th>
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
var thisBaseUrl = 'base/actprocesses';
function beforeAction(opt){
	return true;
}
var actModelCategory = JSON.parse('${category}');
function category_formatter(value, row, index){
    if(!actModelCategory){
        return value;
    }
    var txt= "";
    $.each(actModelCategory,function(i,v){
        if(v.fentityObjectType==value){
            txt = v.fentityName;
        }
    });
    return txt;
}
function suspended_formatter(value, row, index){
	var txt = value;
	if(value==true){
			txt = '激活';
	}else{
			txt = '挂起';
	}
	return txt;
}
$(document).ready(function() {
	var editWin ={title:'流程信息',width:620,height:450};
    var height = top.getTopMainHeight()-50;
	thisListUI = $('#listPanel').listUI({tableEl:'#tblMain',height:height,baseUrl:thisBaseUrl,editWin:editWin,
			hasDefToolbar:false,toolbar:"#table-toolbar",extendTableOptions:{height:height-45}});
	thisListUI.onLoad();
	//挂起
	$('#actProcessSuspend').on('click',function(){
		var actProcess = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(actProcess)&&actProcess.length>0){
				webUtil.showConfirm({title:"操作提醒",content:"你将"+(actProcess[0].suspended?"激活":"挂起")
						+"流程["+actProcess[0].name+"]，是否继续?",
						callBack:function(ok){
								if(ok){
										var seleIds = actProcess[0].id;
										var _thisURL = thisBaseUrl+'/update/'+(actProcess[0].suspended?"active":"suspend");
										var _data = {};
										_data.id = seleIds;
										//同步更改状态
										webUtil.ajaxData({url:_thisURL,async:false,data:_data,success:function(data){
												thisListUI.executeQuery();
										}});
								}
				}});
		}else{
			webUtil.mesg('请先选中对应的数据行，方可进行激活/挂起操作!');
		}
	});
	//删除流程
	$('#actProcessDel').on('click',function(){
		var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
			webUtil.showConfirm({title:"删除提醒",content:"你将删除["+_selRows.length+"]条记录信息，是否继续?",
					callBack:function(ok){
							if(ok){
									var seleIds = _selRows[0].deploymentId;
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
	//流程图片查看
	$('#actProcessPicture').on('click',function(){
		var _selRows = thisListUI.tblMain.getSelections();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var url = app.root+"/"+thisBaseUrl+"/resource/read/"+_selRows[0].id+"/image";
				webUtil.openWin({title:'流程图片',width:(window.outerWidth-20),height:(window.outerHeight-100),url:url});
		}else{
			webUtil.mesg('请先选中对应的数据行，方可进行图片查看操作!');
		}
	});
	//流程XML查看
	$('#actProcessXml').on('click',function(){
			var _selRows = thisListUI.tblMain.getSelections();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
					var url = app.root+"/"+thisBaseUrl+"/resource/read/"+_selRows[0].id+"/xml";
					webUtil.openWin({title:'流程XML',width:(window.outerWidth-20),height:(window.outerHeight-100),url:url});
			}else{
					webUtil.mesg('请先选中对应的数据行，方可进行XML查看操作!');
			}
		});
});
</script>
</html>