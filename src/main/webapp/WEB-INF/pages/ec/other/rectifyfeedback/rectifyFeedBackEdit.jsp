<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>整改通知回复</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel">
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编号</span>
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input class="require input-item" name="name">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span>
						<input name="project" class="require input-item form-control read"
							   data-opt="{type:'f7'}" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">整改通知单</span>
						<input name="rectifyNotice" class="require input-item form-control"
							   data-opt="{type:'f7',dataChange:selectRectifyNotice,uiWin:{title:'整改通知单',height:600,width:800,url:'ec/other/rectifyNoticesF7',uiParams:getParams}}" />
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">整改要求</span>
						<textarea name="requires" class="input-item form-control" rows="3"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">是否整改完毕</span>
						<input name="isDone" class="input-item" data-opt="{type:'checkbox'}"
							type="checkbox" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">是否回复整改</span>
						<input name="isFeedBack" class="input-item" data-opt="{type:'checkbox'}"
							type="checkbox" />
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">整改完成日期</span>
						<input class="require input-item form-control" name="doneDate" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">整改截止日期</span> 
						<input class="require input-item form-control" name="endDate" data-opt="{type:'date'}">
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
	/**
	 * 一切操作前的接口函数
	 */
	function beforeAction(opt) {
		return true;
	}
    function afterAction(_opt){
        if(_opt==OperateType.addnew){
            var uiCtx = getUICtx();
            if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
                &&!webUtil.isEmpty(uiCtx.tree)){
                $('input[name="project"]').myF7().setData(uiCtx.tree);
            }
        }
    }
    function selectRectifyNotice(oldData,newData) {
		if(newData){
			$("textarea[name='requires']").val(newData.requires);
			$("input[name='endDate']").myComponet(DataType.date,{method:"setdata",opt:newData.endDate});
		}
    }
    function getParams(){
        var pro = {};
        pro.projectId = $('input[name="project"]').myF7().getValue();
        return pro;
    }
	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "整改通知回复",
			baseUrl : "ec/other/rectifyFeedBack",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
</script>
</html>