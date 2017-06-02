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
<!--bootstrap-select2-->
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/select/css/select2.css"/>
<!--bootstrap-datepicker-->
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/bootstrap-datepicker/css/bootstrap-datepicker.css"/>
<!--bootstrap-datetimepicker-->
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>
<!--ztree-->
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/ztree/css/metro.css"/>
<!--myapp-->
<link rel="stylesheet" href="<%=appRoot%>/assets/app/css/myapp.main.css"/>

<script type="text/javascript">
    var app = {};
    app.root = '<%=appRoot%>';
</script>


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="<%=appRoot%>/assets/lib/html5shiv/html5shiv.js"></script>
	<script src="<%=appRoot%>/assets/lib/respond/respond.src.js"></script>
 <![endif]-->
<script src="<%=appRoot%>/assets/lib/jquery/jquery.js"></script>
<script src="<%=appRoot%>/assets/lib/bootstrap/js/bootstrap.js"></script>
<script src="<%=appRoot%>/assets/lib/jquery-toggles/toggles.js"></script>
<script src="<%=appRoot%>/assets/js/quirk.js"></script>

<!--bootstrap-table-->
<script src="<%=appRoot%>/assets/lib/bootstrap-table/js/bootstrap-table.js?v=121"></script>
<script src="<%=appRoot%>/assets/lib/bootstrap-table/js/bootstrap-table-zh-CN.js"></script>
<!--layer 3.0-->
<script src="<%=appRoot%>/assets/lib/layer/layer.js"></script>
<!--bootstrap-select2-->
<script src="<%=appRoot%>/assets/lib/select/js/select2.js"></script>
<script src="<%=appRoot%>/assets/lib/select/js/select2.zh-CN.js"></script>
<!--bootstrap-datepicker-->
<script src="<%=appRoot%>/assets/lib/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="<%=appRoot%>/assets/lib/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js"></script>
<!--bootstrap-datetimepicker-->
<script src="<%=appRoot%>/assets/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script src="<%=appRoot%>/assets/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
<!--ztree-->
<script src="<%=appRoot%>/assets/lib/ztree/js/jquery.ztree.all-3.5.min.js"></script>

<script src="<%=appRoot%>/assets/app/js/myapp.util.js"></script>
<script src="<%=appRoot%>/assets/app/js/myapp.main.js?v=13" type="text/javascript"></script>
<script src="<%=appRoot%>/assets/app/js/myapp.component.js?v=3" type="text/javascript"></script>
<script src="<%=appRoot%>/assets/app/js/myapp.tree.js?v=3" type="text/javascript"></script>
<script src="<%=appRoot%>/assets/app/js/myapp.myF7.js" type="text/javascript"></script>
