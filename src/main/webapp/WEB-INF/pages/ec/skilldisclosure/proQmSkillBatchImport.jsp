<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>项目施工技术交底导入</title>
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
					<span class="input-group-addon lable">技术类别</span>
					<select name="skillType" data-opt="{type:'select',selected:'QM',url:'base/common/combox?enum=com.myapp.enums.ec.SkillType'}" class="form-control input-item require read">
					</select>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="batchQmSkillImport" class="input-entry" >
					<thead>
						<tr>
							<th data-field="skillItem" data-type="f7"
								data-editor="{mutil:true,uiWin:{title:'施工技术交底名称',height:600,width:800,url:'ec/basedata/skillitemf7',uiParams:{type:'QM'}}}">施工技术交底</th>
							<th data-field="skillClass" data-width="150" data-type="f7" data-locked="true">技术分类</th>
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
<%@include file="../../base/base_edit.jsp"%>
<script type="text/javascript">
    var editUI;
    var batchQmSkillImportEntry;
    var batchQmSkillImportEntryObj;
    var prefix = "";
    var importData = {};
    function proSubItem_dataChange(oldData,newData){

    }
    function batchQmSkillImport_dataChanged($cell,obj){
        if(webUtil.isEmpty(batchQmSkillImportEntry)) return;
        if(obj.field=='skillItem'){
            if(!webUtil.isEmpty(batchQmSkillImportEntry)){
                var skillItemArr = obj.rowData[obj.field];
                var skillItemFirst = null;
                console.log(skillItemArr);
                if(skillItemArr&&skillItemArr.length>0){
                    $.each(skillItemArr,function(i,skillItemObj){
                        if(i==0){
                            batchQmSkillImportEntry.setTableCellValue(obj.rowIndex,'skillItem',skillItemObj);
                            batchQmSkillImportEntry.setTableCellValue(obj.rowIndex,'skillClass',
								{id:skillItemObj.skillClass_id,name:skillItemObj.skillClass_name});
                            batchQmSkillImportEntry.setTableCellValue(obj.rowIndex,'name',skillItemObj.name);
                            batchQmSkillImportEntry.setTableCellValue(obj.rowIndex,'number',prefix+skillItemObj.number);
                            skillItemFirst = skillItemObj;
                        }else{
                            var rowData = {name:skillItemObj.name,
                                number:prefix+skillItemObj.number,
								skillClass:{id:skillItemObj.skillClass_id,name:skillItemObj.skillClass_name},
                                skillItem:{id:skillItemObj.id,name:skillItemObj.name}};
                            batchQmSkillImportEntry.insertRow(obj.rowIndex+i,rowData);
                        }
                    });
                    batchQmSkillImportEntry.setTableCellValue(obj.rowIndex,'skillItem',{id:skillItemFirst.id,name:skillItemFirst.name});
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
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:batchQmSkillImport_dataChanged}"+
            ",toolbar:{title:'施工技术交底清单'}}";
        $("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "施工技术交底导入",billModel:1,
            baseUrl : "ec/skilldisclosure/proqmskill/batch/import",
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
        batchQmSkillImportEntryObj = editUI.getEntryObj('batchQmSkillImport');
        if(!webUtil.isEmpty(batchQmSkillImportEntryObj)) {
            batchQmSkillImportEntry = batchQmSkillImportEntryObj.entry;
        }
         webUtil.initMainPanel('#editPanel');
        $("select[name='skillType']").myComponet(DataType.select,{method:'setdata',opt:"QM"});
        //批量保存
        $('#saveBatchImport').on('click',function(){
            var entryData = batchQmSkillImportEntry.getData();
            $.each(entryData,function(i,v){
               	v.project=$("input[name='project']").myF7().getData();
                v.skillType = $("select[name='skillType']").myComponet(DataType.select,{method:'getdata'});
            });
            var _thisUrl = "ec/skilldisclosure/proqmskill/batch/import";
            webUtil.ajaxData({url:_thisUrl,async:false,data:{batchImport:JSON.stringify(entryData)},
                success:function(data){
                    if(data.statusCode=='0'){
                        webUtil.mesg('导入施工技术交底成功!');
                        $(parent.document).find(".layui-layer-close").trigger(e);
                    }
                }
            });
		});
    });
</script>
</html>