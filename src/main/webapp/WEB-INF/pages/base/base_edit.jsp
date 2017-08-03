<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/webBase.inc"%>
<script src="<%=appRoot%>/assets/app/js/myapp.form.js?v=25" type="text/javascript"></script>

<% 
String _uiCtx = "";
Object uiCtxObj = request.getAttribute("uiCtx");
 if(uiCtxObj!=null){
	 _uiCtx = uiCtxObj.toString();
//	 _uiCtx = _uiCtx.replaceAll("\'","\"");//单引号转双引号
 }
%>

<script type="text/javascript">

if(typeof billModel == "undefined"){
	var billModel = {};
	billModel.baseData =1;
	billModel.billData =2;
}

var _uiCtx = '<%=_uiCtx%>';
var billId = '${id}';

;(function($, window, document,undefined) {
var EditUI = function(el,opt){
	this.$element = el;
	this.defaults = {title:"",toolbar:'_table-toolbar',billModel:1,hasDefToolbar:true,baseUrl:'',operate:'view',form:{el:'',data:undefined}};
    this.options = $.extend(true,{}, this.defaults, opt);
    this.editForm = undefined;
    this.operate = '${operate}';
    this.statusCode = ${statusCode};
    this.statusMesg = '${statusMesg}';
    this.editData = {};
    this.entrys = [];
    this.form_el = this.options.form.el;
    if(!webUtil.isEmpty(this.form_el)){
    	$(this.form_el).css({"margin-top":"30px"});
    	this.editForm = $(this.form_el).myForm();
    }
    //界面的一些初始化
    //1.1 界面基本工具条初始化
    var $tb = $(el).find(this.options.toolbar);
	if(webUtil.isEmpty($tb)||$tb.length<=0){
		$tb = $('<div id="'+this.options.toolbar+'"></div>');
		$(el).prepend($tb);
	}
	var _btn_g = $('<div class="btn-group"></div>');
	$tb.prepend(_btn_g);
	var thisObj = this;
	var btng = _btn_g.myBtnGroup();
	toDoBtnGroup = function(_btnOpt){
		var _def_btnG_opt = {owerObj:thisObj};
		var btnSet = $.extend(true,{},_def_btnG_opt, _btnOpt);
		return btnSet;
	}
	if(this.options.hasDefToolbar){
		btng.addBtn(toDoBtnGroup({text:'新增',icon:"fa fa-file-o",clickFun:this.btnAddnew}));
		btng.addBtn(toDoBtnGroup({text:'修改',icon:"fa fa-edit",clickFun:this.edit}));
		btng.addBtn(toDoBtnGroup({text:'保存',icon:"fa fa-save",clickFun:this.save}));
		if(this.options.billModel == billModel.billData){
			btng.addBtn(toDoBtnGroup({text:'提交',icon:"fa fa-location-arrow",clickFun:this.submit}));
			btng.addBtn(toDoBtnGroup({text:'审核',icon:"fa fa-mail-forward",clickFun:this.audit}));
			btng.addBtn(toDoBtnGroup({text:'反审核',icon:"fa fa-mail-reply",clickFun:this.unaudit}));
		}
		btng.addBtn(toDoBtnGroup({text:'删除',icon:"fa fa-remove",clickFun:this.remove}));
		btng.addBtn(toDoBtnGroup({text:'附件管理',icon:"fa fa-paperclip",clickFun:this.attach}));
	}
    
}
EditUI.prototype = {
	onLoad:function(){
		this.editForm.init();
		//初始分录表格信息
		var _operate = this.operate;
		var _thisUrl = this.options.baseUrl;
		var thisEditUI = this;
		this.$element.find('table.input-entry').each(function(){
			thisEditUI.initEntryView($(this));
		});
		
		this.$element.data('entrys',this.entrys);
		
		if(OperateType.addnew == _operate){//新增
			this.addNew();
		}else{// 修改 or 查看
			this.loadServerData(_operate,billId);
		}
	},
	getEntrys:function(){
		return this.$element.data('entrys');
	},
	getEntryObj:function(name){
		var thisEntryObj = null;
		if(!webUtil.isEmpty(name)){
			var entrys = this.getEntrys();
			if(!webUtil.isEmpty(entrys)&&entrys.length>0){
				for(var i=0;i<entrys.length;i++){
					var thisObj = this.entrys[i];
					if(name==thisObj.name){
						thisEntryObj = thisObj;
						return thisEntryObj;
					}
				}
			}
		}
		return thisEntryObj;
	},
	getEntry:function(name){
		var entryObj = this.getEntryObj();
		if(!webUtil.isEmpty(entryObj)){
			return entryObj.entry;
		}else {
			return null;
		}
	},
	initEntryToolBar:function (_opt){
		var _defToolBarOpt = {toolbar:true,title:'',icon:'glyphicon glyphicon-list',addBtn:{title:'新增',theme:'btn-success',icon:'fa fa-plus-square'}
				,removeBtn:{title:'删除',theme:'btn-success',icon:'fa fa-minus-square'}};
		var _thisOpt = $.extend(true,{}, _defToolBarOpt, _opt);
		var tb_html = [];
		tb_html.push('<div style="position: relative;margin: 5px 0px;line-height: 35px;height: 35px;vertical-align: middle;padding: 0px 5px;">');
			tb_html.push('<div class="bs-bar pull-left">');
				if(!webUtil.isEmpty(_thisOpt.icon)){
					tb_html.push('<i class="'+_thisOpt.icon+'"></i>');
				}
				if(!webUtil.isEmpty(_thisOpt.title)){
					tb_html.push('&nbsp;'+_thisOpt.title);
				}
			tb_html.push('</div>');	
			tb_html.push('<div class="pull-right">');
				tb_html.push('<div class="btn-group" >');
					if(!webUtil.isEmpty(_thisOpt.addBtn)){
						var thisAddBtn = _thisOpt.addBtn;
						tb_html.push('<a class="btn btn-sm _addRow '+thisAddBtn.theme+'">');
						if(!webUtil.isEmpty(thisAddBtn.icon)){
							tb_html.push('<span class="'+thisAddBtn.icon+'"></span>');
						}
						if(!webUtil.isEmpty(thisAddBtn.title)){
							tb_html.push('&nbsp;'+thisAddBtn.title);
						}
						tb_html.push('</a>');
					}
					if(!webUtil.isEmpty(_thisOpt.removeBtn)){
						var removeBtn = _thisOpt.removeBtn;
						tb_html.push('<a class="btn btn-sm _removeRow '+removeBtn.theme+'">');
						if(!webUtil.isEmpty(removeBtn.icon)){
							tb_html.push('<span class="'+removeBtn.icon+'"></span>');
						}
						if(!webUtil.isEmpty(removeBtn.title)){
							tb_html.push('&nbsp;'+removeBtn.title);
						}
						tb_html.push('</a>');
					}
				tb_html.push('</div>');	
			tb_html.push('</div>');	
		tb_html.push('</div>');	
		return tb_html.join('');
	},
	initEntryView:function(entryDom){
		var name = entryDom.attr('name');
		if(!webUtil.isEmpty(this.getEntry(name))) return;
		var _opt = entryDom.data('opt');
		var dataObj ={};
		var toClick = false;
		if(!webUtil.isEmpty(_opt)){
			dataObj = webUtil.str2Json(_opt);
		}
		if(webUtil.isEmpty(dataObj.tableOpt)){
			dataObj.tableOpt = {};
		}
		if(webUtil.isEmpty(dataObj.cusTopToolBar)){
			toClick = true;
			dataObj.tableOpt.cusTopToolBar = $(this.initEntryToolBar(dataObj.toolbar));
		}else{
			dataObj.tableOpt.cusTopToolBar = dataObj.cusTopToolBar;
		}
		var thisEditUI = this;
		var _defEntryTableOpt = {mypagination:false,height:dataObj.height||300,sortStable:false,updateRow2Body:false,layout:'fixed'};
		var thisTblOpt = $.extend(true,{}, _defEntryTableOpt, dataObj.tableOpt);
		var tblEntry = entryDom.myDataTable(thisTblOpt);
		
		var thisEntryObj = {};
		if(!webUtil.isEmpty(name)){
			thisEntryObj.name = name;
			thisEntryObj.toolbar = dataObj.tableOpt.cusTopToolBar;
			thisEntryObj.entry = tblEntry;
			this.entrys.push(thisEntryObj);
		}
		var editObj = {};
		if(!webUtil.isEmpty(dataObj.tableOpt.editDataChanged)
				&&$.isFunction(dataObj.tableOpt.editDataChanged)){
			editObj.editDataChanged = function($cell,obj){
				/* var obj= {};
						obj.oldVal = oldVal;
						obj.value = val;
						obj.rowData = rowData;
						obj.field = field;
						obj.column = thisColumn;
						obj.rowIndex = rowIdx;
						obj.colIndex = colIdx; */
				dataObj.tableOpt.editDataChanged($cell,obj);
			}
		}
		if(!webUtil.isEmpty(dataObj.tableOpt.statrtEdit)
				&&$.isFunction(dataObj.tableOpt.statrtEdit)){
			editObj.statrtEdit = function($cell,obj){
				/* var obj = {};
	            obj.field = field;
	            obj.value = value; 
	            obj.row = item;
	            obj.column = column;
	            obj.rowIndex = $tr.data('index');
	            obj.colIndex = index;
	            
	            return true  --->表示继续可编辑
	            return false --->表示终止编辑事件
	            */ 
				return dataObj.tableOpt.statrtEdit($cell,obj);
			}
		}
		if(!webUtil.isEmpty(dataObj.tableOpt.endEdit)
				&&$.isFunction(dataObj.tableOpt.endEdit)){
			editObj.endEdit = function($cell,obj){
				/* var obj = {};
	            obj.field = field;
	            obj.value = value; 
	            obj.row = item;
	            obj.column = column;
	            obj.rowIndex = $tr.data('index');
	            obj.colIndex = index;*/ 
				dataObj.tableOpt.endEdit($cell,obj);
			}
		}
		
		tblEntry.initTableColumnEditor('click-cell',editObj);
		
		if(toClick&&!webUtil.isEmpty(dataObj.tableOpt.cusTopToolBar)){
			var tb = $(dataObj.tableOpt.cusTopToolBar);
			tb.find('.pull-right>.btn-group>._addRow').click({source:tblEntry},function(e){
				if(!webUtil.isEmpty(dataObj.addRow)&&$.isFunction(dataObj.addRow)){
					dataObj.addRow(e.source);
				}else{
					var toAdd = true;
					var tbar = dataObj.toolbar;
					if(!webUtil.isEmpty(tbar)&&!webUtil.isEmpty(tbar.beforeClick)
							&&$.isFunction(tbar.beforeClick)){
						toAdd = tbar.beforeClick('addRow');
					}
					var rowData = {};
					if(toAdd){
						if(!webUtil.isEmpty(dataObj.defRowData)){
							if($.isFunction(dataObj.defRowData)){
								rowData = dataObj.defRowData();
							}else{
								rowData = dataObj.defRowData
							}
						}
						if(webUtil.isEmpty(rowData)) rowData = {};
						if($.isArray(rowData)&&rowData.length>0){
							for(var i=0;i<rowData.length;i++){
								tblEntry.addRow(rowData[i]);
							}
						}else{
							tblEntry.addRow(rowData);	
						}
						if(!webUtil.isEmpty(tbar)&&!webUtil.isEmpty(tbar.afterClick)
								&&$.isFunction(tbar.afterClick)){
							tbar.afterClick('addRow',rowData);
						}
					}
				}
			});
			tb.find('.pull-right>.btn-group>._removeRow').click({source:tblEntry},function(e){
				if(!webUtil.isEmpty(dataObj.reomveRow)&&$.isFunction(dataObj.reomveRow)){
					dataObj.reomveRow(e.source);
				}else{
					var tbar = dataObj.toolbar;
					var toRemove = true;
					if(!webUtil.isEmpty(tbar)&&!webUtil.isEmpty(tbar.beforeClick)
							&&$.isFunction(tbar.beforeClick)){
						toRemove = tbar.beforeClick('removeRow');
					}
					if(toRemove){
						tblEntry.removeRow(tblEntry.getSelectRow());
						if(!webUtil.isEmpty(tbar)&&!webUtil.isEmpty(tbar.afterClick)
								&&$.isFunction(tbar.afterClick)){
							tbar.afterClick('removeRow',null);
						}
					}
				}
			});
		}
	},
	loadServerData:function(_operate,bid){
		if(!webUtil.isEmpty(bid)){
			var _thisUrl = this.options.baseUrl;
			_thisUrl +="/"+_operate;
			var _dataParams = {};
			_dataParams.id = bid;
			if(operateParams&&!webUtil.isEmpty(operateParams)&&$.isFunction(operateParams)){
				operateParams(_operate,_dataParams);
			}
			var thisEditUI = this;
			webUtil.ajaxData({url:_thisUrl,data:_dataParams,success:function(data){
				var _editData = data.data;
				thisEditUI.editData = _editData;
				thisEditUI.loadData(_editData);
				thisEditUI.operate = data.operate||_operate;
				thisEditUI.actionAfter(_operate);
				thisEditUI.initUIStyle();
			}});
		}else{
			webUtil.mesg('单据ID为空，无法完成数据的加载操作');
		}
	},
	loadData:function(data){
		if(!webUtil.isEmpty(data)){
			this.editForm.setFormData(data);
			billId = data.id;
		}
		var entrys = this.getEntrys();
		if(webUtil.isEmpty(entrys)) return;
		for(var i=0;i<entrys.length;i++){
			var entryObj = entrys[i];
			if(!webUtil.isEmpty(entryObj)){
				var entryName = entryObj.name;
				var entry = entryObj.entry;
				var entryDatas = data[entryName];
				if(webUtil.isEmpty(entryDatas)||!$.isArray(entryDatas)){
					entryDatas = [];
				}
				if(!webUtil.isEmpty(entry)){
					entry.loadData(entryDatas);
				}
			}
		}
	},
	storeData:function(){
		var editData = {};
		editData.id = billId;
		if(!webUtil.isEmpty(this.editForm)){
			editData = $.extend(true,{},editData, this.editForm.getFormData());
		}
		var entrys = this.getEntrys();
		var exCol = [];
		exCol.push({field:"id",type:DataType.text});
		if(!webUtil.isEmpty(entrys)&&$.isArray(entrys)){
			for(var i=0;i<entrys.length;i++){
				var entryObj = entrys[i];
				if(!webUtil.isEmpty(entryObj)){
					var entryName = entryObj.name;
					var entry = entryObj.entry;
					editData[entryName] = entry.getSubmitData(exCol);
				}
			}
		}
		return editData;
	},
	addNew:function(){
		var _thisUrl = this.options.baseUrl+"/"+OperateType.addnew;
		var _dataParams = {};
		_dataParams.uiCtx = _uiCtx;
		if(operateParams&&!webUtil.isEmpty(operateParams)&&$.isFunction(operateParams)){
			operateParams(OperateType.addnew,_dataParams);
		}
		
		var _thisEditUI = this;
		webUtil.ajaxData({url:_thisUrl,data:_dataParams,success:function(data){
			var _editData = data.data;
			_thisEditUI.editData = _editData;
			_thisEditUI.loadData(_editData);
			_thisEditUI.operate = data.operate||OperateType.edit;
			_thisEditUI.actionAfter(OperateType.addnew);
			_thisEditUI.initUIStyle();
		}});
		
	},
	btnAddnew:function(btn){
		var _thisEditUI = btn.owerObj;
		if(_thisEditUI.actionBefore(OperateType.addnew)){
			_thisEditUI.editForm.clearFormData();
			_thisEditUI.addNew();
		}
	},
	edit:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.actionBefore(OperateType.edit)){
			// 主要用于设置起可编辑状态
			thisEditUI.operate = OperateType.edit;
			thisEditUI.actionAfter(OperateType.edit);
			thisEditUI.initUIStyle();
		}
	},
	save:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.editForm.verifyInputRequire()
				&&thisEditUI.actionBefore(OperateType.save)){
			var this_editData = thisEditUI.storeData();
			var _toData = {};
			_toData.editData = JSON.stringify(this_editData);
			if(operateParams&&!webUtil.isEmpty(operateParams)&&$.isFunction(operateParams)){
				operateParams(OperateType.save,_toData);
			}
			var _thisUrl = thisEditUI.options.baseUrl+"/"+OperateType.save;
			webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
				var _editData = data.data;
				thisEditUI.editData = _editData;
				thisEditUI.loadData(_editData);
				thisEditUI.operate = data.operate||OperateType.edit;
				thisEditUI.actionAfter(OperateType.save);
				thisEditUI.initUIStyle();
			}});
		}
	},
	submit:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.editForm.verifyInputRequire()
				&&thisEditUI.actionBefore(OperateType.submit)){
			var this_editData = thisEditUI.storeData();
			var _toData = {};
			_toData.editData = JSON.stringify(this_editData);
			if(operateParams&&!webUtil.isEmpty(operateParams)&&$.isFunction(operateParams)){
				operateParams(OperateType.save,_toData);
			}
			var _thisUrl = thisEditUI.options.baseUrl+"/"+OperateType.submit;
			webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
				var _editData = data.data;
				thisEditUI.editData = _editData;
				thisEditUI.loadData(_editData);
				thisEditUI.operate = data.operate||OperateType.view;
				thisEditUI.actionAfter(OperateType.submit);
				thisEditUI.initUIStyle();
			}});
		}
	},
	audit:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.actionBefore(OperateType.audit)){
			var bid = billId;
			if(!webUtil.isEmpty(bid)){
				var _thisUrl = thisEditUI.options.baseUrl+"/"+OperateType.audit;
				var _toData = {};
				_toData.id = bid;
				webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
					var _editData = data.data;
					thisEditUI.editData = _editData;
					thisEditUI.loadData(_editData);
					thisEditUI.operate = OperateType.view;
					thisEditUI.actionAfter(OperateType.audit);
					thisEditUI.initUIStyle();
				}});
			}else{
				webUtil.mesg('单据主键为空或者不存在，不能完成审核操作!');
			}
		}
	},
	unaudit:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.actionBefore(OperateType.unaudit)){
			var bid = billId;
			if(!webUtil.isEmpty(bid)){
				var _thisUrl = thisEditUI.options.baseUrl+"/"+OperateType.unaudit;
				var _toData = {};
				_toData.id = bid;
				webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
					var _editData = data.data;
					thisEditUI.editData = _editData;
					thisEditUI.loadData(_editData);
					thisEditUI.operate = OperateType.view;
					thisEditUI.actionAfter(OperateType.unaudit);
					thisEditUI.initUIStyle();
				}});
			}else{
				webUtil.mesg('单据主键为空或者不存在，不能完成反审核操作!');
			}
		}
	},
	remove:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.actionBefore(OperateType.remove)){
			var bid = billId;
			if(!webUtil.isEmpty(bid)){
				webUtil.showConfirm({title:"删除提醒",content:"你将删除此记录信息，是否继续?",callBack:function(ok){
					if(ok){
						var _thisUrl = thisEditUI.options.baseUrl+"/"+OperateType.remove;
						var _toData = {};
						_toData.id = bid;
						webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
							//后续操作什么
							webUtil.mesg('删除操作成功!');
						}});
						thisEditUI.actionAfter(OperateType.remove);
					}
				}});
			}else{
				webUtil.mesg('单据主键为空或者不存在，不能完成删除操作!');
			}
		}
	},
	attach:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.actionBefore(OperateType.attach)){
			var bid = billId;
			if(!webUtil.isEmpty(bid)){
				var attachUrl = webUtil.toUrl('base/attach')+'/toAttach';
				var _win = {url:attachUrl,maxmin:false,title:thisEditUI.options.title+'-附件管理'};
				_win.uiParams = 'billId='+bid;
				_win.btns = ['关闭'];
				webUtil.openWin(_win);
			}else{
				webUtil.mesg('单据主键为空或者不存在，不能操作!');
			}
		}
	},
	initUIStyle:function(){
		//根据界面的状态 设置是否可用相关的组件
		var _operate = this.operate||OperateType.view;
		var isEdit = true
		if(_operate==OperateType.view){
			isEdit = false;
		}
		this.editForm.setFormEnabled(isEdit);
		var entrys = this.getEntrys();
		if(webUtil.isEmpty(entrys)) return;
		for(var i=0;i<entrys.length;i++){
			var entryObj = entrys[i];
			if(!webUtil.isEmpty(entryObj)){
				var entryName = entryObj.name;
				var entry = entryObj.entry;
				entry.setEnabled(isEdit);
			}
		}
	},
	actionBefore:function(_opt){
		if(beforeAction&&!webUtil.isEmpty(beforeAction)&&$.isFunction(beforeAction)){
			return beforeAction(_opt);
		}else{
			return true;
		}
	},
	actionAfter:function(_opt){
		if(afterAction&&!webUtil.isEmpty(afterAction)&&$.isFunction(afterAction)){
			afterAction(_opt);
		}
	}
}

$.fn.editUI = function(options) {
	$(this).css({"padding":"2px"});
    return new EditUI(this,options);
}

})(jQuery, window, document);

function getBillId(){
	return webUtil.isEmpty(billId)?'':billId;
}

function setCellValue(tbl,$el,value){
	$el.data('value',value);
	$el.css({"padding":"8px"});
	$el.html(value);
	$el.data("editStatus",'ed');
	tbl.resetView();
}

function beforeAction(opt){
	return true;
}
function afterAction(_opt){
	
}
//获取界面的传递过来的参数
function getUICtx(){
	if(webUtil.isEmpty(_uiCtx)) return {};
	return JSON.parse(_uiCtx);
}

function operateParams(operate,opt){
	
}

$(document).ready(function(){
	
	
})
</script>

