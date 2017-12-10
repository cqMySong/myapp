<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>业主付款</title>
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
					<span class="input-group-addon lable">单号</span>
					<input class="input-item form-control require" name="number"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">名称</span>
					<input class="input-item form-control require" name="name"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">报送时间</span>
					<input type="text" name="deliveryDate" class="form-control input-item require" data-opt="{type:'date'}">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">报送金额</span>
					<input type="number" name="deliveryAmount" class="form-control input-item require"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">核定金额</span>
					<input type="number" name="approvedAmount" class="form-control input-item require"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">实际支付金额</span>
					<input type="number" name="paymentAmount" class="form-control input-item require"/>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">实际支付比例</span>
					<input type="number" name="actualRatio" class="form-control input-item require read"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">约定支付比例</span>
					<input type="number" name="paymentRatio" class="form-control input-item require"/>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-addon lable">经办人</span>
					<input name="operator" class="input-item form-control require"/>
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
					<span class="input-group-addon lable">业务状态</span>
					<select name="billState" data-opt="{type:'select',selected:'ADDNEW',url:'base/common/combox?enum=com.myapp.core.enums.BillState'}"
							class="form-control input-item require read">
					</select>
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-12">
				<div class="input-group">
					<span class="input-group-addon lable">备注</span>
					<textarea name="remark" style="height:40px;" class="input-item form-control"></textarea>
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

    function parent_dataChange(oldData,newData){
		if(newData){
			$("input[name='contractAmount']").val(newData.amount);
            $("input[name='ecUnitInfo']").myF7().setData({id:newData.ecUnitInfo_id,name:newData.ecUnitInfo_name});
            $("select[name='subcontractExpenseType']").select2().val(newData.subcontractExpenseType.key).trigger("change");
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
        editUI = $('#editPanel').editUI({
            title : "工程付款",billModel:2,
            baseUrl : "ec/engineering/proprietorpayment",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();
        $("input[name='approvedAmount']").on('change',function(){
            if(!isNaN($("input[name='paymentAmount']").val())&&!isNaN($(this).val())){
                $("input[name='actualRatio']").val($("input[name='paymentAmount']").val()/$(this).val());
			}
		});
        $("input[name='paymentAmount']").on('change',function(){
            if(!isNaN($("input[name='approvedAmount']").val())&&!isNaN($(this).val())){
                $("input[name='actualRatio']").val($(this).val()/$("input[name='paymentAmount']").val());
            }
        });
    })
</script>
</html>