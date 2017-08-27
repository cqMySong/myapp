<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>数据字典</title>
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
						<span class="input-group-addon lable">拼音码</span>
						<input class="require input-item" name="pinyinCode" id="pinyinCode">
					</div>
				</div>
				<div class="col-sm-6 mb15">
					<div class="input-group">
						<span class="input-group-addon lable">数据类型</span>
						<select name="dataDicType" data-opt="{type:'select',selected:'STRUCTURAL_MATERIAL',url:'base/common/combox?enum=com.myapp.enums.DataDicType'}"
								class="form-control input-item require">
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
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

	$(document).ready(function() {
		var editUI = $('#editPanel').editUI({
			title : "基础字典",
			baseUrl : "ec/basedata/datadic",
			toolbar : "#table-toolbar",
			form : {
				el : "#editForm"
			}
		});
		editUI.onLoad();
        $('#name').on('input',function(){
            var pinyinCode = pinyinUtil.getFirstLetter($(this).val(),true);
            if(pinyinCode&&pinyinCode.length>0){
                pinyinCode = pinyinCode[0];
                if(pinyinCode.length>10){
                    pinyinCode = pinyinCode.substring(0,10);
				}
			}
            $('#pinyinCode').val(pinyinCode);

        });
	})
</script>
</html>