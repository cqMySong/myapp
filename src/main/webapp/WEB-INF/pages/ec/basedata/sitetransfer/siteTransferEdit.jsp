<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>场地移交协议</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">

</script>
<body style="padding: 5px;">
<div id="editPanel" class="panel">
	<div id="table-toolbar"></div>
	<form id="editForm" class="row">
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">移交标题</span>
				<input class="require input-item form-control" name="name">
			</div>
		</div>
		<div class="col-sm-4 col-lg-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">移交单号</span>
				<input name="number" class="require input-item form-control"/>
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">移交时间</span>
				<input type="text" name="transferDate" class="require form-control input-item" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">移交单位</span>
				<input name="transferUnit" class="require input-item form-control"/>
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">接收单位</span>
				<input name="receivingUnit" class="require input-item form-control"/>
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">移交类型</span>
				<select name="transferType" data-opt="{type:'select',selected:'UNIT',url:'base/common/combox?enum=com.myapp.core.enums.TransferTypeEnum'}"
						class="form-control input-item require">
				</select>
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">业务状态</span>
				<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}"
						class="form-control input-item require read">
				</select>
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">所属项目</span>
				<input name="project" class="require input-item form-control"
					   data-opt="{type:'f7',uiWin:{title:'工程项目',height:600,width:400,url:'ec/basedata/project'}}" />
			</div>
		</div>
		<div class="col-lg-12 col-sm-12 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">移交范围</span>
				<textarea name="transferRange" style="height:40px;" class="require input-item form-control"></textarea>
			</div>
		</div>
		<div class="col-lg-12 col-sm-12 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">场地现状</span>
				<textarea name="siteCondition" style="height:40px;" class="require input-item form-control"></textarea>
			</div>
		</div>
		<div class="col-lg-12 col-sm-12 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">接收单位职责</span>
				<textarea name="receivingDuty" style="height:40px;" class="require input-item form-control"></textarea>
			</div>
		</div>
		<div class="col-lg-12 col-sm-12 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">接收承诺事项</span>
				<textarea name="commitment" style="height:40px;" class="require input-item form-control"></textarea>
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">创建人</span>
				<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">最后修改人</span>
				<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">审核人</span>
				<input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">创建日期</span>
				<input name="createDate" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
			<div class="input-group">
				<span class="input-group-addon lable">最后修改日期</span>
				<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
			</div>
		</div>
		<div class="col-lg-4 col-sm-4 mb5">
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
            }
        }else{
            if(data&&data.other&&data.data){
                $('input[name="project"]').myF7().setData({id:data.data.belongId,
                    name:data.other,type:data.data.type});
            }
        }
    }
    $(document).ready(function() {
        editUI = $('#editPanel').editUI({
            title : "场地移交协议",billModel:2,
            baseUrl : "ec/basedata/sitetransfer",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();
    });
</script>
</html>