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
<style type="text/css">
.fm-sidebar .tag-list li a {
	width: 40px;padding: 5px 5px;
	font-size: 14px;text-align: center;
	height:25px;vertical-align: middle;
	background-color: #259dab;color: #fff;
}
.fm-sidebar .tag-list li a.fa.sel::after {
  content: "\f00c";
  position: relative;
  top: -8px;
  right: -5px;
  border-radius: 6px;
  background-color: #d9534f;
  font-size:10px;
  opacity: .9;
}
.fm-sidebar .tag-list li a.glyphicon.sel::after {
  content: "\e013";
  position: relative;
  top: -21px;
  right: -13px;
  border-radius: 4px;
  background-color: #d9534f;
  font-size:10px;
  opacity: .9;
}
</style>
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
<body style="padding: 5px;" class="panel">
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
			<div class="fm-sidebar">
				<ul class="tag-list">
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
					<li>
						<a class="fa" type="fa" codeType="UNICODE" code="<%=fa_icon%>"  href="#">
							&#x<%=fa_icon%>;
						</a>
							
					</li>
					<%
								}
							}
						}
					}
					%>
				</ul>
				</div>
				<div style="height: 20px;"> </div>
			</div>
			<div class="tab-pane" id="glyphicon">
				<div class="fm-sidebar">
				<ul class="tag-list">
					<%
						String[] other = "2a,2b,20ac,2601,2709,270f,26fa,f8ff,231b,00a5,20bd".split(",");
						for(String gy_icon:other){
					%>
						<li>
							<a class="glyphicon" type="glyphicon" codeType="UNICODE" code="<%=gy_icon%>" href="#">
								&#x<%=gy_icon%>;
							</a>
								
						</li>
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
					<li >
						<a class="glyphicon" type="glyphicon" codeType="UNICODE" code="<%=gy_icon%>" href="#">
							&#x<%=gy_icon%>;
						</a>
							
					</li>
					<%
						}
					%>
				</ul>
				</div>
			</div>
		</div>
</div>
	
</body>
<script type="text/javascript">
function getIconData(){
	var selIcon = $('a.sel').eq(0);
	if(selIcon){
		var iconObj = {};
		iconObj.icon = selIcon.attr('code');
		iconObj.iconCodeType = selIcon.attr('codeType');
		iconObj.iconType = selIcon.attr('type');
		return iconObj;
	}
	return null;
}
$(document).ready(function() {
	$('ul.tag-list>li>a').on('click',function(){
		$('a.sel').removeClass('sel');
		$(this).addClass('sel');
	});
})
</script>
</html>






