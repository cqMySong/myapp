<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>质量样板工作要点</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">

</script>
<body style="padding: 5px;">
<div id="editPanel" class="myMainContent panel">
	<div id="table-toolbar"></div>
	<form id="editForm">
		<div class="row">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">编码</span>
					<input class="require input-item" name="number">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">名称</span>
					<input name="name" class="input-item form-control require">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">分部</span>
					<input name="branchBaseWbs" class="input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'分部',height:560,width:800,url:'ec/basedata/proBaseWbsF7'}}">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">分项</span>
					<input name="subentry" class="input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'分项',height:560,width:800,url:'ec/basedata/proBaseWbsF7'}}">
				</div>
			</div>
			<div class="col-sm-4 mb15">
				<div class="input-group">
					<span class="input-group-addon lable">启用</span>
					<input class="require input-item" name="enabled" data-opt="{type:'checkbox'}" type="checkbox">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon lable">备注</span>
					<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="qualityTemplateDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="position" data-width="180" data-type="f7"
							data-editor="{mutil:true,uiWin:{title:'工作岗位',height:600,width:800,url:'base/positionf7'}}">岗位名称</th>
						<th data-field="jobRequirement" data-type="textarea">工作要求(回车区分要点)</th>
						<th data-field="enable" data-type="checkbox" data-width="100">是否启用</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</form>
</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
    var editUI;
    var qualityTemplateDetailInfosEntry;
    var qualityTemplateDetailInfosEntryObj;
    function qualityTemplateDetailInfos_dataChanged($cell,obj){
        if(webUtil.isEmpty(qualityTemplateDetailInfosEntry)) return;
        if(obj.field=='position'){
            if(!webUtil.isEmpty(qualityTemplateDetailInfosEntry)){
                var positionDetailArr = obj.rowData[obj.field];
                if(positionDetailArr&&positionDetailArr.length>0){
                    var positionInfoFirst = null;
                    $.each(positionDetailArr,function(i,positionInfo){
                        if(i==0){
                            qualityTemplateDetailInfosEntry.setTableCellValue(obj.rowIndex,'enable',true);
                            positionInfoFirst = positionInfo;
                        }else{
                            var rowData = {position:{id:positionInfo.id,name:positionInfo.name},
                                enable:true};
                            qualityTemplateDetailInfosEntry.insertRow(obj.rowIndex+i,rowData);
                        }
                    });
                    qualityTemplateDetailInfosEntry.setTableCellValue(obj.rowIndex,'position',
                        {id:positionInfoFirst.id,name:positionInfoFirst.name});
                }
            }
        }
    }
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
        var height = window.outerHeight-470
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:qualityTemplateDetailInfos_dataChanged}"+
            ",toolbar:{title:'工作要点明细'}}";
        $("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "质量样板工作要点",billModel:1,
            baseUrl : "ec/basedata/qualitytemplate",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        qualityTemplateDetailInfosEntryObj = editUI.getEntryObj('qualityTemplateDetailInfos');
        if(!webUtil.isEmpty(qualityTemplateDetailInfosEntryObj)){
            qualityTemplateDetailInfosEntry = qualityTemplateDetailInfosEntryObj.entry;
            var rightBtnGroup = qualityTemplateDetailInfosEntryObj.toolbar.find('.pull-right>.btn-group').myBtnGroup();
            rightBtnGroup.addBtn({entry:qualityTemplateDetailInfosEntry,css:'btn-sm',text:'复制插入',icon:"fa fa-edit",clickFun:btnCopyInsertRow});
            qualityTemplateDetailInfosEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>