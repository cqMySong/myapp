<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>整改通知单</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
<div id="table-toolbar" class="panel" style="height:42px;padding-top: 2px;">
</div>
<div class="mainContrainer">
	<div class="leftContainer" id="tree_container"></div>
	<div class="rightContainer" id="tblMain_container">
		<div class="panel" style="margin-bottom: 0px;">
			<div class="" id="tblMain_toolbar">
			</div>
			<div class="panel-body" style="padding: 0px 2px 2px 2px;">
				<table id="tblMain">
					<thead >
					<tr>
						<th data-field="number" >编号</th>
						<th data-field="name" >名称</th>
						<th data-field="orgUnit">受检查单位</th>
						<th data-field="orgUnitPerson">受检人</th>
						<th data-field="type">类别</th>
						<th data-field="problem">存在的问题</th>
						<th data-field="requires">整改要求</th>
						<th data-field="endDate" data-type="date">截止日期</th>
						<th data-field="checkOrgUnit">检查单位</th>
						<th data-field="checkOrgPerson">检查人</th>
						<th data-field="checkDate" data-type="date">检查日期</th>
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
    var thisBudgetList ;
    function beforeAction(opt){
        if(opt=='addnew'){
            var params = thisOrgList.uiParams(opt);
            var tree = thisOrgList.tree;
            if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
                &&('project'==params.tree.type||'proStructure'==params.tree.type)){
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

    $(document).ready(function() {
        var treeNode2QueryProp = ["id","name","number","longNumber","type"];
        var editWin ={title:'整改通知单',width:700,height:570};
        var treeOpt = {
            setting:{
                data: {
                    simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
                }
            }};
        var height = window.outerHeight-305;
        thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/other/rectifyNotices',title:'项目工程',height:(height+40),
            treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
            ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-25,sortStable:false}});
        thisOrgList.onLoad();
    });

</script>
</html>