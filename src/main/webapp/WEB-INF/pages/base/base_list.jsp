<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/webBase.inc"%>
<style type="text/css">
.panel {
  width: 100%;
  height: 100%;
  overflow:hidden;
  padding: 0px 2px 2px 2px;
}
</style>
<script type="text/javascript">
if(typeof listModel == "undefined"){
	var listModel = {};
	listModel.baseData =1;
	listModel.billData =2;
}
;(function($, window, document,undefined) {
var ListUI = function(el,options){
	var _Def_listUI = {
			 tableEl:'#tblMain',baseUrl:"",openModel:"",toolbar:"_table-toolbar",pkCol:'id',hasDefToolbar:true,
			 editWin:{title:'^~^',openType:'WIN',url:'',maxmin:true,width:800,height:600,callBack:undefined,btns:null},
			 pageSize :20,curPage :1,listData:undefined,btns:undefined,pagination:true,listModel:0,
			 extendTableOptions:undefined,totalPages:0,queryColumn:undefined,
			 search:true,searchParams:undefined
	};
	this.options = $.extend(true,{},_Def_listUI, options);
	this.options.editWin.title = '<i class="fa fa-windows"></i>&nbsp;'+this.options.editWin.title;
	this.pkCol = this.options.pkCol;
	this.baseUrl = this.options.baseUrl;
	this.el = el;
	//pagination  这个为自定义的分页模式 与 没有用 bootstrap的分页模式
	this.options.editWin.url = webUtil.toUrl(this.options.editWin.url);
	var $tb = $(el).find(this.options.toolbar);
	if(webUtil.isEmpty($tb)||$tb.length<=0){
		$tb = $('<div id="'+this.options.toolbar+'"></div>');
		$(el).prepend($tb);
	}
	this.listUIObj = this;
	var thisObj = this;
	toDoBtnGroup = function(_btnOpt){
		var _def_btnG_opt = {owerObj:thisObj};
		var btnSet = $.extend({},_def_btnG_opt, _btnOpt);
		return btnSet;
	}
	
	this.toolbar = $tb; 
	if(this.options.hasDefToolbar&&this.options.listModel==listModel.baseData){
		var _btn_g = $('<div class="btn-group"></div>');
		$tb.prepend(_btn_g);
		var btng = _btn_g.myBtnGroup();
		btng.addBtn(toDoBtnGroup({text:'启用',icon:"fa fa-toggle-on",clickFun:this.enabled}));
		btng.addBtn(toDoBtnGroup({text:'禁用',icon:"fa fa-toggle-off",clickFun:this.disabled}));
	}
	
	if(this.options.hasDefToolbar){
		var _btn_g = $('<div class="btn-group"></div>');
		$tb.prepend(_btn_g);
		var btng = _btn_g.myBtnGroup();
		
		btng.addBtn(toDoBtnGroup({text:'新增',icon:"fa fa-file-o",clickFun:this.addnew}));
		btng.addBtn(toDoBtnGroup({text:'查看',icon:"fa fa-file-text-o",clickFun:this.view}));
		btng.addBtn(toDoBtnGroup({text:'修改',icon:"fa fa-edit",clickFun:this.edit}));
		btng.addBtn(toDoBtnGroup({text:'删除',icon:"fa fa-trash",clickFun:this.remove}));
		btng.addBtn(toDoBtnGroup({text:'刷新',icon:"fa fa-refresh",clickFun:this.refresh}));
		btng.addBtn(toDoBtnGroup({text:'附件管理',icon:"fa fa-paperclip",clickFun:this.attach}));
		btng.addBtn(toDoBtnGroup({text:'查询',icon:"fa fa-filter",clickFun:this.query}));
	}
	
	var gs = $tb.find('.btn-group');
	if(gs&&gs.length>1){
		for(var i=1;i<gs.length;i++){
			$(gs[i]).css({"margin-left":"1px"});
		}
	}
	
	serachPrams = function(){
		return JSON.stringify(thisObj.options.searchParams);
	};
	var def_listTable_options = {height:730,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
			,cache:false,pageSize:this.options.pageSize,showToggle:true,search:true,queryParams:serachPrams
			,showColumns:true,idField:"id",mypagination:true,url:this.options.baseUrl+'/query'};

	var tbl_opts = $.extend(true,{}, def_listTable_options, this.options.extendTableOptions);
	this.tblMain =  $(this.options.tableEl).myDataTable(tbl_opts);
	
	if(this.options.search){
		var _searchItems = [];
		_searchItems.push({key:'_blank',text:'-清空选择-',type:'blank'});
		var _queryCols = this.options.queryColumn;
		if(!webUtil.isEmpty(_queryCols)&&$.isArray(_queryCols)){
			_searchItems = _queryCols;
		}else{
			var visableColumns = this.tblMain.getVisibleColumns();
			if(!webUtil.isEmpty(visableColumns)&&visableColumns.length>0){
				for(var i=0;i<visableColumns.length;i++){
					var _thisCol = visableColumns[i];
					var isAdd = false;
					if(_thisCol.query!==undefined){//有这query属性
						if(_thisCol.query||'true'==_thisCol.query){
							isAdd = true;
						}
					}else{
						isAdd = true;
						if('_seq'==_thisCol.field){
							isAdd = false;
						}
					}
					if(!isAdd) continue;
					var _field = _thisCol.field;
					var _title = _thisCol.title;
					var _type = _thisCol.type;
					if(isAdd&&!webUtil.isEmpty(_field)&&!webUtil.isEmpty(_title)){
						_searchItems.push({key:_field,text:_title,type:_type});
					}
				}
			}
		}
		
		if(_searchItems.length>0){
			var _btn_search_gp = $('<div class="btn-group"></div>');
			$tb.append(_btn_search_gp);
			var sear_btn_gp = _btn_search_gp.myBtnGroup();
			search_Query = function(_opt,item){
				if(!webUtil.isEmpty(item)){
					var _listUI = _opt.owerObj;
					var _sarchParams = {};
					if('_blank'!=item.key){
						_sarchParams.key = item.key;
						_sarchParams.value = item.value;
						_sarchParams.type = item.type;
					}
					_listUI.executeQueryByParams(_sarchParams);
				}else{
					webUtil.mesg('请选择对应的列!');
				}
			}
			sear_btn_gp.addSearch(toDoBtnGroup({items:_searchItems,dataChange:search_Query}));
		}
	}
}
ListUI.prototype = {
	onLoad:function(){
		if(this.actionBefore('onLoad')){
			this.executeQuery();
		}
	},
	getSelectIds:function(){
		var ids = "";
		var _selRows = this.getSelectRow();
		if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
			for(var i=0;i<_selRows.length;i++){
				var _thisRowData = _selRows[i];
				if(i>0) ids +=",";
				ids += _thisRowData.id;
			}
		}
		return ids;
	},
	getSelectRow:function(){
		return this.tblMain.getSelections();
	},
	actionBefore:function(opt){
		if(beforeAction&&!webUtil.isEmpty(beforeAction)&&$.isFunction(beforeAction)){
			return beforeAction(opt);
		}else{
			return true;
		}
	},
	actionAfter:function(opt){
		if(afterAction&&!webUtil.isEmpty(afterAction)&&$.isFunction(afterAction)){
			afterAction(opt);
		}
	},
	openEditWin:function(_win){
		if(_win.openType=='WIN'){
			_win.url = webUtil.toUrl(_win.url);
			webUtil.openWin(_win);
		}else if(_win.openType=='MAINTAB'){
			var winUrl = _win.url;
			var uiCtx = _win.uiParams;
			if(!webUtil.isEmpty(winUrl)&&!webUtil.isEmpty(uiCtx)){
				if($.isFunction(_win.uiParams)){
					uiCtx = _win.uiParams();
				}
				if($.isPlainObject(uiCtx)){
					uiCtx =  'uiCtx='+webUtil.json2Str(uiCtx);
				}
				winUrl += (winUrl.indexOf('?')>0?'&':'?')+encodeURI(uiCtx);
			} 
			//winUrl = winUrl.replace(/\"/g,"'"); 
			_win.url = winUrl;
			top.myNavTab.addTab('#mainTab', _win);
		}
	},
	addnew:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('addnew')){
			var _win = $.extend(true,{},$thisList.options.editWin);
			_win.title = _win.title+'-新增';
			_win.url = $thisList.baseUrl+'/addnew';
			if(!webUtil.isEmpty(_win.uiParams)&&$.isFunction(_win.uiParams)){
				_win.uiParams = _win.uiParams('addnew');
			}
			_win.colseCallBack =function(){
				$thisList.executeQuery();
			};
			$thisList.openEditWin(_win);
		}
	},
	view:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('view')){
			var _selRows = $thisList.getSelectRow();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var _thisRowData = _selRows[0];
				var _win = $.extend(true,{},$thisList.options.editWin);
				_win.title = _win.title+'-查看';
				if(!webUtil.isEmpty(_win.uiParams)&&$.isFunction(_win.uiParams))
					_win.uiParams = _win.uiParams('view');
				_win.url = $thisList.baseUrl+'/view?id='+_thisRowData[$thisList.pkCol];
				_win.colseCallBack =function(){
					$thisList.executeQuery();
				};
				$thisList.openEditWin(_win);
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行查看操作!');
			}
		}
	},
	edit:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('edit')){
			var _selRows = $thisList.getSelectRow();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var _thisRowData = _selRows[0];
				var _win = $.extend(true,{},$thisList.options.editWin);
				if(!webUtil.isEmpty(_win.uiParams)&&$.isFunction(_win.uiParams))
					_win.uiParams = _win.uiParams('edit');
				_win.title = _win.title+'-修改';
				_win.url = $thisList.baseUrl+'/edit?id='+_thisRowData[$thisList.pkCol];
				_win.colseCallBack =function(){
					$thisList.executeQuery();
				};
				$thisList.openEditWin(_win);
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行编辑操作!');
			}
		}
	},
	remove:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('remove')){
			var _selRows = $thisList.getSelectRow();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				webUtil.showConfirm({title:"删除提醒",content:"你将删除["+_selRows.length+"]条记录信息，是否继续?",callBack:function(ok){
					if(ok){
						var seleIds = "";
						for(var i=0;i<_selRows.length;i++){
							var _thisRowData = _selRows[i];
							if(i>0) seleIds+=',';
							seleIds+= _thisRowData[$thisList.pkCol];
						}
						var _thisURL = $thisList.baseUrl+'/remove';
						var _data = {};
						_data.id = seleIds;
						//同步删除操作
						webUtil.ajaxData({url:_thisURL,async:false,data:_data,success:function(data){
							$thisList.executeQuery();
						}});
					}
				}});
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行删除操作!');
			}
		}
	},
	attach:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore(OperateType.attach)){
			var _selRows = $thisList.getSelectRow();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var bid = _selRows[0][$thisList.pkCol];
				if(!webUtil.isEmpty(bid)){
					var attachUrl = webUtil.toUrl('base/attach')+'/toAttach';
					var _win = {url:attachUrl,maxmin:false,title:$thisList.options.editWin.title+'-附件管理'};
					_win.uiParams = 'billId='+bid;
					_win.btns = ['关闭'];
					webUtil.openWin(_win);
				}else{
					webUtil.mesg('单据主键为空或者不存在，不能查看附件!');
				}
				
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行查看附件!');
			}
		}
	},
	refresh:function(btn){
		var $thisList = btn.owerObj;
		$thisList.executeQuery();
	},
	query:function(btn){
		var $obj = btn.owerObj;
		if($obj){
			$obj.executeQuery();
		}
	},
	executeQuery:function(){
		this.tblMain.refreshData();
		this.actionAfter(OperateType.refesh);
		
	},
	executeQueryByParams:function(params){
		var oldParms = this.options.searchParams;
		this.options.searchParams = $.extend(true,{},oldParms, params);
		this.executeQuery();
	},
	enabled:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('enabled')){
			var _selRows = $thisList.getSelectRow();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var seleIds = "";
				for(var i=0;i<_selRows.length;i++){
					var _thisRowData = _selRows[i];
					if(i>0) seleIds+=',';
					seleIds+= _thisRowData[$thisList.pkCol];
				}
				var _thisURL = $thisList.baseUrl+'/enable';
				var _data = {};
				_data.id = seleIds;
				webUtil.ajaxData({url:_thisURL,data:_data,success:function(data){
					$thisList.executeQuery();
				}});
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行启用操作!');
			}
		}
	},
	disabled:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('disabled')){
			var _selRows = $thisList.getSelectRow();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var seleIds = "";
				for(var i=0;i<_selRows.length;i++){
					var _thisRowData = _selRows[i];
					if(i>0) seleIds+=',';
					seleIds+= _thisRowData[$thisList.pkCol];
				}
				var _thisURL = $thisList.baseUrl+'/disable';
				var _data = {};
				_data.id = seleIds;
				webUtil.ajaxData({url:_thisURL,data:_data,success:function(data){
					$thisList.executeQuery();
				}});
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行禁用操作!');
			}
		}
	}
}

$.fn.listUI = function(options) {
     return new ListUI(this,options);
}

})(jQuery, window, document);


function queryParams(params){
	return params;
}
function beforeAction(opt){
	return true;
}

function afterAction(){
	
}

$(document).ready(function(){
	
})
</script>




