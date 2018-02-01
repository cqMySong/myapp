<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>物料信息</title>
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
						<span class="input-group-addon lable">编码</span> 
						<input class="require input-item" name="number">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">名称</span>
						<input name="name" id="name" class="input-item form-control">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">规格</span>
						<input class="require input-item" name="specification">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">单位</span>
						<input name="unit" class="require input-item form-control"
							   data-opt="{type:'f7',uiWin:{title:'计量单位',height:600,width:700,url:'base/measureUnitF7'}}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon lable">类型</span>
						<select name="materialType" data-opt="{type:'select',selected:'STRUCTURE',url:'base/common/combox?enum=com.myapp.core.enums.MaterialType'}"
								class="form-control input-item require">
						</select>
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">启用</span>
						<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
					</div>
				</div>

			</div>
			<div class="row mt10">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon lable">备注</span>
						<textarea name="remark" class="input-item form-control" rows="2"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
<%@include file="../../base/base_edit.jsp"%>
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

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "物料信息",
			baseUrl : "base/material",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
        $('#name').on('input',function(){
            /*var pinyinCode = pinyinUtil.getFirstLetter($(this).val(),true);
            if(pinyinCode&&pinyinCode.length>0){
                pinyinCode = pinyinCode[0];
                if(pinyinCode.length>10){
                    pinyinCode = pinyinCode.substring(0,10);
				}
			}
            $('#pinyin').val(pinyinCode);
			*/
        });
	})
</script>
</html>