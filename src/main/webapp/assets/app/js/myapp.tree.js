/**
 * tree结构的一些操作js
 */

;(function($, window, document,undefined) {
var MyTreeViewer = function(el, opt){
	 this.$element = el,
	 this.defaults = {};
     this.options = $.extend({}, this.defaults, opt);
     this.treeViewer = undefined||this.options.treeViewer;
     this.tree = undefined||this.options.tree;
}
MyTreeViewer.prototype = {
	init:function(opt){
		var _defViewer = {theme:"panel-success",title:"",height:200,search:true};
		var _opt = $.extend({}, _defViewer, opt);
		this.treeViewer = this.$element.find('panel');
		if(webUtil.isEmpty(this.treeViewer)||this.treeViewer.length<=0){
			this.treeViewer = $('<div class="panel"></div>');
			if(!webUtil.isEmpty(_opt.theme)){
				this.treeViewer.addClass(_opt.theme);
			}
			this.$element.prepend(this.treeViewer);
		}
		var _$panel_detail = [];
		_$panel_detail.push('<div class="panel-heading" style="padding: 13px;">');
			_$panel_detail.push('<h3 class="panel-title">'+_opt.title+'</h3>');
			_$panel_detail.push('<ul class="panel-options"></ul>');
		_$panel_detail.push('</div>');
		_$panel_detail.push('<div class="panel-body" style="padding: 0px 2px 2px 2px;">');
			_$panel_detail.push('<ul class="ztree nomargin" style="width: 99%; height: '+(_opt.height-40)+'px; overflow: auto;"></ul>');
		_$panel_detail.push('</div>');
		this.treeViewer.html(_$panel_detail.join(''));
		if(_opt.search){
			this.addBtn({icon:"fa fa-search",onwer:this,clickFun:function(sarchbtn){
				var thisViewer = sarchbtn.onwer;
				if(!webUtil.isEmpty(thisViewer.tree)){
					var _curIdx = 0;
					var _selNods = [];
					var _curVal = '';
					webUtil.showPrompt({title:'输入名称搜索',callBack:function(val){
						var myTree = thisViewer.tree;
						if(_curVal!=val){
							_selNods = myTree.getNodesByParamFuzzy(val);
							_curIdx = 0;
							_curVal = val;
						}
						if(_selNods.length>0&&_curIdx<_selNods.length){
							myTree.selectNode(_selNods[_curIdx]);
							_curIdx +=1;
						}
					}});
				}
			}});
		}
		
	},
	addTree:function(opt,data){
		if(webUtil.isEmpty(this.tree)){
			var $thisTree = this.treeViewer.find('.panel-body>ul.ztree');
			this.tree = $thisTree.myTree(opt,data);
		}
	},
	addBtns:function(btns){
		if(!webUtil.isEmpty(btns)){
			if($.isArray(btns)){
				for(var i=0;i<btns.length;i++){
					this.addBtn(btns[i]);
				}
			}else if($.isPlainObject(btns)){
				this.addBtn(btns);
			}
		}
	},
	addBtn:function(btn){
		if(!webUtil.isEmpty(this.treeViewer)&&!webUtil.isEmpty(btn)){
			var _defBtn = {icon:"",style:{"font-size":"12px", "color":"#ffffff"},text:"",clickFun:undefined};
			var thisBtn = $.extend({}, _defBtn, btn);
			var $thisBtn = $('<i style="cursor:pointer;">'+thisBtn.text+'</i>');
			if(!webUtil.isEmpty(thisBtn.icon)) $thisBtn.addClass(thisBtn.icon);
			$thisBtn.css(thisBtn.style);
			this.treeViewer.find('.panel-heading>ul.panel-options').append($thisBtn);
			if(!webUtil.isEmpty(thisBtn.clickFun)&&$.isFunction(thisBtn.clickFun)){
				$thisBtn.click(thisBtn,function(e){
					thisBtn.clickFun(e.data);
				});
			}
		}
	},
	
	getTree:function(){
		return this.tree;
	}
}
$.fn.myTreeViewer = function(options) {
    var settings = $.extend({}, options);
    return new MyTreeViewer($(this),settings);
}
})(jQuery, window, document);


;(function($, window, document,undefined) {
var MyTree = function(el, opt,data){
	 this.$element = el,
	 this.defaults = {};
	 var thisTreeCom = this;
	 def_beforClick=function(treeId, treeNode){
		 var fg = true;
		 if (treeNode.isParent) {
			 thisTreeCom.tree.expandNode(treeNode);
         	fg = false;
         }
		 if(opt.callback&&opt.callback.beforeClick&&$.isFunction(opt.callback.beforeClick)){
			 fg = opt.callback.beforeClick(treeId,treeNode);
		 }
		 return fg;
	 }
	 var _def_setting = {
		    view: {dblClickExpand: true,selectedMulti: false},
		    data: {simpleData: {enable:true,idKey: "id",pIdKey: "parent_id",rootPId: ""}},
		    callback: {beforeClick: def_beforClick}
		};
	 if(opt.callback&&opt.callback.beforeClick&&$.isFunction(opt.callback.beforeClick)){
		 opt.callback.beforeClick = undefined;
	 }
	 var setting = $.extend({}, _def_setting, opt);
	 if(webUtil.isEmpty(data)) data = [];
	 this.tree = $.fn.zTree.init(el, setting, data);
}
MyTree.prototype = {
	addNodes:function(parentNode,nodes){
		if(webUtil.isEmpty(parentNode)) parentNode = null;
		this.tree.addNodes(parentNode, nodes);
	},
	getSelectNodes:function(){
		return this.tree.getSelectedNodes();
	},
	selectNodeByIndex:function(indx){
		var nodes = this.tree.getNodes();
		this.selectNode(nodes[indx]);
	},
	selectNode:function(node){
		return this.tree.selectNode(node);
	},
	getNodesByParamFuzzy:function(val){
		return this.tree.getNodesByParamFuzzy("name", val, null);
	}
}
$.fn.myTree = function(options,data) {
    var settings = $.extend({}, options);
    return new MyTree($(this),settings,data);
}
})(jQuery, window, document);