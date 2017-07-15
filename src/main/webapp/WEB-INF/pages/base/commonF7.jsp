<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
	var mutil = false;
</script>
<body>
	<div id="listPanel" class="panel ui-layout-center">
		<div id="table-toolbar">
			<div class="btn-group" id="seachGBtn"></div>
		</div>
		<table id="tblMain">
			<thead ><tr></tr></thead>
		</table>
		
		<div id="selected">
			<div style="position: relative;margin:0px;line-height: 35px;height: 35px;vertical-align: middle;padding: 0px 5px;" id="selToolBar">
				<div class="bs-bar pull-left">
					<i class="glyphicon glyphicon-list"></i>&nbsp;已选清单</div>
				<div class="pull-right">
					<div class="btn-group">
						<a id="_addRow" class="btn btn-sm  btn-success"><span class="fa fa-plus-square"></span>&nbsp;选择</a>
						<a id="_removeRow" class="btn btn-sm  btn-success"><span class="fa fa-minus-square"></span>&nbsp;删除</a>
					</div>
				</div>
			</div>
			<table id="tblSelMain">
				<thead ><tr></tr></thead>
			</table>
		</div> 
	</div>
	 
	 
	
</body>
<%@include file="../inc/webBase.inc"%>
<script type="text/javascript">
var tblMain;
var tblSelMain;
var thisQueryParams = {};
var _uiCtx = {};
function search_Query(_opt,item){
	if(!webUtil.isEmpty(item)){
		var _sarchParams = {};
		if('_blank'!=item.key){
			_sarchParams.key = item.key;
			_sarchParams.value = item.value;
			_sarchParams.type = item.type;
		}
		thisQueryParams = _sarchParams;
		tblMain.refreshData();
	}else{
		webUtil.mesg('请选择对应的列!');
		//layui-layer-btn layui-layer-btn-
	}
}
function getMyQueryParams(){
	var params = thisQueryParams;
	if(!webUtil.isEmpty(_uiCtx)){
		params.uiCtx = _uiCtx;
	}
	return JSON.stringify(params);
}

function getData(){
	if(mutil&&!webUtil.isEmpty(tblSelMain)){
		return tblSelMain.getData();
	} else{
		var selRows = tblMain.getSelections();
		if(selRows&&selRows.length>0){
			return selRows[0];
		}
		return {};
	}
	
}

function addRow(){
	
}

function removeRow(){
	
}
function dbClick_toAddrow(row, $element, field){
	if(!webUtil.isEmpty(tblSelMain)
			&&!webUtil.isEmpty(tblMain)){
		tblSelMain.addRow(row);
	}
}
function dbClick_toRemoveRow(row, $element, field){
	
}
$(document).ready(function() {
	var tbl_tr = $('#tblMain').find('thead>tr');
	var tblSel_tr = $('#tblSelMain').find('thead>tr');
	var cols = '${cols}';
	var json_cols = webUtil.str2Json(cols) ;
	var queryCols = [];
	queryCols.push({key:'_blank',text:'-清空选择-',type:'blank'});
	var dataURL = '${dataUrl}';
	_uiCtx = '${uiCtx}';
	if(!webUtil.isEmpty(_uiCtx)){
		_uiCtx = webUtil.str2Json(_uiCtx) ;
	}
	mutil = '${mutil}'=='true'?true:false;
	$(json_cols).each(function(i){
		var _thHtml = '<th data-field="'+this.field+'" data-type="'+this.type+'" data-query="'+this.filter+'">'+this.name+'</th>';
		tbl_tr.append($(_thHtml));
		if(mutil){
			tblSel_tr.append($(_thHtml));
		}
		if(this.filter||'true'==this.filter){
			queryCols.push({key:this.field,text:this.name,type:this.type});
		}
	}); 
	
	var sear_btn_gp = $('#seachGBtn').myBtnGroup();
	sear_btn_gp.addSearch({items:queryCols,dataChange:search_Query});
	
	var myQueryTalbOpt = {height:400,striped:true,sortStable:true,showRefresh:false,clicToSelect:true
			,cache:false,pageSize:20,showToggle:true,showColumns:true,idField:"id",mypagination:true
			,toolbar:"#table-toolbar",url:dataURL,queryParams:getMyQueryParams,selectModel:1};
	if(mutil){
		myQueryTalbOpt.selectModel = 2;
		myQueryTalbOpt.onDblClickRow = dbClick_toAddrow;
	}
	
	tblMain =  $('#tblMain').myDataTable(myQueryTalbOpt);
	tblMain.refreshData();
	
	$('body').layout({applyDefaultStyles: true});
	
	if(mutil){
		var _defEntryTableOpt = {mypagination:false,height:200,sortStable:false};
		var thisTblOpt = $.extend(true,{}, _defEntryTableOpt,{updateRow2Body:false,cusTopToolBar:$('#selToolBar')});
		thisTblOpt.onDblClickRow = dbClick_toRemoveRow;
		tblSelMain = $('#tblSelMain').myDataTable(thisTblOpt);
		$('#selected').show();
		$('#_addRow').click(function(){
			addRow();
		});
		$('#_removeRow').click(function(){
			removeRow();
		});
		
	}else{
		$('#selected').hide();
	}
});
</script>
</html>