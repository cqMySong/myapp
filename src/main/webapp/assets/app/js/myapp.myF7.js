/**
 * f7结构的一些操作js
 */

;(function($, window, document,undefined) {
var MyF7 = function(el){
	 this.$element = el,
	 this.uiWin_opt = undefined,
	 this.f7Btn = null;
	 this.onShow = undefined;
	 this.displayName = 'name';
	 this.commitName = 'id';
	 this.dataFormat = undefined;
	 this.dataChange = undefined;
	 this.closeWin = undefined;
};
MyF7.prototype = {
	init:function(opt){
		var $thisDom = this.$element;
		$thisDom.attr("readonly","readonly");
		this.f7Btn = $('<div class="input-group-addon" style="cursor:pointer;"><span class="fa fa-search"></span></div>');
		$thisDom.after(this.f7Btn);
		$thisDom.data("f7enabled",true);
		if(!webUtil.isEmpty(opt.enabled)) {
			$thisDom.data("f7enabled",opt.enabled);
		}
		this.uiWin_opt = $.extend(true,{},{title:'信息查询',url:'',uiParams:undefined,width:600,height:450},opt.uiWin);
		if(opt.displayName){
			this.displayName = opt.displayName;
		}
		if(opt.commitName){
			this.commitName = opt.commitName;
		}
		$thisDom.data('displayName',this.displayName);
		$thisDom.data('commitName',this.commitName);
		if(opt.dataFormat){
			this.dataFormat = opt.dataFormat;
		}
		if(opt.dataChange&&$.isFunction(opt.dataChange)){
			this.dataChange = opt.dataChange;
		}
		if(opt.onShow&&$.isFunction(opt.onShow)){
			this.onShow = opt.onShow;
		}
		if(opt.closeWin&&$.isFunction(opt.closeWin)){
			this.closeWin = opt.closeWin;
		}
		if(!webUtil.isEmpty(opt.mutil)&&typeof(opt.mutil) == 'boolean'){
			this.setMutil(opt.mutil);
		}
		this.addEevent(opt.event);
	},
	setMutil:function(mutil){
		if(!webUtil.isEmpty(mutil)&&typeof(mutil) == 'boolean'){
			this.$element.data('mutil',mutil+'');
		}
	},
	isMutil:function(){
		return this.$element.data('mutil');
	},
	addEevent:function(event){
		if(!webUtil.isEmpty(this.f7Btn)){
			this.f7Btn.click({f7:this,ev:event},function(e){
				var edata = e.data;//整个f7对象
				var myf7 = edata.f7;
				myf7.show();
			});
		}
	},
	show:function(){
		if(!webUtil.isEmpty(this.uiWin_opt)){
			var enabled = this.isEnabled();
			if(!enabled) return;
			var _go = true;
			if(!webUtil.isEmpty(this.onShow)&&$.isFunction(this.onShow)){
				_go = this.onShow(myf7);
			}
			if(!_go) return;
			var _win = $.extend(true,{},this.uiWin_opt);
			var _toUrl = _win.url;
			if(!webUtil.isEmpty(_toUrl)){
				var thisF7 = this;
				var thisUrl = webUtil.toUrl(_toUrl+"/f7show");
				_win.url = thisUrl+'?mutil='+this.isMutil();
				_win.btns = ['确定','取消'];
				_win.maxmin = false;
				_win.btnCallBack = function(index,layerIndex,layero){
					if(layero){
						var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
						var f7Data = iframe_win.getData();
						var isOk = false;
						if(index==1){
							var oldVal = thisF7.getValue();
							var oldData = thisF7.getData();
							thisF7.setData(f7Data);
							var newVal = thisF7.getValue();
							if(oldVal!=newVal&&
									!webUtil.isEmpty(thisF7.dataChange)&&$.isFunction(thisF7.dataChange)){
								thisF7.dataChange(oldData,f7Data);
							}
							isOk = true;
						}
						if(!webUtil.isEmpty(thisF7.closeWin)&&$.isFunction(thisF7.closeWin)){
							thisF7.closeWin(isOk,f7Data);
						}
					}
					return true;
				};
				webUtil.openWin(_win);
			}
		}
	},
	setData:function(data){
		$(this.$element).data('data',data);
		if(this.dataFormat&&$.isFunction(this.dataFormat)){
			this.dataFormat(this.$element,data);
		}else{
			var _txt = '';
			var displayName = this.$element.data('displayName');
			if(!webUtil.isEmpty(data)){
				if($.isArray(data)){
					for(var i=0;i<data.length;i++){
						var itemData = data[i];
						if(i>0) _txt += ',';
						_txt += itemData[displayName];
					}
				}else{
					_txt = data[displayName];
				}
			}
			$(this.$element).val(_txt);
		}
	},
	getData:function(){
		return $(this.$element).data('data');
	},
	getValue:function(){
		var data = $(this.$element).data('data');
		var commitVal = '';
		if(!webUtil.isEmpty(data)&&!webUtil.isEmpty(this.commitName)){
			if($.isArray(data)){
				for(var i=0;i<data.length;i++){
					var itemData = data[i];
					if(i>0) commitVal += ',';
					commitVal += itemData[this.commitName]
				}
			}else{
				var val = data[this.commitName];
				if(!webUtil.isEmpty(val)){
					commitVal = val;
				}
			}
		}
		return commitVal;
	},
	setEnabled:function(enabled){
		this.$element.data("f7enabled",enabled);
	},
	isEnabled:function(){
		return this.$element.data("f7enabled");
	},
	isRequire:function(){
		return this.$element.hasClass('require');
	},
	setRequire:function(_opt){
		if(_opt){
			this.$element.addClass('require');
		}else{
			this.$element.removeClass('require');
		}
	},
	setFocus:function(focus){
		if(!webUtil.isEmpty(focus)
				&&$.type(focus) === "boolean"){
			if(focus){
				this.$element.focus();
			}else{
				this.$element.blur();
			}
		}
	}
}
$.fn.myF7 = function(options) {
    return new MyF7($(this));
}
})(jQuery, window, document);
