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
		<!-- <div class="ui-layout-center"></div>
		<div class="ui-layout-north">North</div>  
		<div class="ui-layout-south">South</div>  
		<div class="ui-layout-east">East</div>  
		<div class="ui-layout-west">West</div>  -->
<body style="padding: 5px;" >
	<div class="ui-layout-north">North</div>  
	<div class="ui-layout-center">
		<div id="listPanel" style="padding:2px;">
			<div class="gantt" style="height: 400px;overflow: auto;"></div>
		</div>
	</div>  
</body>

<%@include file="../../../base/base_list.jsp"%>
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/gantt/css/style.css"/>
<script src="<%=appRoot%>/assets/lib/gantt/js/jquery.fn.gantt.js?v=1" charset ="GB2312"></script>

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
	$('body').layout({ applyDefaultStyles: true,north_resizable:true});  
	
	$(".gantt").gantt({
		source: [{
			name: "现场施工",
			desc: "dsat",
			begData:"开始",
			values: [{
				id: "t01",
				from: "2017-05-05",
				to: "2017-05-26",
				percent:50,
				desc:"sdfasdfasdf",
				customClass: "ganttRed",
				label: "Requirement Gathering"
			}]
		},{
			name: "",
			desc: "Scoping",
			values: [{
				id: "t02",
				from: "2017-06-15",percent:50,
				to: "2017-06-25",
				label: "Scoping"
			}]
		},{
			name: "Sprint 1",
			desc: "Development",
			values: [{
				from: "2017-05-25",
				to: "2017-06-05",percent:50,
				label: "Development"
			}]
		},{
			name: " ",
			desc: "Showcasing",
			values: [{
				from: "2017-06-05",
				to: "2017-07-05",percent:50,
				label: "Showcasing"
			}]
		},{
			name: "Sprint 2",
			desc: "Development",
			values: [{
				from: "2017-06-20",
				to: "2017-06-30",percent:50,
				label: "Development"
			}]
		},{
			name: " ",
			desc: "Showcasing",
			values: [{
				from: "2017-06-01",
				to: "2017-07-15",percent:50,
				label: "Showcasing"
			}]
		},{
			name: "Release Stage",
			desc: "Training",
			values: [{
				from: "2017-07-08",
				to: "2017-07-12",percent:50,
				label: "Training"
			}]
		},{
			name: " ",
			desc: "Deployment",
			values: [{
				from: "2017-07-15",
				to: "2017-07-30",
				label: "Deployment"
			}]
		},{
			name: " ",
			desc: "Warranty Period",
			values: [{
				from: "2017-08-05",
				to: "2017-08-15",percent:50,
				label: "Warranty Period"
			}]
		}],
		navigate: "scroll",
		itemsPerPage: 20,
		onItemClick: function(data) {
			alert("data = "+data.label);
		},
		leftCols:[
		          {text:'工序',name:'name',algin:'left',width:100},
		          {text:'工作项',algin:'left',name:'desc',width:100},
		          {text:'里程碑',name:'isKey',type:'boolean',width:45},
		          {text:'开始时间',name:'begDate',width:80},
		          {text:'截止时间',name:'endDate',width:80}
		  ],
		leftColClick:function(data){
			alert('你点击了第:'+data.rowIdx+'行');
		}
	});
})
</script>
</html>