<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<script type="text/javascript">
	var mutil = '${mutil}';
</script>
<body style="padding:5px;">
	<div id="listPanel" class="panel" style="padding:2px;">
		<div id="table-toolbar">
			<div class="btn-group" id="seachGBtn"></div>
		</div>
		<table id="tblMain">
			<thead ><tr></tr></thead>
		</table>
	</div>
</body>
<%@include file="../inc/webBase.inc"%>
<script type="text/javascript">
var tblMain;
var thisQueryParams = {};
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
	return JSON.stringify(thisQueryParams);
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

$(document).ready(function() {
	var tbl_tr = $('#tblMain').find('thead>tr');
	var cols = '${cols}';
	var json_cols = webUtil.str2Json(cols) ;
	var queryCols = [];
	queryCols.push({key:'_blank',text:'-清空选择-',type:'blank'});
	var dataURL = '${dataUrl}';
	$(json_cols).each(function(i){
		var _th = $('<th data-field="'+this.field+'" data-type="'+this.type+'" data-query="'+this.filter+'">'+this.name+'</th>');
		tbl_tr.append(_th);
		if(this.filter||'true'==this.filter){
			queryCols.push({key:this.field,text:this.name,type:this.type});
		}
	}); 
	
	var sear_btn_gp = $('#seachGBtn').myBtnGroup();
	sear_btn_gp.addSearch({items:queryCols,dataChange:search_Query});
	
	var myQueryTalbOpt = {height:400,striped:true,sortStable:true,showRefresh:false,clicToSelect:true
			,cache:false,pageSize:20,showToggle:true,showColumns:true,idField:"id",mypagination:true
			,toolbar:"#table-toolbar",url:dataURL,queryParams:getMyQueryParams};

	tblMain =  $('#tblMain').myDataTable(myQueryTalbOpt);
	tblMain.refreshData();
});
</script>
</html>