<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../base/base_list.jsp"%>
<style type="text/css">
.mainContrainer {
  width: 100%;
  height: 100%;
  overflow:hidden;
  padding: 0px 2px 2px 2px;
}
.leftContainer {
  width: 260px;
  height: 100%;
  float: left;
}
.rightContainer {
  height: 100%;
  overflow:hidden;
  padding-left: 5px;
}

</style>

<script type="text/javascript">
;(function($, window, document,undefined) {
var TreeListUI = function(el,options){
	var _def_treeList = {height:700,treeUrl:undefined,treeSectedChange2Query:true
			,treeContainer:'#tree_container',treeOpt:{},tableEl:'#tblMain'
			,extendTableOptions:{height:700}
			,editWin:{title:'^~^',url:'',maxmin:true,width:800,height:600,callBack:undefined}
			,treeNode2QueryProp:["id","name","number","longNumber"]
	};
	this.options = $.extend({},_def_treeList, options);
	this.treeViewer = undefined;
	this.listUI = undefined;
	var _defTreeOpt = {height:this.options.height,title:this.options.title
			,url:this.options.treeUrl||this.options.baseUrl+'/tree'
			,setting:{
				view: {dblClickExpand: true,selectedMulti: false},
				data: {simpleData: {enable:true,idKey: "id",pIdKey: "parent_id",rootPId: ""}},
				callback:{beforeClick:undefined}
	}};
	this.selTreeId = '';
	var thisTreeUI = this;
	_defTreeOpt.setting.callback.onClick = function(event, treeId, treeNode){
		var selNode = treeNode;
		if(selNode.id!=thisTreeUI.selTreeId){
			thisTreeUI.selTreeId = selNode.id;
			var to_treeNode = {};
			var props = thisTreeUI.options.treeNode2QueryProp;
			for(var i=0;i<props.length;i++){
				var prop = props[i];
				to_treeNode[prop] = selNode[prop];
			}
			var _thisListUI = thisTreeUI.listUI;
			if(thisTreeUI.options.treeSectedChange2Query){
				var thisParms = {tree:to_treeNode};
				_thisListUI.executeQueryByParams(thisParms);
			}
			if(thisTreeUI.options.treeOpt&&thisTreeUI.options.treeOpt.selectChange
					&&$.isFunction(thisTreeUI.options.treeOpt.selectChange)){
				thisTreeUI.options.treeOpt.selectChange(event, treeId, treeNode);
			}
		}
		if(thisTreeUI.options.treeOpt&&thisTreeUI.options.treeOpt.setting
				&&thisTreeUI.options.treeOpt.setting.callback
				&&thisTreeUI.options.treeOpt.setting.callback.onClick){
			thisTreeUI.options.treeOpt.setting.callback.onClick(event, treeId, treeNode);
		}
		return true;
	}
	if(thisTreeUI.options.treeOpt&&thisTreeUI.options.treeOpt.setting
			&&thisTreeUI.options.treeOpt.setting.callback
			&&thisTreeUI.options.treeOpt.setting.callback.onClick){
		thisTreeUI.options.treeOpt.setting.callback.onClick = undefined;
	}
	
	this.treeOpt =  $.extend({},_defTreeOpt, this.options.treeOpt);
	if(!webUtil.isEmpty(this.options.treeContainer)){
		var _defTreeViewer = {theme:"panel-success",title:this.treeOpt.title,height:this.treeOpt.height,search:true};
		this.treeViewer = $(this.options.treeContainer).myTreeViewer(null);
		this.treeViewer.init(_defTreeViewer);
	}
	this.options.editWin.uiParams = function(operate){
		return thisTreeUI.uiParams(operate);
	};
	this.listUI = $(el).listUI(this.options);
	this.tree = undefined;
}
TreeListUI.prototype = {
	onLoad:function(){
		if(!webUtil.isEmpty(this.treeViewer)){
			if(!webUtil.isEmpty(this.treeOpt.url)){
				var tree_url = this.treeOpt.url;
				var _data = getTreeQueryParams();
				var thisTreeList = this;
				webUtil.ajaxData({url:tree_url,async:false,data:_data,success:function(data){
					var treeDatas = data.data;
					thisTreeList.treeViewer.addTree(thisTreeList.treeOpt.setting,treeDatas);
					thisTreeList.tree = thisTreeList.treeViewer.getTree();
					if (treeDatas.length>0) {
						thisTreeList.tree.selectNodeByIndex(0);
					}
				}});
			}
		}
		this.listUI.onLoad();
	},
	getSelectNode:function(){
		if(this.tree){
			var nodes = this.tree.getSelectNodes();
			if(nodes) return nodes[0]
		}
		return {};
	},
	uiParams:function(operate){
		var params = {};
		if(operate=='addnew'){
			var selNode = this.getSelectNode();
			if(webUtil.isEmpty(selNode)) return {};
			var to_treeNode = {};
			var props = this.options.treeNode2QueryProp;
			for(var i=0;i<props.length;i++){
				var prop = props[i];
				var val = selNode[prop];
				if(prop == 'id'&&!webUtil.isEmpty(val)){
					val = webUtil.uuIdReplaceID(val);
				}
				to_treeNode[prop] = val;
			}
			params.tree = to_treeNode;
		}
		if(openUIParams&&$.isFunction(openUIParams)){
			openUIParams(operate,params);
		}
		return params;
	}
}

$.fn.treeListUI = function(options) {
     return new TreeListUI(this,options);
}

})(jQuery, window, document);

function getTreeQueryParams(){
	return {};
}

function openUIParams(operate,params){
	
}

$(document).ready(function(){
	
})
</script>




