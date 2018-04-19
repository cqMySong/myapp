<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>现场签证(支出)</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">

</script>
<body style="padding: 5px;margin-bottom: 0px;" class="panel">
<div id="editPanel" class="myMainContent panel" style="margin-bottom: 2px;">
	<div id="table-toolbar"></div>
	<form id="editForm">
		<div class="row">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">签证名称</span>
					<input class="input-item form-control require" name="name"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">签证单号</span>
					<input class="input-item form-control require" name="number"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">签证时间</span>
					<input type="text" name="visaDate" class="form-control input-item require" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">签证单位</span>
					<input class="input-item form-control require" name="visaUnit"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">工作部位</span>
					<input  name="workPart" class="form-control input-item require"/>
				</div>
			</div>
			<div class="col-sm-9">
				<div class="input-group">
					<span class="input-group-addon lable">工作内容</span>
					<textarea name="jobContent" style="height:40px;" class="input-item form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">计费依据</span>
					<select name="chargingBasis" data-opt="{type:'select',selected:'INCREASE_CONTRACT',url:'base/common/combox?enum=com.myapp.core.enums.ChargingBasis'}"
							class="form-control input-item require">
					</select>
				</div>
			</div>
			<div class="col-sm-9">
				<div class="input-group">
					<span class="input-group-addon lable">依据说明</span>
					<input class="input-item form-control require" name="chargingContent"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">签证金额</span>
					<input type="number" name="amount" class="form-control input-item require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">所属工程</span>
					<input name="project" class="require input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
				</div>
			</div>
			<div class="col-sm-3">
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
				<table name="siteVisaOutDetailInfos" class="input-entry" >
					<thead>
					<tr>
						<th data-field="typeOfWork" data-width="100" data-type="select"
						data-editor="{type:'select',url:'base/common/combox?enum=com.myapp.core.enums.TypeOfWork'}">工种</th>
						<th data-field="workStartTime" data-type="datetime" data-width="100">工作开始时间</th>
						<th data-field="workEndTime" data-type="datetime" data-width="100">工作结束时间</th>
						<th data-field="mechanicalName" data-type="text">机械名称/型号</th>
						<th data-field="mechanicalStartTime" data-type="datetime" data-width="120">工作开始时间</th>
						<th data-field="mechanicalEndTime" data-type="datetime"  data-width="120">工作结束时间</th>
						<th data-field="materialName" data-type="text" data-width="100">材料名称</th>
						<th data-field="useCount" data-type="number" data-width="100">数量</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">创建人</span>
					<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">最后修改人</span>
					<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">审核人</span>
					<input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">创建日期</span>
					<input name="createDate" class="input-item form-control read" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">最后修改日期</span>
					<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">审核日期</span>
					<input name="auditDate" type="text" class="form-control input-item read" data-opt="{type:'date'}"/>
				</div>
			</div>
		</div>
	</form>
</div>
</body>
<%@include file="../../../base/base_edit.jsp"%>
<script type="text/javascript">
    var editUI;
    var siteVisaOutDetailInfosEntry;
    var siteVisaOutDetailInfosEntryObj;
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
    function siteVisaOutDetailInfos_dataChanged($cell,obj){

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
        var height = top.getTopMainHeight()+100;
        height = height>760?760:height;
        var winHeight = height-480;
        var entryOption = "{type:'entry',height:"+(winHeight<190?190:winHeight)+",tableOpt:{editDataChanged:siteVisaOutDetailInfos_dataChanged}"+
            ",toolbar:{title:'现场签证(支出)清单'}}";
        $("table.input-entry").attr("data-opt",entryOption);
        editUI = $('#editPanel').editUI({
            title : "现场签证(支出)",billModel:2,
            baseUrl : "ec/engineering/sitevisaout",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });

        editUI.onLoad();
        siteVisaOutDetailInfosEntryObj = editUI.getEntryObj('siteVisaOutDetailInfos');
        if(!webUtil.isEmpty(siteVisaOutDetailInfosEntryObj)){
            siteVisaOutDetailInfosEntry = siteVisaOutDetailInfosEntryObj.entry;
            siteVisaOutDetailInfosEntry.resetView();
        }
        //webUtil.initMainPanel('#editPanel');
        //$('#editPanel').height(100);
    })
</script>
</html>