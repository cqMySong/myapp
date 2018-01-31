<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myapp.core.license.LicenseInfo" %>
<%@ page import="com.myapp.core.license.ModelItemInfo" %>
<%@ page import="com.myapp.core.util.DateUtil" %>
<%@ page import="com.myapp.core.license.SystemTool" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>许可明显查看</title>
</head>

<%@include file="../inc/webBase.inc"%>
<style type="text/css">
.table-bordered > tbody > tr > td{
	border: 1px solid #17211a1a;
	border-bottom: 0px;
	border-right: 0px;
}
.table-bordered > tbody > tr > td:first-child{
	border-left: 1px solid #17211a1a;
}
.table-bordered > tbody > tr > td:last-child{
	border-right: 1px solid #17211a1a;
}
.table-bordered > tbody > tr:last-child>td{
	border-bottom: 1px solid #17211a1a;
}
</style>
<% 
	Object licObj = request.getServletContext().getAttribute("LIC"); 
	LicenseInfo licInfo = null;
	if(licObj!=null&&licObj instanceof LicenseInfo){
		licInfo = (LicenseInfo)licObj;
	}
%>
<script type="text/javascript">
</script>
<body style="overflow: hidden;" class="contentpanel">
	<div class="row">
		<div class="col-sm-1">&nbsp;</div>
		<div class="col-sm-10">
			<div class="panel panel-primary">
				<ul class="panel-options">
					<li id="showCode" style="color: #fff;">
						<a >特征码<i class="fa fa-eye"></i></a>
					</li>
					<li id="machCode" style="display: none;color: #fff;">
						<%if(licInfo!=null){%>[<%=licInfo.getMachineCode()%>]<%}%>
					</li>
				</ul>
				<div class="panel-heading">
					<h3 class="panel-title">
						<%if(licInfo!=null){%><%=licInfo.getAppName() %><%}%>系统授权许可详情</h3>
				</div>
				<div class="panel-body" style="padding: 5px;"> 
					<%
						if (licInfo == null) {
					%>
					<div class="alert alert-warning">
						<strong>License!</strong>无合法的许可文件!
						特征码:<%=SystemTool.getMachineCode()%>
					</div>
					<%
						} else {
					%>
					<table
						class="table table-bordered table-success ">
						<thead>
							<tr>
								<th colspan="4">应用许可情况(<%=licInfo.getType().getName()%>)
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="text-align: right;">公司名</td>
								<td><%=licInfo.getCompaney()%></td>
								<td style="text-align: right;">版本序号</td>
								<td><%=licInfo.getProductID() + licInfo.getVersion()%></td>
							</tr>
							<tr>
								<td style="text-align: right;">开始时间</td>
								<td><%=DateUtil.formatDate(licInfo.getBegDate())%></td>
								<td style="text-align: right;">截止时间</td>
								<td><%=DateUtil.formatDate(licInfo.getEndDate())%></td>
							</tr>
							<tr>
								<td colspan="5" style="padding: 10px;">░授权明细清单：</td>
							</tr>
							<tr style="text-align: center;">
								<td>功能项</td>
								<td colspan="2">描述</td>
								<td>许可数</td>
							</tr>
							<%
								List<ModelItemInfo> models = licInfo.getModels();
								if(models!=null&&models.size()>0){
									for(ModelItemInfo miInfo:models){
							%>
								<tr>
									<td style="text-align: left;">☑&nbsp;&nbsp;<%=miInfo.getName() %></td>
									<td colspan="2" style="text-align: left;">☑&nbsp;&nbsp;<%=miInfo.getRemark() %></td>
									<td style="text-align: center;"><%=miInfo.getCount()%></td>
								</tr>
							<%
									}
								}
							%>
						</tbody>
					</table>
					<%
						}
					%>
				</div>
			</div>
		</div>
		<div class="col-sm-1">&nbsp;</div>
	</div>
</body>
<script type="text/javascript">
var isShow = false;
$(document).ready(function() {
	$('#showCode').click(function(){
		if(isShow){
			$('#machCode').hide();
		}else{
			$('#machCode').show();
		}
		isShow = !isShow;
	});
})
</script>
</html>