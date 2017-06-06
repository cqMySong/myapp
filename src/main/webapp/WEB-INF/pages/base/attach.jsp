<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
</script>
<body style="padding:2px;overflow: hidden;">
	<div class="panel">
		<ul class="nav nav-tabs nav-success">
			<li class="">
				<a href="#uploadFile" class="_tabHeader" data-toggle="tab">
					<i class="glyphicon glyphicon-list" style="font-size: 14px;"></i>
					<strong style="margin-left: 2px;">文件上传</strong>
				</a>
			</li>
			<li class="active">
				<a href="#attList" class="_tabHeader" data-toggle="tab">
					<i class="glyphicon glyphicon-list-alt" style="font-size: 14px;"></i>
					<strong style="margin-left: 2px;">文件列表</strong>
				</a>
			</li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane fade in" style="padding: 5px;" id="uploadFile">
				<div class="btn-group mt10">
					<button class="btn btn-success" id="selectFile" type="button">
						<i class="glyphicon glyphicon-file"></i>&nbsp;选择文件
					</button>
					
					<button class="btn btn-success" id="btnUploader"  type="button">
						<i class="fa fa-upload"></i>&nbsp;开始上传
					</button>
				</div>
				
				<ul id="fileList" class="media-list media-list-contacts mt20"
					 style="height:370px;overflow-x:hidden;overflow-y: auto;border:1px solid #bdc3d1;" >
             	 	
				</ul>
			</div>
			<div class="tab-pane fade in active" style="padding: 5px;" id="attList">
				<div id="table-toolbar">
					<div class="btn-group">
						<button type="button" id="removeFile" class="btn btn-success">
							<i class="fa fa-remove"></i>&nbsp;删除
						</button>
						<button type="button" id="download" class="btn btn-success">
							<i class="fa fa-download"></i>&nbsp;下载
						</button>
					</div>
					<div class="btn-group" id="seachGBtn">
					</div>
				</div>
				<table id="tblMain">
					<thead><tr>
						<th data-field="fileName">文件名</th>
						<th data-field="fmortSize">大小</th>
						<th data-field="uploadDate" data-type="datetime">上传时间</th>
						<th data-field="storageType" data-formatter="storeage_formater">存储位置</th>
						<th data-field="complete" data-type="checkbox">上传完毕</th>
					</tr></thead>
				</table>
			</div>
		</div>
	</div>
</body>
<%@include file="../inc/webBase.inc"%>
<script type="text/javascript">
var billId = '${billId}';
function getBillId(){
	return billId;
}
//附件断点续传功能的相关配置
var _attach_initFile = {
	url:app.root+'/base/attach/initFile',
	sourceId:billId
};
</script>
<script src="<%=appRoot%>/assets/lib/attach/js/moxie.js"></script>
<script src="<%=appRoot%>/assets/lib/attach/js/plupload.dev.js?v=2"></script>
<script src="<%=appRoot%>/assets/lib/attach/js/jquery.md5.js"></script>
<script type="text/javascript">
var refesh = false;
var tblMain;
var thisQueryParams = {};
thisQueryParams.billId = '${billId}';
function search_Query(_opt,item){
	if(!webUtil.isEmpty(item)){
		var _sarchParams = {};
		if('_blank'!=item.key){
			_sarchParams.key = item.key;
			_sarchParams.value = item.value;
			_sarchParams.type = item.type;
		}
		thisQueryParams = _sarchParams;
		thisQueryParams.billId = getBillId();
		tblMainReshDdata();
	}else{
		webUtil.mesg('请选择对应的列!');
	}
}
function getMyQueryParams(){
	return JSON.stringify(thisQueryParams);
}

function storeage_formater(value, row, index){
	var txt = value;
	if(value='FTP'){
		txt = 'FTP服务器';
	}else if(value='DATABASE'){
		txt = '数据库服务器';
	}else if(value='APP'){
		txt = '应用服务器';
	}
	return txt;
}

function tblMainReshDdata(){
	tblMain.refreshData();
}

function getData(){
	var selRows = tblMain.getSelections();
	if(mutil&&'true'==mutil){
		return selRows;
	} else{
		if(selRows&&selRows.length>0){
			return selRows[0];
		}
	}
	return {};
}


function removeFile(){
	var _selRows = tblMain.getSelections();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
		webUtil.showConfirm({title:"删除提醒",content:"你将删除["+_selRows.length+"]条记录信息，是否继续?",callBack:function(ok){
			if(ok){
				var seleIds = "";
				for(var i=0;i<_selRows.length;i++){
					var _thisRowData = _selRows[i];
					if(i>0) seleIds+=',';
					seleIds+= _thisRowData.id;
				}
				var _thisURL = 'base/attach/remove';
				var _data = {};
				_data.id = seleIds;
				//同步删除操作
				webUtil.ajaxData({url:_thisURL,async:false,data:_data});
				tblMainReshDdata();
			}
		}});
	}else{
		webUtil.mesg("请先选择对应的数据行!");
	}
}
function downloadFile(){
	var _selRows = tblMain.getSelections();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length==1){
		var downUrl = webUtil.toUrl('base/attach/down');
		downUrl+= '?id='+_selRows[0].id;
		$('#downWin').attr("src",downUrl);
	}else{
		webUtil.mesg("请先选择对应的数据行且一次只能选择一个文件进行下载!");
	}
}

$(document).ready(function() {
	var tbl_tr = $('#tblMain').find('thead>tr');
	var queryCols = [];
	queryCols.push({key:'_blank',text:'-清空选择-',type:'blank'});
	queryCols.push({key:'fileName',text:'文件名',type:'text'});
	var dataURL = '${dataUrl}';
	
	var sear_btn_gp = $('#seachGBtn').myBtnGroup();
	sear_btn_gp.addSearch({items:queryCols,dataChange:search_Query});
	var dataURL = "base/attach/list";
	var myQueryTalbOpt = {striped:true,height:440,sortStable:true,showRefresh:false,clicToSelect:true
			,cache:false,pageSize:10,showToggle:true,showColumns:true,idField:"id",mypagination:true
			,toolbar:"#table-toolbar",url:dataURL,queryParams:getMyQueryParams};

	tblMain =  $('#tblMain').myDataTable(myQueryTalbOpt);
	tblMainReshDdata();
	$('._tabHeader').click(function(){
		$('.tab-pane').removeClass('active');
		var target = $(this).attr('href');
		$(target).addClass('active');
		if('#attList'==target){
			if(refesh){
				tblMainReshDdata();
				refesh = false;
			}
		}
	});
	
	$('#removeFile').click(function(){
		removeFile();
	});
	$('#download').click(function(){
		downloadFile();
	});
});
</script>
<script src="<%=appRoot%>/assets/lib/attach/js/attach.js?v=33"></script>
<iframe id="downWin" width=0 height=0 marginheight=0 marginwidth=0 scrolling=no src="" style="display: none;"></iframe>
</html>