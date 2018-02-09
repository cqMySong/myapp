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
                  	<span class="input-group-addon lable">姓名</span>
	                <input name="name" id="name" class="input-item form-control">
	              </div>
                </div>
                 <div class="col-sm-6">
                  <div class="input-group">
	                 <span class="input-group-addon lable">用户账号</span>
	                 <input class="require input-item" id="number" name="number">
	              </div>
                </div>
              </div>
              <div class="row mt10">
                <div class="col-sm-6">
                  <div class="input-group">
	                <span class="input-group-addon lable">所属公司</span>
	                <input name="defOrg" class="input-item form-control require" 
						data-opt="{type:'f7',uiWin:{title:'组织查询',height:550,width:800,url:'base/orgf7',uiParams:{orgType:'COMPANYORG'}}}" />
		
	              </div>
                </div>
                 <div class="col-sm-6">
                  <div class="input-group">
	                <span class="input-group-addon lable">联系方式</span>
	                <input type="text" class="input-item form-control" name="linkers" id="linkers" >
	              </div>
                </div>
              </div>
               <div class="row mt10">
               	<div class="col-sm-6">
                  <div class="input-group">
	                <span class="input-group-addon lable">用户状态</span>
	                <select name="userState" data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.core.enums.UserState'}" 
	                	class="form-control input-item require">
	                </select>
	              </div>
                </div>
                <div class="col-sm-3">
                  <div class="input-group">
	                <span class="input-group-addon lable">系统管理员</span>
	                <input name="admin" class="input-item" data-opt="{type:'checkbox'}" type="checkbox"/>
	              </div>
                </div>
                <div class="col-sm-3">
                  <div class="input-group">
	                <span class="input-group-addon lable">系统用户</span>
	                 <input name="sysUser" class="input-item" data-opt="{type:'radio'}" type="radio"/>
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
              <div class="row mt10">
				<div class="col-sm-12 " style="border: 1px solid #ddd;">
					<table name="positionItems" class="input-entry" data-opt="{type:'entry',height:205
						,defRowData:position_addRow,toolbar:{title:'工作岗位'}}">
						<thead>
							<tr>
								<th data-field="position" data-type="f7"
									data-editor="{uiWin:{title:'工作岗位',height:600,width:800
										,onShow:position_onShow,url:'base/positionf7',uiParams:getParams}}">
									岗位</th>
								<th data-field="main" data-type="checkbox">主要岗位</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
       </form>
	</div>
</body>
<%@include file="../base/base_edit.jsp"%>
<script src="<%=appRoot%>/assets/lib/pinyin/dict/pinyin_dict_withtone.js" type="text/javascript"></script>
<script src="<%=appRoot%>/assets/lib/pinyin/dict/pinyin_dict_polyphone.js" type="text/javascript"></script>
<script src="<%=appRoot%>/assets/lib/pinyin/pinyinUtil.js" type="text/javascript"></script>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
function beforeAction(opt){
	return true;
}
function afterAction(_opt){
	if(_opt==OperateType.addnew){
		var uiCtx = getUICtx();
		if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
				&&!webUtil.isEmpty(uiCtx.tree)){
			$('input[name="defOrg"]').myF7().setData(uiCtx.tree);
		}
	}
}
function getParams(){
	var orgId = $('input[name="defOrg"]').myF7().getValue();
	return {orgId:orgId};
}
function position_onShow(){
	var orgId = $('input[name="defOrg"]').myF7().getValue();
	var fg = true;
	if(webUtil.isEmpty(orgId)){
		webUtil.showMesg({title:'信息验证',content:'请先选择所属公司!',type:"info"});
		fg = false;
	}
	return fg;
}
function position_addRow(){
	return {main:true};
}

$(document).ready(function() {
	var editUI = $('#editPanel').editUI({title:"用户信息",baseUrl:"base/user",toolbar:"#table-toolbar",form:{el:"#editForm"}});
	editUI.onLoad();
	$('#name').on('input',function(){
		$('#number').val(pinyinUtil.getPinyin($(this).val(),'',false,true));
	});
})
</script>
</html>