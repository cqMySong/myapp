<%@ page language="java" contentType="text/html; charset=UTF-8"	 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>岗位工作要求</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">

</script>
<body style="padding: 5px;">
<div id="editPanel" class="myMainContent panel" style="background-color: white;">
	<div id="table-toolbar">
		<div class="btn-group" style="padding-left: 5px;padding-top: 2px;padding-bottom: 2px;">
			<button class="btn btn-success" id="saveJobRequire">
				<span class="fa fa-save"></span>&nbsp;保存
			</button>
		</div>
	</div>
	<form id="editForm" style="margin-top: 10px;">
		<input type="hidden" name="parentId" value="${parentId}">
		<c:forEach items="${jobRequire}" var="job">
			<div class="col-sm-6">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">${job.key}</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<c:forEach items="${job.value}" var="jobRequireItem">
								<div data-name="jobRequireItem" class="col-sm-12 mt5"
									 data-value="${jobRequireItem.checkItem}" data-position="${jobRequireItem.position.id}"
									 data-id="${jobRequireItem.id}">
									<div class="input-group">
										<input name="checked" class="input-item form-control" style="width: 10%;"
											   data-opt="{type:'checkbox',checked:${jobRequireItem.checked}}" type="checkbox">
										<span class="input-group-addon lable" style="width: 90%;text-align: left;">${jobRequireItem.checkItem}</span>
									</div>
								</div>
								<c:set var="checker" value="${jobRequireItem.checker}"></c:set>
								<c:set var="lastUpdateDate" value="${jobRequireItem.lastUpdateDate}"></c:set>
							</c:forEach>
							<div class="col-sm-6 mt10 ">
								<div class="input-group">
									<span class="input-group-addon lable" style="min-width: 60px;">确认人</span>
									<input class="input-item read" name="number" readonly="readonly" value="${checker.name}">
								</div>
							</div>
							<div class="col-sm-6 mt10">
								<div class="input-group">
									<span class="input-group-addon lable" style="min-width: 60px;">确认时间</span>
									<fmt:formatDate value="${lastUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss" var="d" />
									<input class="input-item read" readonly="readonly" name="name" value="${d}">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>

	</form>
</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script src="${appRoot}/assets/app/js/myapp.validate.js" type="text/javascript"></script>
<script src="${appRoot}/assets/app/js/myapp.form.js?v=2" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        var myForm = $('#editForm').myForm();
        myForm.init();
        $('#saveJobRequire').on('click',function(e){
            var jobRequireItemArr = [];
			$("div[data-name='jobRequireItem']").each(function(i,v){
				var jobRequireItem = {};
				jobRequireItem['id'] = $(this).attr("data-id");
                jobRequireItem["position"] = {id:$(this).attr("data-position")};
                jobRequireItem["checkItem"] = $(this).attr("data-value");
                jobRequireItem["checked"] = $(this).find("input[name='checked']").is(':checked');
                jobRequireItem["parent"] = {id:$("input[name='parentId']").val()};
                jobRequireItem["seq"] = i;
                jobRequireItemArr.push(jobRequireItem);
			});
            var _thisUrl = "ec/safe/template/job/require/save";
            webUtil.ajaxData({url:_thisUrl,async:false,data:{jobRequireItems:JSON.stringify(jobRequireItemArr)},
				success:function(data){
                	if(data.statusCode=='0'){
                        webUtil.mesg('保存工作要求成功!');
                        $(parent.document).find(".layui-layer-close").trigger(e);
                    }
            	}
            });
		});
    });
</script>
</html>