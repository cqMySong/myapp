<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>项目质量样板职责</title>
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
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">分部名称</span>
					<input type="text" name="branchBaseWbs" class="form-control input-item require"
						   data-opt="{type:'f7',uiWin:{title:'分部名称',height:600,width:800,url:'ec/basedata/proWbsF7'}}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">分项名称</span>
					<input type="text" name="subentry" class="form-control input-item"
						   data-opt="{type:'f7',uiWin:{title:'分项名称',height:600,width:800,url:'ec/basedata/proWbsF7'}}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">样板标准</span>
					<input type="text" name="qualityTemplateInfo" class="form-control input-item"
						   data-opt="{type:'f7',dataChange:changeQualityTemplate,uiWin:{title:'样板标准',height:600,width:800,url:'ec/basedata/qualitytemplatef7'}}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">项目样板单号</span>
					<input class="require input-item" name="number">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">项目样板名称</span>
					<input name="name" class="input-item form-control require"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">预计施工时间</span>
					<input name="expectStartDate" class="input-item form-control" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">样板验收时间</span>
					<input name="acceptanceDate" class="input-item form-control" data-opt="{type:'date'}">
				</div>
			</div>
		</div>
		<div class="row mt10" id="showJobRequire">
		</div>
		<div class="row mt10">
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
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">创建人</span>
					<input name="createUser" class="input-item form-control read" data-opt="{type:'f7'}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">创建日期</span>
					<input name="createDate" class="input-item form-control read" data-opt="{type:'datetime'}">
				</div>
			</div>
		</div>
		<div class="row mt10">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">最后修改人</span>
					<input name="lastUpdateUser" class="input-item form-control read" data-opt="{type:'f7'}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">最后修改日期</span>
					<input name="lastUpdateDate" class="input-item form-control read" data-opt="{type:'date'}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon lable">审核人</span>
					<input type="text" name="auditor" class="form-control input-item read" data-opt="{type:'f7'}"/>
				</div>
			</div>
			<div class="col-sm-3">
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
        var director = $('input[name="director"]').myF7().getData();
        if(director){
            $('input[name="directorName"]').val(director.name);
		}
        return true;
    }

	function changeQualityTemplate(oldData,newData) {
        if(newData){
            $('#showJobRequire').children().remove();
            webUtil.ajaxData({url:'ec/basedata/qualitytemplates/jobrequire',data:{qualityTemplateId:newData.id},
				async:false,success:function(data){
               	if(data.statusCode=="0"){
					$(data.data).each(function(i,v){
						var str = '<div class="col-sm-6">' +
                            		'<div class="panel panel-success">' +
                            			'<div class="panel-heading">' +
                            				'<h3 class="panel-title">'+v.positionName+'</h3>' +
                           				'</div>' +
                            			'<div class="panel-body">'+
                            				'<div class="row">';
											$(v.jobRequirement).each(function(index,jobRequirement){
                                                str+='<div data-name="jobRequireItem" class="col-sm-12 mt5">' +
                                                    '<div class="input-group">' +
                                                    '<input name="checked" class="input-item form-control disabled" style="width: 10%;"' +
                                                    '   data-opt="{type:\'checkbox\',checked:true,disabled:disabled}"  type="checkbox">' +
                                                    '<span class="input-group-addon lable" style="width: 90%;text-align: left;">'+jobRequirement+'</span>' +
                                                    '</div>' +
                                                    '</div>';
											});
                            			str+='</div>' +
										'</div>' +
                            		'</div>' +
                            	'</div>';
						$('#showJobRequire').append(str);
                        $('#showJobRequire').find('.input-item').each(function(){
                            $(this).myComponet(DataType.checkbox,{method:'init',opt:{checked:false,disabled:'disabled',type:'checkbox'}});
                        });
					});
				}
            }});
        }
    }
    function afterAction(_opt,data){
        if(_opt==OperateType.addnew){
            var uiCtx = getUICtx();
            if(!webUtil.isEmpty(uiCtx)&&$.isPlainObject(uiCtx)
                &&!webUtil.isEmpty(uiCtx.tree)){
                $('input[name="project"]').myF7().setData(uiCtx.tree);
            }
        }else{
            webUtil.ajaxData({url:'ec/quality/template/query/job',data:{proQualityTemplateId:data.data.id},
                async:false,success:function(data){
                    if(data.statusCode=="0"){
                        $(data.data).each(function(i,v){
                            var str = '<div class="col-sm-6">' +
                                '<div class="panel panel-success">' +
                                '<div class="panel-heading">' +
                                '<h3 class="panel-title">'+v.positionName+'</h3>' +
                                '</div>' +
                                '<div class="panel-body">'+
                                '<div class="row">';
                            $(v.jobRequire).each(function(index,jobRequire){
                                str+='<div data-name="jobRequireItem" class="col-sm-12 mt5">' +
                                    '<div class="input-group">' +
                                    '<input name="checked" class="input-item form-control" style="width: 10%;"' +
                                    '   data-opt="{type:\'checkbox\',checked:'+jobRequire.checked+',disabled:\'disabled\'}"  type="checkbox">' +
                                    '<span class="input-group-addon lable" style="width: 90%;text-align: left;">'+jobRequire.checkItem+'</span>' +
                                    '</div>' +
                                    '</div>';
                            });
                            str+='</div>' +
                                '</div>' +
                                '</div>' +
                                '</div>';
                            $('#showJobRequire').append(str);
                            $('#showJobRequire').find('.input-item').each(function(){
                                var opt = eval("("+$(this).attr("data-opt")+")");
                                $(this).myComponet(DataType.checkbox,{method:'init',opt:opt});
                            });
                        });
                    }
                }});
		}
    }
    $(document).ready(function() {
        editUI = $('#editPanel').editUI({
            title : "项目质量样板职责",billModel:2,
            baseUrl : "ec/quality/template",
            toolbar : "#table-toolbar",
            form : {
                el : "#editForm"
            }
        });
        editUI.onLoad();
    })
</script>
</html>