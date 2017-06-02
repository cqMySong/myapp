<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/webBase.inc"%>
<script type="text/javascript">
;(function($, window, document,undefined) {
var ListUI = function(el,options){
	var _Def_listUI = {
			 tableEl:'#tblMain',baseUrl:"",openModel:"",toolbar:"_table-toolbar",pkCol:'id',hasDefToolbar:true,
			 editWin:{title:'^~^',url:'',maxmin:true,width:800,height:600,callBack:undefined},
			 pageSize :20,curPage :1,listData:undefined,btns:undefined,pagination:true
			 ,extendTableOptions:undefined,totalPages:0,queryColumn:undefined,
			 search:true,searchParams:undefined
	};
	this.options = $.extend({},_Def_listUI, options);
	this.pkCol = this.options.pkCol;
	this.baseUrl = this.options.baseUrl;
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
	if(this.options.hasDefToolbar){
		var _btn_g = $('<div class="btn-group"></div>');
		$tb.prepend(_btn_g);
		var btng = _btn_g.myBtnGroup();
		
		btng.addBtn(toDoBtnGroup({text:'新增',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.addnew}));
		btng.addBtn(toDoBtnGroup({text:'查看',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.view}));
		btng.addBtn(toDoBtnGroup({text:'修改',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.edit}));
		btng.addBtn(toDoBtnGroup({text:'删除',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.remove}));
		btng.addBtn(toDoBtnGroup({text:'刷新',icon:"glyphicon glyphicon-refresh icon-refresh",clickFun:this.refresh}));
		btng.addBtn(toDoBtnGroup({text:'查询',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.query}));
	}
	serachPrams = function(){
		return JSON.stringify(thisObj.options.searchParams);
	};
	var def_listTable_options = {height:730,striped:true,sortStable:true,showRefresh:false,clickToSelect:true
			,cache:false,pageSize:this.options.pageSize,showToggle:true,search:true,queryParams:serachPrams
			,showColumns:true,idField:"id",mypagination:true,url:this.options.baseUrl+'/list'};

	var tbl_opts = $.extend({}, def_listTable_options, this.options.extendTableOptions);
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
	addnew:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('addnew')){
			var _win = $.extend({},$thisList.options.editWin);
			_win.title = _win.title+'-新增';
			_win.url = webUtil.toUrl($thisList.baseUrl)+'/addnew';
			if(!webUtil.isEmpty(_win.uiParams)&&$.isFunction(_win.uiParams)){
				_win.uiParams = _win.uiParams('addnew');
			}
			_win.colseCallBack =function(){
				$thisList.executeQuery();
			};
			webUtil.openWin(_win);
		}
	},
	view:function(btn){
		var $thisList = btn.owerObj;
		if($thisList.actionBefore('view')){
			var _selRows = $thisList.getSelectRow();
			if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
				var _thisRowData = _selRows[0];
				var _win = $.extend({},$thisList.options.editWin);
				_win.title = _win.title+'-查看';
				if(!webUtil.isEmpty(_win.uiParams)&&$.isFunction(_win.uiParams))
					_win.uiParams = _win.uiParams('view');
				_win.url = webUtil.toUrl($thisList.baseUrl)+'/view?id='+_thisRowData[$thisList.pkCol];
				_win.colseCallBack =function(){
					$thisList.executeQuery();
				};
				webUtil.openWin(_win);
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
				var _win = $.extend({},$thisList.options.editWin);
				if(!webUtil.isEmpty(_win.uiParams)&&$.isFunction(_win.uiParams))
					_win.uiParams = _win.uiParams('edit');
				_win.title = _win.title+'-修改';
				_win.url = webUtil.toUrl($thisList.baseUrl)+'/edit?id='+_thisRowData[$thisList.pkCol];
				_win.colseCallBack =function(){
					$thisList.executeQuery();
				};
				webUtil.openWin(_win);
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
						var _thisURL = $thisList.baseUrl+'/remove';
						for(var i=0;i<_selRows.length;i++){
							var _thisRowData = _selRows[i];
							var _data = {};
							_data.id = _thisRowData[$thisList.pkCol];
							//同步删除操作
							webUtil.ajaxData({url:_thisURL,async:false,data:_data,success:function(data){
								
							}});
						}
						$thisList.executeQuery();
					}
				}});
			}else{
				webUtil.mesg('请先选中对应的数据行，方可进行删除操作!');
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
	},
	executeQueryByParams:function(params){
		var oldParms = this.options.searchParams;
		this.options.searchParams = $.extend({},oldParms, params);
		this.executeQuery();
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

$(document).ready(function(){
	
})
</script>




