<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>会议纪要台账</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
<div id="table-toolbar" class="panel" style="height:60px;margin-bottom:5px;"></div>
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
							<th data-field="meetingSummaryType" data-type="select">纪要类别</th>
							<th data-field="name">名称</th>
							<th data-field="meetingDate" data-type="date">纪要时间</th>
							<th data-field="number">纪要编号</th>
							<th data-field="content">纪要内容</th>
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
        var editWin ={title:'会议纪要',width:680,height:480};
        var treeOpt = {
            setting:{
                data: {
                    simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
                }
            }};
        var height = window.outerHeight-325;
        thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/basedata/meetingsummaries',title:'项目工程',height:(height+42),
            treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
            ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false}});
        thisOrgList.onLoad();

    });

</script>
</html>