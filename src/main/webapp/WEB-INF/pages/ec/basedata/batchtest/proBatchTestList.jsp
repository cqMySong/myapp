<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>检验批划分</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;">
		<div class="btn-group">
			<button class="btn btn-success" type="button" id="batchimp">
				<span class="fa fa-file-o"></span>&nbsp;检验批标准导入</button>
		</div>
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
								<th data-field="project_name">工程项目</th>
								<th data-field="proBaseWbs_displayName" data-width="250" >分部分项工程</th>
								<th data-field="number">编码</th>
								<th data-field="name">名称</th>
								<th data-field="content" data-width="400" data-type="textarea">划分办法</th>
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
function doLayout(){
	
}
var thisOrgList ;
var includeChild;
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&'project'==params.tree.type){
		}else{
			webUtil.mesg('请先选择的工程项目组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
}
function batchImpData(){
	var treeNode = thisOrgList.getSelectNode();
	if(!webUtil.isEmpty(treeNode)&&'project'==treeNode.type){
		var data = {projectId:treeNode.id};
		var url = 'ec/basedata/probatchtests/batchimp';
		webUtil.ajaxData({url:url,data:data,async:false,success:function(data){
			var statusCode = $(data).attr('statusCode');
			if(0==statusCode){
				var msg = $(data).attr('statusMesg');
				if(!webUtil.isEmpty(msg)){
					webUtil.mesg(msg);
				}
				thisOrgList.listUI.executeQuery();
			}
			//TODO 刷新
		}});
	}else{
		webUtil.mesg('请先选择的工程项目组织，然后才能做导入操作!');
	}
	
}
$(document).ready(function() {
     var treeNode2QueryProp = ["id","name","number","type"];
     var editWin ={title:'项目检验批划分',width:630,height:450,id:"probatchtests_tab"};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     var height =  top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/basedata/probatchtests'
    	 ,title:'工程项目',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false}});
     thisOrgList.onLoad();
     
     $('#batchimp').click(function(){
    	 batchImpData();
     });
});

</script>
</html>