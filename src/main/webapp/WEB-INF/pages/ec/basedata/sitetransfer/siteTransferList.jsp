<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>场地移交协议</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
<div id="table-toolbar" class="panel" style="height:40px;padding-top: 1px;margin-bottom: 5px;"></div>
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
							<th data-field="transferType" data-type="select">协议对象</th>
							<th data-field="number">移交单编号</th>
							<th data-field="transferRange" >移交范围</th>
							<th data-field="transferUnit">移交单位</th>
							<th data-field="receivingUnit">接收单位</th>
							<th data-field="transferDate" data-type="date">移交时间</th>
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
    function beforeAction(opt){
        if(opt=='addnew'){
            var params = thisOrgList.uiParams(opt);
            var tree = thisOrgList.tree;
            if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
                &&('project'==params.tree.type)){
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
        var editWin ={title:'场地移交协议',width:920,height:550};
        var treeOpt = {
            setting:{
                data: {
                    simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
                }
            }};
        var height = top.getTopMainHeight()-50;
        thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/basedata/sitetransfers',title:'项目工程',height:height,
            treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
            ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-45,sortStable:false}});
        thisOrgList.onLoad();

    });

</script>
</html>