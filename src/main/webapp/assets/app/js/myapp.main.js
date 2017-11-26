/**
 * 主要针对main 首页写的一些js，不代表通用性
 */

/**
 * pillmenu 只是支持两级菜单
 */
//{theme:"",skin:"",menus:[{title="",url="",openType="",icon="",child:[{....}]}]}
//skin:danger,info
//theme : white,quirk 只有这两种主题
var _def_pillmenu = {theme:"white",skin:""};
var _def_pillmenu_item ={title:"我的标题",url:"",target:"#mainTab",icon:"",active:false,child:[]};

;(function($, window, document,undefined) {
var MyPillTreeMenu = function(ele, opt){
	 this.$element = ele;
	 var defaults = {};
     this.options = $.extend(true,{},defaults, opt);
}
MyPillTreeMenu.prototype = {
	init:function(opt){
		var $thisDom = this.$element;
		if(!webUtil.isEmpty(opt)){
			this.initStyle(opt);
			this.addClickEvent();
		}
	},
   initStyle:function(_menu){
		var _$dom = this.$element;
		if(!_$dom.hasClass('nav-wrapper')){
            _$dom.addClass('nav-wrapper');
        }
		var _opt = {};
        $.extend(true,_opt,_def_pillmenu);
        if(_menu&&$.isPlainObject(_menu)){
            $.extend(_opt,_menu);
        }
        var _theme = _opt.theme;
        _$dom.addClass(_theme);
        var _skin = _opt.skin;
        if(!webUtil.isEmpty(_skin)){
        	_skin = "nav-"+(_theme=='white'?'quirk':'dark')+_skin;
        }
        var _ul = $('<ul class="nav nav-pills nav-stacked nav-quirk"></ul>');
        _ul.addClass(_skin);
        
        _$dom.append(_ul);
        var _menus = _opt.menus;
        if(!webUtil.isEmpty(_menus)){
        	this.addMenuList(_ul,_menus);
        }
	},
	addMenuList:function(ul_el,_menus){//这是加第一级的方法
		var _$dom = ul_el;
		if(!webUtil.isEmpty(_menus)
				&&$.isArray(_menus)&&_menus.length>0){
			var thisMyMenu = this;
			for(var i=0;i<_menus.length;i++){
				var _menuItem = _menus[i];
				var _li = thisMyMenu.getLiMenum(_menuItem);
				thisMyMenu.addMenuItem(_li,_menuItem.child);
				_$dom.append(_li);
			}
		}
	},
	getLiMenum:function(_item){
		var _menuItem = {};
		$.extend(_menuItem,_def_pillmenu_item);
		$.extend(_menuItem,_item);
		var _li = $('<li>');
		if(_menuItem.active){
			_li.addClass('active');
		}
		var _li_a = $('<a href="#" class="_menuItems"></a>');
		var icon = _menuItem.icon;
		if(!webUtil.isEmpty(icon)){
			var iconDom = webUtil.createIconDom(icon);
			if(!webUtil.isEmpty(icon)){
				_li_a.append(iconDom);
			}
		}
		if(!webUtil.isEmpty(_menuItem.title)){
			var $title = $('<span>'+_menuItem.title+'</span>');
			if(!webUtil.isEmpty(_menuItem.icon)){
				$title.css({"margin-left":"2px"});
			}
			_li_a.append($title);
		}
		if(webUtil.isEmpty(_menuItem.id)){
			_menuItem.id = webUtil.getRandomWord(6);
		}
		_li_a.attr("id",_menuItem.id);
		_li.append(_li_a);
		_li_a.data('menuItem',_menuItem);
		return _li;
	},
	addMenuItem:function(_li,_child){//二级菜单
		if(!webUtil.isEmpty(_child)
				&&$.isArray(_child)&&_child.length>0){
			_li.addClass('nav-parent');
			var _li_ul_child = $('<ul>');
			_li_ul_child.addClass('children');
			var thisMyMenu = this;
			for(var i=0;i<_child.length;i++){
				var _li_ul_child_li = thisMyMenu.getLiMenum(_child[i]);
				_li_ul_child.append(_li_ul_child_li);
				if(_li_ul_child_li.hasClass('active')){
					_li.addClass('active');
				}
			}
			_li.append(_li_ul_child);
		}
	},
	addClickEvent:function(){
		var _$dom =  this.$element;
		_$dom.on('click','a._menuItems',{menu:this},function(){
			var thisA = $(this);
			var sub = thisA.next('ul.children');
			if(!webUtil.isEmpty(sub)&&sub.length>0){
				var parent = thisA.parent();
				if (sub.is(':visible')) {
					sub.slideUp(200);
					if (parent.hasClass('nav-active')) {
						parent.removeClass('nav-active');
					}
				}else{
					sub.slideDown(200);
					if (!parent.hasClass('active')) {
						parent.addClass('nav-active');
					}
				}
			}else{
				_$dom.find('.active').removeClass('active');
				var _parent = thisA.parent('li');
				if(!webUtil.isEmpty(_parent)&&_parent.length>0){
					_parent.addClass('active');
				}
				_parent = _parent.parent('ul').parent('li');
				if(!webUtil.isEmpty(_parent)&&_parent.length>0){
					_parent.addClass('active');
				}
			}
			var _menuItem = thisA.data('menuItem');
			if(!webUtil.isEmpty(_menuItem.url)){
				var $targetDom = $(_menuItem.target)
				if(!webUtil.isEmpty($targetDom)){
					if($targetDom.hasClass('my-nav-tabs')){//以tab为目标
						var _tabItem = {id:_menuItem.id+"_tab",title:_menuItem.title,icon:_menuItem.icon,url:_menuItem.url};
						$targetDom.myTab('addTab',_tabItem);
						//myNavTab.addTab($targetDom, _tabItem);
					}
				}
			}
		});
	}
}
$.fn.myPillTreeMenu = function(method,options) {
   var settings = $.extend({}, options);
   var myPillMenu = new MyPillTreeMenu($(this),settings);
   if(!webUtil.isEmpty(method)){
	   var _method = method.toLowerCase();
	   if(_method=='init'){
		   myPillMenu.init(settings);
	   }
   }
   return myPillMenu;
}
})(jQuery, window, document);

var def_tab = {theme:"nav-primary",items:[]};
var def_tab_item = {id:"",title:"主页",icon:"",enColse:true,url:"",content:"",active:true};
;(function($, window, document,undefined) {
	var MyTab = function(ele, opt){
		 this.$element = ele;
		 var defaults = {};
	     this.options = $.extend(true,{},defaults, opt);
	}
	MyTab.prototype = {
		init:function(_opt){
			var _$dom = this.$element;
			var _tabs = {};
			$.extend(true,_tabs,def_tab);
			if(!webUtil.isEmpty(_opt)){
				$.extend(true,_tabs,_opt);
			}
			this.tabsCheck(_tabs);
			var _items = _tabs.items;
			if(!webUtil.isEmpty(_items)&&$.isArray(_items)&&_items.length>0){
				for(var i=0;i<_items.length;i++){
					this.addTab(_items[i]);
				}
			}
		},
		tabsCheck:function(_opt){
			var _opts = {theme:"nav-primary"};
			if(!webUtil.isEmpty(_opt)){
				$.extend(_opts,_opt);
			}
			var _$this = this.$element;
			if(!webUtil.isEmpty(_$this)&&_$this.length>0){
				var _tab_ul = _$this.find('ul');
				if(webUtil.isEmpty(_tab_ul)||_tab_ul.length<=0){
					_tab_ul = $('<ul class="nav nav-tabs"></ul>');
					_$this.append(_tab_ul);
				}
				if(!_tab_ul.hasClass('nav')) _tab_ul.addClass('nav');
				if(!_tab_ul.hasClass('nav-tabs')) _tab_ul.addClass('nav-tabs');
				if(!webUtil.isEmpty(_opts.theme)){
					_tab_ul.addClass(_opts.theme);
				}
				var _tab_content = _$this.find('div');
				if(webUtil.isEmpty(_tab_content)||_tab_content.length<=0){
					_tab_content = $('<div>');
					_$this.append(_tab_content);
				}
				if(!_tab_content.hasClass('tab-content')) _tab_content.addClass('tab-content');
			}
		},
		addTab:function(_opt){
			var _$dom = this.$element;
			if(!webUtil.isEmpty(_$dom)){
				var _tabItem = {};
				$.extend(true,_tabItem,def_tab_item);
				$.extend(true,_tabItem,_opt);
				if(webUtil.isEmpty(_tabItem.id)){
					_tabItem.id = webUtil.getRandomWord(6);
				}
				var _tab_obj = this.getTabItemById(_tabItem.id );
				if(webUtil.isEmpty(_tab_obj)||_tab_obj.length<=0){//不存在此tab内容
					var _tab_li = this.initTabHeadItem(_tabItem); //initHead
					var _tab_conten_pane = this.initTabContentItem(_tabItem); //initTabContent
					this.tabsCheck(_tabItem);
					_$dom.find('ul.nav-tabs').append(_tab_li);
				    _$dom.find('div.tab-content').append(_tab_conten_pane);
					var _curTab = this.getTabItemById(_tabItem.id );
					if(_tabItem.active&&!webUtil.isEmpty(_curTab)){
						_tab_obj = _curTab;
					}
					var tabObj ={tab:this};
					_curTab.click(tabObj,function(e){
						e.data.tab.setTabSelected(this);
					});
				}
				this.setTabSelected(_tab_obj);
			}
		},
		removeTabItem:function(_itemId){
			if(!webUtil.isEmpty(_itemId)){
				var _$tabHead = this.getTabItemById(_itemId);
				var _$tab_ul = _$tabHead.parent('li').parent('ul');
				(_$tabHead.parent('li')).remove();
				this.$element.find('#'+_itemId).remove();
				this.setTabSelected(this.getTabItemByIndex(-1));
			}
		},
		getTabCount:function(){
			var _$tab_ul = this.$element.find('ul.nav-tabs');
			if(!webUtil.isEmpty(_$tab_ul)&&_$tab_ul.length>0){
				return _$tab_ul.find('li').length;
			}
			return -1;
		},
		getTabItemByIndex:function(index){
			//返回对应的 a 的item
			var _$tab_ul = this.$element.find('ul.nav-tabs'); //ul dom
			if(!webUtil.isEmpty(_$tab_ul)&&_$tab_ul.length>0){
				if(!webUtil.isEmpty(index)&&$.isNumeric(index)){
					if(index>0){
						index = index-1;
					}
					return _$tab_ul.find('li').eq(index).children('a');
				}
			}
			return null;
		},
		setTabSelected:function(_tabItem$){
			if(!webUtil.isEmpty(_tabItem$)&&_tabItem$.length>0){
				var _ul_li = _tabItem$.parent('li');
				if(!_ul_li.hasClass('active')){
					var _ul = _ul_li.parent('ul');
					_ul.parent().find('.active').removeClass('active');
					_ul_li.addClass('active');
					var tabItem = _tabItem$.data('tabItem');
					var _itemId = tabItem.id;
					$('#'+_itemId).addClass('in active');
					var _ifm_tabContent = $('#'+_itemId+"_ifm");
					if(!webUtil.isEmpty(_ifm_tabContent)&&_ifm_tabContent.length>0){
						var _curUrl = _ifm_tabContent.attr('src');
						var tabUrl = tabItem.url;
						var elId = _itemId+"_ifm";
						if(webUtil.isEmpty(_curUrl)&&!webUtil.isEmpty(tabUrl)){
							_ifm_tabContent.attr("src",webUtil.toUrl(tabUrl));
							_ifm_tabContent.load(function(){
								webUtil.setMainTabHeight(elId);
							});
						}else{
							webUtil.setMainTabHeight(elId);
						}
					}
				}
			}
		},
		getTabItemById:function(_itemId){
			if(!webUtil.isEmpty(_itemId)){
				return this.$element.find("#"+_itemId+"_head_a");
			}
			return null;
		},
		initTabHeadItem:function(_tabItem){
			var _tab_li = $('<li>');
			var _tab_li_a = $('<a href="#'+_tabItem.id+'" data-toggle="tab"></a>');
			_tab_li_a.attr("id",_tabItem.id+"_head_a");
			var _tabIconDom = webUtil.createIconDom(_tabItem.icon);
			if(!webUtil.isEmpty(_tabIconDom)){
				_tab_li_a.append(_tabIconDom);
			}
			var _tab_li_a_text = $('<strong>'+_tabItem.title+'</strong>');
			if(!webUtil.isEmpty(_tabIconDom)){
				_tab_li_a_text.css({"margin-left":"2px"});
			}
			
			if(_tabItem.enColse){
				var thisTab = this;
				var _tab_colse$ = $($.parseHTML('<a style="cursor: pointer;" id="">&nbsp;&nbsp;<i class="fa fa-remove" style="font-size: 12px;"></i></a>'));
				_tab_li_a_text.append(_tab_colse$)
				_tab_colse$.click(_tabItem,function(e){
					var tbItem = e.data;
					thisTab.removeTabItem(tbItem.id);
					if(tbItem.colseCallBack&&$.isFunction(tbItem.colseCallBack)){
						tbItem.colseCallBack(tbItem);
					}
				});
			}
			_tab_li_a.append(_tab_li_a_text);
			_tab_li_a.data('tabItem',_tabItem);
			_tab_li.append(_tab_li_a);
			return _tab_li;
		},
		initTabContentItem:function(_tabItem){
			var _tab_conten_pane = $('<div class="tab-pane fade" style="padding:0px;" id="'+_tabItem.id+'"></div>');
			var _url = _tabItem.url;
			var _content = _tabItem.content;
			if(!webUtil.isEmpty(_url)){
				_content = '<iframe src="" id="'+_tabItem.id+'_ifm" width="100%" scrolling="no" marginheight="0" marginwidth="0" frameborder="0"></iframe>';
			}
			_tab_conten_pane.append(_content);
			return _tab_conten_pane;
		}
	}
	$.fn.myTab = function(method,options) {
	    var settings = $.extend({}, options);
	    var myTab = new MyTab($(this),settings);
	    if(!webUtil.isEmpty(method)){
	 	   var _method = method.toLowerCase();
	 	   if(_method=='init'){
	 		  myTab.init(settings);
	 	   }else if(_method=='addtab'){
	 		  myTab.addTab(settings);
	 	   }
	    }
	    return myTab;
	}
})(jQuery, window, document);

/**
 * btnGroup 插件方法 
 * 其他方法待补充
 */
;(function($, window, document,undefined) {
var MyBtnGroups = function(ele, opt){
	 this.$element = ele;
	 this.defaults = {};
     this.options = $.extend(true,{}, this.defaults, opt);
}
MyBtnGroups.prototype = {
	addBtn:function(opt,index){
		var _idx = -1;
		if($.isNumeric(index)){
			_idx = index;
		}
		var defaults = {theme: 'btn-success',css:'', icon: '',text:'按钮',clickFun:undefined};
		var _opt = $.extend({}, defaults, opt);
		var _$btn = $('<button class="btn" type="button">&nbsp;'+_opt.text+'</button>');
		if(!webUtil.isEmpty(_opt.theme)){
			_$btn.addClass(_opt.theme);
		}
		if(!webUtil.isEmpty(_opt.css)){
			_$btn.addClass(_opt.css);
		}
		
		if(!webUtil.isEmpty(_opt.icon)){
			_$btn.prepend($('<span>').addClass(_opt.icon));
		}
		if(_idx<0){
			 this.$element.append(_$btn);
		}else{
			var $btnObj = this.$element.find('button.btn').eq(_idx);
			if(!webUtil.isEmpty($btnObj)&&$btnObj.length>0){
				$btnObj.before(_$btn);
			}else{
				this.$element.append(_$btn);
			}
		}
		if(!webUtil.isEmpty(_opt.clickFun)
				&&$.isFunction(_opt.clickFun)){
			_$btn.click(_opt,function(e){
				_opt.clickFun(e.data);
			});
		}
	},
	addSearch:function(_opt){
		var _def_Search = {theme:"btn-success",css:{"width":"360px","margin-left":"10px"},items:undefined,dataChange:undefined};
		var _def_searchItem = {key:undefined,text:undefined};
		var _search = $.extend({}, _def_Search, _opt);
		var _input_group = $('<div class="input-group"></div>');
		_input_group.css(_search.css);
		var _input = $('<input class="form-control" type="text" placeholder="请选择对应项">');
		if(!webUtil.isEmpty(_search.items)
				&&$.isArray(_search.items)&& _search.items.length>0){
			var _group_btn = $('<div class="input-group-btn"></div>');
			var _btn = $('<button class="btn dropdown-toggle" aria-expanded="false" style="width:100px;" type="button" data-toggle="dropdown">--选择-- &nbsp; <span class="caret"></span></button>');
			_btn.addClass(_search.theme);
			_group_btn.append(_btn);
			var _items = _search.items;
			var _ul = $('<ul class="dropdown-menu"></div');
			for(var i=0;i<_items.length;i++){
				var _item = $.extend({}, _def_searchItem, _items[i]); 
				var _li = $('<li><a href="#">'+_item.text+'</a></li>')
				_li.data('item',_item);
				_ul.append(_li);
			}
			_group_btn.append(_ul);
			_input_group.append(_group_btn);
			
			_ul.find('li').each(function(){
				var _this_li = $(this);
				_this_li.click(function(e){
					var _thisItem = $(this).data('item');
					_btn.html(_thisItem.text+'&nbsp; <span class="caret"></span>');
					
					_input.data('item',_thisItem);
				});
			});
		}else{
			_input.data('item',undefined);
		}
		
		_input_group.append(_input);
		
		var _search_span = $('<span class="input-group-btn"></div>');
		var _search_btn = $('<button class="btn" type="button"><i class="fa fa-search"></i></button>');
		_search_btn.addClass(_search.theme);
		_search_span.append(_search_btn);
		_input_group.append(_search_span);
		this.$element.append(_input_group);
		
		if(!webUtil.isEmpty(_search.dataChange)&&$.isFunction(_search.dataChange)){
			_input.keydown(_search,function(e){
				if(e.which == "13"){//回车事件
					_search_btn.trigger('click',e.data);
				}  
			});
			_search_btn.click(_search,function(e){
				var _thisItem = _input.data('item');
				if(!webUtil.isEmpty(_thisItem)){
					var _thisData = $.extend({}, _def_searchItem,_input.data('item'));
					_thisData.value = _input.val();
					_search.dataChange(e.data,_thisData);
				}else{
					webUtil.mesg('请先选择相关的项，搜索!');
				}
			});
		}
	}
}
$.fn.myBtnGroup = function(options) {
	if(!$(this).hasClass('btn-group')) $(this).addClass('btn-group');
    var settings = $.extend({}, options);
    return new MyBtnGroups($(this),settings);
}
})(jQuery, window, document);

/**
 * Table 插件方法 
 * 其他方法待补充
 */
;(function($, window, document,undefined) {
var MyDataTable = function(ele, opt){
	 this.$element = ele;
	 this.selectModel = 1;//1 单选  2 多选
	 if(!webUtil.isEmpty(opt.selectModel)){
		 this.selectModel = opt.selectModel;
	 }
	 var thisTable = this;
	 var _opt_onClickRow = opt.onClickRow;
	 this.curSelRowIdx = -1;
	 clickRow = function(row, $el, field){
		var thisCurRowIdx = $el.data('index');
		var selectChanged = false;
		var selectModel = thisTable.getSelectModel();
		if(selectModel===1){
			var selRow = $el.siblings('tr.selected').eq(0);
			if(webUtil.isEmpty(selRow)){
				selectChanged = true;
			}else{
				selectChanged = thisCurRowIdx!= selRow.data('index');
			}
			$el.siblings('tr.selected').removeClass('selected');
			$el.addClass('selected');
		}else if(selectModel===2){
			selectChanged = true;
			if($el.hasClass('selected')){
				$el.removeClass('selected');
			}else{
				$el.addClass('selected');
				$el.data('rowData',row);
			}
		}
		$el.data('rowData',row);
		if(!webUtil.isEmpty(_opt_onClickRow)){
			_opt_onClickRow(row, $el, field);
		}
		if(selectChanged){
			if(!webUtil.isEmpty(opt.selectChanaged)&&$.isFunction(opt.selectChanaged)){
				opt.selectChanaged(thisCurRowIdx,row,$el);
			}
		}
	 }
	 opt.onClickRow = undefined;
	 this.mypagination = undefined;
	 this.$pagination = undefined;
	 this.myQueryParams = undefined;
	 if(!webUtil.isEmpty(opt.mypagination)&&opt.mypagination){
		 var _defMypagination = {url:'',pageSize:20,pageList:[10,20,50],curPage:1,totalPages:1,queryParams:undefined};
		 this.myQueryParams = opt.queryParams;
		 this.mypagination = $.extend(true,{}, _defMypagination, {url:opt.url,pageList:opt.pageList,pageSize:opt.pageSize});
		 opt.url = undefined;
	 }
	 var defaults_bt = {height:600,onClickRow:clickRow,mypagination:true};//这个是客户端的过滤
     this.options = $.extend(true,{}, defaults_bt, opt);
     this.tblMain =  this.$element.bootstrapTable(this.options);
     if(!webUtil.isEmpty(this.mypagination)){
    	 this.addMyPagination(this.mypagination);
     }
     this.editDataChanged = undefined;
}
MyDataTable.prototype = {
	addMyPagination:function(_opt){
		var _tblBody = this.$element.parent('div.fixed-table-body');
		this.$pagination = _tblBody.parent().find('div.fixed-table-pagination');
		if(webUtil.isEmpty(this.$pagination)||this.$pagination.length<=0){
			this.$pagination = $('<div class="fixed-table-pagination" style="display: block;"></div>');
			_tblBody.parent().append(this.$pagination);
		}else{
			this.$pagination.html('');
			this.$pagination.show();
		}
		var _pagination_detail = [];
		_pagination_detail.push('<div class="pull-left pagination-detail" style="margin-top:0px;margin-bottom:0px;">');
			_pagination_detail.push('<span id="tbl_pageinfo" class="pagination-info">显示第 1 到第 0 条记录，总共 0 条记录</span>');
			_pagination_detail.push('<span class="page-list">每页显示');
				_pagination_detail.push('<span class="btn-group dropup form-group" style="padding-top: 15px;">');
					_pagination_detail.push('<select class="form-control _changePage" style="width: 50px;height:26px;padding :2px 5px;" data-placeholder="页码">');
		var _pageList = _opt.pageList||[10,20,50];
					for(var i=0;i<_pageList.length;i++){
						var thisPageSize = _pageList[i];
						_pagination_detail.push('<option value="'+_pageList[i]+'"');
						if(thisPageSize==_opt.pageSize){
							_pagination_detail.push(' selected="selected"');
						}
						_pagination_detail.push('">'+_pageList[i]+'</option>');
					}
					_pagination_detail.push('</select>');
				_pagination_detail.push('</span> 条记录');
			_pagination_detail.push('</span>');
		_pagination_detail.push('</div>');
		_pagination_detail.push('<div class="pull-right pagination">');
			_pagination_detail.push('<ul class="pagination" style="border:1px solid #2596ab;">');
				_pagination_detail.push('<li class="page-pre firstpage"><a href="#">首页</a></li>');
				_pagination_detail.push('<li class="page-number prepage"><a href="#">上一页</a></li>');
				_pagination_detail.push('<li class="page-number">');
					_pagination_detail.push('<div style="width:40px;float:left;padding:0px;">');
						_pagination_detail.push('<input style="height:32px;border: 0px;" type="text" class="form-control _tocurPage" value="1"/>');
					_pagination_detail.push('</div>');
				_pagination_detail.push('</li>');
				_pagination_detail.push('<li class="page-number nextpage"><a href="#">下一页</a></li>');
				_pagination_detail.push('<li class="page-next lastpage"><a href="#">末页</a></li>');
			_pagination_detail.push('</ul>');
		_pagination_detail.push('</div>');
		
		this.$pagination.html(_pagination_detail.join(''));
		
		//注册相关事件 进行查询 搜索;
		this.initPaginationEvent();
	},
	initPaginationEvent:function(){
		
		this.$pagination.find('select._changePage').change(this,function(e){
			var tableObj = e.data;
			tableObj.mypagination.pageSize = $(this).val();
			tableObj.refreshData();
		});
		this.$pagination.find('li.firstpage>a').click(this,function(e){
			var tableObj = e.data;
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				$li.parent().find('li.active').removeClass('active');
				$li.addClass('active');
				tableObj.mypagination.curPage = 1;
				tableObj.refreshData();
			}
		});
		this.$pagination.find('li.prepage>a').click(this,function(e){
			var tableObj = e.data;
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				$li.parent().find('li.active').removeClass('active');
				$li.addClass('active');
				var _curPage = tableObj.mypagination.curPage-1;
				if(_curPage<1) _curPage = 1;
				tableObj.mypagination.curPage =_curPage;
				tableObj.refreshData();
			}
		});
		this.$pagination.find('input._tocurPage').keydown(this,function(e){
			if(e.which == "13"){//回车事件
				var tableObj = e.data;
				var _curPage = $(this).val();
				if($.isNumeric(_curPage)){
					var _total_page = tableObj.mypagination.totalPages;
					if(_curPage<1){
						_curPage = 1;
					}else if(_curPage>_total_page){
						_curPage = _total_page;
					}
					tableObj.mypagination.curPage =_curPage;
					$(this).val(_curPage);
					tableObj.refreshData();
				}else{
					webUtil.mesg('请输入正确的数字页码!');
				}
			}
		});
		this.$pagination.find('li.nextpage>a').click(this,function(e){
			var tableObj = e.data;
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				$li.parent().find('li.active').removeClass('active');
				$li.addClass('active');
				var _curPage = tableObj.mypagination.curPage+1;
				if(_curPage>tableObj.mypagination.totalPages) _curPage = tableObj.mypagination.totalPages;
				tableObj.mypagination.curPage =_curPage;
				tableObj.refreshData();
			}
		});
		this.$pagination.find('li.lastpage>a').click(this,function(e){
			var tableObj = e.data;
			var $li = $(this).parent('li');
			if(!$li.hasClass('disabled')){
				$li.parent().find('li.active').removeClass('active');
				$li.addClass('active');
				tableObj.mypagination.curPage =tableObj.mypagination.totalPages;
				tableObj.refreshData();
			}
		});
	},
	refreshData:function(url){
		var _otherQueryParams = '';
		if(!webUtil.isEmpty(this.myQueryParams)){
			if($.isFunction(this.myQueryParams)){
				_otherQueryParams = this.myQueryParams();
			}else{
				_otherQueryParams = this.myQueryParams;
			}
		}
		var _data = {};
		var _url = "";
		if(url){
		    _url = url;
		}else{
		    _data.curPage = this.mypagination.curPage;
            _data.pageSize = this.mypagination.pageSize;
            _url = this.mypagination.url;
		}
		_data.search = _otherQueryParams;
		var _myTableMain = this;
		webUtil.ajaxData({url:_url,data:_data,success:function(data){
			var _ret_Data = data.data;
			if(!webUtil.isEmpty(_ret_Data)){
				if(!webUtil.isEmpty(_myTableMain.$pagination)){
					_myTableMain.$pagination.find('li.disabled').removeClass('disabled');
					if(_ret_Data.currentPage<=1){
						_myTableMain.$pagination.find('li.firstpage').addClass('disabled');
						_myTableMain.$pagination.find('li.prepage').addClass('disabled');
					}else{
						if(_ret_Data.currentPage>=_ret_Data.totalPages){
							_myTableMain.$pagination.find('li.nextpage').addClass('disabled');
							_myTableMain.$pagination.find('li.lastpage').addClass('disabled');
						}
					}
					if(_ret_Data.totalPages<=1){
						_myTableMain.$pagination.find('li.firstpage').addClass('disabled');
						_myTableMain.$pagination.find('li.prepage').addClass('disabled');
						_myTableMain.$pagination.find('li.lastpage').addClass('disabled');
						_myTableMain.$pagination.find('li.nextpage').addClass('disabled');
					}
					_myTableMain.mypagination.curPage = _ret_Data.currentPage;
					_myTableMain.mypagination.pageSize = _ret_Data.pageSize;
					_myTableMain.mypagination.totalPages = _ret_Data.totalPages;
					_myTableMain.$pagination.find('input._tocurPage').val(_ret_Data.currentPage);
					_myTableMain.$pagination.find('span.pagination-info').html("显示第 "+(_ret_Data.startNum+1)+" 到第 "+(_ret_Data.startNum+_ret_Data.pageSize)+" 条记录，总共  "+_ret_Data.totalRows+" 条记录")
				}
				var _dataRows = _ret_Data.datas;
				if(!webUtil.isEmpty(_dataRows)){
					_myTableMain.loadData(_dataRows);
				}
			}
		}});
	},
	addRow:function(rowData){
		this.insertRow(this.getRowCount(),rowData);
	},
	insertRow:function(index,rowData){
		var _idx = -1;
		if($.isNumeric(index)){
			_idx = index;
		}
		this.checkTable();
		this.tblMain.bootstrapTable('insertRow',{index:_idx,row:rowData});
		this.tblMain.bootstrapTable('resetView');
		
	},
	removeRow:function(row){
		var index = -1;
		if($.isNumeric(row)){
			index = row;
		}else{
			var rowDom = webUtil.getJqueryDom(row);
			if(!webUtil.isEmpty(rowDom)){
				index = rowDom.attr('data-index');
			}
		}
		if($.isNumeric(index)&&index>=0&&index<this.getRowCount()){
			var curTableData = this.tblMain.bootstrapTable('getData', false);
			curTableData.splice(index, 1);
			this.loadData(curTableData);
		}else{
			alert('无法找到对应的数据行!');
		}
	},
	getSelectRow:function(){
		var selRows = this.getSelectRows();
		if(!webUtil.isEmpty(selRows)&&selRows.length>0){
			return selRows.eq(0);
		}
		return null;
	},
	getSelectRowIndex:function(){
		var row = this.getSelectRow();
		if(!webUtil.isEmpty(row)){
			return row.data('index');
		}
		return -1;
	},
	getSelectRows:function(){
		return this.$element.find('tbody>tr.selected');
	},
	getRowCount:function(){
		return this.$element.find('tbody>tr').length;
	},
	removeAllRow:function(){
		 this.tblMain.bootstrapTable('removeAll'); 
	},
	setSelectRow:function($el){
		$el.find('td').eq(0).trigger('click');
	},
	getRow:function(index){
		var _idx = -1;
		if($.isNumeric(index)){
			_idx = index;
		}
		if(_idx>=0){
			return this.$element.find('tbody>tr').eq(_idx);
		}
		return null;
	},
	getSelections:function(){
		var seledRow = [];
		this.$element.find('tbody>tr.selected').each(function(){
			seledRow.push($(this).data('rowData'));
		});
		return seledRow;
		
	},
	
	getData:function(){
		return this.tblMain.bootstrapTable('getData'); 
	},
	getSubmitData:function(otherCol){
		var datas = [];
		var cols = this.getColumns();
		var tableDatas = this.getData();
		this.checkTable();
		if(!webUtil.isEmpty(cols)&&cols.length>0
				&&!webUtil.isEmpty(tableDatas)&&tableDatas.length>0){
			if(!webUtil.isEmpty(otherCol)&&otherCol.length>0&&$.isArray(otherCol)){
				for(var i=0;i<otherCol.length;i++){
					cols.push(otherCol[i]);
				}
			}
			for(var i=0;i<tableDatas.length;i++){
				var rowObj = {};
				var rowData = tableDatas[i];
				for(var j=0;j<cols.length;j++){
					var column = cols[j];
					var field = column.field;
					if(field=='_seq') continue;
					var val = '' ;
					var _type = column.type;
					if(_type == DataType.F7){
						var f7Obj = rowData[field];
						if(!webUtil.isEmpty(f7Obj)){
							if($.isArray(f7Obj)){
								for(var k=0;k<f7Obj.length;k++){
                 					var thisItem = f7Obj[k];
                 					if(k>0) val +=',';
                 					val += thisItem.id;
                 				}
							}else if($.isPlainObject(f7Obj)){
								if(!webUtil.isEmpty(f7Obj.id)){
									val = f7Obj.id;
								}
							}
						}else{
							val = '';
						}
					}else if(_type == DataType.select){
                        var selectObj = rowData[field];
                        if(selectObj){
                            val = selectObj.key;
						}
					}else{
						val = rowData[field];
						if(!webUtil.isEmpty(val)&&$.isNumeric(val)
								&&(_type == DataType.date ||_type == DataType.datetime)){
							var thisDate = new Date();
                     		thisDate.setTime(val);
                     		var _thisFormtStr = _type==DataType.date?"yyyy-MM-dd":"yyyy-MM-dd h:m:s";
                     		val = thisDate.format(_thisFormtStr);
						}
					}
					if(webUtil.isEmpty(val)) val = '';
					rowObj[field] = val;
				}
				datas.push(rowObj);
			}
		}
		return datas;
	},
	loadData:function(data){
		this.tblMain.bootstrapTable('load', data);
	},
	getVisibleColumns:function(){
		return this.tblMain.bootstrapTable('getVisibleColumns');
	},
	getHiddenColumns:function(){
		return this.tblMain.bootstrapTable('getHiddenColumns');
	},
	getColumns:function(){
		var col1 = this.getVisibleColumns()||[];
		var col2 = this.getHiddenColumns()||[];
		return col1.concat( col2 );
	},
	getColumn:function(field){
		if(webUtil.isEmpty(field)) return null;
		var cols = this.getColumns();
		if(!webUtil.isEmpty(cols)&&cols.length>0){
			if($.isNumeric(field)){
				if(field>=0&&field<cols.length){
					return cols[field];
				}
			}else if(typeof field === 'string' ){
				var thisCol = null;
				$(cols).each(function(i,col){
					if(col.field==field){
						thisCol = col;
						return false;
					}
				});
				return thisCol;
			}
		}
		return null;
		
	},
	addColumn:function(index,cols){
		var _idx = -1;
		if($.isNumeric(index)){
			_idx = index;
		}
		var _cols = [];
		var _oldCols = this.getVisibleColumns();
		var hasAdd = false;
		for(var i=0;i<_oldCols.length;i++){
			if(i==_idx){
				_cols.push(cols);
				hasAdd = true;
			}
			_cols.push(_oldCols[i]);
		}
		if(!hasAdd){
			_cols.push(cols);
		}
		this.tblMain.bootstrapTable('refreshOptions',{columns:_cols});
	},
	getTblMain:function(){
		return this.tblMain;
	},
	addTableEvent:function(eventName,fun){
		this.$element.on(eventName+'.bs.table',fun);
	},
	resetView:function(){
		this.getTblMain().bootstrapTable('resetView');
	},
	updateRow:function(rowIdx,row){
		if($.isNumeric(rowIdx)){
			var rowData = {};
			if(!webUtil.isEmpty(row)&&$.isPlainObject(row)){
				rowData = row;
			}
			this.getTblMain().bootstrapTable('updateRow',{index:rowIdx,row:rowData});
		}
	},
	getSelectModel:function(){
		return this.selectModel;
	},
	isEnabled:function(){
		var enable = this.$element.data('enabled');
		if(!webUtil.isEmpty(enable)&&typeof(enable) == 'boolean'){
			return enable;
		}
		return true;
	},
	setEnabled:function(enabled){
		var enable = true;
		if(!webUtil.isEmpty(enabled)&&typeof(enabled) == 'boolean'){
			enable = enabled;
		}
		this.$element.data('enabled',enable);
	},
	getRowData:function(rowIdx){
		var tableDatas = this.getData();
		if(!webUtil.isEmpty(tableDatas)&&$.isArray(tableDatas)){
			if(rowIdx>=0&&rowIdx<tableDatas.length)
				return tableDatas[rowIdx];
		}
		return null;
	},
	getCell:function(rowIdx,colIdx){
		var row = this.getRow(rowIdx);
		if(!webUtil.isEmpty(row)){
			return row.find('td').eq(colIdx);
		}
		return null;
	},
	getTableCellValue:function(rowIdx,field){
		var rowData = this.getRowData(rowIdx);
		if(!webUtil.isEmpty(rowData)){
			return rowData[field];
		}
		return null;
	},
	setTableCellValue:function(rowIdx,field,val){
		if(webUtil.isEmpty(field)) return;
		var rowData = this.getRowData(rowIdx);
		if(webUtil.isEmpty(rowData)){
			rowData = {};
		}
		var oldVal = rowData[field];
		var newVal = val;
		var thisColumn = null;
		var colIdx = -1;
		var _cols = this.getColumns();
		$.each(_cols, function (i, column) {
            if (column.field === field) {
            	thisColumn = column;
            	colIdx = i;
                return false;
            }
            return true;
        });
		
		var cell = this.getCell(rowIdx,colIdx);
		if(!webUtil.isEmpty(thisColumn)&&!webUtil.isEmpty(cell)){
			if(!webUtil.isEmpty(val)){
				if(thisColumn.type == DataType.number){
					if(!$.isNumeric(val)){
						webUtil.mesg("请输入有效的数字!");
						if(!webUtil.isEmpty(oldVal)){
							newVal = oldVal;
						}else{
							newVal = null;
						}
					}
				}
			}
			
			rowData[field] = newVal;
			this.updateRow(rowIdx,rowData);
			
			var _cellHtml = newVal;
			if(!webUtil.isEmpty(thisColumn.formatter)){
				if($.isFunction(thisColumn.formatter)){
					_cellHtml = thisColumn.formatter(newVal,rowData,colIdx);
				}else{
					_cellHtml = thisColumn.formatter;
				}
			}
			cell.html(_cellHtml);
			var hasChanged = oldVal!=newVal;
			var col = this.getColumn(field);
			if('f7'==col.type){
				if(!webUtil.isEmpty(oldVal)&&!webUtil.isEmpty(newVal)){
					if($.isArray(oldVal)&&$.isArray(newVal)){
						if(oldVal.length!=newVal.length) {
							hasChanged = true;
						}else{
							for(var i=0;i<newVal.length;i++){
								if(newVal[i].id!=oldVal[i].id){
									hasChanged = true;
									return true;
								}
							}
						}
					}else if($.isPlainObject(oldVal)&&$.isPlainObject(newVal)){
						hasChanged = newVal.id!=oldVal.id;
					}
				}
			}
			if(hasChanged){
				if(!webUtil.isEmpty(this.editDataChanged)&&$.isFunction(this.editDataChanged)){
					var changeObj= {};
					changeObj.oldVal = oldVal;
					changeObj.value = newVal;
					changeObj.rowData = rowData;
					changeObj.field = field;
					changeObj.column = thisColumn;
					changeObj.rowIndex = rowIdx;
					changeObj.colIndex = colIdx;
					this.editDataChanged(cell,changeObj);
				}
			}
			this.resetView();
		}
	},
	checkTable:function(){
		var thisEditorColumn = this.$element.data('curEditorColumn');
		var tblOpt = this.$element.data('editorOpt');
		if(!webUtil.isEmpty(thisEditorColumn)&&!webUtil.isEmpty(tblOpt)){
			var ridx = thisEditorColumn.rowIndex;
			var colIdx = thisEditorColumn.colIndex;
			var curEditor = thisEditorColumn.editor;
			if(!webUtil.isEmpty(curEditor)){
				var thisCellVal = curEditor.getTableCellData();
				var go = true;
				var endObj= $.extend(true,{},thisEditorColumn, {value:thisCellVal});
				if(!webUtil.isEmpty(tblOpt.endEdit)&&$.isFunction(tblOpt.endEdit)){
					go = tblOpt.endEdit($td,endObj);
				}
				if(go){
					this.setTableCellValue(ridx,thisEditorColumn.field,thisCellVal);
				}
				thisEditorColumn.cell.data('hasInit','no');
			}
			this.$element.data('curEditorColumn',null);
		}
	},
	initTableColumnEditor:function(medthed,opt){
		//dbl-click-cell 双击事件名
		if(webUtil.isEmpty(medthed)) medthed = 'click-cell';//单击开始编辑
		var tblOpt = $.extend(true,{}, {statrtEdit:undefined,endEdit:undefined,editDataChanged:undefined}, opt);
		if(!webUtil.isEmpty(tblOpt.editDataChanged)&&$.isFunction(tblOpt.editDataChanged)){
			this.editDataChanged = tblOpt.editDataChanged;
		}
		this.$element.data('editorOpt',tblOpt);
		var thisMyTable = this;
		this.addTableEvent(medthed,function(e,$td,obj){
			var toEdit = true;
			 /* var obj = {};
	            obj.field = field;
	            obj.value = value; 
	            obj.row = item;
	            obj.column = column;
	            obj.rowIndex = $tr.data('index');
	            obj.colIndex = index;*/
			if('_seq'==obj.field){
				toEdit = false;
			}
			if(toEdit){
				toEdit = thisMyTable.isEnabled();
			}
			var thisColumn  = obj.column;
			if(toEdit){
				var locked = false;
				if(!webUtil.isEmpty(thisColumn.locked)&&typeof(thisColumn.locked) == 'boolean'){
					locked = thisColumn.locked;
				}
				if(locked){
					var tdLocked = $td.data("locked");
					if(!webUtil.isEmpty(tdLocked)&&typeof(tdLocked) == 'boolean'){
						locked = tdLocked;
					}
				}
				toEdit = !locked;
			}
			if(toEdit){
				var thisEditorColumn = thisMyTable.$element.data('curEditorColumn');
				if(!webUtil.isEmpty(thisEditorColumn)){
					var ridx = thisEditorColumn.rowIndex;
					var colIdx = thisEditorColumn.colIndex;
					if(ridx===obj.rowIndex&&colIdx===obj.colIndex){
						toEdit = false;
					}else{
						//bs-table 肯爹的方式啊  更新行居然把整个表格重新生成一次
						var curEditor = thisEditorColumn.editor;
						if(!webUtil.isEmpty(curEditor)){
							var thisCellVal = curEditor.getTableCellData();
							var go = true;
							var endObj= $.extend(true,{},obj, {value:thisCellVal});
							if(!webUtil.isEmpty(tblOpt.endEdit)&&$.isFunction(tblOpt.endEdit)){
								go = tblOpt.endEdit($td,endObj);
							}
							if(go){
								thisMyTable.setTableCellValue(ridx,thisEditorColumn.field,thisCellVal);
							}
							thisMyTable.$element.data('curEditorColumn',null);
							thisEditorColumn.cell.data('hasInit','no');
						}
					}
				}
			}
			if(toEdit&&!webUtil.isEmpty(tblOpt.statrtEdit)&&$.isFunction(tblOpt.statrtEdit)){
				toEdit = tblOpt.statrtEdit($td,obj);
			}
			if(toEdit){
				var thisClickObj = obj;
				var colEditor = $td.myDataTableCellEditor(thisColumn.type);
				var _editorOpt = {};
				_editorOpt.endEdit = function(val){
					var go = true;
					obj.value = val;
					if(!webUtil.isEmpty(tblOpt.endEdit)&&$.isFunction(tblOpt.endEdit)){
						go = tblOpt.endEdit($td,obj);
					}
					if(go){
						var ridx =  thisClickObj.rowIndex;
						thisMyTable.setTableCellValue(ridx,thisClickObj.field,val);
					}
					thisMyTable.$element.data('curEditorColumn',null);
					$td.data('hasInit','no');
				};
				_editorOpt.initData = thisMyTable.getTableCellValue(obj.rowIndex,obj.field);
				var editOpt = $.extend(true,{}, _editorOpt)
				var thisEditor = thisColumn.editor;
				if(!webUtil.isEmpty(thisEditor)){
					if(typeof thisEditor === 'string'){
						thisEditor = webUtil.str2Json(thisEditor);
					}
					if($.isPlainObject(thisEditor)){
						editOpt = $.extend(true,editOpt, thisEditor)
					}
				}
				colEditor.initEditor(editOpt);
				thisMyTable.resetView();
				thisClickObj.editor = colEditor;
				thisClickObj.cell = $td;
				thisMyTable.$element.data('curEditorColumn', $.extend(true,{},thisClickObj));
			}
			return true;
		});
	}
}
$.fn.myDataTable = function(options) {
	 var defaults = {height:600};
     var settings = $.extend(true,defaults, options);
     return new MyDataTable($(this),settings);
}
})(jQuery, window, document);

;(function($, window, document, undefined) {
var MyDataTableCellEditor = function(ele) {
	this.$element = ele;
}
MyDataTableCellEditor.prototype = {
	initEditor:function(opt){
		var hasInit = this.$element.data('hasInit');
		if(!webUtil.isEmpty(hasInit)&&hasInit=='yes'){//已经初始化了的
			return ;
		}else{
			this.$element.css({"padding":"0px"});
			var width = this.$element.innerWidth();
			var height = this.$element.parent().innerHeight()-2;
			this.$element.html('');
			var _$editor = $('<div class="input-group"></div>');
			var type = this.$element.data('dataType');
			var cell_css = {"margin":"0px","width":width+"px","height":height+"px","padding":"0px"};
			if(!webUtil.isEmpty(type)){
				var cell_componet = null;
				if(DataType.text==type){
					cell_componet = $('<input type="text" class="form-control" />');
					cell_componet.css(cell_css);
				}else if(DataType.F7==type){
					cell_componet = $('<input style="margin:0px;width:100%;height:100%;padding:0px;" class="form-control"/>');
				}else if(DataType.date==type||DataType.datetime==type){
					cell_componet = $('<input style="margin:0px;height:100%;padding:0px;" readonly="readonly" class="form-control" />');
				}else if(DataType.select == type){
					cell_componet = $('<select class="form-control"></select>');
				}else if(DataType.textarea == type){
					cell_componet = $('<textarea class="input-item form-control"></textarea>');
				}else{
					//其他未知都初始化成text
					cell_componet = $('<input type="text" class="form-control"/>');
					cell_componet.css(cell_css);
				}
				if(!webUtil.isEmpty(cell_componet)){
					_$editor.append(cell_componet);
					_$editor.data('componet',cell_componet);
				}
			}
			
			this.$element.append(_$editor);
			var thisMyComponet = _$editor.data('componet');
			if(!webUtil.isEmpty(thisMyComponet)){
				_$editor.css(cell_css);
				if(DataType.F7==type
						||DataType.date==type||DataType.datetime==type){
					thisMyComponet.css({"margin":"0px","padding":"0px",'height':(height+10)+"px"}); 
					if(DataType.F7==type){
						opt.closeWin = function(isOk,f7Data){
							var thisData = f7Data;
							if(!isOk){
								thisData = thisMyComponet.myF7().getData();
							}
							if(!webUtil.isEmpty(opt.endEdit)&&$.isFunction(opt.endEdit)){
								opt.endEdit(thisData);
							}
						 }
					}else if(DataType.date==type||DataType.datetime==type){
						
					}
				}else{
					thisMyComponet.css(cell_css);
				}
				
				this.$element.data('editor',_$editor);
				this.$element.width(width);
				this.$element.height(height-2);
				this.$element.data('hasInit','yes');
				//1:控件初始化
				thisMyComponet.myComponet(type,{method:'init',opt:opt});
				//2：初始化事件
				this.initEditorEvent(opt.endEdit);
				//3:初始化值
				thisMyComponet.myComponet(type,{method:'setData',opt:opt.initData});
			}
		}
	},
	getTableCellData:function(){
		var type = this.$element.data('dataType');
		var _editor = this.$element.data('editor');
		var val = null;
		if(!webUtil.isEmpty(_editor)&&!webUtil.isEmpty(type)){
			var thisMyComponet =_editor.data('componet');
			if(!webUtil.isEmpty(thisMyComponet)){
				if(DataType.F7==type){
					val = thisMyComponet.myF7().getData();
				}else{
					val = thisMyComponet.myComponet(type,{method:'getdata'});
				}
			}
		}
		return val;
	},
	initEditorEvent:function(endEdit){
		var type = this.$element.data('dataType');
		var _editor = this.$element.data('editor');
		if(!webUtil.isEmpty(_editor)&&!webUtil.isEmpty(type)){
			var thisMyComponet = _editor.data('componet');
			if(DataType.F7==type){
				_editor.blur(function(e) {
					e.stopPropagation();
					if(!webUtil.isEmpty(endEdit)&&$.isFunction(endEdit)){
						endEdit(thisMyComponet.myF7().getData());
					}
				});
			}else if(DataType.date==type||DataType.datetime==type){
				thisMyComponet.parent('.date').on('changeDate',function(e){
					if(!webUtil.isEmpty(endEdit)&&$.isFunction(endEdit)){
						endEdit(thisMyComponet.myComponet(type,{method:'getData'}));
					}
				});
			}else if(DataType.select == type){
				_editor.on('select2:select', function (evt) {
					if(!webUtil.isEmpty(endEdit)&&$.isFunction(endEdit)){
						endEdit(thisMyComponet.myComponet(type,{method:'getData'}));
					}
				});
			}else{
				_editor.focus();
				if(!webUtil.isEmpty(thisMyComponet)){
					_editor.blur(function(e) {
						if(!webUtil.isEmpty(endEdit)&&$.isFunction(endEdit)){
							endEdit(thisMyComponet.myComponet(type,{method:'getdata'}));
						}
					});
				
				/*	_editor.off('focusout').on('focusout',function(e) {
						if(!webUtil.isEmpty(endEdit)&&$.isFunction(endEdit)){
							endEdit(thisMyComponet.myComponet(type,{method:'getdata'}));
						}
					});*/
				}
			}
			var thisCell = this;
			if(DataType.text==type){
				thisMyComponet.keyup(function(event){
					var myEvent =event||window.event;  
					var kcode=myEvent.keyCode;  
					if(kcode==13){
						if(!webUtil.isEmpty(endEdit)&&$.isFunction(endEdit)){
							endEdit($(this).myComponet(DataType.text,{method:'getdata'}));
						}
					}
				});
			}
		}
	}
}
$.fn.myDataTableCellEditor = function(type) {
	if(webUtil.isEmpty(type)) type = DataType.text;
	$(this).data("dataType",type);
	return new MyDataTableCellEditor($(this));
}
})(jQuery, window, document);

