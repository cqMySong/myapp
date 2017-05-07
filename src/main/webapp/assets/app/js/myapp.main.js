/**
 * 主要针对main 首页写的一些js，不代表通用性
 */
//{tabs:[{title:"主菜单",id:"",icon:"",active:true}]}
var mainTabsMenu = function(el,option){
	
}
/**
 * pillmenu 只是支持两级菜单
 */
//{theme:"",skin:"",menus:[{title="",url="",openType="",icon="",child:[{....}]}]}
//skin:danger,info
//theme : white,quirk 只有这两种主题
var _def_pillmenu = {theme:"white",skin:""};
var _def_pillmenu_item ={title:"我的标题",url:"",target:"#mainTab",icon:"",active:false,child:[]};
var mainLeftMenu = {
	init:function(el){
		var _$dom = webUtil.getJqueryDom(el,'.my-pill-menu');
		_$dom.each(function () {
			var _$this = $(this);
			var _menu = _$this.data('opt');
			if(!webUtil.isEmpty(_menu)){
				_menu = eval('('+_menu+')');
				mainLeftMenu.initStyle(_$this,_menu);
	        }
			mainLeftMenu.addClickEvent(_$this);
		});
	},
	initStyle:function(el,_menu){
		var _$dom = webUtil.getJqueryDom(el,'');
		if(!_$dom.hasClass('nav-wrapper')){
            _$dom.addClass('nav-wrapper');
        }
		var _opt = {};
        $.extend(true,_opt,_def_pillmenu);
        if(_menu&&$.isPlainObject(_menu)){
            $.extend(true,_opt,_menu);
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
        	mainLeftMenu.addMenuList(_ul,_menus);
        }
	},
	addMenuList:function(ul_el,_menus){//这是加第一级的方法
		var _$dom = webUtil.getJqueryDom(ul_el,'');
		if(!webUtil.isEmpty(_menus)
				&&$.isArray(_menus)&&_menus.length>0){
			for(var i=0;i<_menus.length;i++){
				var _menuItem = _menus[i];
				var _li = mainLeftMenu.getLiMenum(_menuItem);
				mainLeftMenu.addMenuItem(_li,_menuItem.child);
				_$dom.append(_li);
			}
		}
	},
	getLiMenum:function(_item){
		var _menuItem = {};
		$.extend(true,_menuItem,_def_pillmenu_item);;
		$.extend(true,_menuItem,_item);;
		var _li = $('<li>');
		if(_menuItem.active){
			_li.addClass('active');
		}
		var _li_a = $('<a href="#" class="_menuItems"></a>');
		if(!webUtil.isEmpty(_menuItem.icon)){
			_li_a.append('<i class="'+_menuItem.icon+'"></i>');
		}
		if(!webUtil.isEmpty(_menuItem.title)){
			_li_a.append('<span>'+_menuItem.title+'</span>');
		}
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
			for(var i=0;i<_child.length;i++){
				var _li_ul_child_li = mainLeftMenu.getLiMenum(_child[i]);
				_li_ul_child.append(_li_ul_child_li);
				if(_li_ul_child_li.hasClass('active')){
					_li.addClass('active');
				}
			}
			_li.append(_li_ul_child);
		}
	},
	addClickEvent:function(el){
		var _$dom = webUtil.getJqueryDom(el,'my-pill-menu');
		_$dom.find('a._menuItems').each(function(){
			$(this).click(function(){
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
					if(_menuItem.target=="mainTab"){
						
					}
				}
			});
		});
	}
};
var mainTabs = {
	addTab :function(el,_opt){
		var _$dom = webUtil.getJqueryDom(el,'my-pill-menu');
	}
}
$(document).ready(function() {
	mainLeftMenu.init();
})
