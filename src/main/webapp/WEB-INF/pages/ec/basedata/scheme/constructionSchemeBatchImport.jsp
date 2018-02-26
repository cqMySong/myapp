<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>施工方案导入</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">

</script>
<body style="padding: 5px;padding-bottom: 0px;">
<div id="editPanel" class="myMainContent panel">
	<div id="table-toolbar">
		<div class="btn-group" style="padding-left: 5px;padding-top: 2px;padding-bottom: 2px;">
			<button class="btn btn-success" id="saveBatchImport">
				<span class="fa fa-save"></span>&nbsp;保存
			</button>
		</div>
	</div>
	<form id="editForm">
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">工程项目</span>
					<input name="project" class="require input-item form-control read"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon lable">业务状态</span>
					<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}"
							class="form-control input-item require read">
					</select>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="batchSchemeImport" class="input-entry" >
					<thead>
					<tr>
						<th data-field="schemeType" data-type="f7"
							data-editor="{mutil:true,uiWin:{title:'施工方案名称',height:550,width:780,url:'ec/basedata/schemeTypeF7'}}">施工方案名称</th>
						<th data-field="number" data-width="200" data-type="text">编号</th>
						<th data-field="name" data-type="text" data-width="240">名称</th>
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
    var batchSchemeImportEntry;
    var batchSchemeImportEntryObj;
    var prefix = "";
    var importData = {};
    function proSubItem_dataChange(oldData,newData){

    }
    function batchSchemeImport_dataChanged($cell,obj){
        if(webUtil.isEmpty(batchSchemeImportEntry)) return;
        if(obj.field=='schemeType'){
            if(!webUtil.isEmpty(batchSchemeImportEntry)){
                var schemeTypeArr = obj.rowData[obj.field];
                var schemeTypeFirst = null;
                console.log(schemeTypeArr);
                if(schemeTypeArr&&schemeTypeArr.length>0){
                    $.each(schemeTypeArr,function(i,schemeTypeObj){
                        if(i==0){
                            batchSchemeImportEntry.setTableCellValue(obj.rowIndex,'schemeType',schemeTypeObj);
                            batchSchemeImportEntry.setTableCellValue(obj.rowIndex,'name',schemeTypeObj.name);
                            batchSchemeImportEntry.setTableCellValue(obj.rowIndex,'number',prefix+schemeTypeObj.number);
                            schemeTypeFirst = schemeTypeObj;
                        }else{
                            var rowData = {name:schemeTypeObj.name,
                                number:prefix+schemeTypeObj.number,
                                schemeType:{id:schemeTypeObj.id,name:schemeTypeObj.name}};
                            batchSchemeImportEntry.insertRow(obj.rowIndex+i,rowData);
                        }
                    });
                    batchSchemeImportEntry.setTableCellValue(obj.rowIndex,'schemeType',{id:schemeTypeFirst.id,name:schemeTypeFirst.name});
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
    $(document).ready(function() {
        var height = window.outerHeight-330;
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:batchSchemeImport_dataChanged}"+
            ",toolbar:{title:'施工方案清单'}}";
        $("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "施工方案导入",billModel:1,
            baseUrl : "ec/basedata/schemeedit/batch/import",
            toolbar : "#table-toolbar",
            hasDefToolbar:false,
            form : {
                el : "#editForm"
            }
        });
        editUI.operate = "flowAudit";
        editUI.onLoad();
		var treeNode = '${uiCtx}';
        treeNode = JSON.parse(treeNode);
        $("input[name='project']").myF7().setData(treeNode.project);
        prefix = treeNode.project.number;
        batchSchemeImportEntryObj = editUI.getEntryObj('batchSchemeImport');
        if(!webUtil.isEmpty(batchSchemeImportEntryObj)) {
            batchSchemeImportEntry = batchSchemeImportEntryObj.entry;
        }
        webUtil.initMainPanel('#editPanel');
        //批量保存
        $('#saveBatchImport').on('click',function(){
            var entryData = batchSchemeImportEntry.getData();
            $.each(entryData,function(i,v){
               	v.project=$("input[name='project']").myF7().getData();
                v.billState = $("select[name='billState']").myComponet(DataType.select,{method:'getdata'});
            });
            var _thisUrl = "ec/basedata/schemeedit/batch/import";
            webUtil.ajaxData({url:_thisUrl,async:false,data:{batchImport:JSON.stringify(entryData)},
                success:function(data){
                    if(data.statusCode=='0'){
                        webUtil.mesg('导入施工方案成功!');
                        $(parent.document).find(".layui-layer-close").trigger(e);
                    }
                }
            });
		});
    });
</script>
</html>