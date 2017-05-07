/**
 *  一些通用的工具类
 */

var ajax_defaultOpt = {type:"POST",url:"",data:"",dataType:"json",success:function(data){}};
var webUtil = {
	isEmpty:function(obj){
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
	showMesg:function(obj){
		alert(obj.mesg);
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
		$.ajax({type:opt.type,url:opt.url,data:opt.data,dataType:opt.dataType,success:function(data){
			if(!webUtil.isEmpty(data)){
				var statusCode = $(data).attr('statusCode');
				if(statusCode!=0){ //异常或者错误或者提示或者警告
					var msgObj = {title:".::系统提示::.",type:"info"};//==1 提示
					msgObj.mesg = $(data).attr('statusMesg');
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
	}
};

