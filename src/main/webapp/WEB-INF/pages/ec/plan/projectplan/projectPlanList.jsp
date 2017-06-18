<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目进度计划</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
	<div id="listPanel" class="panel" style="padding:2px;">
		<div class="gantt" style="height: 500px;"></div>
	</div>
</body>

<%@include file="../../../base/base_list.jsp"%>
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/gantt/css/style.css"/>
<script src="<%=appRoot%>/assets/lib/gantt/js/jquery.fn.gantt.min.js" charset ="GB2312"></script>

<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
var listUI;
function beforeAction(opt){
	return true;
}

function enableClick(btn){
	alert(btn.text);
}

$(document).ready(function() {
	$(".gantt").gantt({
		source: [{
			name: "Sprint 0",
			desc: "Analysis",
			values: [{
				id: "t01",
				from: "/Date(1320192000000)/",
				to: "/Date(1322401600000)/",
				label: "Requirement Gathering",
				customClass: "ganttRed"
			}]
		},{
			name: " ",
			desc: "Scoping",
			values: [{
				id: "t02",
				from: "/Date(1322611200000)/",
				to: "/Date(1323302400000)/",
				label: "Scoping",
				customClass: "ganttRed"
			}]
		},{
			name: "Sprint 1",
			desc: "Development",
			values: [{
				from: "/Date(1323802400000)/",
				to: "/Date(1325685200000)/",
				label: "Development",
				customClass: "ganttGreen"
			}]
		},{
			name: " ",
			desc: "Showcasing",
			values: [{
				from: "/Date(1325685200000)/",
				to: "/Date(1325695200000)/",
				label: "Showcasing",
				customClass: "ganttBlue"
			}]
		},{
			name: "Sprint 2",
			desc: "Development",
			values: [{
				from: "/Date(1326785200000)/",
				to: "/Date(1325785200000)/",
				label: "Development",
				customClass: "ganttGreen"
			}]
		},{
			name: " ",
			desc: "Showcasing",
			values: [{
				from: "/Date(1328785200000)/",
				to: "/Date(1328905200000)/",
				label: "Showcasing",
				customClass: "ganttBlue"
			}]
		},{
			name: "Release Stage",
			desc: "Training",
			values: [{
				from: "/Date(1330011200000)/",
				to: "/Date(1336611200000)/",
				label: "Training",
				customClass: "ganttOrange"
			}]
		},{
			name: " ",
			desc: "Deployment",
			values: [{
				from: "/Date(1336611200000)/",
				to: "/Date(1338711200000)/",
				label: "Deployment",
				customClass: "ganttOrange"
			}]
		},{
			name: " ",
			desc: "Warranty Period",
			values: [{
				from: "/Date(1336611200000)/",
				to: "/Date(1349711200000)/",
				label: "Warranty Period",
				customClass: "ganttOrange"
			}]
		}],
		navigate: "scroll",
		months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
		maxScale: "months",minScale:"months",
		itemsPerPage: 10,
		onItemClick: function(data) {
			alert("Item clicked - show some details");
		},
		onAddClick: function(dt, rowId) {
			alert("Empty space clicked - add an item!");
		},
		onRender: function() {
			if (window.console && typeof console.log === "function") {
				console.log("chart rendered");
			}
		},
		onDataLoadFailed: function(data) {
			alert("Data failed to load!")
		}
	});
	
	$(".gantt").popover({
		selector: ".bar",
		title: "I'm a popover",
		content: "And I'm the content of said popover.",
		trigger: "hover"
	});
})
</script>
</html>