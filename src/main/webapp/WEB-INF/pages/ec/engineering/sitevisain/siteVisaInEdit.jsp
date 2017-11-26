<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>现场签证(收入)</title>
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
					<span class="input-group-addon lable">签证名称</span>
					<input class="input-item form-control require" name="name"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">签证单号</span>
					<input class="input-item form-control require" name="number"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">签证时间</span>
					<input type="text" name="visaDate" class="form-control input-item require" data-opt="{type:'date'}">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">支出签证</span>
					<input class="require input-item form-control" name="siteVisaOutInfo"
						   data-opt="{type:'f7',dataChange:selectVisaOut,uiWin:{title:'现场签证(支出)',height:600,width:800,url:'ec/engineering/siteVisaOutF7'}}" />
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">支出单号</span>
					<input class="input-item form-control require read" name="visaOutNumber"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">签证单位</span>
					<input class="input-item form-control require" name="visaUnit"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">计费依据</span>
					<select name="chargingBasis" data-opt="{type:'select',selected:'INCREASE_CONTRACT',url:'base/common/combox?enum=com.myapp.core.enums.ChargingBasis'}"
							class="form-control input-item require">
					</select>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">依据说明</span>
					<input class="input-item form-control" name="chargingContent"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">签证金额</span>
					<input type="number" name="amount" class="form-control input-item require"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">办理情况</span>
					<select name="handleType" data-opt="{type:'select',selected:'NOT_HANDLE',url:'base/common/combox?enum=com.myapp.core.enums.HandleType'}"
							class="form-control input-item require">
					</select>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">所属工程</span>
					<input name="project" class="require input-item form-control"
						   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:300,url:'ec/basedata/project'}}" />
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">业务状态</span>
					<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}"
							class="form-control input-item require read">
					</select>
				</div>
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

    function selectVisaOut(oldData,newData){
        if(newData){
            $("input[name='visaOutNumber']").val(newData.number);
        }
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
        editUI = $('#editPanel').editUI({
            title : "现场签证(支出)",billModel:2,
            baseUrl : "ec/engineering/sitevisain",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();
    })
</script>
</html>