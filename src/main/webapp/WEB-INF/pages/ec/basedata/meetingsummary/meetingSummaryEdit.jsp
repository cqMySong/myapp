<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>会议纪要</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">

</script>
<body style="padding: 5px;">
<div id="editPanel" class="panel">
	<div id="table-toolbar"></div>
	<form id="editForm" class="row">
		<div class="col-lg-4 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">纪要名称</span>
				<input class="require input-item form-control" name="name">
			</div>
		</div>
		<div class="col-sm-6 col-lg-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">纪要编号</span>
				<input name="number" class="require input-item form-control"/>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">纪要时间</span>
				<input type="text" name="meetingDate" class="require form-control input-item" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">纪要类型</span>
				<select name="meetingSummaryType" class="require form-control input-item"
					   data-opt="{type:'select',selected:'DRAWING',url:'base/common/combox?enum=com.myapp.core.enums.MeetingSummaryType'}">
				</select>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">业务状态</span>
				<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}"
						class="form-control input-item require read">
				</select>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">纪要所属</span>
				<input name="type" class="input-item" type="hidden"/>
				<input name="project" class="require input-item form-control"
					   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:400,url:'ec/basedata/project'}}" />
			</div>
		</div>

		<div class="col-lg-12 col-sm-12 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">纪要内容</span>
				<textarea name="content" style="height:40px;" class="require input-item form-control"></textarea>
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">创建人</span>
				<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">创建日期</span>
				<input name="createDate" class="input-item form-control read" data-opt="{type:'datetime'}">
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">最后修改人</span>
				<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">最后修改日期</span>
				<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">审核人</span>
				<input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
			</div>
		</div>
		<div class="col-lg-3 col-sm-6 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">审核日期</span>
				<input name="auditDate" type="text" class="form-control input-item read" data-opt="{type:'date'}"/>
			</div>
		</div>
	</form>
</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
    var editUI;
    /**
     * 一切操作前的接口函数
     */
    function beforeAction(opt) {
        return true;
    }


    function afterAction(_opt,data){
        if(_opt==OperateType.addnew){
            var uiCtx = getUICtx();
            if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
                &&!webUtil.isEmpty(uiCtx.tree)){
                $('input[name="project"]').myF7().setData(uiCtx.tree);
                $('input[name="type"]').val(uiCtx.tree.type);
            }
        }else{
            if(data&&data.other&&data.data){
                $('input[name="project"]').myF7().setData({id:data.data.belongId,
                    name:data.other,type:data.data.type});
            }
        }
    }
    function btnCopyInsertRow(btn){
        var entry = btn.entry;
        if(!webUtil.isEmpty(entry)){
            var selRowsData = entry.getSelections();
            var rowIdx = entry.getSelectRowIndex();
            if(!webUtil.isEmpty(selRowsData)&&selRowsData.length>0
                &&rowIdx>=0){
                var rowData =  $.extend(true,{},selRowsData[0]);
                rowData.id = '';
                entry.insertRow(rowIdx+1,rowData);
            }else{
                webUtil.mesg("请先选中行");
            }
        }
    }
    $(document).ready(function() {
        editUI = $('#editPanel').editUI({
            title : "会议纪要",billModel:2,
            baseUrl : "ec/basedata/meetingsummary",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();
    });
</script>
</html>