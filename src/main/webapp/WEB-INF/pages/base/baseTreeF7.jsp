<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>....</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div class="panel" id="treeContainer">
		
	</div>
</body>
<%@include file="../inc/webBase.inc"%>
<script type="text/javascript">
var treeMain;
var treeViewer;
var treeSetting = {
    view: {
        dblClickExpand: true, showLine: true,selectedMulti: false
    },
    data: {
        simpleData: {enable:true,idKey: "id", pIdKey: "parent_id",rootPId: ''}
    }
};
function getData(){
	var nodes = treeMain.getSelectNodes();
	if(nodes) return nodes[0];
	return {};
}
//f7中配置 height:580,width:300 合适
$(document).ready(function() {
	treeViewer = $('#treeContainer').myTreeViewer(null);
	treeViewer.init({title:'${treeTitle}',height:460});
	treeViewer.addTree(treeSetting,[]);
	treeMain = treeViewer.getTree();
	var tree_url = '${dataUrl}';
	var _uiCtx = "${uiCtx}";
	var data = {};
	if(!webUtil.isEmpty(_uiCtx)){
		data = webUtil.str2Json(_uiCtx);
	}
	webUtil.ajaxData({url:tree_url,data:data,async:false,success:function(data){
		var treeDatas = data.data;
		if (treeDatas.length>0) {
			treeMain.reLoadTree(treeDatas)
			treeMain.selectNodeByIndex(0);
		}
	}});
});

</script>
</html>