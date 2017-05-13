<meta content="text/html;charset=utf-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="个人应用框架开发实践">
<meta name="author" content="mySong">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" >
<%
 String appRoot = request.getContextPath();
%>
<link rel="shortcut icon" href="<%=appRoot%>/assets/images/favicon.png" type="image/png">

<link rel="stylesheet" href="<%=appRoot%>/assets/lib/fontawesome/css/font-awesome.css"/>
<link rel="stylesheet" href="<%=appRoot%>/assets/css/quirk.css"/>

<!--bootstrap-table-->
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/bootstrap-table/css/bootstrap-table.min.css"/>


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="<%=appRoot%>/assets/lib/html5shiv/html5shiv.js"></script>
	<script src="<%=appRoot%>/assets/lib/respond/respond.src.js"></script>
 <![endif]-->

<script type="text/javascript">
    var app = {};
    app.root = '<%=appRoot%>';
</script>



<script src="<%=appRoot%>/assets/lib/jquery/jquery.js"></script>
<script src="<%=appRoot%>/assets/lib/bootstrap/js/bootstrap.js"></script>
<script src="<%=appRoot%>/assets/lib/jquery-toggles/toggles.js"></script>
<script src="<%=appRoot%>/assets/js/quirk.js"></script>


<!--bootstrap-table-->
<script src="<%=appRoot%>/assets/lib/bootstrap-table/js/bootstrap-table.min.js"></script>
<script src="<%=appRoot%>/assets/lib/bootstrap-table/js/bootstrap-table-zh-CN.js"></script>
<style type="text/css">
	table.table-hover thead tr th{background-color: #fff;}
	table.table-hover tbody tr.selected td{background-color: #8dc1ed;}
</style>

<script src="<%=appRoot%>/assets/app/js/myapp.util.js"></script>
<script src="<%=appRoot%>/assets/app/js/myapp.main.js" type="text/javascript"></script>
