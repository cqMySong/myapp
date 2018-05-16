<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>影像资料</title>
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
								<th data-field="proStructure_name">工程结构</th>
								<th data-field="proBaseWbs_name">分部分项</th>
								<th data-field="proBatchTest_name">检验批</th>
								<th data-field="number">编码</th>
								<th data-field="name">名称</th>
								<th data-field="imageDataType" data-type="select">资料类型</th>
								<th data-field="attachs" data-type='attach'>附件数</th>
								<th data-field="createUser_name" >创建人</th>
								<th data-field="createDate" data-type="datetime">创建时间</th>
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
				&&('baseOrg'!=params.tree.type)){
		}else{
			webUtil.mesg('请先选择的工程项目组织或项目工程结构，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}

//界面参数传递扩展方法
function openUIParams(operate,params){
    var selectNode = thisOrgList.getSelectNode();
    if(selectNode&&params["tree"]) {
        params["tree"]["parentId"] = webUtil.uuIdReplaceID(selectNode.parentId);
    }
}
$(document).ready(function() {
     var treeNode2QueryProp = ["id","name","number","longNumber","type","parentId"];
     var editWin ={title:'工程影像资料',width:620,height:450};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     var height = top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',listModel:1,treeUrl:'ec/basedata/imagedatas/image/data/tree',baseUrl:'ec/basedata/imagedatas',title:'工程项目',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false}});
     thisOrgList.onLoad();
});

</script>
</html>