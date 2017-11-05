<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字体图标库</title>
<%
 String appRoot = request.getContextPath();
%>
<link rel="stylesheet" href="<%=appRoot%>/assets/lib/fontawesome/css/font-awesome.css"/>
<link rel="stylesheet" href="<%=appRoot%>/assets/css/quirk.css"/>
<script type="text/javascript">
    var app = {};
    app.root = '<%=appRoot%>';
</script>
<!--[if lt IE 9]>
	<script src="<%=appRoot%>/assets/lib/html5shiv/html5shiv.js"></script>
	<script src="<%=appRoot%>/assets/lib/respond/respond.src.js"></script>
 <![endif]-->
<script src="<%=appRoot%>/assets/lib/jquery/jquery.js"></script>
<script src="<%=appRoot%>/assets/lib/bootstrap/js/bootstrap.js"></script>
<script src="<%=appRoot%>/assets/lib/jquery-toggles/toggles.js"></script>
<script src="<%=appRoot%>/assets/js/quirk.js"></script>
</head>
<body style="padding: 5px;">
	<div class="panel">
		<ul class="nav nav-tabs nav-line" >
			<li class="active" style="padding-top: 10px;">
				<a href="#fa" data-toggle="tab">
					<strong><i class="fa fa-flag"></i>Font Awesome字体库</strong>
				</a>
			</li>
			<li class="" style="padding-top: 10px;">
				<a href="#glyphicon" data-toggle="tab">
					<strong><i class="glyphicon glyphicon-leaf"></i>Glyphicon字体库</strong>
				</a>
			</li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="fa">
				<div class="row">
					<% 
					{
						String f1 = "f";
						boolean isGo = true;
						String[] c2 = "0,1,2".split(",");
						String[] c3 = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f".split(",");
						String[] c4 = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e".split(",");
						for(String f2:c2){
							if(!isGo) break;
							for(String f3:c3){
								if(!isGo) break;
								for(String f4:c4){
							String fa_icon = f1+f2+f3+f4;
							if("f2e1".equals(fa_icon)) isGo = false;
							if(!isGo) break;
					%>
					<div class="fa-hover col-md-1 col-sm-3">
						<i class="fa" style="color: #259dab;font-size: 14px;">
							&nbsp;&#x<%=fa_icon%>;
						</i>
						&nbsp;' \<%=fa_icon%>'	
					</div>
					<%
								}
							}
						}
					}
					%>
				</div>
				<div style="height: 20px;"> </div>
			</div>
			<div class="tab-pane" id="glyphicon">
				<div class="row">
					<%
						String[] other = "2a,2b,20ac,2601,2709,270f,26fa,f8ff,231b,00a5,20bd".split(",");
						for(String gy_icon:other){
					%>
						<div class="fa-hover col-md-1 col-sm-3">
							<i class="glyphicon" style="color: #259dab;font-size: 14px;">
								&nbsp;&#x<%=gy_icon%>;
							</i>
							&nbsp;'\<%=gy_icon%>'	
						</div>
					<%} %>
					
					<% 
						String f1 = "e";
						String gy_icon = "000";
						for(int i=1;i<261;i++){
							if(i<10){
								gy_icon=f1+"00"+i;
							}else if(i<100) {
								gy_icon=f1+"0"+i;
							}else{
								gy_icon = f1+i;
							}
					%>
					<div class="fa-hover col-md-1 col-sm-3">
						<i class="glyphicon" style="color: #259dab;font-size: 14px;">
							&nbsp;&#x<%=gy_icon%>;
						</i>
						&nbsp;'\<%=gy_icon%>'	
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
</div>
	
</body>
<script type="text/javascript">
	
$(document).ready(function() {
	
})
</script>
</html>






