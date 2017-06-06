<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/webBase.inc"%>
<script src="<%=appRoot%>/assets/app/js/myapp.form.js?v=22" type="text/javascript"></script>

<% 
String _uiCtx = "";
Object uiCtxObj = request.getAttribute("uiCtx");
 if(uiCtxObj!=null){
	 _uiCtx = uiCtxObj.toString();
	 _uiCtx = _uiCtx.replaceAll("\'","\"");//单引号转双引号
 }
%>
<script type="text/javascript">
var _uiCtx = '<%=_uiCtx%>';


;(function($, window, document,undefined) {
var EditUI = function(el,opt){
	this.$element = el;
	this.defaults = {title:"",toolbar:'_table-toolbar',baseUrl:'',operate:'view',from:{el:'',data:undefined}};
    this.options = $.extend({}, this.defaults, opt);
    this.editForm = undefined;
    this.operate = '${operate}';
    this.statusCode = ${statusCode};
    this.statusMesg = '${statusMesg}';
    this.billId = '${id}'
    this.editData = {};
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
		var btnSet = $.extend({},_def_btnG_opt, _btnOpt);
		return btnSet;
	}
	
	btng.addBtn(toDoBtnGroup({text:'新增',icon:"fa fa-file-o",clickFun:this.btnAddnew}));
	btng.addBtn(toDoBtnGroup({text:'修改',icon:"fa fa-edit",clickFun:this.edit}));
	btng.addBtn(toDoBtnGroup({text:'保存',icon:"fa fa-save",clickFun:this.save}));
	btng.addBtn(toDoBtnGroup({text:'删除',icon:"fa fa-remove",clickFun:this.remove}));
	btng.addBtn(toDoBtnGroup({text:'附件管理',icon:"fa fa-paperclip",clickFun:this.attach}));
    
    var form_el = this.options.form.el;
    if(!webUtil.isEmpty(form_el)){
    	$(form_el).css({"margin-top":"30px"});
    	this.editForm = $(form_el).myForm();
    }
}
EditUI.prototype = {
	onLoad:function(){
		this.editForm.init();
		var _operate = this.operate;
		var _thisUrl = this.options.baseUrl;
		if(OperateType.addnew == _operate){//新增
			this.addNew();
		}else{// 修改 or 查看
			this.loadServerData(_operate,this.billId);
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
			this.billId = data.id;
		}
	},
	storeData:function(){
		var editData = {};
		editData.id = this.billId;
		if(!webUtil.isEmpty(this.editForm)){
			editData = $.extend({},editData, this.editForm.getFormData());
		}
		return editData;
	},
	addNew:function(){
		var _thisUrl = this.options.baseUrl+"/"+OperateType.addnew;
		var _dataParams = {};
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
	remove:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.actionBefore(OperateType.remove)){
			var bid = thisEditUI.billId;
			if(!webUtil.isEmpty(bid)){
				var _thisUrl = thisEditUI.options.baseUrl+"/"+OperateType.remove;
				var _toData = {};
				_toData.id = bid;
				webUtil.ajaxData({url:_thisUrl,async:false,data:_toData,success:function(data){
					//后续操作什么
				}});
				thisEditUI.actionAfter(OperateType.remove);
			}else{
				webUtil.mesg('单据主键为空或者不存在，不能完成删除操作!');
			}
		}
	},
	attach:function(btn){
		var thisEditUI = btn.owerObj;
		if(thisEditUI.actionBefore(OperateType.attach)){
			var bid = thisEditUI.billId;
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
		if(_operate==OperateType.view){
			this.editForm.setFormEnabled(false);
		}else{
			this.editForm.setFormEnabled(true);
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