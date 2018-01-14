<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>施工机具例行保养检查</title>
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
					<span class="input-group-addon lable">检查名称</span>
					<input class="input-item form-control require" name="name"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">检查单号</span>
					<input class="input-item form-control require" name="number"/>
				</div>
			</div>

			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">单位</span>
					<input class="input-item form-control require" name="maintenanceCompany"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">检查对象</span>
					<input type="text" name="dataGroupInfo" class="form-control input-item require"
						   data-opt="{type:'f7',dataChange:dataGroupInfoChange,uiWin:{title:'检查对象',height:560,width:800,url:'base/machineManitenanceObjF7'}}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">检查时间</span>
					<input type="text" name="inspectionDate" class="form-control input-item require" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">检查人</span>
					<input  name="inspector" class="form-control input-item require"/>
				</div>
			</div>
		</div>

		<div class="row mt10">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon lable">检查意见</span>
					<textarea name="inspectionOption" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon lable">整改结果</span>
					<textarea name="remedialResult" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">所属工程</span>
					<input name="project" class="require input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">创建人</span>
					<input  name="createUser" class="form-control input-item require read" data-opt="{type:'f7'}"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">创建时间</span>
					<input type="text" name="createDate" class="form-control input-item require read" data-opt="{type:'date'}">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12 " style="border: 1px solid #ddd;">
				<table name="machineMaintenanceDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="inspectionItem" data-width="100" data-locked="true">作业项目</th>
						<th data-field="description" data-locked="true">要求及说明</th>
						<th data-field="evaluationOption" data-width="300" data-type="textarea">评定意见</th>
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
    var machineMaintenanceDetailInfosEntry;
    var machineMaintenanceDetailInfosEntryObj;
    function getParams(){
        var pro = {};
        pro.projectId = $('input[name="project"]').myF7().getValue();
        return pro;
    }

    /**
     * 一切操作前的接口函数
     */
    function beforeAction(opt) {
        return true;
    }
    function machineMaintenanceDetailInfos_dataChanged($cell,obj){

    }

    /**
	 * 选择检查对象
     * @param oldVal
     * @param newVal
     */
	function dataGroupInfoChange(oldVal,newVal){
	    if(!newVal){
	        return false;
		}
		//检查目录
        webUtil.ajaxData({url:"ec/basedata/machinemaintenance/query/inspectionitem",data:{parentId:newVal.id},async:false,success:function(data){
            if(data.statusCode==0){
                $.each(data.data,function(i,val){
                   val['inspectionItem'] = val.name;
				});
                //清空表格
                machineMaintenanceDetailInfosEntry.loadData(data.data);
            }
        }});
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
        var height = window.outerHeight-500;
        var entryOption = "{type:'entry',height:"+height+",tableOpt:{editDataChanged:machineMaintenanceDetailInfos_dataChanged}"+
            ",toolbar:{title:'保养检查项目',addBtn:null,removeBtn:null}}";
        $("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "施工机具例行保养检查",billModel:1,
            baseUrl : "ec/basedata/machinemaintenance",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();

        machineMaintenanceDetailInfosEntryObj = editUI.getEntryObj('machineMaintenanceDetailInfos');
        if(!webUtil.isEmpty(machineMaintenanceDetailInfosEntryObj)){
            machineMaintenanceDetailInfosEntry = machineMaintenanceDetailInfosEntryObj.entry;
            machineMaintenanceDetailInfosEntry.resetView();
        }
        webUtil.initMainPanel('#editPanel');
    })
</script>
</html>