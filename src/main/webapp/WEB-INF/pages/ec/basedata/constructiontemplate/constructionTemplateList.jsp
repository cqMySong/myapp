<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>施工样板清单</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
<div id="listPanel" class="panel">
	<div id="table-toolbar">
	</div>
	<table id="tblMain">
		<thead >
		<tr>
			<th data-field="number">编码</th>
			<th data-field="name">名称</th>
			<th data-field="engineeringProject">工程项目</th>
			<th data-field="templateType">样板种类</th>
			<th data-field="content" datatype="textarea">配合的技术交底</th>
			<th data-field="enabled" data-type="checkbox">启用</th>
			<th data-field="remark" >备注</th>
		</tr>
		</thead>
	</table>
</div>
</body>

<%@include file="../../../base/base_list.jsp"%>
<script type="text/javascript">
    /**
     * 一切操作前的接口函数
     */
    var listUI;
    function beforeAction(opt){
        return true;
    }

    function enableClick(btn){

    }

    $(document).ready(function() {
        var editWin ={title:'施工样板清单',width:620,height:460};
        var height = top.getTopMainHeight()-45;
        listUI = $('#listPanel').listUI({tableEl:'#tblMain',listModel:1,baseUrl:'ec/basedata/constructiontemplates'
            ,editWin:editWin,toolbar:"#table-toolbar",extendTableOptions:{height:height-40}});
        listUI.onLoad();
    })
</script>
</html>