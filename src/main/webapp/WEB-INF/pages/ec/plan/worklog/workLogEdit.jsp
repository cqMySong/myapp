<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目施工日志</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;">
		<div id="editPanel" class="myMainContent panel">
		<div id="table-toolbar"></div>
		<form id="editForm">
			<div style="display:none;">
				<input class="input-item" name="number">
				<input name="billState" class="input-item"/>
			</div>
			<div class="row">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">日志名称</span>
						<input type="text" name="name" class="input-item" >
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">日期</span>
						<input type="text" name="bizDate" class="form-control input-item" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">星期</span>
						<input name="week" class="input-item"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">气温(℃)</span>
						<input name="temperature" class="input-item"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">上午天气</span>
						<input type="text" name="amweather" class="input-item">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">下午天气</span>
						<input type="text" name="pmweather" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">工程项目</span> 
						<input name="project" class="require input-item form-control read" 
							data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">施工部位</span>
						<input name="workSite" class="input-item"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">出勤人数(人)</span>
						<input name="attendance" class="input-item"/>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">施工内容</span>
						<textarea name="wrokContent" style="height:80px;" class="input-item"></textarea>
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">设计变更</span>
						<input name="sjbg" class="input-item"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">文号</span>
						<input name="bgwh" class="input-item">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">通知单位</span>
						<input name="tzdw" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-8">
					<div class="input-group">
						<span class="input-group-addon lable">技术交底</span>
						<input name="jsjd" class="input-item"/>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">接受交底人</span>
						<input name="jsjdr" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group" style="width: 100%;">
						<span class="input-group-addon lable" style="width: 120px;">隐蔽工程验收部位</span>
						<input name="ybgcysbw" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group" style="width: 100%;">
						<span class="input-group-addon lable" style="width: 120px;">砼砂浆试块制作</span>
						<input name="sjskzz" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group" style="width: 100%;">
						<span class="input-group-addon lable" style="width: 120px;">材料进场送检情况</span>
						<input name="cljcsjqk" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">质量</span>
						<input name="zl" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">安全</span>
						<input name="aq" class="input-item">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">其他</span>
						<input name="remark" class="input-item">
					</div>
				</div>
			</div>
			
			<div class="row mt10">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">工长</span>
						<input name="gz" class="input-item"/>
					</div>
				</div>
				<div class="col-sm-4">
					
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">记录员</span>
						<input name="jly" class="input-item">
					</div>
				</div>
			</div>
			
			
			<div class="row mt10" style="display:none;">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">创建人</span> 
						<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">最后修改人</span>
						<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">审核人</span>
						 <input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
					</div>
				</div>
			</div>
			<div class="row mt10" style="display:none;">
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">创建日期</span> 
						<input name="createDate" class="input-item form-control read" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">最后修改日期</span>
						<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<span class="input-group-addon lable">审核日期</span>
						<input name="auditDate" type="text" class="form-control input-item read" data-opt="{type:'date'}"/>
					</div>
				</div>
			</div>
			
			</form>
		</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
	var editUI;
	
	function getParams(){
		var pro = {};
		pro.projectId = $('input[name="project"]').myF7().getValue();
		return pro;
	}
	/**
	 * 一切操作前的接口函数
	 */
	function beforeAction(opt) {
		return true;
	}
	
	
	function afterAction(_opt){
		if(_opt==OperateType.addnew){
			var curDate = (new Date()).format('yyyy-MM-dd');
			$('input[name="number"]').myComponet(DataType.text,{method:'setdata',opt:curDate});
			$('input[name="bizDate"]').myComponet(DataType.date,{method:'setdata',opt:curDate});
			
			var uiCtx = getUICtx();
			if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
					&&!webUtil.isEmpty(uiCtx.tree)){
				$('input[name="project"]').myF7().setData(uiCtx.tree);
				var defName = uiCtx.tree.name +'-施工日志';
				$('input[name="name"]').myComponet(DataType.text,{method:'setdata',opt:defName});
			}
		}
	}
	
	$(document).ready(function() {
		editUI = $('#editPanel').editUI({
			title : "项目工作日志",billModel:2,
			baseUrl : "ec/plan/worklog",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		var height = $(top.window).height()-120;
		$('#editPanel').height(height);
	})
</script>
</html>