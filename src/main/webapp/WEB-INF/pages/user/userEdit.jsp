<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="editPanel" class="panel" >
			<div id="table-toolbar">
				<div class="btn-group">
					<button class="btn btn-success" type="button">
						<span class="glyphicon glyphicon-file"></span>查询
					</button>
				</div>
			</div>
            <form id="editForm">
	              <div class="row">
	                <div class="col-sm-6">
	                  <div class="input-group">
		                <span class="input-group-addon lable">用户账号</span>
		                <input class="require input-item" name="number">
		              </div>
	                </div>
	                 <div class="col-sm-6 mb15">
	                  <div class="input-group">
		                <span class="input-group-addon lable">用户实名</span>
		                <input name="name" class="input-item form-control">
		              </div>
	                </div>
	              </div>
	               <div class="row">
	               	<div class="col-sm-6">
	                  <div class="input-group">
		                <span class="input-group-addon lable">用户状态</span>
		                <select name="userState" data-opt="{type:'select',selected:'WOMAN',url:'base/common/combox?enum=com.myapp.core.enums.UserState'}" 
		                	class="form-control input-item require">
		                </select>
		              </div>
	                </div>
	                <div class="col-sm-3 mb15">
	                  <div class="input-group">
		                <span class="input-group-addon lable">系统管理员</span>
		                <input name="admin" class="input-item" data-opt="{type:'checkbox'}" type="checkbox"/>
		              </div>
	                </div>
	                <div class="col-sm-3 mb15">
	                  <div class="input-group">
		                <span class="input-group-addon lable">系统用户</span>
		                 <input name="sysUser" class="input-item" data-opt="{type:'radio'}" type="radio"/>
		              </div>
	                </div>
	               </div>
	               
	               <div class="row">
	                <div class="col-sm-6">
	                  <div class="input-group">
		                <span class="input-group-addon lable">行政部门</span>
		                <input name="defOrg" class="input-item form-control require" 
							data-opt="{type:'f7',uiWin:{title:'组织查询',height:550,width:800,url:'base/orgf7'}}" />
			
		              </div>
	                </div>
	                 <div class="col-sm-6 mb15">
	                  <div class="input-group">
		                <span class="input-group-addon lable">创建时间</span>
		                <input type="text" name="createDate" id="createtime" class="form-control input-item" 
		                	data-opt="{type:'datetime'}">
		              </div>
	                </div>
	              </div>
	              
	              <div class="row mt10" >
	                <div class="col-sm-12">
	                  <div class="input-group">
		                <span class="input-group-addon lable">备注信息</span>
		                <textarea name="remark" class="input-item form-control" rows="2"></textarea>
		              </div>
	                </div>
	              </div>
              </form>
	</div>
</body>
<%@include file="../base/base_edit.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
function beforeAction(opt){
	return true;
}

$(document).ready(function() {
	var editUI = $('#editPanel').editUI({title:"用户信息",baseUrl:"base/user",toolbar:"#table-toolbar",form:{el:"#editForm"}});
	editUI.onLoad();
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
})
</script>
</html>