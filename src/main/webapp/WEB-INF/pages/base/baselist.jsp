<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
;(function($, window, document,undefined) {
var ListUI = function(el,options){
	var _Def_listUI = {
			 el:'#tblMain',dataUrl:"",editUrl:"",openModel:"",toolbar:"_table-toolbar",
			 pageSize :20,curPage :1,listData:undefined,btns:undefined,pagination:true,
			 tableOption:{},hasSeq:true,extendTableOptions:undefined,totalPages:0,queryColumn:undefined,
			 hasCheck:true,getOtherToolBar:undefined,search:true,searchParams:undefined,index:true
	};
	this.options = $.extend({},_Def_listUI, options);
	//pagination  这个为自定义的分页模式 与 没有用 bootstrap的分页模式
	var $tb = $(el).find(this.options.toolbar);
	if(webUtil.isEmpty($tb)||$tb.length<=0){
		$tb = $('<div id="'+this.options.toolbar+'"></div>');
		$(el).prepend($tb);
	}
	var _btn_g = $('<div class="btn-group"></div>');
	this.listUIObj = this;
	var thisObj = this;
	$tb.prepend(_btn_g);
	var btng = _btn_g.myBtnGroup();
	toDoBtnGroup = function(_btnOpt){
		var _def_btnG_opt = {owerObj:thisObj};
		var btnSet = $.extend({},_def_btnG_opt, _btnOpt);
		return btnSet;
	}
	
	btng.addBtn(toDoBtnGroup({text:'新增',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.addnew}));
	btng.addBtn(toDoBtnGroup({text:'查看',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.view}));
	btng.addBtn(toDoBtnGroup({text:'修改',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.edit}));
	btng.addBtn(toDoBtnGroup({text:'删除',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.remove}));
	btng.addBtn(toDoBtnGroup({text:'查询',icon:"glyphicon glyphicon-shopping-cart",clickFun:this.query}));
	
	var def_listTable_options = {height:700,striped:true,sortStable:true,showRefresh:true,clickToSelect:true
			,cache:false,pageSize:this.options.pageSize,showToggle:true,toolbar:this.options.toolbar
			,showColumns:true,idField:"id"};

	var tbl_opts = $.extend({}, def_listTable_options, this.options.extendTableOptions);
	this.tblMain =  $(this.options.el).myDataTable(tbl_opts);
	if(this.options.index){
		setSeqInex = function(value, row, index){
			return index+1;
		}
		var col = {field:'seq',width:50,title:"序号",visible:true,formatter:setSeqInex};
		this.tblMain.addColumn(0,col);
	}
	
	if(this.options.pagination){
		$('#tbl_pagination').show();
		//改变pagesize
		$('#tbl_pageSize').change(thisObj,function(e){
			var _curPageSize = $(this).val();
			var _thisList = e.data;
			_thisList.options.pageSize = _curPageSize;
			_thisList.options.curPage = 1;
			$('#tbl_pagination').find('li.active').removeClass('active');
			$('#tbl_firstpage').parent('li').addClass('active');
			$('#tbl_curPage').val(1);
			_thisList.executeQuery();
		});
		$('#tbl_firstpage').click(thisObj,function(e){
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				$('#tbl_pagination').find('li.active').removeClass('active');
				$li.addClass('active');
				var _thisList = e.data;
				_thisList.options.curPage = 1;
				_thisList.executeQuery();
			}
		});
		$('#tbl_prepage').click(thisObj,function(e){
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				var _thisList = e.data;
				$('#tbl_pagination').find('li.active').removeClass('active');
				$li.addClass('active');
				var _curPage = _thisList.options.curPage-1;
				_thisList.options.curPage = _curPage;
				$('#tbl_curPage').val(_curPage);
				_thisList.executeQuery();
			}
		});
		$('#tbl_nextpage').click(thisObj,function(e){
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				$('#tbl_pagination').find('li.active').removeClass('active');
				$li.addClass('active');
				var _thisList = e.data;
				var _curPage = _thisList.options.curPage+1;
				_thisList.options.curPage = _curPage;
				$('#tbl_curPage').val(_curPage);
				_thisList.executeQuery();
			}
		});
		$('#tbl_lastpage').click(thisObj,function(e){
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				$('#tbl_pagination').find('li.active').removeClass('active');
				$li.addClass('active');
				var _thisList = e.data;
				var _curPage = _thisList.options.totalPages;
				_thisList.options.curPage = _curPage;
				$('#tbl_curPage').val(_curPage);
				_thisList.executeQuery();
			}
		});
		$('#tbl_curPage').keydown(thisObj,function(e){
			if(e.which == "13"){//回车事件
				var _curPage = $(this).val();
				if(_curPage<1){
					_curPage =1;
					$('#tbl_pagination').find('li.active').removeClass('active');
				}
				var _thisList = e.data;
				var _total_page = _thisList.options.totalPages;
				if(_curPage>_total_page){
					_curPage = _total_page;
					$('#tbl_pagination').find('li.active').removeClass('active');
				}
				_thisList.options.curPage = _curPage;
				
				$(this).val(_curPage);
				_thisList.executeQuery();
			}  
		});
	}else{
		$('#tbl_pagination').hide();
	}
	
	if(this.options.search){
		var _searchItems = [];
		var _queryCols = this.options.queryColumn;
		if(!webUtil.isEmpty(_queryCols)&&$.isArray(_queryCols)){
			_searchItems = _queryCols;
		}else{
			var visableColumns = this.tblMain.getVisibleColumns();
			var _def_serch_item = {key:"",text:""};
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
					}
					var _field = _thisCol.field;
					var _title = _thisCol.title;
					if(isAdd&&!webUtil.isEmpty(_field)&&!webUtil.isEmpty(_title)){
						_searchItems.push({key:_field,text:_title});
					}
				}
			}
		}
		
		if(_searchItems.length>0){
			var _btn_search_gp = $('<div class="btn-group"></div>');
			$tb.append(_btn_search_gp);
			var sear_btn_gp = _btn_search_gp.myBtnGroup();
			search_Changed = function(_opt,item){
				if(!webUtil.isEmpty(item)){
					var _listUI = _opt.owerObj;
					var _sarchParams = {};
					_sarchParams.key = item.key;
					_sarchParams.value = item.value;
					_listUI.options.searchParams = _sarchParams;
					var column = _listUI.tblMain.getVisibleColumns();
					_listUI.executeQuery();
				}
			}
			sear_btn_gp.addSearch(toDoBtnGroup({items:_searchItems,dataChange:search_Changed}));
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
		var $obj = btn.owerObj;
		if($obj.actionBefore('addnew')){
			alert($obj.tblMain.getRowCount());
		}
	},
	view:function(btn){
		var $obj = btn.owerObj;
		if($obj.actionBefore('view')){
			alert($obj.getSelectIds());
		}
	},
	edit:function(btn){
		
	},
	remove:function(btn){
		
	},
	query:function(btn){
		var $obj = btn.owerObj;
		if($obj){
			$obj.executeQuery();
		}
	},
	executeQuery:function(){
		var _listUI = this;
		var _data = $.extend({},queryParams({}));;
		_data.curPage = _listUI.options.curPage;
		_data.pageSize = _listUI.options.pageSize;
		_data.search = _listUI.options.searchParams;
		
		webUtil.ajaxData({url:app.root+'/'+this.options.dataUrl,data:_data,success:function(data){
			var _ret_Data = data.data;
			if(!webUtil.isEmpty(_ret_Data)){
				$('#tbl_pagination').find('li.disabled').removeClass('disabled');
				if(_ret_Data.currentPage<=1){
					$('#tbl_firstpage').parent('li').addClass('disabled');
					$('#tbl_prepage').parent('li').addClass('disabled');
				}else{
					if(_ret_Data.currentPage>=_ret_Data.totalPages){
						$('#tbl_lastpage').parent('li').addClass('disabled');
						$('#tbl_nextpage').parent('li').addClass('disabled');
					}
				}
				if(_ret_Data.totalPages<=1){
					$('#tbl_firstpage').parent('li').addClass('disabled');
					$('#tbl_prepage').parent('li').addClass('disabled');
					$('#tbl_lastpage').parent('li').addClass('disabled');
					$('#tbl_nextpage').parent('li').addClass('disabled');
				}
				_listUI.options.curPage = _ret_Data.currentPage;
				_listUI.options.pageSize = _ret_Data.pageSize;
				_listUI.options.totalPages = _ret_Data.totalPages;
				$('#tbl_pageinfo').html("显示第 "+(_ret_Data.startNum+1)+" 到第 "+(_ret_Data.startNum+_ret_Data.pageSize)+" 条记录，总共 0 条记录")
				var _dataRows = _ret_Data.datas;
				if(!webUtil.isEmpty(_dataRows)&&_dataRows.length>0){
					_listUI.tblMain.loadData(_dataRows);
				}
			}
			
		}}); 
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

<div id="tbl_pagination" class="fixed-table-pagination" style="display: block;">
	<div class="pull-left pagination-detail" style="margin-top:0px;">
		<span id="tbl_pageinfo" class="pagination-info">显示第 1 到第 0 条记录，总共 0 条记录</span>
			<span class="page-list">每页显示
				 <span class="btn-group dropup form-group" style="padding-top: 15px;">
					  <select id="tbl_pageSize" class="form-control" style="width: 50px;height:26px;padding :2px 5px;" data-placeholder="页码">
		                  <option value="10">10</option>
		                  <option value="20" selected="selected">20</option>
		                  <option value="50">50</option>
		                </select>
				</span> 条记录
			</span>
	</div>
	<div class="pull-right pagination">
		<ul class="pagination" id="tbl_pagination" style="border:1px solid #2596ab;">
			<li class="page-pre "><a href="#" id="tbl_firstpage">首页</a></li>
			<li class="page-number "><a href="#" id="tbl_prepage">上一页</a></li>
			<li class="page-number">
				<div style="width:40px;float:left;padding:0px;">
					<input style="height:32px;border: 0px;" id="tbl_curPage" type="text" class="form-control" value="1"/>
				</div>
			</li>
			<li class="page-number active"><a href="#" id="tbl_nextpage">下一页</a></li>
			<li class="page-next "><a href="#" id="tbl_lastpage">末页</a></li>
		</ul>
	</div>
</div>
