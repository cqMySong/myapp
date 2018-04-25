/**
 *  一些通用的工具类
 */
if(typeof DataType == "undefined"){
	var DataType = {};
	DataType.text = 'text';
	DataType.textarea = 'textarea';
	DataType.date = 'date';
	DataType.datetime = 'datetime';
	DataType.select = 'select';
	DataType.time = 'time';
	DataType.F7 = 'f7';
	DataType.number = 'number';
	DataType.checkbox = 'checkbox';
	DataType.radio = 'radio';
	DataType.password = 'password';
	DataType.attach = 'attach';
}
if(typeof OperateType == "undefined"){
    var OperateType = {};
    OperateType.addnew = 'addNew';
    OperateType.edit = 'edit';
    OperateType.view = 'view';
    OperateType.save = 'save';
    OperateType.submit = 'submit';
    OperateType.remove = 'remove';
    OperateType.audit = 'audit';
    OperateType.unaudit = 'unAudit';
    OperateType.attach = 'attach';
    OperateType.refesh = 'refesh';
    OperateType.flowAudit='flowAudit';
}
if(typeof EarlyWarning == "undefined"){
    var EarlyWarning = {};
    EarlyWarning.danger="#f56c79";
    EarlyWarning.warning="#f5e727";
    EarlyWarning.normal="";
    EarlyWarning.serious="#FF0000";
    EarlyWarning.danger_font="#";
    EarlyWarning.warning_font="";
    EarlyWarning.normal_font="";
    EarlyWarning.serious_font="";
}
if(typeof BillState == "undefined"){
    var BillState = {"ADDNEW":"编制中","SAVE":"已保存","SUBMIT":"已提交","AUDIT":"已审核","NOPASS":"审核不通过"};
    var SchemeState = {"COMPANY":"公司内审中","SUPERVISION":"监理审批中","PROPRIETOR":"业主审核中",
		"FINISH":"审批完成"};
    var HandleType = {"NOT_HANDLE":"未办理","IN_PROCESS":"办理中","ALREADY_HANDLE":"已办理"};
    var ChargingBasis  = {"INCREASE_CONTRACT":"合同外增加","EMERGENCY_INSPECTION":"应急检查","OWNER_ORDER":"业主指令",
	"CONTRACT_STIPULATION":"合同约定","INCREASE_QUANTITY":"工程量增加","OTHER":"其他"};
    var TypeOfWork = {"GENERAL":"普工","MECHANIC":"技工"};
}
//'yyyy-MM-dd h:m:s'
//yyyy-MM-dd
Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
};
Date.prototype.addDays = function(d){
	var date = this.getDate(); 
	this.setDate(date + d); 
	return this; 
};
Date.prototype.addWeeks = function(w){
	return this.addDays(w * 7); 
};
Date.prototype.addMonths= function(m){
	var month = this.getMonth(); 
	this.setMonth(month + m); 
	return this; 
};
Date.prototype.addYears = function(y){
	var year = this.getFullYear(); 
	this.setFullYear(year + y); 
	return this; 
};
Date.prototype.getFirstDayOfWeek = function(){
	var day = this.getDay() || 7;
	return new Date(this.getFullYear(), this.getMonth(), this.getDate() + 1 - day);
};
/** javascript map 对象
var map=new Map(); 
map.put("a","A");map.put("b","B");map.put("c","C");
map.get("a"); //返回：A
map.entrySet() // 返回Entity[{key,value},{key,value}]
map.containsKey('kevin') //返回:false
**/
function Map() {
    this.keys = new Array();
    this.data = new Object();

    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
            this.data[key] = value;
        }else{
            this.data[key]=this.data[key];
        }
        return true;
    };

    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };

    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
        for(var i=0;i<this.keys.length;i++){
            if(key===this.keys[i]){
                var del_keys= this.keys.splice(i,1);
                for(k in del_keys){
                    this.data[k] = null;
                }
                return true;
            }
        }
        return false;
    };

    /**
     * 遍历Map,执行处理函数
     *
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn){
        if(typeof fn != 'function'){
            return;
        }
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var k = this.keys[i];
            fn(k,this.data[k],i);
        }
    };

    /**
     * 获取键值数组
     * @return entity[{key,value},{key,value}]
     */
    this.entrySet = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++) {
            entrys[i] = {
                key : this.keys[i],
                value : this.data[this.keys[i]]
            };
        }
        return entrys;
    };

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function() {
        return this.keys.length == 0;
    };

    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };

    this.containsKey=function(key){
        return this.keys.filter(function(v){
           if(v===key){
               return key;
           }
        }).length>0;
    };
    /**
     * 重写toString
     */
    this.toString = function(){
        var s = "{";
        for(var i=0;i<this.keys.length;i++){
            var k = this.keys[i];
            s += k+"="+this.data[k];
            if(this.keys.length>i+1){
                s+=',';
            }
        }
        s+="}";
        return s;
    };
    /**
     * 解析字符串到Map
     * {a=A,b=B,c=B,}
     */
    this.parserStringAndAddMap=function(str){
        var count=0;
        if(str && str.length>0){
            str=str.trim();
            var startIndex=str.indexOf("{"),endIndex=str.lastIndexOf("}");
            if(startIndex!==-1 && endIndex!==-1){
                str=str.substring(startIndex+1,endIndex);
                var arrs= str.split(",");
                for(var i=0;i<arrs.length;i++){
                    var kv=arrs[i].trim();
                    if(kv.length>0 && kv.indexOf("=")!==-1){
                        var kv_arr=kv.split("=");
                        if(kv_arr.length==2){
                            if(this.put(kv_arr[0].trim(),kv_arr[1].trim())){
                                count++;
                            }else{
                                console.error('error: kv:'+kv);
                            }

                        }
                    }
                }
            }else{
                console.log("data error:"+str);
            }
        }else{
            console.log('data is not empty');
        }
        return count;
    };
}
var ajax_defaultOpt = {type:"POST",url:"",async:true,data:"",contentType:"application/json; charset=utf-8",dataType:"json",success:function(data){}};
var webUtil = {
	betweenDateDays:function(begDate,endDate,fromater){
		var days = null;
		if(webUtil.isEmpty(fromater)) fromater = 'yyyy-MM-dd';
		if(!webUtil.isEmpty(begDate)&&!webUtil.isEmpty(begDate)){
			if($.isNumeric(begDate)){
				var thisDate = new Date();
         		thisDate.setTime(begDate);
         		begDate = thisDate.format(fromater);
			}
			if($.isNumeric(endDate)){
				var thisDate = new Date();
         		thisDate.setTime(endDate);
         		endDate = thisDate.format(fromater);
			}
			if(!$.isNumeric(begDate)){
				begDate = Date.parse(begDate);
			}
			if(!$.isNumeric(endDate)){
				endDate = Date.parse(endDate);
			}
			days = (endDate-begDate)/(24 * 60 * 60 * 1000)+1;
		}
		return days;
	},
	isEmpty:function(obj){
		if(typeof(obj) == 'number'){
			return false;
		}
		if (typeof(obj) == 'boolean') {
			return false;
		}
		if(obj){
			if($.isPlainObject(obj)){
				return $.isEmptyObject(obj);
			}
			return false;
		}else{
			return true;
		}
	},
	toUrl:function(url){
		var toURL = app.root;
		if(url.indexOf('/')!=0){
			toURL += '/';
		}
		return toURL+url;
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
		parent.layer.msg(mesg, {icon: 1});//最简单的mesg体现信息
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
		var _opt = $.extend(true,{},_defConfirm,_comfirm);
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
		var _opt = {title:obj.title||'....',content:obj.content||'',shade:0.1,scrollbar:false};
		_opt.icon = webUtil.getMesgIcon(obj.type);
		parent.layer.open(_opt);
	},
	openWin:function(_winOpt){
		// 此处 最大整四个btn 注意回调函数必须返回值 已确定是否允许关闭此win (true 关闭 false 不关闭)
		var _def_win = {title:'^~^',url:'',maxmin:true,width:800,height:600,uiParams:undefined
					,btns:['确定','取消'],callBack:undefined,btnCallBack:undefined,colseCallBack:undefined};
		var _opt = $.extend({},_def_win,_winOpt);
		var btns = _opt.btns;
		var btnCallBack = _opt.btnCallBack;
		var winCallBack = _opt.callBack;
		var colseCallBack = _opt.colseCallBack; 
		var winUrl = _opt.url;
		var uiCtx = _opt.uiParams;
		if(!webUtil.isEmpty(winUrl)&&!webUtil.isEmpty(uiCtx)){
			if($.isFunction(_opt.uiParams)){
				uiCtx = _opt.uiParams();
			}
			if($.isPlainObject(uiCtx)){
				uiCtx =  'uiCtx='+webUtil.json2Str(uiCtx);
			}
			winUrl += (winUrl.indexOf('?')>0?'&':'?')+encodeURI(uiCtx);
		}
		var _maxHeight = $(top.window).height(); 
		if(_opt.height>_maxHeight){
			_opt.height = _maxHeight-2;
		}
		var _maxWidth = $(top.window).width();
		if(_opt.width>_maxWidth){
			_opt.width = _maxWidth-2;
		}
		//winUrl = encodeURI(winUrl.replace(/\"/g,"'")); 
		parent.layer.open({type : 2,
			btn : _opt.btns,title :_opt.title,scrollbar : false,
			shadeClose : false,close : false,shade : true,shade : 0.1,zIndex:2500,
			maxmin : _opt.maxmin,area:[ _opt.width+'px', _opt.height+'px' ],
			content : winUrl,skin: _opt.skin||'layui-layer-lan',
			btn1:function(index,layero) {
				var toClose = true;
				if(btns.length>0){
					if(!webUtil.isEmpty(btnCallBack)&&$.isFunction(btnCallBack)){
						toClose = btnCallBack(1,index,layero);
					}
				}
				if(toClose) parent.layer.close(index);
			},
			btn2:function(index,layero) {
				if(btns.length>1){
					if(!webUtil.isEmpty(btnCallBack)&&$.isFunction(btnCallBack)){
						return btnCallBack(2,index,layero);
					}
				}
				parent.layer.close(index);
			},
			btn3:function(index,layero) {
				if(btns.length>2){
					if(!webUtil.isEmpty(btnCallBack)&&$.isFunction(btnCallBack)){
						return btnCallBack(3,index,layero);
					}
				}
				parent.layer.close(index);
			},
			btn4:function(index,layero) {
				if(btns.length>3){
					if(!webUtil.isEmpty(btnCallBack)&&$.isFunction(btnCallBack)){
						return btnCallBack(4,index,layero);
					}
				}
				parent.layer.close(index);
			},
			cancel : function(index) {
				if(!webUtil.isEmpty(btnCallBack)&&$.isFunction(btnCallBack)){
					return btnCallBack(99);
				}
				parent.layer.close(index);
			},
			success:function(layero,index){
				if(!webUtil.isEmpty(winCallBack)&&$.isFunction(winCallBack)){
					 winCallBack(layero,index);
				}
			},
			end:function(){
				if(!webUtil.isEmpty(colseCallBack)&&$.isFunction(colseCallBack)){
					colseCallBack();
				}
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
		var opt = $.extend(true,{},ajax_defaultOpt,_opt);
		var _thisUrl = webUtil.toUrl(_opt.url);
		var loadIdx = layer.msg('数据处理中...', {icon: 16,time:0,shade : true,shade: 0.1});
		$.ajax({type:opt.type,url:_thisUrl,async:opt.async,data:opt.data,dataType:opt.dataType,success:function(data){
			layer.close(loadIdx);
			if(!webUtil.isEmpty(data)){
				var statusCode = $(data).attr('statusCode');
				if(statusCode!=0){ //异常或者错误或者提示或者警告
					var msgObj = {title:".::系统提示::.",type:"info"};//==1 提示
					msgObj.content = $(data).attr('statusMesg');
					if(statusCode==-99){//权限验证或登陆验证
						parent.layer.open({title:data.title,icon:5,content:data.statusMesg,end:function(){
							if(!webUtil.isEmpty(data.toUrl)){
								window.open(data.toUrl,'_top');
							}
						}});
					}else{
						if(statusCode<0){//异常 错误
							msgObj.title = ".::系统操作"+(statusCode==-100?"异常":"错误")+"::.";
							msgObj.type = "error";
						}else if(statusCode==100){//警告
							msgObj.title = ".::系统操作警告::.";
							msgObj.type = "warning";
						}
						webUtil.showMesg(msgObj);
					}
				}
				if(statusCode>=0){
					opt.success(data);
				}
			}
		},complete:function(){
			layer.close(loadIdx);
		}
		,error:function(event, XMLHttpRequest, ajaxOptions, thrownError){
			layer.close(loadIdx);
			webUtil.showMesg('请求失败');
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
	        var pos = Math.round(Math.random() * (arr.length-1));
	        str += arr[pos];
	    }
	    return str;
	},
	getJqueryDom:function(el,str){
		var _$dom = el ;
		if (!webUtil.isEmpty(_$dom)) {
			if(typeof (_$dom) == 'string') _$dom = $(_$dom);
		}else if(!webUtil.isEmpty(str)){
			_$dom = str;
			if(typeof (_$dom) == 'string') _$dom = $(str);
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
		 ifrm.style.height = (webUtil.getDomHeight(dom) -30) + "px"; 
		 ifrm.style.visibility = 'visible'; 
		 var _parent = $("#"+elId).parent('div');
		 if(!webUtil.isEmpty(_parent)){
			 _parent.height(ifrm.style.height);
		 }
	},
	getMainTabBodyHeight:function(dom){
		var ifrmHeiht = webUtil.getDomHeight(dom) -30;
		ifrmHeiht = Math.max(ifrmHeiht,webUtil.getDomHeight(top.document)-120);
		return ifrmHeiht;
	},
	setMainTabHeight:function(elId){
		var ifrm = document.getElementById(elId); 
		var dom = ifrm.contentDocument? ifrm.contentDocument:ifrm.contentWindow.document; 
		ifrm.style.visibility = 'hidden'; 
		ifrm.style.height = '10px';
		var maxHeight = Math.max(top.getTopMainHeight(),webUtil.getMainTabBodyHeight(dom));
		ifrm.style.height = maxHeight + "px"; 
		ifrm.style.visibility = 'visible'; 
		var _parent = $("#"+elId).parent('div');
		 if(!webUtil.isEmpty(_parent)){
			 _parent.height(ifrm.style.height);
		 }
	},
	initMainPanel:function(dom){
		var $mainPanel = webUtil.getJqueryDom(dom, '.myMainPanel');
		if(!webUtil.isEmpty($mainPanel)&&$mainPanel.length>0){
			$mainPanel.height(webUtil.getMainTabBodyHeight(document));
		}
	},
	str2Json:function(str){
		var _jsonObj = {};
		if(!webUtil.isEmpty(str)){
			try{
				_jsonObj = eval('('+str+')');
			}catch (e) {
				_jsonObj = str;
			}
		}
		return _jsonObj;
	},
	json2Str:function(_json){
		var _str = "";
		if(!webUtil.isEmpty(_json)){
			_str = JSON.stringify(_json);
		}
		return _str;
	}
	,createIconDom:function(icon){
		var iconHtml = $('<i></i>');
		if(!webUtil.isEmpty(icon)){
			if($.type(icon) === "string" ){
				iconHtml.addClass(icon);
			}else if($.isPlainObject(icon)){
				if(!webUtil.isEmpty(icon.name)){
					if(!webUtil.isEmpty(icon.type)){
						iconHtml.addClass(icon.type);
					}
					if('UNICODE'==icon.codeType){
						iconHtml.append('&#x'+icon.name+';');
					}else if('CLASS'==icon.codeType){
						iconHtml.addClass(icon.name);
					}else{
						iconHtml.append('&nbsp;&nbsp;');
					}
				}
			}
		}
		return iconHtml;
	},
     showAttach:function(bid,title,operate){
		if(webUtil.isEmpty(bid)) return;
		if(webUtil.isEmpty(title)) title = '单据';
		if(webUtil.isEmpty(operate)) operate='view';

		var attachUrl = webUtil.toUrl('base/attach')+'/toAttach';
		var _win = {url:attachUrl,maxmin:false,title:title+'-附件管理'};
		_win.uiParams = 'billId='+bid+'&operate='+operate;
		_win.btns = ['关闭'];
		webUtil.openWin(_win);
	}
};

