/**
 * 数字文本框插件
 * 使用方法：
 * var nubox = $("#num3").myNumberBox();
	   nubox.init({sign:"￥",precision:4,toFormat:true,fixed:true});
	   nubox.setData(468784.54);
	   console.log(nubox.getData());
	   console.log(nubox.getFormaterData());
 */
;(function($, window, document, undefined) {
	function numberformater(num, precision, separator, fixed) {
		if (!isNaN(parseFloat(num)) && isFinite(num)) {
			num = Number(num);
			precision = precision || -1;
			num = (precision >= 0 ? num.toFixed(precision) : num).toString();
			var parts = num.split('.');
			parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,
					'$1' + (separator || ','));
			if (parts.length > 1 && $.type(fixed) === 'boolean' && fixed) {
				var stufix = '0.' + parts[1];
				var sfix = parseFloat(stufix);
				stufix = sfix + '';
				return sfix > 0 ? parts[0] + stufix.substring(1, stufix.length)
						: parts[0];
			}
			return parts.join('.');
		}
		return '';
	}
	var _defNumberOptions = {};
	_defNumberOptions.precision = 2;//-1 表示可以任意小数位数
	_defNumberOptions.sign = "";//数量单位符号 如：金额RMB的￥
	_defNumberOptions.toFormat = true;//是否进行格式化
	_defNumberOptions.formater = undefined;//自定义格式化
	_defNumberOptions.separator = ',';//默认格式化的分隔符
	_defNumberOptions.fixed = true; //在做精度保留时候 是否去除小数末尾的0
	var MyNumberBox = function(el) {
		this.$element = el
	}
	MyNumberBox.prototype = {
		init : function(opt) {
			var opts = $.extend(true, {}, _defNumberOptions, opt)
			var formaterEl = this.$element.clone(true);
			this.$element.before(formaterEl);
			formaterEl.attr('id', '');
			formaterEl.attr('name', '');
			formaterEl.addClass('form-control');
			formaterEl.removeClass('input-item');
			this.$element.hide();
			$(this.$element).data('opts',opts);
			$(this.$element).data('fromaterEl',formaterEl);
			this.initEvent(opts);
		},
		getOptions:function(){
			return $(this.$element).data('opts');
		},
		getformaterEl:function(){
			return $(this.$element).data('fromaterEl');
		},
		initEvent : function(opt) {
			var $this = this;
			var $thisel = this.$element;
			var $thisfel = this.getformaterEl();
			var thisOpions = this.getOptions();
			$thisfel.on("keypress", function(e) {
				var code = (e.keyCode ? e.keyCode : e.which); //兼容火狐 IE    
				if (!$.support.msie && (e.keyCode == 0x8)) { //火狐下不能使用退格键   
					return;
				}
				var val = this.value || '';
				if (0 == thisOpions.precision) {//精度为0 表示不能输入小数点和负号
					return code <= 57
							&& (code > 48 || (code == 48 && val.length > 0));
				} else {
					return (code == 45 || code == 46 && val.length > 0)
							|| (code >= 48 && code <= 57);
				}
			});
			$thisfel.on("keyup", function() {
				var val = this.value || '';
				if (isNaN(val) && val.length > 0 && '-' != val) {//排除中间输入 - 
					val = val.substr(0, val.length - 1);
				}
				if(0==thisOpions.precision&&!webUtil.isEmpty(this.value)
						&&!isNaN(this.value)){
					val = parseInt(this.value);
				}
				this.value = val;
				$($thisel).val(val);
			});
			$thisfel.on("blur", function() {
				$this.setData(this.value);
			});
			$thisfel.on("focus", function() {
				this.style.imeMode = 'disabled';
				this.value = $($thisel).val();
			});
		},
		setData : function(data) {
			var $thisel = this.$element;
			var $thisfel = this.getformaterEl();
			data = data + '';
			if (isNaN(data)) {
				data = "";
			}
			if (data.lastIndexOf(".") == (data.length - 1)) {
				data = data.substr(0, data.length - 1);
			}
			var thisOpions = this.getOptions();
			$($thisel).val(data);
			var fomatVal = data;
			if ((typeof (thisOpions.toFormat) == 'boolean')
					&& thisOpions.toFormat) {
				if (thisOpions.formater && $.isFunction(thisOpions.formater)) {
					//自定义的格式化函数接口
					fomatVal = thisOpions.formater(data);
				} else {
					fomatVal = thisOpions.sign
							+ ' '
							+ numberformater(data, thisOpions.precision,
									thisOpions.separator, thisOpions.fixed);
				}
			}
			$($thisfel).val(fomatVal);
		},
		getData : function() {
			return $(this.$element).val();
		},
		getValue : function() {
			return $(this.$element).val();
		},
		getFormaterData : function() {
			return $(this.getformaterEl()).val();
		},
		setEnabled : function(enabled) {
			var $thisfel = this.getformaterEl();
			if($thisfel&&$.type(enabled) === "boolean"){
				if(enable){
					$thisfel.removeAttr("disabled");
				}else{
					$thisfel.attr("disabled","disabled");
				}
			}
		},
		isEnabled : function() {
			var $thisfel = this.getformaterEl();
			var enabled = true;
			if($thisfel){
				var propDisable = $thisDom.prop('disabled');
				if(!webUtil.isEmpty(propDisable)){
					enabled = !propDisable;
				}
			}
			return enabled;
		},
		isRequire : function() {
			var $thisfel = this.getformaterEl();
			if($thisfel)
				return $thisfel.hasClass('require');
			return false;
		},
		setRequire : function(require) {
			var $thisfel = this.getformaterEl();
			if($thisfel&&!webUtil.isEmpty(require)
					&&$.type(require) === "boolean"){
				if(require){
					$thisfel.addClass("require");
				}else{
					$thisfel.removeClass("require");
				}
			}
		},
		setFocus:function(focus){
			var $thisfel = this.getformaterEl();
			if($thisfel&&!webUtil.isEmpty(focus)
					&&$.type(focus) === "boolean"){
				if(focus){
					$thisfel.focus();
				}else{
					$thisfel.blur();
				}
			}
		}
	}
	$.fn.myNumberBox = function(options) {
		return new MyNumberBox($(this));
	}
})(jQuery, window, document);