<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>用户信息</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;" class="panel">
	<div id="editPanel">
		<div id="table-toolbar"></div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">姓名</span> <input name="name"
							id="name" class="input-item form-control">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">用户账号</span> <input
							class="require input-item" id="number" name="number">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">用户状态</span> <select
							name="userState"
							data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.core.enums.UserState'}"
							class="form-control input-item require">
						</select>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">联系方式</span> <input
							type="text" class="input-item form-control" name="linkers"
							id="linkers">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">系统管理员</span> <input
							name="admin" class="input-item" data-opt="{type:'checkbox'}"
							type="checkbox" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">系统用户</span> <input
							name="sysUser" class="input-item" data-opt="{type:'radio'}"
							type="radio" />
					</div>
				</div>
			</div>

			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注信息</span>
						<textarea name="remark" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-12 "
					style="border: 1px solid #ddd; padding: 2px;">
					<table name="positionItems" class="input-entry"
						data-opt="{type:'entry',height:200
						,tableOpt:{editDataChanged:positionItems_dataChanged}
						,addRow:positionItems_addRow,toolbar:{title:'工作岗位'}}">
						<thead>
							<tr>
								<th data-field="org" data-type="f7" data-locked="true"
									data-formatter="displayName">组织</th>
								<th data-field="position" data-type="f7" data-locked="true">岗位</th>
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
	function beforeAction(opt) {
		return true;
	}
	function afterAction(_opt) {
		if (_opt == OperateType.addnew) {
			var uiCtx = getUICtx();
			if (!webUtil.isEmpty(uiCtx) && $.isPlainObject(uiCtx)
					&& !webUtil.isEmpty(uiCtx.tree)) {
				$('input[name="defOrg"]').myF7().setData(uiCtx.tree);
			}
		}
	}
	function getParams() {
		var orgId = $('input[name="defOrg"]').myF7().getValue();
		return {orgId : orgId};
	}
	function positionItems_addRow(tbl){
		var url = webUtil.toUrl('base/positions/treeShow');
		webUtil.openWin({title:'<i class="fa fa-windows"></i>&nbsp;用户岗位选择',url:url,width:800,height:600
			,btns:['确定','取消'],maxmin:false,btnCallBack:function(index,layerIndex,layero){
				if(layero){
					var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
					var data = iframe_win.getData();
					
					var rowData = {main:true};
					var _org = {id:data.org_id,name:data.org_name,displayName:data.org_displayName};
					var _position = {id:data.id,name:data.name};
					rowData.org = _org;
					rowData.position = _position;
					
					positionItems.addRow(rowData);
				}
				return true;
			}});
	}
	function positionItems_dataChanged($cell, obj) {
		if (webUtil.isEmpty(positionItems) || webUtil.isEmpty(obj))
			return;
		var field = obj.field;
		if ('position' == field) {

		}
	}
	var positionItems;
	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "用户信息",
			baseUrl : "base/user",
			toolbar : "#table-toolbar",
			form : {el : "#editForm"}
		});
		editUI.onLoad();
		$('#name').on('input',function() {
			$('#number').val(pinyinUtil.getPinyin($(this).val(), '',false, true));
		});
		var positionItemsObj = editUI.getEntryObj('positionItems');
		if (!webUtil.isEmpty(positionItemsObj)) {
			positionItems = positionItemsObj.entry;
		}
	})
</script>
</html>