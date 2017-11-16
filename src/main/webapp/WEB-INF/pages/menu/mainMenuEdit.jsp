<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>系统菜单信息</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
<body style="padding: 5px;overflow: hidden;" class="panel">
	<div id="editPanel" >
		<div id="table-toolbar">
			<div class="btn-group"></div>
		</div>
		<form id="editForm">
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">编码</span> <input
							class="require input-item" name="number" type="text">
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span> <input name="name"
							class="input-item form-control require">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">上级菜单</span> 
						<input name="parent" class="input-item form-control read"
							data-opt="{type:'f7'}" />

					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">图标</span>
						<input name="icon" style="display: none;" class="input-item form-control">
						<div class="btn-group">
							<button class="btn btn-success tooltips" id="addIcon" type="button" data-toggle="tooltip" title="图标选择"
								style="border-right: 0px;border-radius:0px;">
								<i class="fa fa-search"></i>
							</button>
							<input style="width: 133px;" name="_icon" readonly="readonly" class="form-control">
							<button class="btn btn-success tooltips" id="clearIcon"  type="button" data-toggle="tooltip" title="清除图标">
								<i class="fa fa-trash"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">图标编码</span> <select
							name="iconCodeType"
							data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.core.enums.IconCodeType'}"
							class="form-control input-item read"></select>

					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">图标类型</span> <select
							name="iconType"
							data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.core.enums.IconType'}"
							class="form-control input-item read"></select>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">请求地址</span> <input
							name="url" class="input-item form-control" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">地址参数</span> <input
							name="params" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">打开方式</span> <select
							name="menuOpenType"
							data-opt="{type:'select',url:'base/common/combox?enum=com.myapp.core.enums.MenuOpenType'}"
							class="form-control input-item "></select>

					</div>
				</div>
				<div class="col-sm-3">
					<div class="input-group">
						<span class="input-group-addon lable">系统菜单</span> <input
							name="sysMenu" class="input-item" data-opt="{type:'checkbox'}"
							type="checkbox" />
					</div>
				</div>
				<div class="col-sm-3">
					<div class="input-group">
						<span class="input-group-addon lable">显示</span> <input
							name="onShow" class="input-item" data-opt="{type:'checkbox'}"
							type="checkbox" />
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
		</form>
	</div>
</body>
<%@include file="../base/base_edit.jsp"%>
<script type="text/javascript">
	function beforeAction(opt) {
		return true;
	}
	function afterAction(_opt) {
		if (_opt == OperateType.addnew) {
			var uiCtx = getUICtx();
			if (!webUtil.isEmpty(uiCtx) && $.isPlainObject(uiCtx)
					&& !webUtil.isEmpty(uiCtx.tree)) {
				$('input[name="parent"]').myF7().setData(uiCtx.tree);
				$('input[name="number"]').val(uiCtx.tree.number+'.');
			}
		}
		$('input[name="_icon"]').val($('input[name="icon"]').val());
		initIcon();
	}
	
	function initIcon(){
		var icon = $('input[name="icon"]').val();
		var iconHtml = $('<i></i>');
		if(!webUtil.isEmpty(icon)){
			var iconType = $('select[name="iconType"]').myComponet('select',{method:'getdata'});
			var iconCodeType = $('select[name="iconCodeType"]').myComponet('select',{method:'getdata'});
			iconHtml.addClass(iconType);
			if('UNICODE'==iconCodeType){
				iconHtml.append('&#x'+icon+';');
			}else if('CLASS'==iconCodeType){
				iconHtml.addClass(icon);
			}
		}else{
			iconHtml.append('&nbsp;&nbsp;');
		}
		$('#addIcon').html(iconHtml);
	}
	function steIconData(iconData){
		if(iconData){
			$('select[name="iconType"]').myComponet('select',{method:'setdata',opt:iconData.iconType});
			$('select[name="iconCodeType"]').myComponet('select',{method:'setdata',opt:iconData.iconCodeType});
			$('input[name="icon"]').myComponet('text',{method:'setdata',opt:iconData.icon});
			$('input[name="_icon"]').val(iconData.icon);
			initIcon();
		}
	}
	var iconUrl = app.root+"/base/web/font";
	var iconWin = {
			title:'<i class="fa fa-windows"></i>&nbsp;ICON图标选择'
		   ,url:app.root+"/base/web/font"
		   ,maxmin:false,width:700,btns:['确定']
		,btnCallBack:function(index,layerIndex,layero){
			if(layero&&index==1){
				var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
				steIconData(iframe_win.getIconData());
			}
			return true;
		}
	};
	function openIconSearch(){
		webUtil.openWin(iconWin);
	}
	
	function trashIconData(){
		$('input[name="icon"]').val('');
		$('input[name="_icon"]').val('');
	}

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "系统菜单配置",
			baseUrl : "base/home/menu",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
		
		$('#addIcon').click(function(){
			openIconSearch();
		});
		$('#clearIcon').click(function(){
			trashIconData();
		});
	})
</script>
</html>