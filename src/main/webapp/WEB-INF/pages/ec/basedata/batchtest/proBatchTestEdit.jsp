<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目检验批划分</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;overflow:hidden;" class="panel">
	<div id="editPanel" class="panel">
		<div id="table-toolbar">
		</div>
		<form id="editForm">
			<div style="display: none;">
			</div>
			<div class="row ">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="input-item form-control read" data-opt="{type:'f7'}"/>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">分项工程</span> 
						<input name="proBaseWbs" class="input-item form-control"
						 data-opt="{type:'f7',dataChange:proBaseWbs_dataChange,uiWin:{title:'分项工程',height:560,width:800,url:'ec/basedata/proBaseWbsF7'}}" />
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" class="require input-item form-control">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">划分办法</span>
						<textarea name="content" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">划分标准</span>
						<textarea name="remark" class="input-item form-control read" rows="1"></textarea>
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

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "项目检验批划分",
			baseUrl : "ec/basedata/probatchtest",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
	})
	var proBaseWbsUrl = 'ec/basedata/batchtests/batchTestData';
	function proBaseWbs_dataChange(oldVal,newVal){
		var starand = "";
		if(!webUtil.isEmpty(newVal)&&!webUtil.isEmpty(newVal.id)){
			var dataParams = {wbsId:newVal.id};
			webUtil.ajaxData({url:proBaseWbsUrl,data:dataParams,async:false,success:function(data){
				var retData = data.data;
				if(!webUtil.isEmpty(retData)){
					starand = retData.content;
					var number = $('input[name="number"]').val();
					var name = $('input[name="name"]').val();
					var content = $('textarea[name="content"]').val();
					if(webUtil.isEmpty(number)){
						$('input[name="number"]').val(retData.number);
					}
					if(webUtil.isEmpty(name)){
						$('input[name="name"]').val(retData.name);
					}
					if(webUtil.isEmpty(content)){
						$('textarea[name="content"]').val(starand);
					}
				}
			}});
		}
		$('textarea[name="remark"]').val(starand);
		
		
	}
</script>
</html>