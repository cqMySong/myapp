<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>劳务分包登记备案台帐</title>
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
		.panel {
			width: 100%;
			height: 100%;
			padding: 0px 2px 2px 2px;
		}
	</style>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
<div class="panel">
	<div id="table-toolbar" style="height:40px;padding-top: 2px;">
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">负责人</span>
				<input name="directorName"  autocomplete="off" type="text" class="input-item form-control">
			</div>
		</div>
		<div class="col-sm-3">
			<div class="input-group">
				<span class="input-group-addon lable">单位名称</span>
				<input type="text" class="input-item form-control" name="unitName">
			</div>
		</div>
		<div class="btn-group">
			<button class="btn btn-success" type="button" id="queryAnalysis">
				<span class="fa fa-search"></span>&nbsp;查询</button>
		</div>
	</div>
	<hr style="margin: 2px 0px;">
	<div class="mainContrainer">
		<div class="leftContainer" style="border-right: 2px solid #d8dce3" id="left_container">
		</div>
		<div class="rightContainer" id="main_container">
			<table id="tblMain">
				<thead >
					<tr>
						<th data-field="unitName">单位名称</th>
						<th data-field="treatyContents">分包工程内容</th>
						<th data-field="directorName">现场分包负责人</th>
						<th data-field="directorTel">联系电话</th>
						<th data-field="aptitude">公司资质</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
    var orgTree;
    var curSelOrg;
    var tblMain;
    function treeClick(event, treeId, treeNode){
        if(webUtil.isEmpty(curSelOrg)) curSelOrg = {id:'xyz'};
        if(webUtil.isEmpty(curSelOrg.id)) curSelOrg.id = 'xyz';
        if(curSelOrg.id!=treeNode.id){
            curSelOrg = treeNode;
            tblMain.refreshData();
        }
    }

    function initOrgTree(){
        var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
            ,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
            ,callback:{onClick:treeClick}
        };
        var treeViewer = $('#left_container').myTreeViewer(null);
        treeViewer.init({theme:"panel-success",title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
        treeViewer.addTree(treeOpt,[]);
        orgTree = treeViewer.getTree();
        treeViewer.addRefreshBtn({clickFun:function(btn){
            loadTreeData();
        }});
        loadTreeData();
    }
    function loadTreeData(){
        webUtil.ajaxData({url:'ec/basedata/projects/projectTree',async:false,success:function(data){
            var treeDatas = data.data;
            if (treeDatas.length>0&&!webUtil.isEmpty(orgTree)) {
                orgTree.reLoadTree(treeDatas)
                orgTree.selectNodeByIndex(0);
            }
        }});
    }
    function initTable(){
        var height = top.getTopMainHeight()-105;
        var table_options = {height:height,striped:true,sortStable:false,showRefresh:false,selectModel:1
            ,cache:false,showToggle:false,search:false,queryParams:searchPrams,toolbar:false
            ,showColumns:false,idField:"id",mypagination:true,url:'ec/engineering/artificialcontractledger/query'};
        tblMain = $('#tblMain').myDataTable(table_options);
    }
    function searchPrams(){
        var params = {};
        params.projectId = curSelOrg.id;
        params.directorName = $("input[name='directorName']").val();
        params.unitName = $("input[name='unitName']").val();
        return webUtil.json2Str(params);
    }
    $(function(){
        var height = top.getTopMainHeight()-40;
        $(".mainContrainer").height(height);
        initOrgTree();
        initTable();
        $("#queryAnalysis").on('click',function(){
            tblMain.refreshData();
        });
    });
</script>
</html>