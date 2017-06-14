/**
 *  app 中所有简单表单组件的定义（除table，tab，tree）
 */

;(function($, window, document,undefined) {
	var MyComponet = function(el,type){
		this.$element = el;
		this.type = type||DataType.text;
	}
	MyComponet.prototype = {
		init:function(_opt){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			var thisObj = this;
			if(_type ==DataType.text){
				if(!webUtil.isEmpty(_opt)){
					if(!webUtil.isEmpty(_opt.initData)){
						thisObj.setData(_opt.initData);
					}
				}
				thisObj.initEnvent();
			}else if(_type ==DataType.select){
				var _defSelOpt = {url:'',data:undefined,key:'key',val:'val',selected:undefined,opt:undefined};
				var _selOpt = $.extend(true,{},_defSelOpt, _opt);
				var _items = _selOpt.data;
				if(!webUtil.isEmpty(_items)&&$.isArray(_items)){
					thisObj.initViewr(_selOpt);
					thisObj.initRender(_selOpt.opt);
				}else if(!webUtil.isEmpty(_selOpt.url)){
					webUtil.ajaxData({url:_selOpt.url,async:false,success:function(data){
						_selOpt.data = data.data;
						thisObj.initViewr(_selOpt);
						thisObj.initRender(_selOpt.opt);
					}});
				}else{
					thisObj.initRender(_selOpt.opt);
				}
			}else if(_type ==DataType.checkbox||_type ==DataType.radio){
				var $parentContainer = $thisDom.parent('.mycheckradiobox_container');
				if(webUtil.isEmpty($parentContainer)||$parentContainer.length==0){
					var _ckrad_opt = $.extend(true,{},{event:undefined}, _opt);
					thisObj.initViewr(_ckrad_opt);
					thisObj.initEnvent(_ckrad_opt.event);
				}
			}else if(_type ==DataType.date||_type ==DataType.datetime){
				thisObj.initViewr({icon:_opt.icon});
				thisObj.initRender(_opt);
			}else if(_type == DataType.F7){
				var f7dom = $thisDom.myF7();
				f7dom.init(_opt);
			}
		},
		initEnvent:function(_opt){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			var thisObj = this;
			var opt = $.extend(true,{},{name:'',params:undefined,callBack:undefined}, _opt);
			if(_type == DataType.text){
				
			}else if(_type ==DataType.select){//下拉选择
				
			}else if(_type ==DataType.checkbox||_type ==DataType.radio){
				var $parentContainer = $thisDom.parent('.mycheckradiobox_container');
				if(!webUtil.isEmpty($parentContainer)&&$parentContainer.length>0){
					var _clas = 'div.my'+_type;
					$parentContainer.click(opt.params,function(e){
						if(!$(this).find(_clas).hasClass('disabled')){
							var _checked = $(this).find(_clas).hasClass('checked');
							thisObj.setData(!_checked);
							if(opt.name == 'click'&&!webUtil.isEmpty(opt.callBack)
									&&$.isFunction(opt.callBack)){
								opt.callBack(e.data);
							}
						}
					});
					
					$parentContainer.hover(function(){
						$(this).find(_clas).addClass('hover');
					},function(){
						$(this).find(_clas).removeClass('hover');
					});
				}
			}
		},
		initViewr:function(_opt){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			if(_type ==DataType.select){//下拉选择
				var _defSelOpt = {data:undefined,key:'key',val:'val',selected:undefined};
				var _selOpt = $.extend(true,{},_defSelOpt, _opt);
				var _items = _selOpt.data;
				if(!webUtil.isEmpty(_items)&&$.isArray(_items)&&_items.length>0){
					for(var i=0;i<_items.length;i++){
						var item = _items[i];
						var _optionDom = $('<option value="'+item[_selOpt.key]+'">'+item[_selOpt.val]+"</option>");
						if(!webUtil.isEmpty(_selOpt.selected)
								&&_selOpt.selected==item[_selOpt.key]){
							_optionDom.attr("selected","selected");
						}
						$thisDom.append(_optionDom);
					}
				}
			}else if(_type ==DataType.checkbox||_type ==DataType.radio){
				var _ckrad_opt = $.extend(true,{},{theme:'green',checked:false}, _opt);
				var $parentContainer = $thisDom.parent('.mycheckradiobox_container');
				if(webUtil.isEmpty($parentContainer)||$parentContainer.length==0){
					$parentContainer = $('<div class="mycheckradiobox_container"></div>');
					$thisDom.wrap($parentContainer);
					var _div = $('<div></div>');
					_div.addClass('my'+_type+" "+_ckrad_opt.theme);
					if(_ckrad_opt.checked){
						_div.addClass('checked');
					}
					$thisDom.hide();
					$thisDom.before(_div);
				}
			}else if(_type ==DataType.date||_type ==DataType.datetime){
				var _dateOpt = $.extend(true,{},{icon:'glyphicon glyphicon-calendar'},_opt);
				var $parentContainer = $thisDom.parent('.date');
				if(webUtil.isEmpty($parentContainer)||$parentContainer.length==0){
					$thisDom.css({"border-radius":"0px"});   
					$parentContainer = $('<div class="input-group date"></div>');
					$thisDom.wrap($parentContainer);
					if(!webUtil.isEmpty(_dateOpt.icon)){
						var _iconG  = $('<div class="input-group-addon"><span class="'+_dateOpt.icon+'"></span></div>');
						$thisDom.after(_iconG);
					}
				}
			}
		},
		getLable:function(){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			if(_type ==DataType.checkbox||_type ==DataType.radio){
				return $thisDom.parent().prev('.lable').text();
			}else{
				return $thisDom.prev('.lable').text();
			}
		},
		initRender:function(_opt){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			if(_type ==DataType.select){//下拉选择
				var thisSelect_opt = $.extend(true,{allowClear: true}, _opt);
				$thisDom.select2(thisSelect_opt);
				if($thisDom.hasClass('require')){
					this.setRequire(true);
				}
			}else if(_type ==DataType.date||_type ==DataType.datetime){
				var $parentContainer = $thisDom.parent('.date');
				if(!webUtil.isEmpty($parentContainer)&&$parentContainer.length>0){
					var _format = (_type ==DataType.date)?'yyyy-mm-dd':'yyyy-mm-dd hh:ii:ss';
					var _def_date = {language: 'zh-CN',format:_format,autoclose: true,todayBtn: true,pickerPosition: "bottom-left"};
					var _dateOpt = $.extend(true,{},_def_date,_opt);
					if(_type ==DataType.date){
						$parentContainer.datepicker(_dateOpt);
					}else{
						$parentContainer.datetimepicker(_dateOpt);
					}
				}
			}
		},
		setData:function(data){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			var _data = data;
			if(_type ==DataType.text){
				if(webUtil.isEmpty(data)){
					_data = '';
				}
				$thisDom.val(_data);
			}else if(_type ==DataType.select){//下拉选择
				var comObj = $thisDom.select2();
				comObj.val(_data).trigger("change");
			}else if(_type ==DataType.date||_type ==DataType.datetime){
				var $parentContainer = $thisDom.parent('.date');
				if(!webUtil.isEmpty($parentContainer)&&$parentContainer.length>0){
					var thisDate = _data;
					if(!webUtil.isEmpty(_data)){
						if($.isNumeric(_data)){
							thisDate = new Date();
                     		thisDate.setTime(_data);
                     		var _thisFormtStr = _type==DataType.date?"yyyy-MM-dd":"yyyy-MM-dd h:m:s";
                     		thisDate = thisDate.format(_thisFormtStr);
						}
					}
					$thisDom.val(thisDate);
				}
			}else if(_type ==DataType.checkbox||_type ==DataType.radio){
				var $parentContainer = $thisDom.parent('.mycheckradiobox_container');
				if(!webUtil.isEmpty($parentContainer)&&$parentContainer.length>0){
					var _clas = 'div.my'+_type;
					if(data){
						$parentContainer.find(_clas).addClass('checked');
						$thisDom.prop("checked", true);
					}else{
						$parentContainer.find(_clas).removeClass('checked');
						$thisDom.prop("checked", false);
					}
				}
			}else if(_type==DataType.F7){
				$thisDom.myF7().setData(data);
			}
		},
		getData:function(){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			var _data = '';
			if(_type ==DataType.text){
				_data = $thisDom.val();
			}else if(_type ==DataType.select){//下拉选择
				_data = $thisDom.select2().val();
			}else if(_type ==DataType.date||_type ==DataType.datetime){
				_data = $thisDom.val();
				/*var $parentContainer = $thisDom.parent('.date');
				if(!webUtil.isEmpty($parentContainer)&&$parentContainer.length>0){
					if(_type ==DataType.date){
						_data = $parentContainer.datepicker('getDate');
					}else{
						_data = $parentContainer.datetimepicker('getDate');
					}
				}*/
			}else if(_type ==DataType.checkbox||_type ==DataType.radio){
				_data = $thisDom.prop("checked");
			}else if(_type==DataType.F7){
				_data = $thisDom.myF7().getValue();
			}else{
				_data = $thisDom.val();
			}
			return _data;
		},
		setEnable:function(enable){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			if(_type ==DataType.text
					||_type ==DataType.date||_type ==DataType.datetime){
				if(enable){
					$thisDom.removeAttr("disabled");
				}else{
					$thisDom.attr("disabled","disabled");
				}
			}else if(_type ==DataType.checkbox||_type ==DataType.radio){
				var $parentContainer = $thisDom.parent('.mycheckradiobox_container');
				var _toDom = $parentContainer.find('div.my'+_type);
				if(enable){
					_toDom.removeClass('disabled');
				}else{
					_toDom.addClass('disabled');
				}
			}else if(_type==DataType.F7){
				$thisDom.myF7().setEnabled(enable);
			}
		},
		setRequire:function(_opt){
			var _type = this.type;
			if(webUtil.isEmpty(_type)) return;
			var $thisDom = this.$element;
			var _toDom = $thisDom;
			if(_type ==DataType.select){
				var _select = ($thisDom.next('span.select2').children('span').children('span'));
				if(!webUtil.isEmpty(_select)&&_select.length>0){
					_toDom = _select;
				}
			}
			if(_opt){
				_toDom.addClass('require');
			}else{
				_toDom.removeClass('require');
			}
			
		}
		
	}
	$.fn.myComponet = function(type,options) {
		var defaults = {method:'init',opt:undefined};
		var options = $.extend(true,{},defaults, options);
		if(webUtil.isEmpty(type)) type = DataType.text;
		var mycom = new MyComponet($(this),type);
		var _data = options.opt;
		var _method = options.method;
		if(!webUtil.isEmpty(_method)){
			_method = _method.toLowerCase();
			if(_method=='init'){
				mycom.init(_data);
			}else if(_method=='getdata'){
				return mycom.getData();
			}else if(_method=='setdata'){
				mycom.setData(_data);
			}else if(_method=='enable'){
				mycom.setEnable(_data);
			}else if(_method=='require'){
				mycom.setRequire(_data);
			}else if(_method=='getlable'){
				return mycom.getLable();
			}else{
				webUtil.mesg("未知情况:其他的待续.....");
			}
		}
		return mycom
	}
})(jQuery, window, document);