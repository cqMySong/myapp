<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
</script>
<body style="padding:2px;overflow: hidden;"  class="panel">
	<div>
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
						<button type="button" id="onlineView" class="btn btn-success">
							<i class="fa fa-eye"></i>&nbsp;预览
						</button>
					</div>
					<div class="btn-group" id="seachGBtn">
					</div>
				</div>
				<table id="tblMain">
					<thead><tr>
						<th data-field="fileName" >文件名</th>
						<th data-field="fmortSize">大小</th>
						<th data-field="uploadDate" data-type="date">上传时间</th>
						<th data-field="storageType" data-formatter="storeage_formater">存储位置</th>
						<th data-field="complete" data-type="checkbox">上传完毕</th>
						<th data-formatter="opt_formater" data-width="120">操作</th>
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
var operate = '${operate}';
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
function opt_formater(value, row, index){
	var html = '<div class="btn-group">';
	html+= '<button style="padding:5px;" class="btn btn-success" onclick="_onlineview(\''+row.id+'\')"><i class="fa fa-eye"></i>预览</button>';
	html+= '<button style="padding:5px;" class="btn btn-success" onclick="_downdoc(\''+row.id+'\')"><i class="fa fa-download"></i>下载</button>';
	html+= '</div>';
	return html;
}
function _onlineview(rid){
	if(webUtil.isEmpty(rid)) return;
	var viewUrl ='base/attach/view';
	var _data = {};
	_data.id = rid;
	webUtil.ajaxData({url:viewUrl,async:false,data:_data,success:function(data){
		var fileData = data.data;
		if(!webUtil.isEmpty(fileData)){
			var title = fileData.fileName||'在线文档查看';
			title ='<i class="fa fa-file-text-o"></i>&nbsp;'+title;
			webUtil.openWin({title:title,width:1000,height:750,btns:['关闭'],url:fileData.viewUrl});
		}
	}});
}
function _downdoc(rid){
	if(webUtil.isEmpty(rid)) return;
	var downUrl = webUtil.toUrl('base/attach/down');
	downUrl+= '?id='+rid;
	
	$('#downWin').attr("src",downUrl);
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
		_downdoc(_selRows[0].id);
	}else{
		webUtil.mesg("请先选择对应的数据行且一次只能选择一个文件进行下载!");
	}
}
function onlineViewDoc(){
	var _selRows = tblMain.getSelections();
	if(!webUtil.isEmpty(_selRows)&&_selRows.length==1){
		_onlineview(_selRows[0].id);
	}else{
		webUtil.mesg("请先选择对应的数据行且一次只能选择一个文件进行下载!");
	}
}
$(document).ready(function() {
	var tbl_tr = $('#tblMain').find('thead>tr');
	var queryCols = [];
	queryCols.push({key:'_blank',text:'-清空选择-',type:'blank'});
	queryCols.push({key:'fileName',text:'文件名',type:'text'});
	
	var sear_btn_gp = $('#seachGBtn').myBtnGroup();
	sear_btn_gp.addSearch({items:queryCols,dataChange:search_Query});
	var dataURL = "base/attach/list";
	var myQueryTalbOpt = {striped:true,height:400,sortStable:true,showRefresh:false,clicToSelect:true
			,cache:false,pageSize:10,showToggle:true,showColumns:true,idField:"id",mypagination:true
			,toolbar:"#table-toolbar",url:dataURL,queryParams:getMyQueryParams,selectModel:2};

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
	if('view'==operate){
		$('._tabHeader').each(function(){
			$('.tab-pane').removeClass('active');
			var target = $(this).attr('href');
			if('#attList'==target){
				$(target).addClass('active');
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	}
	
	
	$('#removeFile').click(function(){
		removeFile();
	});
	$('#download').click(function(){
		downloadFile();
	});
	$('#onlineView').click(function(){
		onlineViewDoc();
	});
});
</script>
<script src="<%=appRoot%>/assets/lib/attach/js/attach.js?v=33"></script>
<iframe id="downWin" width=0 height=0 marginheight=0 marginwidth=0 scrolling=no src="" style="display: none;"></iframe>
</html>