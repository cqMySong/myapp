<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>检验批划分</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;"></div>
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
								<th data-field="number">编码</th>
								<th data-field="testGroup" data-type="select">分部工程</th>
								<th data-field="name">子分部工程</th>
								<th data-field="content" data-width="400" data-type="textarea">分项工程</th>
								<th data-field="enabled" data-type="checkbox">启用</th>
								<th data-field="remark" data-width="300" data-type="textarea">备注</th>
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
		var tree = thisOrgList.tree;
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&!webUtil.isEmpty(params.tree.id)){
		}else{
			webUtil.mesg('请先选择具体的检验批分部工程，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
	
}
$(document).ready(function() {
     var treeNode2QueryProp = ["id","name"];
     var editWin ={title:'检验批划分',width:620,height:360};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:false,idKey: "id"}
    	 }}};
     var height = top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',listModel:1,treeUrl:'ec/basedata/batchtests/treeData'
    	 ,baseUrl:'ec/basedata/batchtests',title:'检验批分部工程',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false}});
     thisOrgList.onLoad();
});

</script>
</html>