/**
 * 表单元素验证
 */
var dataRules = {
	addRule:function(name,fun,mesg){
		if(!webUtil.isEmpty(name)){
			this[name] = fun;
			this[name].mesg = mesg||'';	
		}
	},
	getLength:function(value){
		if(webUtil.isEmpty(value)) return 0;
		return value.length;
	},
	formater:function(name,params){
		var mesg = '';
		if(!webUtil.isEmpty(name)){
			mesg = this[name].mesg ;
			if ( params === undefined ) {
				return mesg;
			}else{
				if ( params.constructor !== Array ) {
					params = [ params ];
				}
				$.each( params, function( i, n ) {
					mesg = mesg.replace( new RegExp( "\\{" + i + "\\}", "g" ), function() {
						return n;
					});
				});
			}
		}
		return mesg;
	}
};
;(function($, window, document, undefined) {
	dataRules.addRule('notEmpty',function(value){
		return webUtil.isEmpty(value)===false;
	},'不能为空!');
	dataRules.addRule('email',function(val){
		return /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test( val );
	},'Email地址不正确!');
	dataRules.addRule('url',function(value){
		return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test( value );
	});
	dataRules.addRule('number',function(value){
		return /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test( value );
	},'不是合法的数字!');
	dataRules.addRule('phone',function(value){
		return /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/.test( value );
	},'电话号码不正确!');	
	dataRules.addRule('idCard',function(value){
		return /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test( value )
			 ||/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/.test( value );
	},'身份证号码不正确!');	
	dataRules.addRule('minLen',function(value,min){
		return this.number(min)&&this.getLength(value) >= min;
	},'最小字符数为{0}!');	
	dataRules.addRule('maxLen',function(value,max){
		return this.number(max)&&this.getLength(value) <= max;
	},'最大字符数为{0}!');	
	dataRules.addRule('rangeLen',function(value,len){
		var length = this.getLength(value);
		if($.isArray(len)&&len.length==2){
			return this.number(len[0])&&length>=len[0]&&this.number(len[1])&&length<=len[1];
		}
		return true;
	},'输入的字符数范围为{0}-{1}!');	
	dataRules.addRule('min',function(value,min){
		if(this.number(value)&&this.number(min)){
			return value>=min;
		}
		return true;
	},'最小数字为{0}!');
	dataRules.addRule('max',function(value,max){
		if(this.number(value)&&this.number(max)){
			return value<=max;
		}
		return true;
	},'最大数字为{0}!');
	dataRules.addRule('range',function(value,ranges){
		if(this.number(value)&&$.isArray(ranges)&&ranges.length==2){
			return this.number(ranges[0])&&value>=ranges[0]&&this.number(ranges[1])&&value<=ranges[1];
		}
		return false;
	},'输入的数字范围为{0}-{1}');
	dataRules.addRule('equals',function(value1,value2){
		return value1===value2;
	},'{0}与{1}值必须相等!');
	var MyValidate = function(el, type, opt) {
		this.$element = el;
		this.type = type;
		this.options = opt;
	}
	MyValidate.prototype = {
		getData : function() {
			return $(this.$element).myComponet(this.type, {
				method : 'getData'
			});
		},
		toValid:function(data,rule){
			var _defret = {code:1,mesg:''};//code ==1 代表正确  0 代表错误
			var rt = {code:1};
			if(!webUtil.isEmpty(rule)){
				if ( typeof(rule) === "string" ) {
					//次方法，用于无需参数传递
					var fg = dataRules[rule](data);
					if(fg === false){
						rt.code = 0;
						rt.mesg = dataRules.formater(rule); 
					}
				}else if($.isPlainObject(rule)){
					var _defRuls = {name:'',params:''};
					var _rule = $.extend(true,{},_defRuls,rule);
					if(!webUtil.isEmpty(_rule.name)){
						var fg = dataRules[_rule.name](data,_rule.params);
						if(fg === false){
							rt.code = 0;
							rt.mesg = _rule.formater||dataRules.formater(_rule.name,_rule.params); 
						}
					}
				}else if($.isFunction(rule)){
					rt = _fun(data);
				}
			}
			return $.extend(true,{},_defret,rt);
		},
		toValids:function(rules){
			var _defret = {code:1,mesg:''};//code ==1 代表正确  0 代表错误
			var rt = {code:1};
			if(!webUtil.isEmpty(rules)){
				if($.isFunction(rules)){
					rt = rules(this.getData());
				}else if($.isPlainObject(rules)){
					rt = this.toValid(this.getData(),rules);
				}else if($.isArray(rules)){
					var thisInputData = this.getData();
					for(var i=0;i<rules.length;i++){
						rt = this.toValid(thisInputData,rules[i]);
						if(rt.code == 0){
							break;
						}
					}
				}else if(typeof(rules) === "string"){
					rt = this.toValid(this.getData(),rules);
				}
			}
			return $.extend(true,{},_defret,rt);
		}
	}
	$.fn.myValidate = function(type, options) {
		var settings = $.extend(true, {}, options);
		if (webUtil.isEmpty(type)) type = DataType.text;
		return new MyValidate($(this), type, settings);
	}
})(jQuery, window, document);