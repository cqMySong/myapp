/**
 * 表单的一些操作js
 */


;(function($, window, document,undefined) {
var myForm = function(el, opt){
	 this.$element = el,
	 this.defaults = {};
     this.options = $.extend(true,{}, this.defaults, opt);
}
myForm.prototype = {
	init:function(){
		this.initComponet();
		this.initStyle();
	},
	initComponet:function(){
		//初始化一些控件
		var thisObj = this;
		this.$element.find('.input-item').each(function(){
			thisObj.initComponetItem($(this));
		});
	},
	initStyle:function(){
		var thisObj = this;
		this.$element.find('.input-item').each(function(){
			var _opt = $(this).data('opt');
			var dataObj ={};
			if(!webUtil.isEmpty(_opt)){
				dataObj = webUtil.str2Json(_opt);
			}
			thisObj.initItemStyle($(this),dataObj);
		});
	},
	initComponetItemType:function(item_el,opt){
		var $itemel = webUtil.getJqueryDom(item_el);
		var _type = DataType.text;
		if(opt&&!webUtil.isEmpty(opt.type)){
			_type = opt.type;
		}else{
			_type = $itemel.data('type');
		}
		if(webUtil.isEmpty(_type)){
			_type = DataType.text;
		}
		$itemel.data('dataType',_type);
		return _type;
	},
	initComponetItem:function(item_el){
		var $itemel = webUtil.getJqueryDom(item_el);
		var _opt = $itemel.data('opt');
		var dataObj = webUtil.str2Json(_opt);
		var _type = this.initComponetItemType($itemel,dataObj);
		$itemel.myComponet(_type,{method:'init',opt:dataObj});
	},
	initItemStyle:function(item_el,_opt){
		var $itemel = webUtil.getJqueryDom(item_el);
		var _type = '';
		if(!webUtil.isEmpty(_opt)&&!webUtil.isEmpty(_opt.type)){
			_type = _opt.type;
		}else{
			_type = $itemel.data('type');
		}
		if(webUtil.isEmpty(_type)){
			_type = DataType.text;
		}
		if(_type ==DataType.text){
			$itemel.addClass('form-control');
		}else if(_type ==DataType.select){
			$itemel.css({"width":"100%"});
		}
	},
	getFormData:function(){
		var data = {};
		var thisObj = this;
		this.$element.find('.input-item').each(function(){
			var _name = $(this).attr('name');
			if(!webUtil.isEmpty(_name)){
			    var $itemel = webUtil.getJqueryDom($(this));
			    var _type = $itemel.data('dataType');
			    if(DataType.radio==_type){
			        if($(this).attr("value")==undefined){
                        data[_name] = thisObj.getItemData($(this));
			        }else if(thisObj.getItemData($(this))){
                        data[_name] = $(this).val();
                    }
			    }else{
			        data[_name] = thisObj.getItemData($(this));
			    }
			}
		});
		return data;
	},
	setFormData:function(data){
		var thisObj = this;
		this.$element.find('.input-item').each(function(){
			var _name = $(this).attr('name');
			thisObj.setItemData($(this),data[_name]);
		});
	},
	clearFormData:function(){
		var thisObj = this;
		this.$element.find('.input-item').each(function(){
			thisObj.setItemData($(this),null);
		});
	},
	getItemData:function(item_el){
		var $itemel = webUtil.getJqueryDom(item_el);
		var _type = $itemel.data('dataType');
		if(!webUtil.isEmpty(_type)){
			return $itemel.myComponet(_type,{method:'getData'});
		}
		return null;
	},
	getItemLable:function(item_el){
		var $itemel = webUtil.getJqueryDom(item_el);
		var _type = $itemel.data('dataType');
		if(!webUtil.isEmpty(_type)){
			return $itemel.myComponet(_type,{method:'getLable'});
		}
		return '';
	},
	setItemData:function(item_el,val){
		var $itemel = webUtil.getJqueryDom(item_el);
		var _type = $itemel.data('dataType');
		if(!webUtil.isEmpty(_type)){
			$itemel.myComponet(_type,{method:'setData',opt:val});
		}
	},
	setFormEnabled:function(val){
		var thisObj = this;
		this.$element.find('.input-item').each(function(){
			var isRead = val;
			if(isRead&&$(this).hasClass("read")){
				isRead = false;
			}
			thisObj.setItemEnabled($(this),isRead);
		});
	},
	setItemEnabled:function(item_el,val){
		var $itemel = webUtil.getJqueryDom(item_el);
		var _type = $itemel.data('dataType');
		if(!webUtil.isEmpty(_type)){
			$itemel.myComponet(_type,{method:'enable',opt:val});
		}
	},
	verifyInputItem:function($el){
		var ret = {code:1,mesg:''};
		if(!webUtil.isEmpty($el)){
			var _type = $el.data('dataType');
			if(!webUtil.isEmpty(_type)){
				var thisObj = this;
				if($el.hasClass('require')){
					var _data = thisObj.getItemData($el);
					ret = $el.myValidate(_type).toValid(_data,'notEmpty');
				}
				var _rules = $el.data('rule');
				var data_rules = webUtil.str2Json(_rules);
				if(!webUtil.isEmpty(data_rules)){
					ret = $el.myValidate(_type).toValids(data_rules);
				}
			}
		}
		return ret;
	},
	verifyInput:function(){
		var isOk = true;
		var thisObj = this;
		this.$element.find('.input-item').each(function(){
			var ret = thisObj.verifyInputItem($(this));
			if(!webUtil.isEmpty(ret)&&!webUtil.isEmpty(ret.code)){
				if(ret.code == 0){
					var _textLable = thisObj.getItemLable($(this))||'输入项:';
					var mesg = ret.mesg||'输入数据规则有误!';
					webUtil.mesg(_textLable+' '+mesg);
					isOk = false;
					return isOk;
				}
			}
		});
		return isOk;
	}
}
$.fn.myForm = function(options) {
    var settings = $.extend(true,{}, options);
    return new myForm($(this),settings);
}
})(jQuery, window, document);