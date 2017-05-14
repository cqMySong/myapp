/**
 *  一些通用的工具类
 */

var ajax_defaultOpt = {type:"POST",url:"",data:"",contentType:"application/json; charset=utf-8",dataType:"json",success:function(data){}};
var webUtil = {
	isEmpty:function(obj){
		if($.isNumeric(obj)&&(obj ==0 || obj=='0')){
			return false;
		}
		if(obj){
			return false;
		}else{
			return true;
		}
	},
	uuIdReplaceID:function(billId){
		if(billId){
			var r, re;                 
			re = /\+/g;
			r = billId.replace(re, "%2B");
			re = /\=/g;
			r = r.replace(re, "%3D");
			re = /\//g;
			r = r.replace(re, "%2F");
			return(r);
		}
	    return billId; 
	},
	mesg:function(mesg){
		layer.msg(mesg, {icon: 1});//最简单的mesg体现信息
	},
	getMesgIcon:function(type){
		//icon :1：√ ,2： ✘, 3:?,4:锁，5：哭脸，6：笑脸，7：感叹号
		var _icon = 1;
		if(!webUtil.isEmpty(type)){
			if('error'==type){
				_icon.icon = 2;
			}else if('warning'==type){
				_icon.icon = 7;
			}else if('question'==type){
				_icon.icon = 3;
			}else if('lock'==type){
				_icon.icon = 4;
			}else if('happley'==type){
				_icon.icon = 6;
			}else if('sad'==type){
				_icon.icon = 5;
			}
		}
		return _icon;
		
	},
	showConfirm:function(_comfirm){
		var _defConfirm = {title:'信息',content:'',callBack:undefined};
		var _opt = $.extend({},_defConfirm,_comfirm);
		var _opt_obj = {title:'输出提示'||_opt.title,icon:1,shade:0.1,scrollbar:false,btn: ['确定','取消']};
		_opt_obj.icon = webUtil.getMesgIcon('question');
		
		parent.layer.confirm(_opt.content, _opt_obj, function(index){
		  if(!webUtil.isEmpty(_opt.callBack)&&$.isFunction(_opt.callBack)){
			  _opt.callBack(true);
		  }
		  parent.layer.close(index);
		}, function(index){
			if(!webUtil.isEmpty(_opt.callBack)&&$.isFunction(_opt.callBack)){
				  _opt.callBack(false);
			  }
			parent.layer.close(index);
		});
	},
	showMesg:function(obj){
		// layer open 中的type 定义 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
		//shade  0-1  越大背景色越深
		var _opt = {title:obj.title||'....',content:obj.content||'',shade:0.1,scrollbar:false}
		_opt.icon = webUtil.getMesgIcon(obj.type);
		parent.layer.open(_opt);
	},
	openWin:function(_winOpt){
		// 此处 最大整四个btn 注意回调函数必须返回值 已确定是否允许关闭此win (true 关闭 false 不关闭)
		var _def_win = {title:'^~^',url:'',maxmin:true,width:800,height:600
					,btns:['确定','取消'],callBack:undefined};
		var _opt = $.extend({},_def_win,_winOpt);
		var btns = _opt.btns;
		var callBack = _opt.callBack;
		parent.layer.open({type : 2,
			btn : _opt.btns,title :_opt.title,scrollbar : false,
			shadeClose : false,close : false,shade : true,shade : 0.1,
			maxmin : _opt.maxmin,area:[ _opt.width+'px', _opt.height+'px' ],
			content : _opt.url,
			btn1:function(index) {
				var toClose = true;
				if(btns.length>0){
					if(!webUtil.isEmpty(callBack)&&$.isFunction(callBack)){
						toClose = callBack(1);
					}
				}
				if(toClose) parent.layer.close(index);
			},
			btn2:function(index) {
				if(btns.length>1){
					if(!webUtil.isEmpty(callBack)&&$.isFunction(callBack)){
						return callBack(2);
					}
				}
				parent.layer.close(index);
			},
			btn3:function(index) {
				if(btns.length>2){
					if(!webUtil.isEmpty(callBack)&&$.isFunction(callBack)){
						return callBack(3);
					}
				}
				parent.layer.close(index);
			},
			btn4:function(index) {
				if(btns.length>3){
					if(!webUtil.isEmpty(callBack)&&$.isFunction(callBack)){
						return callBack(4);
					}
				}
				parent.layer.close(index);
			},
			cancel : function(index) {
				if(!webUtil.isEmpty(callBack)&&$.isFunction(callBack)){
					return callBack(99);
				}
				parent.layer.close(index);
			}
		});
	},
	showPrompt:function(_prompt){
		var _opt = {title:_prompt.title||'请输入',type:_prompt.type||'text'};
		if(_opt.type=='textarea'){
			_opt.formType = 2;
		}else if(_opt.type=='password'){
			_opt.formType = 1;
		}else{
			_opt.formType = 0;
		}
		parent.layer.prompt(_opt,function(val, index){
			if(!webUtil.isEmpty(_prompt.callBack)&&$.isFunction(_prompt.callBack)){
				_prompt.callBack(val);
			}
			parent.layer.close(index);
		});
	},
	getFrame:function(_id){
		var ofrm1 = document.getElementById(_id).document;
		if (ofrm1 == undefined) {
			return document.getElementById(_id).contentWindow;
		} else {
			return document.frames[_id];
		}
	},
	ajaxData:function(_opt){
		var opt = $.extend({},ajax_defaultOpt,_opt);
		var _thisUrl = app.root+'/'+opt.url;
		$.ajax({type:opt.type,url:_thisUrl,data:opt.data,dataType:opt.dataType,success:function(data){
			if(!webUtil.isEmpty(data)){
				var statusCode = $(data).attr('statusCode');
				if(statusCode!=0){ //异常或者错误或者提示或者警告
					var msgObj = {title:".::系统提示::.",type:"info"};//==1 提示
					msgObj.content = $(data).attr('statusMesg');
					if(statusCode<0){//异常 错误
						msgObj.title = ".::系统操作"+(statusCode==-100?"异常":"错误")+"::.";
						msgObj.type = "error";
					}else if(statusCode==100){//警告
						msgObj.title = ".::系统操作警告::.";
						msgObj.type = "warning";
					}
					webUtil.showMesg(msgObj);
				}
				if(statusCode>=0){
					opt.success(data);
				}
			}
		},complete:function(){}
		,error:function(){
			alert('请求失败');
		}
		});
	},
	numberFormatter:function(num, precision, separator){
		var parts;
		if (!isNaN(parseFloat(num)) && isFinite(num)) {
			num = Number(num);
			if(webUtil.isEmpty(precision)){
				precision = 2;
			}
			num = (typeof precision !== 'undefined' ? num.toFixed(precision) : num).toString();
			parts = num.split('.');
			parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','));

			return parts.join('.');
		}
		return '0.00';
	},
	getRandomWord:function (length){
	    var str = "",
	        range = length,
	        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	               , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	               , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

	    for(var i=0; i<range; i++){
	        pos = Math.round(Math.random() * (arr.length-1));
	        str += arr[pos];
	    }
	    return str;
	},
	getJqueryDom:function(el,str){
		var _$dom = el || str;
		if (typeof (_$dom) == 'string') {
			_$dom = $(_$dom);
		}
		return _$dom;
	},
	getDomHeight:function(dom){
		dom = dom || document; 
	    var body = dom.body, html = dom.documentElement; 
	    return Math.max(body.scrollHeight, body.offsetHeight,  
	        html.clientHeight, html.scrollHeight, html.offsetHeight ); 
	},
	setIframeAutoHeight:function(elId){
		 var ifrm = document.getElementById(elId); 
		 var dom = ifrm.contentDocument? ifrm.contentDocument:ifrm.contentWindow.document; 
		 ifrm.style.visibility = 'hidden'; 
		 ifrm.style.height = "10px"; 
		 ifrm.style.height = webUtil.getDomHeight(dom) + 4+ "px"; 
		 ifrm.style.visibility = 'visible'; 
		 var _parent = $("#"+elId).parent('div');
		 if(!webUtil.isEmpty(_parent)){
			 _parent.height(ifrm.style.height);
		 }
	}
};

