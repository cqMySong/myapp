<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目组织树</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div class="panel" id="treeContainer">
		
	</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
var projectTree;
var projectTreeViewer;
var projectSetting = {
    view: {
        dblClickExpand: false, showLine: true,selectedMulti: false
    },
    data: {
        simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    },
    callback: {
        beforeClick: function(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("projectTree");
            if (treeNode.isParent) {
                zTree.expandNode(treeNode);
                return false;
            } else {
                return true;
            }
        }
    }
};
function getData(){
	var nodes = projectTree.getSelectNodes();
	if(nodes) return nodes[0];
	return {};
}
//f7中配置 height:580,width:300 合适
$(document).ready(function() {
	var treeData = '${treeData}';
	projectTreeViewer = $('#treeContainer').myTreeViewer(null);
	projectTreeViewer.init({title:'工程项目',height:460});
	projectTreeViewer.addTree(projectSetting,[]);
	projectTree = projectTreeViewer.getTree();
	var tree_url = "ec/basedata/project/orgData";
	webUtil.ajaxData({url:tree_url,async:false,success:function(data){
		var treeDatas = data.data;
		if (treeDatas.length>0) {
			projectTree.reLoadTree(treeDatas)
			projectTree.selectNodeByIndex(0);
		}
	}});
});

</script>
</html>