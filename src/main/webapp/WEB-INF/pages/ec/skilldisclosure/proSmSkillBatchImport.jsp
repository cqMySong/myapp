<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>项目安全技术交底导入</title>
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
					<select name="skillType" data-opt="{type:'select',selected:'SM',url:'base/common/combox?enum=com.myapp.enums.ec.SkillType'}" class="form-control input-item require read">
					</select>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="batchSmSkillImport" class="input-entry" >
					<thead>
						<tr>
							<th data-field="skillItem" data-type="f7"
								data-editor="{mutil:true,uiWin:{title:'安全技术交底名称',height:600,width:800,url:'ec/basedata/skillitemf7',uiParams:{type:'SM'}}}">安全技术交底</th>
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
    var batchSmSkillImportEntry;
    var batchSmSkillImportEntryObj;
    var prefix = "";
    var importData = {};
    function proSubItem_dataChange(oldData,newData){

    }
    function batchSmSkillImport_dataChanged($cell,obj){
        if(webUtil.isEmpty(batchSmSkillImportEntry)) return;
        if(obj.field=='skillItem'){
            if(!webUtil.isEmpty(batchSmSkillImportEntry)){
                var skillItemArr = obj.rowData[obj.field];
                var skillItemFirst = null;
                console.log(skillItemArr);
                if(skillItemArr&&skillItemArr.length>0){
                    $.each(skillItemArr,function(i,skillItemObj){
                        if(i==0){
                            batchSmSkillImportEntry.setTableCellValue(obj.rowIndex,'skillItem',skillItemObj);
                            batchSmSkillImportEntry.setTableCellValue(obj.rowIndex,'skillClass',
								{id:skillItemObj.skillClass_id,name:skillItemObj.skillClass_name});
                            batchSmSkillImportEntry.setTableCellValue(obj.rowIndex,'name',skillItemObj.name);
                            batchSmSkillImportEntry.setTableCellValue(obj.rowIndex,'number',prefix+skillItemObj.number);
                            skillItemFirst = skillItemObj;
                        }else{
                            var rowData = {name:skillItemObj.name,
                                number:prefix+skillItemObj.number,
								skillClass:{id:skillItemObj.skillClass_id,name:skillItemObj.skillClass_name},
                                skillItem:{id:skillItemObj.id,name:skillItemObj.name}};
                            batchSmSkillImportEntry.insertRow(obj.rowIndex+i,rowData);
                        }
                    });
                    batchSmSkillImportEntry.setTableCellValue(obj.rowIndex,'skillItem',{id:skillItemFirst.id,name:skillItemFirst.name});
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
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:batchSmSkillImport_dataChanged}"+
            ",toolbar:{title:'安全技术交底清单'}}";
        $("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "安全技术交底导入",billModel:1,
            baseUrl : "ec/skilldisclosure/prosmskill/batch/import",
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
        batchSmSkillImportEntryObj = editUI.getEntryObj('batchSmSkillImport');
        if(!webUtil.isEmpty(batchSmSkillImportEntryObj)) {
            batchSmSkillImportEntry = batchSmSkillImportEntryObj.entry;
        }
         webUtil.initMainPanel('#editPanel');
        $("select[name='skillType']").myComponet(DataType.select,{method:'setdata',opt:"SM"});
        //批量保存
        $('#saveBatchImport').on('click',function(){
            var entryData = batchSmSkillImportEntry.getData();
            $.each(entryData,function(i,v){
               	v.project=$("input[name='project']").myF7().getData();
                v.skillType = $("select[name='skillType']").myComponet(DataType.select,{method:'getdata'});
            });
            var _thisUrl = "ec/skilldisclosure/prosmskill/batch/import";
            webUtil.ajaxData({url:_thisUrl,async:false,data:{batchImport:JSON.stringify(entryData)},
                success:function(data){
                    if(data.statusCode=='0'){
                        webUtil.mesg('导入安全技术交底成功!');
                        $(parent.document).find(".layui-layer-close").trigger(e);
                    }
                }
            });
		});
    });
</script>
</html>