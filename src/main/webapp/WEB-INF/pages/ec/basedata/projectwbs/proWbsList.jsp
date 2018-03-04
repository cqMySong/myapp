<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>工程项目分解结构</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;">
		<button class="btn btn-success" type="button" id="batchimp">
			<span class="fa fa-file-o"></span>&nbsp;工程分解结构标准导入</button>
	
	</div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel">
				<div class="" id="tblMain_toolbar">
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead >
							<tr>
								<th data-field="proStruct_name">单位工程</th>
								<th data-field="number" data-align="left">编码</th>
								<th data-field="name">名称</th>
								<th data-field="parent_number" data-align="left">上级编码</th>
								<th data-field="displayName" data-align="left">全名</th>
								<th data-field="wbsType" data-type="select">结构类别</th>
								<th data-field="enabled" data-type="checkbox">启用</th>
								<th data-field="remark" >备注</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="../../../base/base_treelist.jsp"%>
<script type="text/javascript">

var thisOrgList ;
var includeChild;
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&('proStructure'==params.tree.type)){
		}else{
			webUtil.mesg('请先选择的单位工程结构，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
	
}
var impurl = 'ec/basedata/probasewbss/batchimp';
function batchImpData(){
	var treeNode = thisOrgList.getSelectNode();
	if(!webUtil.isEmpty(treeNode)&&'proStructure'==treeNode.type){
		var data = {structId:treeNode.id};
		webUtil.ajaxData({url:impurl,data:data,async:false,success:function(data){
			var statusCode = $(data).attr('statusCode');
			if(0==statusCode){
				var msg = $(data).attr('statusMesg');
				if(!webUtil.isEmpty(msg)){
					webUtil.mesg(msg);
				}
				thisOrgList.listUI.executeQuery();
			}
		}});
	}else{
		webUtil.mesg('请先选择的工程项目的单位工程结构，然后才能做导入操作!');
	}
}
function getAllChildrenNodes(treeNode,result){
    result.push(treeNode.id);
	var childrenNodes = treeNode.children;
	if (childrenNodes) {
		for (var i = 0; i < childrenNodes.length; i++) {
            result = getAllChildrenNodes(childrenNodes[i], result);
		}
	}
    return result;
}
$(document).ready(function() {
     var treeNode2QueryProp = ["id","name","number","longNumber","type"];
     var editWin ={title:'工程项目分解结构',width:620,height:450};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     var height = top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',listModel:1,treeUrl:'ec/basedata/prostructures/proStructureTree',baseUrl:'ec/basedata/prowbss',title:'工程项目',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false}});
     thisOrgList.onLoad();
     
     $('#batchimp').click(function(){
    	 //batchImpData();
         var tree = thisOrgList.getSelectNode();
         if(webUtil.isEmpty(tree)||'proStructure'!=tree.type){
             webUtil.mesg('请先选择的工程项目的单位工程结构，然后才能做导入操作!');
             return false;
         }
         var _win = $.extend(true,{},{title:'工程分解结构标准导入',width:900,height:570,
			 btns:['确定','取消'],btnCallBack:function(index,layerIndex,layero){
             if(layero){
                 if(index==1){
                     var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
                     var datas = iframe_win.getData();
                     if(!webUtil.isEmpty(datas)&&datas.length>0){
                         var wbsIds = [];
                         $.each(datas,function(i,val){
                           getAllChildrenNodes(val,wbsIds);
						 });
                         var pams = {structId:tree.id,structCode:tree.number,wbsIds:wbsIds.join(",")};
                         webUtil.ajaxData({url:"ec/basedata/prowbs/import",data:pams,async:true,success:function(data){
                             thisOrgList.listUI.executeQuery();
                         }});
                         return true;
                     }else{
                         webUtil.mesg("未选择任何数据!");
                         return false;
                     }
                 }
             }
             return true;
         }});
         _win.url =  webUtil.toUrl('ec/basedata/prowbss/forward/batchimp');
         _win.uiParams={project:{id:webUtil.uuIdReplaceID(tree.id),name:tree.name,number:tree.number}};
         _win.colseCallBack =function(){
             thisOrgList.listUI.executeQuery();
         };
         webUtil.openWin(_win);
     });
});

</script>
</html>